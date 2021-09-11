//package com.konkuk.solvedac;
//
//import com.konkuk.solvedac.api.application.ProblemsProvider;
//import com.konkuk.solvedac.api.application.UserInfoProvider;
//import com.konkuk.solvedac.problem.application.ProblemService;
//import com.konkuk.solvedac.user.application.UserService;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class Scheduler {
//
//    private final ProblemsProvider problemsProvider;
//    private final UserInfoProvider userInfoProvider;
//    private final ProblemService problemService;
//    private final UserService userService;
//    private final JdbcTemplate jdbcTemplate;
//
//    @Scheduled(cron = "0 0 0/2 * * *")
//    public void dbUpdate() {
//        long start = System.nanoTime();
//        final List<String> sql = new ArrayList<>(Arrays.asList(
//            "truncate table TEMP_PROBLEM;",
//            "truncate table TEMP_USER;",
//            "truncate table TEMP_USER_PROBLEM_MAP;"));
//        sql.forEach(jdbcTemplate::update);
//        sql.clear();
//
//        problemService.saveProblemsOnTemp(problemsProvider.getAllProblems());
//        userService.saveUsersOnTemp(194L, userInfoProvider.getUserInfosInGroup(194L));
//        userService.saveSolvedProblemsOfUsersOnTemp(194L, userService.findByGroupId(194L));
//
//        sql.add("alter table PROBLEM rename to TEMP2_PROBLEM");
//        sql.add("alter table TEMP_PROBLEM rename to PROBLEM");
//        sql.add("alter table TEMP2_PROBLEM rename to TEMP_PROBLEM");
//
//        sql.add("alter table USER rename to TEMP2_USER");
//        sql.add("alter table TEMP_USER rename to USER");
//        sql.add("alter table TEMP2_USER rename to TEMP_USER");
//
//        sql.add("alter table USER_PROBLEM_MAP rename to TEMP2_USER_PROBLEM_MAP");
//        sql.add("alter table TEMP_USER_PROBLEM_MAP rename to USER_PROBLEM_MAP");
//        sql.add("alter table TEMP2_USER_PROBLEM_MAP rename to TEMP_USER_PROBLEM_MAP");
//        sql.forEach(jdbcTemplate::update);
//        long end = System.nanoTime();
//        System.out.printf("스케줄링 작업 완료! 소요 시간: %d\n",
//            TimeUnit.SECONDS.convert((end - start), TimeUnit.NANOSECONDS));
//    }
//}
