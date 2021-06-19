package com.konkuk.solvedac.problem.dao;

import com.konkuk.solvedac.problem.domain.Problem;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProblemDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Problem> rowMapper = (rs, rowNum) ->
        new Problem(
            rs.getLong("id"),
            rs.getInt("level"),
            rs.getString("title"),
            rs.getLong("solved_count")
        );

    public void batchInsert(List<Problem> problems) {
        final String sql = "insert into PROBLEM (id, level, title, solved_count) values (?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, problems, problems.size(), (ps, argument) -> {
            ps.setLong(1, argument.getId());
            ps.setInt(2, argument.getLevel());
            ps.setString(3, argument.getTitle());
            ps.setLong(4, argument.getSolvedCount());
        });
    }

    public void batchInsertTemp(List<Problem> problems) {
        final String sql = "insert into TEMP_PROBLEM (id, level, title, solved_count) values (?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, problems, problems.size(), (ps, argument) -> {
            ps.setLong(1, argument.getId());
            ps.setInt(2, argument.getLevel());
            ps.setString(3, argument.getTitle());
            ps.setLong(4, argument.getSolvedCount());
        });
    }

    public void batchInsert(String userId, Long groupId, List<Problem> problems) {
        final String sql = "insert into USER_PROBLEM_MAP (user_id, group_id, problem_id) values(?, ?, ?)";
        insert(userId, groupId, problems, sql);
    }

    public void batchInsertTemp(String userId, Long groupId, List<Problem> problems) {
        final String sql = "insert into TEMP_USER_PROBLEM_MAP (user_id, group_id, problem_id) values(?, ?, ?)";
        insert(userId, groupId, problems, sql);
    }

    private void insert(String userId, Long groupId, List<Problem> problems, String sql) {
        jdbcTemplate.batchUpdate(sql, problems, problems.size(), (ps, argument) -> {
            ps.setString(1, userId);
            if (Objects.isNull(groupId)) {
                ps.setNull(2, Types.BIGINT);
            } else {
                ps.setLong(2, groupId);
            }
            ps.setLong(3, argument.getId());
        });
    }

    public List<Problem> findAllProblems() {
        final String sql = "select * from PROBLEM";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Problem> findByUserId(String userId) {
        final String sql = "select * from PROBLEM P inner join "
            + "USER_PROBLEM_MAP UPM on P.id = UPM.problem_id where UPM.user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public List<Problem> findSolvedProblemByUserIdAndLevel(String userId, int level) {
        final String sql = "select * from PROBLEM where level = ? and id in "
            + "(select distinct(PROBLEM_ID) from USER_PROBLEM_MAP where user_id = ?) order by solved_count desc";
        return jdbcTemplate.query(sql, rowMapper, level, userId);
    }

    public List<Problem> findProblemByLevel(int level) {
        final String sql = "select * from PROBLEM where level = ?";
        return jdbcTemplate.query(sql, rowMapper, level);
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

    public List<Problem> findSolvedProblemByGroupIdAndLevel(Long groupId, int level) {
        final String sql = "select * from PROBLEM where level = ? and id in "
            + "(select distinct(PROBLEM_ID) from USER_PROBLEM_MAP where group_id = ?) order by solved_count desc";
        return jdbcTemplate.query(sql, rowMapper, level, groupId);
    }

    public List<Problem> findUnsolvedProblemByGroupIdAndLevel(Long groupId, int level) {
        final String sql = "select * from PROBLEM where level = ? and id not in "
            + "(select distinct(PROBLEM_ID) from USER_PROBLEM_MAP where group_id = ?) order by solved_count desc";
        return jdbcTemplate.query(sql, rowMapper, level, groupId);
    }

    public void deleteAllProblems() {
        final String sql = "truncate table PROBLEM";
        jdbcTemplate.update(sql);
    }

    public void deleteAllProblemMap() {
        final String sql = "truncate table USER_PROBLEM_MAP";
        jdbcTemplate.update(sql);
    }

    public boolean isAlreadyMappedUserProblems(String userId) {
        final String sql = "select exists(select * from USER_PROBLEM_MAP where user_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId);
    }
}
