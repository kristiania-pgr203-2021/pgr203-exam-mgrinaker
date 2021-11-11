package no.kristiania.db.option;

import no.kristiania.TestData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionDaoTest {
    private OptionDao dao = new OptionDao(TestData.testDataSource());


    @Test
    void shouldRetrieveSavedOption() throws SQLException {

        dao.insert(exampleOption());
        dao.insert(exampleOption());

        Option option = exampleOption();
        option.setOptionId(dao.insert(option));

        assertThat(dao.retrieve(option.getOptionId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(option)
        ;
    }

    @Test
    void shouldListAllPeople() throws SQLException {
        Option option1 = exampleOption();
        dao.insert(option1);

        Option anotherOption = exampleOption();
        dao.insert(anotherOption);

        Option option = exampleOption();
        option.setOptionId(dao.insert(option));

        assertThat(dao.listAll())
                .extracting((Option::getOptionName))
                .contains(option.getOptionName(), anotherOption.getOptionName());
    }

    public static Option exampleOption() {
        Option option = new Option();
        option.setOptionName(TestData.pickOne("Bra", "Dårlig", "Okei"));
        option.setQuestionId(TestData.pickOneInteger(1, 2, 3, 4));
        return option;
    }

}
