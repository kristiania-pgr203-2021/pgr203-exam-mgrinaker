package no.kristiania.http;

import no.kristiania.question.Question;
import no.kristiania.question.QuestionDao;

import java.sql.SQLException;
import java.util.Map;

public class AddQuestionController implements HttpController {
    private QuestionDao questionDao;


    public AddQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String getPath() {
        return "/api/newQuestion";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Question question = new Question();

        question.setQuestionTitle(queryMap.get("questionTitle"));
        question.setQuestionDescription(queryMap.get("questionDescription"));

        // Skrive test for at inputfelt ikke er tomt!
        if (question.getQuestionTitle() == null || question.getQuestionTitle().isBlank()){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "Title must be provided"
            );
        } else if (question.getQuestionDescription() == null || question.getQuestionDescription().isBlank()){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "Description must be provided"
            );
        } else if ((question.getQuestionTitle() == null || question.getQuestionTitle().isBlank()) &&
                    (question.getQuestionDescription() == null || question.getQuestionDescription().isBlank())){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "Both title and description must be provided"
            );
        }
        questionDao.saveQuestion(question);

        return new HttpMessage("HTTP/1.1 303 See other", "It is done", "http://localhost:1963/addOption.html" );
    }
}
