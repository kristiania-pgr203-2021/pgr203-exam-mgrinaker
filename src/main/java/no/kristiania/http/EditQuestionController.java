package no.kristiania.http;

import no.kristiania.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class EditQuestionController implements HttpController{
    private QuestionDao questionDao;

    public EditQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String getPath() {
        return "/api/editQuestion";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);

        String questionTitle = queryMap.get("questionTitle");
        String newTitle = queryMap.get("newTitle");

        //questionDao.getQuestionId(questionTitle);

        questionDao.editQuestion(questionTitle, newTitle);

        return new HttpMessage("HTTP/1.1 200 OK", "It is done");
    }
}
