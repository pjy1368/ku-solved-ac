package com.konkuk.solvedac.user.dao;

import com.konkuk.solvedac.user.domain.User;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper = (rs, rowNum) ->
        new User(
            rs.getString("id"),
            rs.getInt("group_id")
        );

    public void batchInsert(List<User> users) {
        final String sql = "insert into USER (id, group_id) values(?, ?)";
        insert(users, sql);
    }

    public void batchInsertTemp(List<User> users) {
        final String sql = "insert into TEMP_USER (id, group_id) values(?, ?)";
        insert(users, sql);
    }

    private void insert(List<User> users, String sql) {
        jdbcTemplate.batchUpdate(sql, users, users.size(), (ps, argument) -> {
            ps.setString(1, argument.getId());
            if (Objects.isNull(argument.getGroupId())) {
                ps.setNull(2, Types.BIGINT);
            } else {
                ps.setLong(2, argument.getGroupId());
            }
        });
    }

    public List<User> findAll() {
        final String sql = "select * from USER";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<User> findByGroupId(Integer groupId) {
        final String sql = "select * from USER where group_id = ?";
        return jdbcTemplate.query(sql, rowMapper, groupId);
    }

    public void deleteAllUsers() {
        final String sql = "delete from USER";
        jdbcTemplate.update(sql);
    }

    public boolean existsByUserId(String userId) {
        final String sql = "select exists(select * from USER where id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, userId);
    }

    public boolean existsByGroupId(Integer groupId) {
        final String sql = "select exists(select * from USER where group_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, groupId);
    }
}
