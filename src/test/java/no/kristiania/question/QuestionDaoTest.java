package no.kristiania.question;

import no.kristiania.TestData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionDaoTest {

    private QuestionDao dao = new QuestionDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedQuestion() throws SQLException {
        Question question = exampleQuestion();

        dao.saveQuestion(question);

        assertThat(dao.retrieveQuestion(question.getQuestion_id()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    @Test
    void shouldListAllQuestions() throws SQLException {
        Question question = exampleQuestion();
        dao.saveQuestion(question);

        Question anotherQuestion = exampleQuestion();
        dao.saveQuestion(anotherQuestion);

        assertThat(dao.listAllQuestion())
                .extracting(Question::getQuestion_id)
                .contains(question.getQuestion_id(), anotherQuestion.getQuestion_id());

    }

    private Question exampleQuestion() {
        Question question = new Question();
        question.setQuestionTitle(TestData.pickOne("Hvordan går det?", "Har du det bra?", "Hvordan trives du?", "Hei"));
        question.setQuestionDescription(TestData.pickOne("Vi ønsker å se hvordan du har det", "Vi vil se din tilstand", "Vi mene"));
        return question;
    }
}
