package com.konkuk.solvedac.user.dao;

import com.konkuk.solvedac.user.domain.User;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    private final RowMapper<User> rowMapper = (rs, rowNum) ->
        new User(
            rs.getLong("id"),
            rs.getString("nickname")
        );

    public UserDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
            .withTableName("USER")
            .usingGeneratedKeyColumns("id");
    }

    public User insert(User user) {
        final SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        final Long id = insertAction.executeAndReturnKey(params).longValue();
        return new User(id, user.getGroupId(), user.getNickname());
    }

    public void batchInsert(List<User> users) {
        final String sql = "insert into USER (group_id, nickname) values(?, ?)";
        jdbcTemplate.batchUpdate(sql, users, users.size(), (ps, argument) -> {
            ps.setLong(1, argument.getGroupId());
            ps.setString(2, argument.getNickname());
        });
    }

    public List<User> findByGroupId(Long groupId) {
        final String sql = "select * from USER where group_id = ?";
        return jdbcTemplate.query(sql, rowMapper, groupId);
    }
}
