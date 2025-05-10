package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CartDTO;
import org.example.entity.BookDetail;
import org.example.entity.Cart;
import org.example.entity.Users;
import org.example.exception.ErrorCode;
import org.example.exception.RidiException;
import org.example.repository.BookDetailRepository;
import org.example.repository.CartRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final BookDetailRepository bookDetailRepository;
    private final CartRepository cartRepository;

    // 해당 상품 카트에 추가
    @Transactional
    public void addCartItem(CartDTO dto) {

        // 1. 현재 로그인한 사용자 정보 조회
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. 사용자 객체 조회
        Users user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new RidiException(ErrorCode.USER_NOT_FOUND));

        // 3. 책 조회
        BookDetail book = bookDetailRepository.findById(dto.getBookDetailId())
                .orElseThrow(() -> new RidiException(ErrorCode.BOOK_NOT_FOUND));

        // 4. 장바구니 검사
        if (cartRepository.existsByUsersAndBookDetailAndBuyType(user, book, dto.getBuyType())) {
            throw new RidiException(ErrorCode.CART_ITEM_ALREADY_EXISTS);
        }

        // 5. 카트 객체 만들어 저장
        Cart cart = Cart.builder()
                .users(user)
                .bookDetail(book)
                .buyType(dto.getBuyType())
                .build();

        cartRepository.save(cart);
    }

    // 카트에서 아이템 삭제
    @Transactional
    public void removeCartItem(Long cartId) {

        // 1. ID로 아이템 찾기
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RidiException(ErrorCode.CART_ITEM_NOT_FOUND));

        // 2. 해당 물품이 현재 로그인한 사람이 넣은게 맞는지 확인
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!cart.getUsers().getName().equals(userId)) {
            throw new RidiException(ErrorCode.ITEM_NOT_OWNED_BY_USER);
        }

        cartRepository.deleteById(cartId); // 장바구니에서 아이템 삭제
    }

    // 카트 전체 비우기
    @Transactional
    public void clearUserCart() {

        // 1. 현재 로그인한 사용자 정보 조회
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. 사용자 객체 조회
        Users user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new RidiException(ErrorCode.USER_NOT_FOUND));

        // 3. 해당 사용자의 장바구니 물품 조회
        List<Cart> cartItems = cartRepository.findByUsers(user);

        // 4. 장바구니에서 모든 물품 삭제
        if (!cartItems.isEmpty()) {
            cartRepository.deleteAll(cartItems);
        } else {
            throw new RidiException(ErrorCode.CART_EMPTY);  // 장바구니가 비어있을 경우
        }
    }

}
