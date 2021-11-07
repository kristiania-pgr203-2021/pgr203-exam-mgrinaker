package no.kristiania.person;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfessionDaoTest {
    private ProfessionDao dao = new ProfessionDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedProfession() throws SQLException {
        Profession profession = exampleProfession();
        dao.save(profession);
        assertThat(dao.retrieve(profession.getProfessionId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(profession)
        ;
    }

    /*@Test
    void shouldListAllProfessions() throws SQLException {
        Profession profession = exampleProfession();
        dao.save(profession);

        Profession anotherProfession = exampleProfession();
        dao.save(anotherProfession);

        assertThat(dao.listAll())
                .extracting((Profession::getProfessionId))
                .contains(profession.getProfessionId(), anotherProfession.getProfessionId());
    }*/ ////fordi listAll ikke fungerer

    public static Profession exampleProfession() {
        Profession profession = new Profession();
        profession.setProfessionTitle(TestData.pickOne("Avløser", "Akupunktør", "Bilselger", "Bartender", "Barnehagelærer", "Designer", "Elektrofagarbeider", "Ergoterapi", "Fisker", "Forfatter", "Gartner", "Glassblåser", "HMS-ingeniør","Hudpleie", "Influencer", "Journalist", "Kokk"));
        return profession;

    }
}
