package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;
import no.kristiania.db.objects.Option;
import no.kristiania.db.dao.OptionDao;
import no.kristiania.db.objects.Question;
import no.kristiania.db.dao.QuestionDao;

import java.sql.SQLException;

public class ListQuestionAndOptionController implements HttpController {
    private QuestionDao questionDao;
    private OptionDao optionDao;

    public ListQuestionAndOptionController(QuestionDao questionDao, OptionDao optionDao) {
        this.questionDao = questionDao;
        this.optionDao = optionDao;
    }

    @Override
    public String getPath() {
        return "/api/question";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String response = "";

        for (Question question : questionDao.listAll()){
            response += "<form method='POST' action='/api/newAnswer'>" +
                    "<h2><input type='hidden' checked='true' name='questionId' value='" + question.getQuestionId() + "'>" + question.getQuestionTitle() + "</h2>" +
                    question.getQuestionDescription() + "<br>";

            for (Option option : optionDao.listAll()){
                if(question.getQuestionId() == option.getQuestionId()){
                    response +=
                            "<input type='checkbox' name='optionId' value='" + option.getOptionId() + "'>" +
                            option.getOptionId() + ". " + option.getOptionName()
                    ;
                }
            }
        response += "<br><button>Add</button></form><br><br>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
