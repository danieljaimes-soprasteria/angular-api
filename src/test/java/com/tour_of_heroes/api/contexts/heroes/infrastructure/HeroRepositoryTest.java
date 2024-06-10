package com.tour_of_heroes.api.contexts.heroes.infrastructure;

import com.tour_of_heroes.api.heroes.domain.Hero;
import com.tour_of_heroes.api.heroes.domain.HeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class HeroRepositoryTest {
    @Autowired
    HeroRepository repository;

    private Hero hero;

    @BeforeEach
    public void setupTestData() {
        // Given : Setup object or precondition
        hero = new Hero("Pepe");
    }

    @Test
    @DisplayName("test for save hero operation")
    public void givenHeroObject_whenSave_thenReturnSaveHero() {
        // When : Action of behavious that we are going to test
        Hero saveHero = repository.save(hero);

        // Then : Verify the output

        assertThat(saveHero).isNotNull();
        assertThat(saveHero.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("test for get Hero List")
    public void givenHeroList_whenFindAll_thenHeroList() {
        Hero heroOne = new Hero("Pepe");
        Hero heroTwo = new Hero("Pepa");

        repository.save(heroOne);
        repository.save(heroTwo);

        // When : Action of behavious that we are going to test
        List<Hero> heroList = repository.findAll();

        // Then : Verify the output
        assertThat(heroList).isNotEmpty();
        assertThat(heroList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("test for get Hero By Id")
    public void givenHeroObject_whenFindById_thenReturnHeroObject() {
        // Given : Setup object or precondition
        hero = repository.save(hero);

        // When : Action of behavious that we are going to test
        Hero getHero = repository.findById(hero.getId()).get();

        // Then : Verify the output
        assertThat(getHero).isNotNull();
    }

    @Test
    @DisplayName("test for get Hero update operation")
    public void givenHeroObject_whenUpdate_thenHeroObject() {

        // Given: Setup object or precondition
        hero = repository.save(hero);

        // When: Action or behavior that we are going to test
        Hero getHero = repository.findById(hero.getId()).get();

        String updatedName = "Pepa";
        getHero.setName(updatedName);

        Hero updatedHero = repository.save(getHero);

        // Then: Verify the output or expected result
        assertThat(updatedHero).isNotNull();
        assertThat(updatedHero.getName()).isEqualTo(updatedName);
    }

    @Test
    @DisplayName("test for delete hero operation")
    public void givenHeroObject_whenDelete_thenRemoveHero() {

        // Given: Setup object or precondition
        hero = repository.save(hero);

        // When: Action or behavior that we are going to test
        repository.deleteById(hero.getId());
        Optional<Hero> deleteHero = repository.findById(hero.getId());

        // Then: Verify the output or expected result
        assertThat(deleteHero).isEmpty();
    }
}