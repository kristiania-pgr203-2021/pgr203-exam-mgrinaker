package no.kristiania.http;

import no.kristiania.person.PersonDao;
import no.kristiania.person.RoleDao;
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
        RoleDao roleDao = new RoleDao(dataSource);
        PersonDao personDao = new PersonDao(dataSource);

        HttpServer httpServer = new HttpServer(1962);
        httpServer.addController(new RoleOptionsController(roleDao));
        httpServer.addController(new AddPersonController(personDao));
        httpServer.addController(new ListPeopleController(personDao));
        httpServer.addController(new HelloFileTargetController());
        //httpServer.addController(new checkFileExtensionController());

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
