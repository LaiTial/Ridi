package org.example.service;

import org.example.dto.CreateBookDTO;
import org.example.type.SerialStatus;

import java.util.*;

public class BookDataGenerator {

    private static final List<String> KEYWORDS = Arrays.asList(
            "퓨전판타지","현대판타지","정통판타지","게임판타지","동료/케미",
            "회귀물","빙의물","환생물","학원/아카데미","성장물","먼치킨",
            "또라이","신무협","전통무협","대체역사","아포칼립스","이세계",
            "삼국지","상태창/시스템","생존물","레이드물","경영물","연예계물",
            "재벌물","스포츠물","직업물","전쟁물","차원이동물","귀환물",
            "탑등반물","성좌물","헌터","배우","아이돌","소드마스터","군인",
            "회사원","매니저","의사/의원"
    );

    private static final Random RANDOM = new Random();
    private static final Set<String> USED_ISBNS = new HashSet<>();

    private final BookService bookService; // createBook 호출 서비스

    public BookDataGenerator(BookService bookService) {
        this.bookService = bookService;
    }

    // 13자리 랜덤 ISBN 생성 (유니크 보장)
    private String generateUniqueIsbn() {
        String isbn;
        do {
            String prefix = RANDOM.nextBoolean() ? "978" : "979";
            long rest = (long)(RANDOM.nextDouble() * 1_000_000_0000L); // 10자리
            isbn = prefix + String.format("%010d", rest);
        } while (USED_ISBNS.contains(isbn));
        USED_ISBNS.add(isbn);
        return isbn;
    }

    // 랜덤 책 데이터 생성
    public void generateRandomBooks(int count) {

        for (int i = 1; i <= count; i++) {

            // 1️⃣ 랜덤 키워드 3~10개 선택
            Collections.shuffle(KEYWORDS);
            int keywordCount = 3 + RANDOM.nextInt(8); // 3~10개
            List<String> selectedKeywords = new ArrayList<>(KEYWORDS.subList(0, keywordCount));

            // 2️⃣ DTO 생성
            CreateBookDTO dto = new CreateBookDTO();
            dto.setTitle("testBook " + i);
            dto.setCategory(1L); // 테스트용 카테고리 ID
            dto.setAuthor("대대원");
            dto.setPublisher("문피아");
            dto.setImageUrl("https://example.com/bookcover/" + i + ".jpg");
            dto.setIsbn(generateUniqueIsbn());
            dto.setDescription("자동 생성된 책 설명 " + i);
            dto.setStatus(SerialStatus.COMPLETED);
            dto.setKeywords(selectedKeywords);

            // 3️⃣ createBook 호출
            bookService.createBook(dto);
        }
    }
}
