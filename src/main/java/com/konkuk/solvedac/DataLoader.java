package com.konkuk.solvedac;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.api.application.UserInfoProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.user.application.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {
    private ProblemsProvider problemsProvider;
    private UserInfoProvider userInfoProvider;
    private ProblemService problemService;
    private UserService userService;

    public DataLoader(ProblemsProvider problemsProvider, UserInfoProvider userInfoProvider,
        ProblemService problemService, UserService userService) {
        this.problemsProvider = problemsProvider;
        this.userInfoProvider = userInfoProvider;
        this.problemService = problemService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        problemService.deleteAllProblems();
        problemService.deleteAllProblemMap();
        userService.deleteAll();

        problemService.saveProblems(problemsProvider.getAllProblems());
        userService.saveUsers(194L, userInfoProvider.getUserInfosInGroup(194L));
        userService.saveSolvedProblemsOfUsers(194L, userService.findByGroupId(194L));
    }
}
