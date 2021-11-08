package no.kristiania.http;

import no.kristiania.answer.Answer;
import no.kristiania.answer.AnswerDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class AddNewAnswerController implements HttpController{
    private AnswerDao answerDao;

    public AddNewAnswerController(AnswerDao answerDao){
        this.answerDao = answerDao;
    }

    @Override
    public String getPath() {
        return "/api/newAnswer";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Answer answer = new Answer();

        answer.setQuestion_id(Long.parseLong(queryMap.get("1")));

        answerDao.saveAnswer(answer);
        return new HttpMessage("HTTP/1.1 200 OK", "It is done");
    }
}
