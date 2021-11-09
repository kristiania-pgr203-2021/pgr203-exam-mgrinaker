package no.kristiania.http;

import no.kristiania.question.QuestionDao;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

        long questionTitle = Long.parseLong(queryMap.get("questionTitle"));
        String newTitle = URLDecoder.decode(queryMap.get("newTitle"), StandardCharsets.UTF_8);
        String newDescription = URLDecoder.decode(queryMap.get("newDescription"), StandardCharsets.UTF_8);
        //questionDao.getQuestionId(questionTitle);

        if(newTitle == ""){
            questionDao.editQuestionDescription(questionTitle, newDescription);
        }else if(newDescription == ""){
            questionDao.editQuestion(questionTitle, newTitle);
        }else {
            questionDao.editQuestion(questionTitle, newTitle);
            questionDao.editQuestionDescription(questionTitle, newDescription);
        }

        String tester = String.valueOf(questionTitle);

        return new HttpMessage("HTTP/1.1 200 OK", "A question has been edited to: " + newTitle + ": " + newDescription);
    }
}
