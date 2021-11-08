package no.kristiania.http;

import no.kristiania.person.Person;
import no.kristiania.person.PersonDao;
import no.kristiania.question.Question;
import no.kristiania.question.QuestionDao;

import java.sql.SQLException;

public class ListPeopleController implements HttpController {
    private QuestionDao questionDao;

    public ListPeopleController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String getPath() {
        return "/api/question";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String response = "";

        for (Question question : questionDao.listAllQuestion()){
            response += "<div>" + question.getQuestionTitle() + ", " + question.getQuestionDescription() + "</div>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
