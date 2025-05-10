package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingStatisticsScheduler {

    private final JobLauncher jobLauncher;
    private final Job ratingStatisticsJob;

    // api 서버처럼 매번 떠있을때 가능
    // batch는 한번 실행하고 끝.
    // jenken처럼 등록해서 우리가 만든 배치를 실행시키도록 해야함.
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void runRatingStatisticsJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("runTime", System.currentTimeMillis()) // 매번 다르게
                    .toJobParameters();

            jobLauncher.run(ratingStatisticsJob, params);
        } catch (Exception e) {
            // 로그 찍기나 슬랙 알림 등
            e.printStackTrace();
        }
    }
}
