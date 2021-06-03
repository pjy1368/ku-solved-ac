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
        final List<ProblemInfoResponse> problemInfoResponses = problemDao.findAll().stream()
            .map(problem -> new ProblemInfoResponse(problem.getProblemId(), problem.getTitle()))
            .collect(Collectors.toList());
        return new ProblemInfoResponses(problemInfoResponses);
    }

    public void saveProblems(ProblemInfoResponses problemInfoResponses) {
        final List<Problem> problems = problemInfoResponses.getProblemInfoResponses().stream()
            .map(ProblemInfoResponse::toEntity)
            .collect(Collectors.toList());
        problemDao.batchInsert(problems);
    }
}
