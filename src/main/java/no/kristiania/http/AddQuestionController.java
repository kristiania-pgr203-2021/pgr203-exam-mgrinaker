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
        return "/api/question";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Question question = new Question();

        question.setQuestionTitle(queryMap.get("questionTitle")); //LEGGE INN STRINGS
        question.setQuestionDescription(queryMap.get("questionDescription")); //LEGGE INN STRINGS

        // Skrive test for at inputfelt ikke er tomt!
        if (question.getQuestionTitle() == null || question.getQuestionTitle().isBlank()){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "First name must be provided"
            );
        } else if (question.getQuestionDescription() == null || question.getQuestionDescription().isBlank()){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "Last name must be provided"
            );
        } else if ((question.getQuestionTitle() == null || question.getQuestionTitle().isBlank()) &&
                    (question.getQuestionDescription() == null || question.getQuestionDescription().isBlank())){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "Both first name and last name must be provided"
            );
        }
        questionDao.saveQuestion(question);

        return new HttpMessage("HTTP/1.1 200 OK", "It is done");
    }
}
