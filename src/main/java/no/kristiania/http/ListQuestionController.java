package no.kristiania.http;

import no.kristiania.option.Option;
import no.kristiania.option.OptionDao;
import no.kristiania.question.Question;
import no.kristiania.question.QuestionDao;

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

        for (Question question : questionDao.listAllQuestion()){
                    response += "<div>" +
                            "<h2 value='"+ question.getQuestionId() +"'>" + question.getQuestionTitle() + "</h2>" +
                            question.getQuestionDescription() + "<br>";

            for (Option option : optionDao.listAllOption()){
                if(question.getQuestionId() == option.getQuestionId()){
                    response += "<form action=''> <input type='checkbox' value='" + option.getOptionId() + "'>" + option.getOptionId() + ". " + option.getOptionName() + "" + "</form>"
                    ;
                }
            }
        }


        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
