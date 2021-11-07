package no.kristiania.person;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonDao {
    private final DataSource dataSource;

    public PersonDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/person_db");
        dataSource.setUser("person_dbuser");
        dataSource.setPassword("PASSORDHER");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return dataSource;
    }

    public void save(Person person) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into person (first_name, last_name, email, profession_id, workplace_id) values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.setString(3, person.getMailAddress());
                statement.setLong(4, person.getProfessionId());
                statement.setLong(5, person.getWorkplaceId());

                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    person.setUserId(rs.getLong("person_id"));
                }
            }
        }
    }

    public Person retrieve(long userId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from person where person_id = ?")) {
                statement.setLong(1, userId);

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
        person.setUserId(rs.getLong("person_id"));
        person.setFirstName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setMailAddress(rs.getString("email"));
        return person;
    }

    public static void main(String[] args) throws SQLException {
        PersonDao dao = new PersonDao(createDataSource());

        System.out.println("Please enter a last name: ");

        Scanner scanner = new Scanner(System.in);
        String lastName = scanner.nextLine().trim();

//      //System.out.println(dao.listByLastName(lastName));
    }
}
