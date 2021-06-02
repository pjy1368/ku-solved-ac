package com.konkuk.solvedac.user.application;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.problem.domain.Problem;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.dto.UserInfoResponse;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ProblemsProvider problemsProvider;

    public UserService(ProblemsProvider problemsProvider) {
        this.problemsProvider = problemsProvider;
    }

    public ProblemInfoResponses showSolvedProblemsOfUsers(UserInfoResponses userInfosInGroup) {
        final List<String> userIds = userInfosInGroup.getUserInfoResponses().stream()
            .map(UserInfoResponse::getUserId)
            .collect(Collectors.toList());

        final Set<Problem> problems = new LinkedHashSet<>();
        for (final String id : userIds) {
            final List<Problem> result = problemsProvider.getSolvedProblems(id).getProblemInfoResponses().stream()
                .map(ProblemInfoResponse::toEntity)
                .collect(Collectors.toList());
            problems.addAll(result);
        }
        return ProblemInfoResponses.of(problems);
    }
}
