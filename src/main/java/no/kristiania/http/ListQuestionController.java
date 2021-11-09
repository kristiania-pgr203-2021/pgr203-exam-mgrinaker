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
            response += "<form>" +
                    "<h2><input type='checkbox' checked='true' name='questionId' value='" + question.getQuestionId() + "' +>" + question.getQuestionTitle() + "</input></h2>" +
                    question.getQuestionDescription() + "<br>";

            for (Option option : optionDao.listAllOption()){
                if(question.getQuestionId() == option.getQuestionId()){
                    response +=
                            "<input type='checkbox' name='optionId' value='" + option.getOptionId() + "'>" +
                           // "<input type='checkbox' name='questionId' value='" + option.getQuestionId() + "'>" +
                            option.getOptionId() + ". " + option.getOptionName()
                    ;
                }
            }
        response += "<br><button>Add</button></form>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
