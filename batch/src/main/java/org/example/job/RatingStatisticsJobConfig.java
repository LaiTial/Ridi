package org.example.job;

import lombok.RequiredArgsConstructor;
import org.example.entity.Book;
import org.example.entity.Rating;
import org.example.repository.BookRepository;
import org.example.repository.RatingRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Iterator;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RatingStatisticsJobConfig {

    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;

    @Bean
    public Job ratingStatisticsJob(JobRepository jobRepository, Step ratingStatisticsStep) {
        return new JobBuilder("ratingStatisticsJob", jobRepository)
                .start(ratingStatisticsStep) // Step 설정
                .build();
    }

    @Bean
    public Step ratingStatisticsStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("ratingStatisticsStep", jobRepository) // Step을 생성
                .<Book, Book>chunk(100, transactionManager) // chunk를 DB에서 100건씩x 실제로 100건씩만 x
                // 메모리를 잘라서
                .reader(reader())  // 책별로 별점 통계 조회
                .processor(processor())  // 통계 가공
                .writer(writer()) // DB 저장
                .build();
    }

    @Bean
    public ItemReader<Book> reader() {
        return new ItemReader<>() {
            private final Iterator<Book> books = bookRepository.findAll().iterator(); // 책 목록을 가져옵니다.
            // 다 읽을 시 에러 // 성능 문제
            @Override
            public Book read() {
                return books.hasNext() ? books.next() : null; // 책 하나씩 읽어옴
            }
        };
    }


    // 2가지 방법
    // query 자체를 메모리에서 잘라서 ID만 뽑아오는거.
    // limit를 걸어서 쿼리를 아예 나눠서 처리 -> 단점 : 여러번 쿼리
    // 쿼리는 한번에, 데이터 가져오는걸 쪼개서.(patchsize)
    @Bean
    public ItemProcessor<Book, Book> processor() {
        return book -> { // book 정보 한번에 가져와서 처리
            // 배치 처리 개선하는걸 1페이지로 정리해서 이력서 등록
            
            // 1. 책에 해당하는 별점 조회
            List<Rating> ratings = ratingRepository.findByBook(book);

            // 2. 별점 계산
            int totalScore = ratings.stream().mapToInt(Rating::getScore).sum();
            int count = ratings.size();

            // 3. 별점 등록
            if (count > 0) {
                book.setRating((double) totalScore / count); // 평균 별점 계산
                book.setRatingCount(count); // 별점 갯수 설정
            } else {
                book.setRating(0.0); // 별점이 없으면 0으로 설정
                book.setRatingCount(0); // 별점 갯수도 0으로 설정
            }

            return book; // 처리된 책 반환
        };
    }

    @Bean
    public ItemWriter<Book> writer() {
        // 책을 DB에 저장
        return bookRepository::saveAll;
    }
}
