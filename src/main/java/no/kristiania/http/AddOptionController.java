package no.kristiania.http;

import no.kristiania.question.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class AddOptionController implements HttpController {

    private final QuestionDao questionDao;

    public AddOptionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public String getPath() {
        return "/api/alternativeAnswers";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        return null;
    }
}
