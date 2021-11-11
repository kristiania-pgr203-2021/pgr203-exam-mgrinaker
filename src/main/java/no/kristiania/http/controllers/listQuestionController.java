package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;
import no.kristiania.db.question.Question;
import no.kristiania.db.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class listQuestionController implements HttpController{

    private QuestionDao questionDao;

    public listQuestionController(QuestionDao questionDao){
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
        for(Question question : questionDao.listAll()){
            responseText += "<option value=" + question.getQuestionId() + ">" +question.getQuestionTitle() + ":  " + question.getQuestionDescription() + "</option>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
