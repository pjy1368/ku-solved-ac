package com.konkuk.solvedac;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.api.application.UserInfoProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.user.application.UserService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("test")
public class DataLoader implements ApplicationRunner {

    private final ProblemsProvider problemsProvider;
    private final UserInfoProvider userInfoProvider;
    private final ProblemService problemService;
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long start = System.nanoTime();
        problemService.deleteAllProblems();
        problemService.deleteAllProblemMap();
        userService.deleteAll();

        problemService.saveProblems(problemsProvider.getAllProblems());
        userService.saveUsers(194, userInfoProvider.getUserInfosInGroup(194));
        userService.saveSolvedProblemsOfUsers(194, userService.findByGroupId(194));
        long end = System.nanoTime();
        System.out.printf("초기 작업 완료! 소요 시간 : %d초\n",
            TimeUnit.SECONDS.convert((end - start), TimeUnit.NANOSECONDS));
    }
}
