package no.kristiania.db.option;

import no.kristiania.http.AbstractDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDao extends AbstractDao<Option> {


    public OptionDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    public List<Option> listAll() throws SQLException {
        return super.listAll("SELECT * FROM option");
    }

    @Override
    protected Option rowToObject(ResultSet rs) throws SQLException {
        Option option = new Option();
        option.setOptionId(rs.getLong("id"));
        option.setOptionName(rs.getString("option_name"));
        option.setQuestionId(rs.getLong("question_id"));
        return option;
    }

    @Override
    protected void insertObject(Option obj, PreparedStatement insertStatement) throws SQLException {
        insertStatement.setString(1, obj.getOptionName());
        insertStatement.setLong(2, obj.getQuestionId());
    }

    public Option retrieve(long id) throws SQLException {
        return super.retrieve("SELECT * FROM option WHERE id= ?", id);
    }
/*
    public long insert(Option option){
        return insert(option, "INSERT into option (option_name)");
    }*/

    public void saveOption(Option option) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into option (option_name, question_id) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, option.getOptionName());
                statement.setLong(2, option.getQuestionId());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    option.setOptionId(rs.getLong("option_id"));
                }
            }
        }
    }

    public Option retrieveOption(long optionId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from option where option_id = ?")) {
                statement.setLong(1, optionId);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSetOption(rs);
                }
            }
        }
    }

    private Option readFromResultSetOption(ResultSet rs) throws SQLException {
        Option option = new Option();
        option.setOptionId(rs.getLong("option_id"));
        option.setOptionName(rs.getString("option_name"));
        option.setQuestionId(rs.getLong("question_id"));
        return option;
    }

    public List<Option> listAllOption() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "select * from option"
            )) {

                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Option> result = new ArrayList<>();

                    while (rs.next()){
                        result.add(readFromResultSetOption(rs));
                    }

                    return result;
                }
            }
        }
    }
}
