package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.UnAuthorizedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @Test
    @DisplayName("유저 생성")
    public void createUser() {
        User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        assertThat(user.equals(JAVAJIGI)).isTrue();
    }

    @Test
    @DisplayName("User 수정")
    public void userUpdate(){
        User user = new User(1L, "javajigi", "password", "변경된 이름", "test@test.com");
        JAVAJIGI.update(JAVAJIGI, user);
        assertThat(JAVAJIGI.equalsNameAndEmail(user)).isTrue();
    }

    @Test
    @DisplayName("User Update ID 비교")
    public void userIdCheck(){
        assertThatThrownBy(
                () -> JAVAJIGI.update(SANJIGI, JAVAJIGI)
        ).isInstanceOf(UnAuthorizedException.class).hasMessageContaining("로그인 한 계정만 수정이 가능 합니다.");
    }

    @Test
    @DisplayName("User Update PASSWORD 비교")
    public void userPasswordCheck(){
        assertThatThrownBy(
                () -> JAVAJIGI.update(JAVAJIGI, new User("testId", "notMatchPassword", "test", "test@test.com"))
        ).isInstanceOf(UnAuthorizedException.class).hasMessageContaining("패스워드를 잘 못 입력하셨습니다.");
    }

    @Test
    @DisplayName("Guest User")
    public void isGuestUser(){
        User user = User.GUEST_USER;
        assertThat(user.isGuestUser()).isTrue();
    }
}