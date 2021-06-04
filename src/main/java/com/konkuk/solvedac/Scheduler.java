package com.konkuk.solvedac;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.api.application.UserInfoProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.user.application.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private ProblemsProvider problemsProvider;
    private UserInfoProvider userInfoProvider;
    private ProblemService problemService;
    private UserService userService;

    public Scheduler(ProblemsProvider problemsProvider, UserInfoProvider userInfoProvider,
        ProblemService problemService, UserService userService) {
        this.problemsProvider = problemsProvider;
        this.userInfoProvider = userInfoProvider;
        this.problemService = problemService;
        this.userService = userService;
    }

    @Scheduled(cron = "0  0/10  *  *  * *")
    public void dbUpdate() {
        problemService.deleteAllProblems();
        problemService.deleteAllProblemMap();
        userService.deleteAll();

        problemService.saveProblems(problemsProvider.getAllProblems());
        userService.saveUsers(194L, userInfoProvider.getUserInfosInGroup(194L));
        userService.saveSolvedProblemsOfUsers(194L, userService.findByGroupId(194L));
    }
}
