package com.konkuk.solvedac.problem.application;

import com.konkuk.solvedac.exception.NotFoundException;
import com.konkuk.solvedac.problem.dao.ProblemDao;
import com.konkuk.solvedac.problem.domain.Problem;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import com.konkuk.solvedac.user.domain.LevelMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

    private final ProblemDao problemDao;

    public ProblemService(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    public ProblemInfoResponses findAllProblems() {
        final List<ProblemInfoResponse> problemInfoResponses = problemDao.findAllProblems().stream()
            .map(problem -> new ProblemInfoResponse(problem.getId(), problem.getLevel(),
                problem.getTitle(), problem.getSolvedCount()))
            .collect(Collectors.toList());
        return new ProblemInfoResponses(problemInfoResponses);
    }

    public void saveProblems(ProblemInfoResponses problemInfoResponses) {
        final List<Problem> problems = problemInfoResponses.getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toList());
        problemDao.batchInsert(problems);
    }

    public void saveProblemsOnTemp(ProblemInfoResponses problemInfoResponses) {
        final List<Problem> problems = problemInfoResponses.getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toList());
        problemDao.batchInsertTemp(problems);
    }

    public void saveProblems(String userId, ProblemInfoResponses problemInfoResponses) {
        saveProblems(userId, null, problemInfoResponses);
    }

    public void saveProblems(String userId, Long groupId, ProblemInfoResponses problemInfoResponses) {
        if (problemInfoResponses.getProblemInfoResponses().isEmpty()) {
            throw new NotFoundException("해당하는 유저가 존재하지 않거나, 해당 유저가 푼 문제가 없습니다.");
        }
        final List<Problem> problems = problemInfoResponses.getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toList());
        problemDao.batchInsert(userId, groupId, problems);
    }

    public void saveProblemsOnTemp(String userId, Long groupId, ProblemInfoResponses problemInfoResponses) {
        if (problemInfoResponses.getProblemInfoResponses().isEmpty()) {
            throw new NotFoundException("해당하는 유저가 존재하지 않거나, 해당 유저가 푼 문제가 없습니다.");
        }
        final List<Problem> problems = problemInfoResponses.getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toList());
        problemDao.batchInsertTemp(userId, groupId, problems);
    }

    public ProblemInfoResponses findProblemByTier(String tier) {
        final int level = LevelMapper.getLevel(tier);
        return new ProblemInfoResponses(problemDao.findProblemByLevel(level).stream()
            .map(problem -> new ProblemInfoResponse(problem.getId(), problem.getLevel(),
                problem.getTitle(), problem.getSolvedCount()))
            .collect(Collectors.toList()));
    }

    public ProblemInfoResponses findSolvedProblemByUserId(String userId) {
        return new ProblemInfoResponses(problemDao.findByUserId(userId).stream()
            .map(ProblemInfoResponse::of)
            .collect(Collectors.toList()));
    }

    public ProblemInfoResponses findSolvedProblemByGroupId(Long groupId) {
        return new ProblemInfoResponses(problemDao.findSolvedProblemByGroupId(groupId).stream()
            .map(ProblemInfoResponse::of)
            .collect(Collectors.toList()));
    }

    public ProblemInfoResponses findUnsolvedProblemByGroupId(Long groupId) {
        return new ProblemInfoResponses(problemDao.findUnsolvedProblemByGroupId(groupId).stream()
            .map(ProblemInfoResponse::of)
            .collect(Collectors.toList()));
    }

    public ProblemInfoResponses findSolvedProblemByGroupIdAndLevel(Long groupId, int level) {
        return new ProblemInfoResponses(problemDao.findSolvedProblemByGroupIdAndLevel(groupId, level).stream()
            .map(ProblemInfoResponse::of)
            .collect(Collectors.toList()));
    }

    public ProblemInfoResponses findUnsolvedProblemByGroupIdAndLevel(Long groupId, int level) {
        return new ProblemInfoResponses(problemDao.findUnsolvedProblemByGroupIdAndLevel(groupId, level).stream()
            .map(ProblemInfoResponse::of)
            .collect(Collectors.toList()));
    }

    public void deleteAllProblems() {
        problemDao.deleteAllProblems();
    }

    public void deleteAllProblemMap() {
        problemDao.deleteAllProblemMap();
    }

    public boolean isAlreadyMappedUserProblems(String userId) {
        return problemDao.isAlreadyMappedUserProblems(userId);
    }
}
