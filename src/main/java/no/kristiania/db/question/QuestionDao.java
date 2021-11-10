package no.kristiania.db.question;

import no.kristiania.http.AbstractDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends AbstractDao<Question> {


    public QuestionDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    public List<Question> listAll() throws SQLException {
        return super.listAll("SELECT * FROM question");
    }

    @Override
    protected Question rowToObject(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setQuestionId(rs.getLong("id"));
        question.setQuestionTitle(rs.getString("question_title"));
        question.setQuestionDescription(rs.getString("question_description"));
        return question;
    }

    @Override
    protected void insertObject(Question obj, PreparedStatement insertStatement) throws SQLException {
        insertStatement.setString(1, obj.getQuestionTitle());
        insertStatement.setString(2, obj.getQuestionDescription());
    }

    public Question retrieve(long id) throws SQLException {
        return super.retrieve("SELECT * FROM question WHERE id = ?", id);
    }

    public long insert(Question question) throws SQLException {
        return insert(question, "INSERT into question (question_title, question_description) values(?, ?)");
    }


    public void saveQuestion(Question question) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into question (question_title, question_description) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, question.getQuestionTitle());
                statement.setString(2, question.getQuestionDescription());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    question.setQuestionId(rs.getLong("id"));
                }
            }
        }
    }


    private Question readFromResultSetQuestion(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setQuestionId(rs.getLong("id"));
        question.setQuestionTitle(rs.getString("question_title"));
        question.setQuestionDescription(rs.getString("question_description"));
        return question;
    }

    public List<Question> listAllQuestion() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from question order by id"
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

    /*public void editQuestion(String questionTitle, String newTitle) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE question SET question_title = ? where question_title = 'ugh'")) {
                statement.setString(1, newTitle);
                statement.setString(2, questionTitle);
                makeNewTitle(newTitle);
                statement.executeUpdate();

            }
        }
    }*/

    public void getQuestionId(String questionTitle) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {


            try (PreparedStatement statement = connection.prepareStatement("select question_id from question where question_title = ?")) {
                statement.setString(1, questionTitle);

            }

        }
    }

    public void editQuestion(Long questionTitle, String newTitle) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE question SET question_title = ? where id = ?")) {
                statement.setString(1, newTitle);
                statement.setLong(2, questionTitle);

                statement.executeUpdate();

            }
        }
    }

    public void updateQuestionTitle(String questionTitle, long id) throws SQLException {
        updateQuestionTitle(questionTitle, id, "UPDATE question set question_title = ? WHERE id = ?");
    }

    public void updateQuestionDescription(String questionDescription, long id) throws SQLException {
        updateQuestionDescription(questionDescription, id, "UPDATE question set question_description = ? WHERE id = ?");
    }

    public void editQuestionDescription(Long questionTitle, String newDescription) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE question SET question_description = ? where id = ?")) {
                statement.setString(1, newDescription);
                statement.setLong(2, questionTitle);

                statement.executeUpdate();
            }
        }
    }

    /*private void makeNewTitle(String newTitle) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE question SET question_title = ? where question_title = ?")) {
                statement.setString(1, newTitle);
                //statement.setString(2, questionTitle);

                statement.executeUpdate();
            }
        }
    }*/
}
