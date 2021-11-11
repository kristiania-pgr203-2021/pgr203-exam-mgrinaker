package no.kristiania.db.answer;

import no.kristiania.TestData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {

    private AnswerDao dao = new AnswerDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedAnswer() throws SQLException {

        Answer answer1 = exampleAnswer();
        dao.insert(answer1);

        Answer anotherAnswer = exampleAnswer();
        dao.insert(anotherAnswer);

        Answer answer = exampleAnswer();
        answer.setAnswerId(dao.insert(answer));

        assertThat(dao.retrieve(answer.getAnswerId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(answer);
    }

    @Test
    void shouldListAllAnswers() throws SQLException {
        Answer answer1 = exampleAnswer();
        dao.insert(answer1);

        Answer anotherAnswer = exampleAnswer();
        dao.insert(anotherAnswer);

        Answer answer = exampleAnswer();
        answer.setAnswerId(dao.insert(answer));

        assertThat(dao.listAll())
                .extracting(Answer::getOptionId)
                .contains(answer.getOptionId(), anotherAnswer.getOptionId());

    }

    private Answer exampleAnswer(){
        Answer answer = new Answer();
        answer.setQuestionId(TestData.pickOneInteger(1, 2, 3, 4, 5));
        answer.setPersonId(TestData.pickOneInteger(1, 2, 3));
        answer.setOptionId(TestData.pickOneInteger(1, 2, 3, 4, 5));

        return answer;
    }
}
