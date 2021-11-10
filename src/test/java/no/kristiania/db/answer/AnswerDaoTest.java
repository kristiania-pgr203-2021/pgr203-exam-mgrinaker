package no.kristiania.db.answer;

import no.kristiania.TestData;
import no.kristiania.db.daos.AnswerDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {

    private AnswerDao dao = new AnswerDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedAnswer() throws SQLException {
        Answer answer = exampleAnswer();
        dao.saveAnswer(answer);

        assertThat(dao.retrieveAnswer(answer.getAnswerId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(answer);
    }

    @Test
    void shouldListAllAnswers() throws SQLException {
        Answer answer = exampleAnswer();
        dao.saveAnswer(answer);

        Answer anotherAnswer = exampleAnswer();
        dao.saveAnswer(anotherAnswer);

        assertThat(dao.listAllAnswers())
                .extracting(Answer::getAnswerId)
                .contains(answer.getAnswerId(), anotherAnswer.getAnswerId());

    }

    private Answer exampleAnswer(){
        Answer answer = new Answer();
        answer.setQuestionId(TestData.pickOneInteger(1, 2, 3, 4, 5));
        answer.setPersonId(TestData.pickOneInteger(1, 2, 3));
        answer.setOptionId(TestData.pickOneInteger(1, 2, 3, 4, 5));

        return answer;
    }
}
