package no.kristiania.db.option;

import no.kristiania.TestData;
import no.kristiania.http.AbstractDao;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionDaoTest extends AbstractDao<Option> {
    private OptionDao dao = new OptionDao(TestData.testDataSource());

    public OptionDaoTest(DataSource dataSource) {
        super(dataSource);
    }

    @Test
    void shouldRetrieveSavedOption() throws SQLException {
        Option option = exampleOption();
        dao.insert(option);
        assertThat(dao.retrieve(option.getOptionId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(option)
        ;
    }

    @Test
    void shouldListAllPeople() throws SQLException {
        Option option = exampleOption();
        dao.insert(option);

        Option anotherOption = exampleOption();
        dao.insert(anotherOption);

        assertThat(dao.listAll())
                .extracting((Option::getOptionId))
                .contains(option.getOptionId(), anotherOption.getOptionId());
    }

    public static Option exampleOption() {
        Option option = new Option();
        option.setOptionName(TestData.pickOne("Bra", "Dårlig", "Okei"));
        option.setQuestionId(TestData.pickOneInteger(1, 2, 3, 4));
        return option;
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

    }
}
