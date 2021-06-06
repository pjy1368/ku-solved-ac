package com.konkuk.solvedac.problem.dao;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Problem> rowMapper = (rs, rowNum) ->
        new Problem(
            rs.getLong("id"),
            rs.getString("title")
        );

    public ProblemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsert(List<Problem> problems) {
        final String sql = "insert into PROBLEM (id, title) values(?, ?)";
        jdbcTemplate.batchUpdate(sql, problems, problems.size(), (ps, argument) -> {
            ps.setLong(1, argument.getId());
            ps.setString(2, argument.getTitle());
        });
    }

    public void batchInsert(String userId, Long groupId, List<Problem> problems) {
        final String sql = "insert into USER_PROBLEM_MAP (user_id, group_id, problem_id) values(?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, problems, problems.size(), (ps, argument) -> {
            ps.setString(1, userId);
            ps.setLong(2, groupId);
            ps.setLong(3, argument.getId());
        });
    }

    public List<Problem> findAll() {
        final String sql = "select * from PROBLEM";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Problem> findByUserId(String userId) {
        final String sql = "select * from PROBLEM P inner join "
            + "USER_PROBLEM_MAP UPM on P.id = UPM.problem_id where UPM.user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public List<Problem> findSolvedProblemByGroupId(Long groupId) {
        final String sql = "select * from PROBLEM where id in "
            + "(select distinct(PROBLEM_ID) from USER_PROBLEM_MAP where group_id = ?)";
        return jdbcTemplate.query(sql, rowMapper, groupId);
    }

    public List<Problem> findUnsolvedProblemByGroupId(Long groupId) {
        final String sql = "select * from PROBLEM where id not in "
            + "(select distinct(PROBLEM_ID) from USER_PROBLEM_MAP where group_id = ?)";
        return jdbcTemplate.query(sql, rowMapper, groupId);
    }

    public void deleteAllProblems() {
        final String sql = "truncate table PROBLEM";
        jdbcTemplate.update(sql);
    }

    public void deleteAllProblemMap() {
        final String sql = "truncate table USER_PROBLEM_MAP";
        jdbcTemplate.update(sql);
    }
}
