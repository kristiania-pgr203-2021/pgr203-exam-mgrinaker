package no.kristiania.answer;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao {
    private final DataSource dataSource;

    public AnswerDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void saveAnswer(Answer answer) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answer (question_id, person_id, option_id) values (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setLong(1, answer.getQuestionId());
                statement.setLong(2, answer.getPersonId());
                statement.setInt(3, answer.getOptionId());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    answer.setAnswerId(rs.getLong("answer_id"));
                }

            }
        }
    }

    public Answer retrieveAnswer(long answer_id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from answer where answer_id = ?")) {
                statement.setLong(1, answer_id);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSetAnswer(rs);
                }
            }
        }
    }

    private Answer readFromResultSetAnswer(ResultSet rs) throws SQLException {
        Answer answer = new Answer();
        answer.setAnswerId(rs.getLong("answer_id"));
        answer.setQuestionId(rs.getLong("question_id"));
        answer.setPersonId(rs.getLong("person_id"));
        answer.setOptionId(rs.getInt("response"));
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
