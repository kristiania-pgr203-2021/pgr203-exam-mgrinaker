package no.kristiania.http;

import no.kristiania.answer.Answer;
import no.kristiania.answer.AnswerDao;

import java.io.IOException;
import java.sql.SQLException;

public class ListAnswersController implements HttpController {

    private AnswerDao answerDao;

    public ListAnswersController(AnswerDao answerDao){
        this.answerDao = answerDao;
    }


    @Override
    public String getPath() {
        return "/api/answer";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String response = "";

        for (Answer answer : answerDao.listAllAnswers()){
            response += "<div>" + answer.getPerson_id() + ", " + answer.getQuestion_id() + ", " + answer.getResponse() + "</div>";

        }

        return new HttpMessage("Http/1.1 200 OK", response);
    }
}
