package no.kristiania.http;

import no.kristiania.option.Option;
import no.kristiania.option.OptionDao;

import java.io.IOException;
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
        option.setOptionName(queryMap.get("optionName"));
        optionDao.saveOption(option);



        return new HttpMessage("HTTP/1.1 200 OK", "It is done");
    }
}
