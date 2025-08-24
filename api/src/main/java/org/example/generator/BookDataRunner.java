package org.example.generator;

import org.example.service.BookDataGenerator;
import org.example.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*@Component
public class BookDataRunner implements CommandLineRunner {

    private final BookService bookService;

    public BookDataRunner(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        BookDataGenerator generator = new BookDataGenerator(bookService);

        int totalBooks = 1000; // 생성할 책 수
        int batchSize = 100;   // 한 번에 생성할 단위 (메모리/DB 부하 조절용)

        for (int i = 0; i < totalBooks; i += batchSize) {
            int currentBatch = Math.min(batchSize, totalBooks - i);
            generator.generateRandomBooks(currentBatch);
            System.out.println("생성 완료: " + (i + currentBatch) + "권");
        }

        System.out.println("자동 생성된 책 " + totalBooks + "권 완료!");
    }
}*/
