package com.konkuk.solvedac.problem.application;

import com.konkuk.solvedac.exception.NotFoundException;
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

    public ProblemInfoResponses findAllProblems() {
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
        if (problemInfoResponses.getProblemInfoResponses().isEmpty()) {
            throw new NotFoundException("해당하는 유저가 존재하지 않거나, 해당 유저가 푼 문제가 없습니다.");
        }
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

    public boolean isAlreadyMappedUserProblems(String userId) {
        return problemDao.isAlreadyMappedUserProblems(userId);
    }
}
