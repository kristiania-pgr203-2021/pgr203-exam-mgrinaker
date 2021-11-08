package no.kristiania.option;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDao {

    private final DataSource dataSource;

    public OptionDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void saveOption(Option option) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into option (option_name) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, option.getOptionName());

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
