package com.konkuk.solvedac;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.api.application.UserInfoProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.problem.domain.Problem;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.user.application.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private final ProblemsProvider problemsProvider;
    private final UserInfoProvider userInfoProvider;
    private final ProblemService problemService;
    private final UserService userService;
    private final JdbcTemplate jdbcTemplate;

    public Scheduler(ProblemsProvider problemsProvider, UserInfoProvider userInfoProvider,
        ProblemService problemService, UserService userService,
        JdbcTemplate jdbcTemplate) {
        this.problemsProvider = problemsProvider;
        this.userInfoProvider = userInfoProvider;
        this.problemService = problemService;
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void dbUpdate() {
        System.out.println("스케줄링 시작!");
        long start = System.nanoTime();
        final List<String> sql = new ArrayList<>(Arrays.asList(
            "truncate table TEMP_PROBLEM;",
            "truncate table TEMP_USER;",
            "truncate table TEMP_USER_PROBLEM_MAP;"));
        sql.forEach(jdbcTemplate::update);
        sql.clear();

        problemService.saveProblemsOnTemp(problemsProvider.getAllProblems());
        System.out.println("전체 문제 업데이트중");
        userService.saveUsersOnTemp(194L, userInfoProvider.getUserInfosInGroup(194L));
        System.out.println("건대생 목록 업데이트중");
        userService.saveSolvedProblemsOfUsersOnTemp(194L, userService.findByGroupId(194L));
        System.out.println("건대생이 푼 문제 업데이트중");

        sql.add("alter table PROBLEM rename to TEMP2_PROBLEM");
        sql.add("alter table TEMP_PROBLEM rename to PROBLEM");
        sql.add("alter table TEMP2_PROBLEM rename to TEMP_PROBLEM");
        System.out.println("전체 문제 업데이트 완료!");

        sql.add("alter table USER rename to TEMP2_USER");
        sql.add("alter table TEMP_USER rename to USER");
        sql.add("alter table TEMP2_USER rename to TEMP_USER");
        System.out.println("건대생 목록 업데이트 완료!");

        sql.add("alter table USER_PROBLEM_MAP rename to TEMP2_USER_PROBLEM_MAP");
        sql.add("alter table TEMP_USER_PROBLEM_MAP rename to USER_PROBLEM_MAP");
        sql.add("alter table TEMP2_USER_PROBLEM_MAP rename to TEMP_USER_PROBLEM_MAP");
        System.out.println("건대생이 푼 문제 업데이트 완료!");
        sql.forEach(jdbcTemplate::update);
        System.out.println("DB 적용 완료!");
        long end = System.nanoTime();
        System.out.printf("스케줄링 작업 완료! 소요 시간: %d\n",
            TimeUnit.SECONDS.convert((end - start), TimeUnit.NANOSECONDS));
    }
}
