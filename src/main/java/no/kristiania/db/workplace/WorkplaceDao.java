package no.kristiania.db.workplace;

import javax.sql.DataSource;
import java.sql.*;

public class WorkplaceDao {
    private final DataSource dataSource;

    public WorkplaceDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Workplace workplace) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into workplace (workplace_name, workplace_address) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, workplace.getWorkplaceName());
                statement.setString(2, workplace.getWorkplaceAddress());

                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    workplace.setWorkplaceId(rs.getLong("workplace_id"));
                }
            }
        }
    }

    public Workplace retrieve(long workplaceId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from workplace where workplace_id = ?")) {
                statement.setLong(1, workplaceId);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSet(rs);
                }
            }
        }
    }

    private Workplace readFromResultSet(ResultSet rs) throws SQLException {
        Workplace workplace = new Workplace();
        workplace.setWorkplaceId(rs.getLong("workplace_id"));
        workplace.setWorkplaceName(rs.getString("workplace_name"));
        workplace.setWorkplaceAddress(rs.getString("workplace_address"));
        return workplace;
    }

}
