package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {

    private List<Answer> answerList;
    private Answers answers;
    private Answer newAnswer;

    @BeforeEach
    public void setup() {
        answerList = new ArrayList<>();
        answerList.add(AnswerTest.A1);
        newAnswer = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents3");
        answerList.add(newAnswer);
        answers = new Answers(answerList);
    }

    @Test
    @DisplayName("답변 삭제")
    public void answerDelete() throws CannotDeleteException {
        answers.delete(UserTest.JAVAJIGI);
        assertThat(answers.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("답변 삭제 이력")
    public void answerDeleteHistory() throws CannotDeleteException {
        List<DeleteHistory> delete = answers.delete(UserTest.JAVAJIGI);

        List<DeleteHistory> deleteHistories = Arrays.asList(
                DeleteHistory.answerHistory(AnswerTest.A1.getId(), UserTest.JAVAJIGI)
                ,DeleteHistory.answerHistory(newAnswer.getId(), UserTest.JAVAJIGI));

        assertThat(delete.equals(deleteHistories)).isTrue();
    }

    @Test
    @DisplayName("다른 사람이 쓴 답변이 있을 경우")
    public void answerUserCheck(){

        answerList.add(AnswerTest.A2);

        assertThatThrownBy(
                () -> answers.delete(UserTest.JAVAJIGI)
        ).isInstanceOf(CannotDeleteException.class).hasMessageContaining("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
