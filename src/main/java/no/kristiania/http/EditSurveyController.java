package no.kristiania.http;

import no.kristiania.question.Question;
import no.kristiania.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class EditSurveyController implements HttpController{
    private QuestionDao questionDao;

    public EditSurveyController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String getPath() {
        return "api/editSurvey";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String response = "";

        int value = 1;
        for (Question question: questionDao.listAllQuestion()) {
            response += "<option value=>" + (value++) + ">" +
            question.getQuestionTitle() +
                    "</option>"
            ;
        }
        return new HttpMessage("HTTP/1.1 200 OK", response);
    }
}
