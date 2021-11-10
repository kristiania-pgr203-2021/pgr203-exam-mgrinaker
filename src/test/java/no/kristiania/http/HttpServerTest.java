package no.kristiania.http;

import no.kristiania.TestData;
import no.kristiania.db.option.OptionDao;
import no.kristiania.db.person.PersonDao;
import no.kristiania.db.question.Question;
import no.kristiania.db.question.QuestionDao;
import no.kristiania.db.question.QuestionDaoTest;
import no.kristiania.http.controllers.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class HttpServerTest {
    private HttpServer server = new HttpServer(0); // server port 0 gir tilfeldig port
    public HttpServerTest() throws IOException {
    }

    @Test
    void shouldReturn404ForUnknownRequestTarget() throws IOException {
        // InetSocketAddress
        HttpClient client= new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReturnWithRequestTargetIn404() throws IOException {
        HttpClient client= new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals("File not found: /non-existing", client.getMessageBody());
    }

    @Test
    void shouldRespondWith200forKnownRequestTarget() throws IOException {
        server.addController(new FileTargetController());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/hello");
        assertAll(
                () -> assertEquals(200, client.getStatusCode()),
        //        () -> assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type")),
                () -> assertEquals("<p>Hello world!</p>", client.getMessageBody())
        );
    }

    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        server.addController(new FileTargetController());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/hello").getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/hello").getStatusCode());
    }

    @Test
    void shouldEchoQueryParameter() throws IOException {
        server.addController(new FileTargetController());
        HttpClient client = new HttpClient(
                "localhost",
                server.getPort(),
                "/hello?firstName=Test&lastName=Persson");
        assertEquals("<p>Hello Persson, Test!</p>", client.getMessageBody());
    }

    @Test
    void shouldServeFiles() throws IOException {
        server.addController(new CheckFileExtensionController());

        String fileContent = "A file created at " + LocalTime.now();
        Files.write(Paths.get("target/test-classes/example-file.txt"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.txt");
        assertEquals(fileContent, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldReadFileFromDisk() throws IOException {
        server.addController(new CheckFileExtensionController());

        String file = "Testing read file from disk";
        File filePath = new File("src/main/resources");

        Files.writeString(new File(filePath, "file.txt").toPath(), file);

        HttpClient client = new HttpClient("localhost", server.getPort(), "/file.txt");
        assertEquals(file, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldUseFileExtensionForContentType() throws IOException {
        server.addController(new CheckFileExtensionController());
        String fileContent = "<p>Hello</p>";
        Files.write(Paths.get("target/test-classes/example-file.html"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
    }

    @Test
    void shouldListPeopleFormDatabase() throws SQLException, IOException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        OptionDao optionDao = new OptionDao(TestData.testDataSource());

        Question question1 = QuestionDaoTest.exampleQuestion();
        questionDao.insert(question1);
        Question question2 = QuestionDaoTest.exampleQuestion();
        questionDao.insert(question2);

        server.addController(new ListQuestionController(questionDao, optionDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/question");
        assertThat(client.getMessageBody())
                .contains(question1.getQuestionTitle() + ", " + question1.getQuestionDescription());
                //.contains(question1.getQuestionTitle() + ", " + question1.getQuestionDescription());

    }

    @Test
    void shouldCreateNewPerson() throws IOException, SQLException {
        PersonDao personDao = new PersonDao(TestData.testDataSource());
        //server.addController(new AddQuestionController(personDao));

        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/newPerson",
                "lastName=Persson&firsName=Test"
        );
        assertEquals(200, postClient.getStatusCode());
        assertThat(personDao.listAll())
                .anySatisfy(p -> {
                    assertThat(p.getFirstName()).isEqualTo("Test");
                    assertThat(p.getLastName()).isEqualTo("Persson");
                });
    }

    @Test
    void shouldCreateNewQuestion() throws IOException, SQLException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        server.addController(new AddQuestionController(questionDao));

        HttpPostClient postclient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/newQuestion",
                "ExampleQuestion"
        );
        assertEquals(200, postclient.getStatusCode());
        Question question = questionDao.listAll().get(0);
        assertEquals(question, "Hvordan trives du på jobb?");
    }

    @Test
    void shouldEditTitle() throws IOException, SQLException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        server.addController(new EditQuestionController(questionDao));

        HttpPostClient postclient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/editQuestion",
                "questionTitle=Question&newTitle=Lol"
        );
        assertEquals(200, postclient.getStatusCode());
        assertThat(questionDao.listAll())
                .anySatisfy(edit -> {
                    assertThat(edit.getQuestionTitle()).isEqualTo("Question");
                });
    }
}
