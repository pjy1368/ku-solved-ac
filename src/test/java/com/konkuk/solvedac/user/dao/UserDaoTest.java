package com.konkuk.solvedac.user.dao;

import static com.konkuk.solvedac.user.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

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

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate);
        userDao.batchInsert(USERS);
    }

    @Test
    @DisplayName("그룹 아이디로 유저 리스트를 조회한다.")
    void findByGroupId() {
        assertThat(userDao.findByGroupId(GROUP_ID)).isEqualTo(USERS);
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