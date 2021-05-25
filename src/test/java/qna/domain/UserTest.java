package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @Autowired
    UserRepository userRepository;

    private String userId;
    private String password;
    private String name;
    private String email;

    @BeforeEach
    public void setup(){
        userId = "dolilu";
        password = "password";
        name = "이정수";
        email = "lsecret@naver.com";
    }

    @Test
    @DisplayName("유저 생성")
    public void createUser() {
        User user = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
        assertThat(user.equals(JAVAJIGI)).isTrue();
    }

    @Test
    @DisplayName("유저 저장")
    public void saveUser() {

        User user = new User(userId, password, name, email);
        User saveUser = userRepository.save(user);

        assertAll(
                () -> assertThat(saveUser.getId()).isNotNull(),
                () -> assertThat(saveUser.getUserId()).isEqualTo(userId),
                () -> assertThat(saveUser.getPassword()).isEqualTo(password),
                () -> assertThat(saveUser.getName()).isEqualTo(name),
                () -> assertThat(saveUser.getEmail()).isEqualTo(email)
        );
    }

    @Test
    @DisplayName("유저 검색")
    public void selectUser() {
        User saveUser = userRepository.save(new User(userId, password, name, email));
        User selectUser = userRepository.findByUserId(userId).orElseThrow(NotFoundException::new);

        assertThat(selectUser.equals(saveUser)).isTrue();
    }
}