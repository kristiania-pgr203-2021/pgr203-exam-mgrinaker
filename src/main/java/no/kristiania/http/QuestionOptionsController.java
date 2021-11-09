package no.kristiania.http;

import no.kristiania.question.Question;
import no.kristiania.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class QuestionOptionsController implements HttpController{

    private QuestionDao questionDao;

    public QuestionOptionsController(QuestionDao questionDao){
        this.questionDao = questionDao;
    }

    @Override
    public String getPath() {
        return "/api/questionOptions";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String responseText = "";

        int value = 1;
        for(Question question : questionDao.listAllQuestion()){
            responseText += "<option value=" + question.getQuestionId() + ">" +question.getQuestionTitle() + ":  " + question.getQuestionDescription() + "</option>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
