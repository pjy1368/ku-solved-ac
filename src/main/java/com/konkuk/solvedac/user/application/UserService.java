package com.konkuk.solvedac.user.application;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.problem.domain.Problem;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.dao.UserDao;
import com.konkuk.solvedac.user.domain.User;
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
    private final ProblemService problemService;
    private final UserDao userDao;

    public UserService(ProblemsProvider problemsProvider, ProblemService problemService, UserDao userDao) {
        this.problemsProvider = problemsProvider;
        this.problemService = problemService;
        this.userDao = userDao;
    }

    public void saveUsers(Long groupId, UserInfoResponses userInfosInGroup) {
        final List<User> users = userInfosInGroup.getUserInfoResponses().stream()
            .map(userInfoResponse -> userInfoResponse.toEntity(groupId))
            .collect(Collectors.toList());

        userDao.batchInsert(users);
    }

    public ProblemInfoResponses showSolvedProblemsOfUsers(Long groupId, UserInfoResponses userInfoResponses) {
        final List<String> nicknames = userInfoResponses.getUserInfoResponses().stream()
            .map(UserInfoResponse::getNickname)
            .collect(Collectors.toList());

        final Set<Problem> problems = new LinkedHashSet<>();
        for (final String id : nicknames) {
            ProblemInfoResponses solvedProblems = problemsProvider.getSolvedProblems(id);
            problemService.saveProblems(id, groupId, solvedProblems);
//            final List<Problem> result = problemService.findByUserId(id).getProblemInfoResponses().stream()
//                .map(ProblemInfoResponse::toEntity)
//                .collect(Collectors.toList());
//            problems.addAll(result);
        }
        return ProblemInfoResponses.of(problems);
    }

    public ProblemInfoResponses showSolvedProblemsOfUsers2(Long groupId) {
        final Set<Problem> problems = problemService.findSolvedProblemByGroupId(groupId).getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity).collect(Collectors.toCollection(LinkedHashSet::new));
        return ProblemInfoResponses.of(problems);
    }

    public ProblemInfoResponses showUnsolvedProblemsOfUsers(Long groupId) {
        final Set<Problem> problems = problemService.findSolvedProblemByGroupId(groupId).getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity).collect(Collectors.toCollection(LinkedHashSet::new));
        return ProblemInfoResponses.of(problems);
    }

    public UserInfoResponses findByGroupId(Long groupId) {
        return new UserInfoResponses(userDao.findByGroupId(groupId).stream()
            .map(user -> new UserInfoResponse(user.getNickname()))
            .collect(Collectors.toList()));
    }
}
