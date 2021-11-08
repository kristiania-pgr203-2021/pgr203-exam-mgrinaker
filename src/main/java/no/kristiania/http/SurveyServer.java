package no.kristiania.http;

import no.kristiania.answer.AnswerDao;
import no.kristiania.option.OptionDao;
import no.kristiania.person.PersonDao;
import no.kristiania.profession.ProfessionDao;
import no.kristiania.question.QuestionDao;
import no.kristiania.workplace.WorkplaceDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SurveyServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {
        DataSource dataSource = createDataSource();

        PersonDao personDao = new PersonDao(dataSource);
        ProfessionDao professionDao = new ProfessionDao(dataSource);
        QuestionDao questionDao = new QuestionDao(dataSource);
        WorkplaceDao workplaceDao = new WorkplaceDao(dataSource);
        AnswerDao answerDao = new AnswerDao(dataSource);
        OptionDao optionDao = new OptionDao(dataSource);


        HttpServer httpServer = new HttpServer(1963);
        httpServer.addController(new AddQuestionController(questionDao));
        httpServer.addController(new ListQuestionController(questionDao, optionDao));
        httpServer.addController(new FileTargetController());
        httpServer.addController(new AddOptionController(optionDao));
        httpServer.addController(new ListAnswersController(answerDao));
        //httpServer.addController(new checkFileExtensionController());
        httpServer.addController(new QuestionOptionsController(questionDao));
        httpServer.addController(new AddNewAnswerController(answerDao));
        httpServer.addController(new EditSurveyController(questionDao));

        // logger.info, logger.debug, logger.error, logger.warning etc
        // {} er placeholder for parameteret httpServer.getPort()
        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr203.properties")) {
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty(
                "dataSource.url",
                "jdbc:postgresql://localhost:5432/survey_db"));
        dataSource.setUser(properties.getProperty(
                "dataSource.user",
                "survey_dbuser"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
