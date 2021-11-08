package no.kristiania.question;

import no.kristiania.person.Person;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {

    private final DataSource dataSource;

    public QuestionDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void saveQuestion(Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into question (questionTitle, questionDescription) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, question.getQuestionTitle());
                statement.setString(2, question.getQuestionDescription());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    question.setQuestion_id(rs.getLong("questionId"));
                }
            }
        }
    }

    public Question retrieveQuestion(long questionId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from question where questionId = ?")) {
                statement.setLong(1, questionId);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSetQuestion(rs);
                }
            }
        }
    }

    private Question readFromResultSetQuestion(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setQuestion_id(rs.getLong("questionId"));
        question.setQuestionTitle(rs.getString("questionTitle"));
        question.setQuestionDescription(rs.getString("questionDescription"));
        return question;
    }

    public List<Question> listAllQuestion() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from question"
            )) {

                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Question> result = new ArrayList<>();

                    while (rs.next()){
                        result.add(readFromResultSetQuestion(rs));
                    }

                    return result;
                }
            }
        }
    }
}
