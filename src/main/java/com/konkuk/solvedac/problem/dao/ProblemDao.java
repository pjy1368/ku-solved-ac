package com.konkuk.solvedac.problem.dao;

import com.konkuk.solvedac.problem.domain.Problem;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    private final RowMapper<Problem> rowMapper = (rs, rowNum) ->
        new Problem(
            rs.getLong("id"),
            rs.getLong("problem_id"),
            rs.getString("title")
        );


    public ProblemDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
            .withTableName("PROBLEM")
            .usingGeneratedKeyColumns("id");
    }

    public Problem insert(Problem problem) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(problem);
        final Long id = insertAction.executeAndReturnKey(params).longValue();
        return new Problem(id, problem.getProblemId(), problem.getTitle());
    }

    public void batchInsert(List<Problem> problems) {
        final String sql = "insert into PROBLEM (problem_id, title) values(?, ?)";
        jdbcTemplate.batchUpdate(sql, problems, problems.size(), (ps, argument) -> {
            ps.setLong(1, argument.getProblemId());
            ps.setString(2, argument.getTitle());
        });
    }

    public void batchInsert(String userId, List<Problem> problems) {
        final String sql = "insert into USER_PROBLEM_MAP (user_id, problem_id) values(?, ?)";
        jdbcTemplate.batchUpdate(sql, problems, problems.size(), (ps, argument) -> {
            ps.setString(1, userId);
            ps.setLong(2, argument.getProblemId());
        });
    }

    public List<Problem> findAll() {
        final String sql = "select * from PROBLEM";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Problem> findByUserId(String userId) {
        final String sql = "select P.id, P.problem_id, P.title from PROBLEM P inner join "
            + "USER_PROBLEM_MAP UPM on P.id = UPM.problem_id where UPM.user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }
}
