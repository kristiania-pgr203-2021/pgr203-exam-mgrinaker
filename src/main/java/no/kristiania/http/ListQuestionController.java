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
                    "<h2 name='questionId'>" + question.getQuestionTitle() + "</h2>" +
                    question.getQuestionDescription() + "<br>" +
                    "<form method = 'POST' action='/api/newAnswer'>";

            for (Option option : optionDao.listAllOption()){
                if(question.getQuestionId() == option.getQuestionId()){
                    response += "<input type='checkbox' name='optionId' value='" + option.getOptionId() + "'>" + option.getOptionId() + ". " + option.getOptionName()
                    ;
                }
            }
        }
        response += "<button>Add</button></form>";

        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
