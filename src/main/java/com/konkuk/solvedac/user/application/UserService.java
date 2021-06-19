package com.konkuk.solvedac.user.application;

import com.konkuk.solvedac.api.application.ProblemsProvider;
import com.konkuk.solvedac.exception.NotFoundException;
import com.konkuk.solvedac.problem.application.ProblemService;
import com.konkuk.solvedac.problem.domain.Problem;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.dao.UserDao;
import com.konkuk.solvedac.user.domain.LevelMapper;
import com.konkuk.solvedac.user.domain.User;
import com.konkuk.solvedac.user.dto.UserInfoResponse;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ProblemsProvider problemsProvider;
    private final ProblemService problemService;
    private final UserDao userDao;

    public void saveUsers(Long groupId, UserInfoResponses userInfosInGroup) {
        if (userInfosInGroup.getUserInfoResponses().isEmpty()) {
            throw new NotFoundException("해당하는 그룹이 존재하지 않거나, 해당 그룹에 속한 유저가 없습니다.");
        }
        final List<User> users = userInfosInGroup.getUserInfoResponses().stream()
            .map(userInfoResponse -> userInfoResponse.toEntity(groupId))
            .collect(Collectors.toList());

        userDao.batchInsert(users);
    }

    public void saveUsersOnTemp(Long groupId, UserInfoResponses userInfosInGroup) {
        if (userInfosInGroup.getUserInfoResponses().isEmpty()) {
            throw new NotFoundException("해당하는 그룹이 존재하지 않거나, 해당 그룹에 속한 유저가 없습니다.");
        }
        final List<User> users = userInfosInGroup.getUserInfoResponses().stream()
            .map(userInfoResponse -> userInfoResponse.toEntity(groupId))
            .collect(Collectors.toList());

        userDao.batchInsertTemp(users);
    }

    public void saveSolvedProblemsOfUsers(Long groupId, UserInfoResponses userInfoResponses) {
        final List<String> nicknames = userInfoResponses.getUserInfoResponses().stream()
            .map(UserInfoResponse::getId)
            .collect(Collectors.toList());

        for (final String nickname : nicknames) {
            final ProblemInfoResponses solvedProblems = problemsProvider.getSolvedProblems(nickname);
            if (!solvedProblems.getProblemInfoResponses().isEmpty()) {
                problemService.saveProblems(nickname, groupId, solvedProblems);
            }
        }
    }

    public void saveSolvedProblemsOfUsersOnTemp(Long groupId, UserInfoResponses userInfoResponses) {
        final List<String> nicknames = userInfoResponses.getUserInfoResponses().stream()
            .map(UserInfoResponse::getId)
            .collect(Collectors.toList());

        for (final String nickname : nicknames) {
            final ProblemInfoResponses solvedProblems = problemsProvider.getSolvedProblems(nickname);
            if (!solvedProblems.getProblemInfoResponses().isEmpty()) {
                problemService.saveProblemsOnTemp(nickname, groupId, solvedProblems);
            }
        }
    }

    public ProblemInfoResponses showSolvedProblemsOfUsers(Long groupId) {
        if (!userDao.existsByGroupId(groupId)) {
            throw new NotFoundException("해당하는 그룹이 존재하지 않거나, 해당 그룹에 속한 유저가 없습니다.");
        }
        final Set<Problem> problems = problemService.findSolvedProblemByGroupId(groupId)
            .getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toCollection(LinkedHashSet::new));
        return ProblemInfoResponses.of(problems);
    }

    public ProblemInfoResponses showUnsolvedProblemsOfUsers(Long groupId) {
        if (!userDao.existsByGroupId(groupId)) {
            throw new NotFoundException("해당하는 그룹이 존재하지 않거나, 해당 그룹에 속한 유저가 없습니다.");
        }
        final Set<Problem> problems = problemService.findUnsolvedProblemByGroupId(groupId)
            .getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toCollection(LinkedHashSet::new));
        return ProblemInfoResponses.of(problems);
    }

    public ProblemInfoResponses showSolvedProblemsOfUsersByTier(Long groupId, String tier) {
        if (!userDao.existsByGroupId(groupId)) {
            throw new NotFoundException("해당하는 그룹이 존재하지 않거나, 해당 그룹에 속한 유저가 없습니다.");
        }
        final int level = LevelMapper.getLevel(tier);
        final Set<Problem> problems = problemService.findSolvedProblemByGroupIdAndLevel(groupId, level)
            .getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toCollection(LinkedHashSet::new));
        return ProblemInfoResponses.of(problems);
    }

    public ProblemInfoResponses showUnsolvedProblemsOfUsersByTier(Long groupId, String tier) {
        if (!userDao.existsByGroupId(groupId)) {
            throw new NotFoundException("해당하는 그룹이 존재하지 않거나, 해당 그룹에 속한 유저가 없습니다.");
        }
        final int level = LevelMapper.getLevel(tier);
        final Set<Problem> problems = problemService.findUnsolvedProblemByGroupIdAndLevel(groupId, level)
            .getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toCollection(LinkedHashSet::new));
        return ProblemInfoResponses.of(problems);
    }

    public ProblemInfoResponses findSolvedProblemByUserId(String userId) {
        if (!userDao.existsByUserId(userId)) {
            throw new NotFoundException("해당하는 유저가 존재하지 않거나, 해당 유저가 푼 문제가 없습니다.");
        }
        return problemService.findSolvedProblemByUserId(userId);
    }

    public ProblemInfoResponses findSolvedProblemByUserIdAndTier(String userId, String tier) {
        if (!userDao.existsByUserId(userId)) {
            throw new NotFoundException("해당하는 유저가 존재하지 않거나, 해당 유저가 푼 문제가 없습니다.");
        }
        return problemService.findSolvedProblemByUserIdAndLevel(userId, LevelMapper.getLevel(tier));
    }

    public UserInfoResponses findByGroupId(Long groupId) {
        if (!userDao.existsByGroupId(groupId)) {
            throw new NotFoundException("해당하는 그룹이 존재하지 않거나, 해당 그룹에 속한 유저가 없습니다.");
        }
        return new UserInfoResponses(userDao.findByGroupId(groupId).stream()
            .map(user -> new UserInfoResponse(user.getId()))
            .collect(Collectors.toList()));
    }

    public void deleteAll() {
        userDao.deleteAllUsers();
    }

    public boolean isSavedUserInGroup(Long groupId) {
        return userDao.existsByGroupId(groupId);
    }
}
