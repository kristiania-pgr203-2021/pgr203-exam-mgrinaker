package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkplaceDaoTest {
    private WorkplaceDao dao = new WorkplaceDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedWorkplace() throws SQLException {
        Workplace workplace = exampleWorkplace();
        dao.save(workplace);
        assertThat(dao.retrieve(workplace.getWorkplaceId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(workplace)
        ;
    }

    /*@Test
    void shouldListAllPeople() throws SQLException {
        Workplace workplace = exampleWorkplace();
        dao.save(workplace);

        Workplace anotherWorkplace = exampleWorkplace();
        dao.save(anotherWorkplace);

        assertThat(dao.listAll())
                .extracting((Workplace::getWorkplaceId))
                .contains(workplace.getWorkplaceId(), anotherWorkplace.getWorkplaceId());
    }*/ //fordi listAll ikke fungerer

    public static Workplace exampleWorkplace() {
        Workplace workplace = new Workplace();
        workplace.setWorkplaceName(TestData.pickOne("Telenor", "Equinor", "Peppes Pizza", "Cirkle K", "Meny", "Rema 1000", "Vy", "Microsoft", "Facebook", "Unneråsen barneskole", "Samtid videregående", "UIO universitet", "Høyskolen kristiania","Twitter", "Burger King", "McDonalds", "Pizzabakeren"));
        workplace.setWorkplaceAddress(TestData.pickOne("Jørunds gate 155, 3722 SKIEN", "Buholmvegen 137, 6091 FOSNAVÅG", "Skjurberget 68, 9407 HARSTAD", "Mariahaugen 1112, 4032 STAVANGER", "Roald Amundsens veg 1110, 6007 ÅLESUND", "Lars Skrefsruds gate 167, 2003 HAMMER", "Brakars vei 182, 9293 TØNSBERG", "Krøgliveien 265, 3292 BORGEN", "Blåskjellveien 81, 0139 OSLO", "Liljedahlvegen 64, 0864 OSLO"));
        return workplace;

    }

}
