package no.kristiania.http.controllers;

import no.kristiania.http.HttpMessage;
import no.kristiania.db.option.Option;
import no.kristiania.db.option.OptionDao;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AddOptionController implements HttpController {

    private final OptionDao optionDao;

    public AddOptionController(OptionDao optionDao) {
        this.optionDao = optionDao;
    }

    @Override
    public String getPath() {
        return "/api/alternativeAnswers";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Option option = new Option();

        String questionId = URLDecoder.decode(queryMap.get("questionId"), StandardCharsets.UTF_8);
        String optionName = URLDecoder.decode(queryMap.get("optionName"), StandardCharsets.UTF_8);

        option.setQuestionId(Long.parseLong(questionId));
        option.setOptionName(optionName);
        optionDao.saveOption(option);



        return new HttpMessage("HTTP/1.1 303 See other", "It is done", "http://localhost:1963/addOption.html");
    }
}
