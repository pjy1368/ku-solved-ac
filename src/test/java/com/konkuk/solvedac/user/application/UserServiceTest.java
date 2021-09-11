package com.konkuk.solvedac.user.application;

import static com.konkuk.solvedac.user.UserFixture.GROUP_ID;
import static com.konkuk.solvedac.user.UserFixture.KON_KUK_USERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.konkuk.solvedac.user.dao.UserDao;
import com.konkuk.solvedac.user.domain.User;
import com.konkuk.solvedac.user.dto.UserInfoResponses;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Test
    @DisplayName("특정 그룹에 속한 유저 리스트를 조회한다.")
    void findByGroupId() {
        given(userDao.existsByGroupId(GROUP_ID)).willReturn(true);
        given(userDao.findByGroupId(GROUP_ID)).willReturn(KON_KUK_USERS);
        final List<User> actual = dtoToEntity(GROUP_ID, userService.findByGroupId(GROUP_ID));

        assertThat(actual).isEqualTo(KON_KUK_USERS);
        verify(userDao, times(1)).findByGroupId(GROUP_ID);
    }

    @Test
    @DisplayName("특정 그룹 ID로 저장된 유저 리스트가 있는지 확인한다.")
    void isSavedUserInGroup() {
        given(userDao.existsByGroupId(GROUP_ID)).willReturn(true);
        given(userDao.existsByGroupId(10000000)).willReturn(false);

        assertThat(userService.isSavedUserInGroup(GROUP_ID)).isTrue();
        assertThat(userService.isSavedUserInGroup(10000000)).isFalse();

        verify(userDao, times(1)).existsByGroupId(GROUP_ID);
        verify(userDao, times(1)).existsByGroupId(10000000);
    }

    private List<User> dtoToEntity(Integer groupId, UserInfoResponses dto) {
        return dto.getUserInfoResponses().stream()
            .map(userInfoResponse -> userInfoResponse.toEntity(groupId))
            .collect(Collectors.toList());
    }
}
