package com.konkuk.solvedac.user.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.konkuk.solvedac.user.domain.User;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:schema.sql")
class UserDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;

    final List<User> users = Arrays.asList(
        new User("pjy1368", 194L),
        new User("whitePiano", 194L)
    );

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate);
        userDao.batchInsert(users);
    }

    @Test
    @DisplayName("그룹 아이디로 유저 리스트를 조회한다.")
    void findByGroupId() {
        final Long groupId = 194L;
        assertThat(userDao.findByGroupId(groupId)).isEqualTo(users);
    }

    @Test
    @DisplayName("모든 유저 리스트를 조회한다.")
    void findAll() {
        assertThat(userDao.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("모든 유저 리스트를 삭제한다.")
    void deleteAll() {
        userDao.deleteAllUsers();
        assertThat(userDao.findAll()).hasSize(0);
    }
}