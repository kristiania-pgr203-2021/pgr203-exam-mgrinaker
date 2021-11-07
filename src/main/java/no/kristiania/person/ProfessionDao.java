package no.kristiania.person;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class ProfessionDao {
    private final DataSource dataSource;

    ///KAN DERE SE DETTE
    public ProfessionDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/person_db");
        dataSource.setUser("survey_dbuser");
        dataSource.setPassword("TvsVM5wRCdh");

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return dataSource;
    }

    public void save(Profession profession) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into profession (profession_title) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, profession.getProfessionTitle());

                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    profession.setProfessionId(rs.getLong("profession_id"));
                }
            }
        }
    }

    public Profession retrieve(long professionId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from profession where profession_id = ?")) {
                statement.setLong(1, professionId);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSet(rs);
                }
            }
        }
    }

    private Profession readFromResultSet(ResultSet rs) throws SQLException {
        Profession profession = new Profession();
        profession.setProfessionId(rs.getLong("profession_id"));
        profession.setProfessionTitle(rs.getString("profession_title"));
        return profession;
    }
}
