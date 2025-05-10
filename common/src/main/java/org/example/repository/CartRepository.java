package org.example.repository;

import org.example.entity.BookDetail;
import org.example.entity.Cart;
import org.example.entity.Users;
import org.example.type.BuyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 이미 해당 상품이 장바구니에 있는지 검사하는 함수
    boolean existsByUsersAndBookDetailAndBuyType(Users users, BookDetail bookDetail, BuyType buyType);

    // 사용자에 의해 장바구니에 담긴 모든 물품을 조회
    List<Cart> findByUsers(Users user);
}
