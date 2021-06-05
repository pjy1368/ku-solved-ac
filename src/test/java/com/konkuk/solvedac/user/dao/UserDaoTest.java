package com.konkuk.solvedac.user.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.konkuk.solvedac.user.domain.User;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class UserDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate, dataSource);
        userDao.insert(new User(194L, "pjy1368"));
        userDao.insert(new User(194L, "whitePiano"));
    }

    @Test
    @DisplayName("그룹 아이디로 유저 리스트를 조회한다.")
    void findByGroupId() {
        final Long groupId = 194L;
        final List<User> expected = Arrays.asList(
            new User(groupId, "pjy1368"),
            new User(groupId, "whitePiano")
        );

        assertThat(userDao.findByGroupId(groupId))
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
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