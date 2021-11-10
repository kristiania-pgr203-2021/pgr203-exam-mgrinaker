package no.kristiania.db.question;

import no.kristiania.TestData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionDaoTest {

    private QuestionDao dao = new QuestionDao(TestData.testDataSource());


    @Test
    void shouldRetrievedInsertedQuestion() throws SQLException {

        dao.insert(exampleQuestion());
        dao.insert(exampleQuestion());

        Question question = exampleQuestion();
        question.setQuestionId(dao.insert(question));


        assertThat(dao.retrieve(question.getQuestionId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(question);
    }


    @Test
    void shouldListAllQuestions() throws SQLException {

        Question question1 = exampleQuestion();
        dao.insert(question1);

        Question anotherQuestion = exampleQuestion();
        dao.insert(anotherQuestion);

        Question question = exampleQuestion();
        question.setQuestionId(dao.insert(question));

        assertThat(dao.listAllQuestion())
                .extracting(Question :: getQuestionTitle)
                .contains(question.getQuestionTitle(), anotherQuestion.getQuestionTitle());

    }

    public static Question exampleQuestion() {
        Question question = new Question();
        question.setQuestionTitle(TestData.pickOne("Hvordan går det?", "Har du det bra?", "Hvordan trives du?", "Hei"));
        question.setQuestionDescription(TestData.pickOne("Vi ønsker å se hvordan du har det", "Vi vil se din tilstand", "Vi mene"));
        return question;
    }
}
