package no.kristiania.answer;

import no.kristiania.question.Question;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {

    private AnswerDao dao = new AnswerDao(TestDataAnswer.testDataSource());

    @Test
    void shouldRetrieveSavedAnswer() throws SQLException {
        Answer answer = exampleAnswer();
        dao.saveAnswer(answer);

        assertThat(dao.retrieveAnswer(answer.getQuestion_id(), answer.getPerson_id()))
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
                .extracting(Answer::getPerson_id)
                .contains(answer.getQuestion_id(), anotherAnswer.getQuestion_id());

    }

    private Answer exampleAnswer(){
        Answer answer = new Answer();
        answer.setQuestion_id(TestDataAnswer.pickOneInteger(1, 2, 3, 4, 5));
        answer.setPerson_id(TestDataAnswer.pickOneInteger(1, 2, 3));
        answer.setResponse(TestDataAnswer.pickOneInteger(1, 2, 3, 4, 5));

        return answer;
    }
}
