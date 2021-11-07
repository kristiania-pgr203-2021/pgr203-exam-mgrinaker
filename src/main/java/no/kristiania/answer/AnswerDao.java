package no.kristiania.answer;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao {
    private final DataSource dataSource;

    public AnswerDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public static DataSource createDataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/survey_db");
        dataSource.setUser("servey_dbuser");
        dataSource.setPassword("TvsVM5wRCdh");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return dataSource;
    }

    public void saveAnswer(Answer answer) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answer (question_id, person_id, response) values (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setLong(1, answer.getQuestion_id());
                statement.setLong(2, answer.getPerson_id());
                statement.setInt(3, answer.getResponse());

                statement.executeUpdate();

            }
        }
    }

    public Answer retrieveAnswer(long question_id, long person_id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from answer where question_id = ? AND person_id = ?")) {
                statement.setLong(1, question_id);
                statement.setLong(2, person_id);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSetAnswer(rs);
                }
            }
        }
    }

    private Answer readFromResultSetAnswer(ResultSet rs) throws SQLException {
        Answer answer = new Answer();
        answer.setQuestion_id(rs.getLong("question_id"));
        answer.setPerson_id(rs.getLong("person_id"));
        answer.setResponse(rs.getInt("response"));
        return answer;
    }

    public List<Answer> listAllAnswers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from answer"
            )) {

                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Answer> result = new ArrayList<>();

                    while (rs.next()){
                        result.add(readFromResultSetAnswer(rs));
                    }

                    return result;
                }
            }
        }
    }
}
