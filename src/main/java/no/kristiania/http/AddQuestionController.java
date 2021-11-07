package no.kristiania.http;

import no.kristiania.person.Person;
import no.kristiania.person.PersonDao;

import java.sql.SQLException;
import java.util.Map;

public class AddQuestionController implements HttpController {
    private PersonDao personDao;

    public AddQuestionController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public String getPath() {
        return "/api/newPerson";
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Person person = new Person();
        person.setFirstName(queryMap.get("firstName"));
        person.setLastName(queryMap.get("lastName"));

        // Skrive test for at inputfelt ikke er tomt!
        if (person.getFirstName() == null || person.getFirstName().isBlank()){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "First name must be provided"
            );
        } else if (person.getLastName() == null || person.getLastName().isBlank()){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "Last name must be provided"
            );
        } else if ((person.getFirstName() == null || person.getFirstName().isBlank()) &&
                    (person.getLastName() == null || person.getLastName().isBlank())){
            return new HttpMessage(
                    "HTTP/1.1 400 request error",
                    "Both first name and last name must be provided"
            );
        }
        personDao.save(person);

        return new HttpMessage("HTTP/1.1 200 OK", "It is done");
    }
}
