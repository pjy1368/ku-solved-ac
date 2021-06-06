package com.konkuk.solvedac.problem.application;

import com.konkuk.solvedac.problem.dao.ProblemDao;
import com.konkuk.solvedac.problem.domain.Problem;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponse;
import com.konkuk.solvedac.problem.dto.ProblemInfoResponses;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

    private final ProblemDao problemDao;

    public ProblemService(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    public ProblemInfoResponses findAll() {
        final List<ProblemInfoResponse> problemInfoResponses = problemDao.findAllProblems().stream()
            .map(problem -> new ProblemInfoResponse(problem.getId(), problem.getTitle()))
            .collect(Collectors.toList());
        return new ProblemInfoResponses(problemInfoResponses);
    }

    public void saveProblems(ProblemInfoResponses problemInfoResponses) {
        final List<Problem> problems = problemInfoResponses.getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toList());
        problemDao.batchInsert(problems);
    }

    public void saveProblems(String userId, ProblemInfoResponses problemInfoResponses) {
        saveProblems(userId, null, problemInfoResponses);
    }

    public void saveProblems(String userId, Long groupId, ProblemInfoResponses problemInfoResponses) {
        final List<Problem> problems = problemInfoResponses.getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toList());
        problemDao.batchInsert(userId, groupId, problems);
    }

    public ProblemInfoResponses findByUserId(String userId) {
        return new ProblemInfoResponses(problemDao.findByUserId(userId).stream()
            .map(problem -> new ProblemInfoResponse(problem.getId(), problem.getTitle()))
            .collect(Collectors.toList()));
    }

    public ProblemInfoResponses findSolvedProblemByGroupId(Long groupId) {
        return new ProblemInfoResponses(problemDao.findSolvedProblemByGroupId(groupId).stream()
            .map(problem -> new ProblemInfoResponse(problem.getId(), problem.getTitle()))
            .collect(Collectors.toList()));
    }

    public ProblemInfoResponses findUnsolvedProblemByGroupId(Long groupId) {
        return new ProblemInfoResponses(problemDao.findUnsolvedProblemByGroupId(groupId).stream()
            .map(problem -> new ProblemInfoResponse(problem.getId(), problem.getTitle()))
            .collect(Collectors.toList()));
    }

    public void deleteAllProblems() {
        problemDao.deleteAllProblems();
    }

    public void deleteAllProblemMap() {
        problemDao.deleteAllProblemMap();
    }
}
