package no.kristiania.person;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
    private final DataSource dataSource;

    public PersonDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Person person) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into person (first_name, last_name, email, profession_id, workplace_id) values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.setString(3, person.getMailAddress());
                statement.setLong(4, person.getProfessionId());
                statement.setLong(5, person.getWorkplaceId());

                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    person.setPerson_id(rs.getLong("person_id"));
                }
            }
        }
    }

    public Person retrieve(long person_id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from person where person_id = ?")) {
                statement.setLong(1, person_id);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSet(rs);
                }
            }
        }
    }

    public List<Person> listAll() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from person")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Person> result = new ArrayList<>();
                    while(rs.next()){
                        result.add(readFromResultSet(rs));
                    }
                    return result;
                }
            }
        }
    }

    private Person readFromResultSet(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setPerson_id(rs.getLong("person_id"));
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setMailAddress(rs.getString("email"));
        person.setProfessionId(rs.getLong("profession_id"));
        person.setWorkplaceId(rs.getLong("workplace_id"));
        return person;
    }
}
