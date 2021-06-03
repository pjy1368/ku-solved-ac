package com.konkuk.solvedac;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Scheduled(fixedRate = 3000)
    public void dbUpdate() {
        // DB 저장 로직
        // TEMP_ 테이블 업데이트.
        // 이름만 바꿔치기.
    }
}
