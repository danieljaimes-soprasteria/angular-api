package com.tour_of_heroes.api.contexts.heroes.domain;

import com.tour_of_heroes.api.heroes.domain.Hero;
import com.tour_of_heroes.api.heroes.domain.HeroRepository;
import com.tour_of_heroes.api.heroes.domain.HeroServiceImpl;
import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HeroServiceImplTest {
    @InjectMocks
    private HeroServiceImpl heroServiceMock;

    @Mock
    private HeroRepository repository;

    @Nested
    class OK {
        @Test
        void whenGetAll_thenReturnHeroList() {
            List<Hero> heroListMockExpected = List.of(
                    new Hero("Pepa"),
                    new Hero("Pepe"),
                    new Hero("Pepo")
            );
            when(repository.findAll()).thenReturn(heroListMockExpected);
            List<Hero> heroListServiceActual = heroServiceMock.getAll();
            assertThat(heroListServiceActual, is(heroListMockExpected));
        }

        @Test
        void whenGetOneById_thenReturnHero() {
            Hero heroMockExpected = new Hero(1, "Pepa");
            when(repository.findById(heroMockExpected.getId())).thenReturn(Optional.of(heroMockExpected));
            Optional<Hero> heroActual = heroServiceMock.getOne(heroMockExpected.getId());
            assertSame(heroMockExpected, heroActual.get());
        }

        @Test
        void whenAddHeroSaveHero_thenReturnHero() throws InvalidDataException {
            Hero heroMock = new Hero(1, "Pepa");
            when(repository.save(heroMock)).thenReturn(heroMock);
            when(heroServiceMock.add(heroMock)).thenReturn(heroMock);
            assertEquals(heroServiceMock.add(heroMock), repository.save(heroMock));
        }

        @Test
        void whenModifyOrder_thenReturnOrder() throws NotFoundException, InvalidDataException {
            Hero heroOne = new Hero("Pepe");
            Hero heroTwo = new Hero("Pepo");
            when(repository.findById(heroOne.getId())).thenReturn(Optional.of(heroOne));
            lenient().when(repository.save(heroOne.merge(heroTwo))).thenReturn(heroOne);
            lenient().when(heroServiceMock.modify(heroOne)).thenReturn(heroTwo);
            assertEquals(heroServiceMock.modify(heroOne), heroTwo);
        }

        @Test
        void deleteProductWhenProductNotNull() throws InvalidDataException {
            Hero heroMock = new Hero(1, "Pepa");
            doNothing().when(repository).delete(heroMock);
            heroServiceMock.delete(heroMock);
            verify(repository, times(1)).delete(heroMock);
        }

        @Test
        void deleteProductById() {
            int id = 1;
            doNothing().when(repository).deleteById(id);
            heroServiceMock.deleteById(id);
            verify(repository, times(1)).deleteById(id);
        }
    }

    @Nested
    class KO {
        @Test
        void whenAddNullHero_thenThrowInvalidDataException() {
            Hero hero = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                    heroServiceMock.add(hero)
            );
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }

        @Test
        void whenModifyNullHero_thenThrowInvalidDataException() {
            Hero hero = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                    heroServiceMock.modify(hero)
            );
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }

        @Test
        void whenModifyHeroNotSaved_thenThrowNotFoundException() {

            Hero hero = new Hero();
            NotFoundException exception = assertThrows(NotFoundException.class, () ->
                    heroServiceMock.modify(hero)
            );
            assertEquals(exception.getMessage(), NotFoundException.MESSAGE_STRING);
        }

        @Test
        void whenDeleteNullHero_thenThrowInvalidDataException() {
            Hero hero = null;
            InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
                    heroServiceMock.delete(hero)
            );
            assertEquals(exception.getMessage(), InvalidDataException.CANT_BE_NULL);
        }
    }
}

