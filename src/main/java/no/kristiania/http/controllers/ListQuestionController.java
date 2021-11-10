package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;
import no.kristiania.db.option.Option;
import no.kristiania.db.option.OptionDao;
import no.kristiania.db.question.Question;
import no.kristiania.db.question.QuestionDao;

import java.sql.SQLException;

public class ListQuestionController implements HttpController {
    private QuestionDao questionDao;
    private OptionDao optionDao;

    public ListQuestionController(QuestionDao questionDao, OptionDao optionDao) {
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
                    "<h2><input type='hidden' checked='true' name='questionId' value='" + question.getQuestionId() + "' +>" + question.getQuestionTitle() + "</input></h2>" +
                    question.getQuestionDescription() + "<br>";

            for (Option option : optionDao.listAll()){
                if(question.getQuestionId() == option.getQuestionId()){
                    response +=
                            "<input type='checkbox' name='optionId' value='" + option.getOptionId() + "'>" +
                            option.getOptionId() + ". " + option.getOptionName()
                    ;
                }
            }
        response += "<br><button>Add</button></form>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
