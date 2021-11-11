package no.kristiania.http.controllers;

import no.kristiania.db.objects.Answer;
import no.kristiania.db.dao.AnswerDao;
import no.kristiania.db.dao.OptionDao;
import no.kristiania.db.dao.PersonDao;
import no.kristiania.db.dao.QuestionDao;
import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public class ListAnswersController implements HttpController {

    private AnswerDao answerDao;
    private QuestionDao questionDao;
    private OptionDao optionDao;
    private PersonDao personDao;

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

        for(Answer answer : answerDao.listAll()){
           response += answer.getQuestionId() + answer.getOptionId() + answer.getPersonId();
        }
        return new HttpMessage("Http/1.1 200 OK", response);
    }
}
