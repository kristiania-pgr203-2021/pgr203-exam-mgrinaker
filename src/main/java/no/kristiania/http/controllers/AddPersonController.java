package no.kristiania.http.controllers;

import no.kristiania.db.person.Person;
import no.kristiania.db.person.PersonDao;
import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class AddPersonController implements HttpController {
    private PersonDao personDao;

    public AddPersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public String getPath() {
        return "/api/setCookie";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> cookieQueryMap = HttpMessage.parseRequestParameters(request.getHeader("Cookie"));
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);

        String firstName = URLDecoder.decode(cookieQueryMap.get("firstName"), StandardCharsets.UTF_8);
        String lastName = URLDecoder.decode(queryMap.get("lastName"), StandardCharsets.UTF_8);
        String email = URLDecoder.decode(queryMap.get("email"), StandardCharsets.UTF_8);
        String profession = queryMap.get("professionId");
        String workplace = queryMap.get("workplaceId");

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setMailAddress(email);
        person.setProfessionId(Long.parseLong(profession));
        person.setWorkplaceId(Long.parseLong(workplace));

        personDao.insert(person);

        return new HttpMessage("HTTP/1.1 303 See other", "WOOP", "/index.html");
    }
}
