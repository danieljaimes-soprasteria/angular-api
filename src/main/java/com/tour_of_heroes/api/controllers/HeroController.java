package com.tour_of_heroes.api.controllers;

import com.tour_of_heroes.api.heroes.domain.Hero;
import com.tour_of_heroes.api.heroes.domain.HeroService;
import com.tour_of_heroes.api.heroes.infrastructure.dtos.InHeroDTO;
import com.tour_of_heroes.api.heroes.infrastructure.dtos.OutHeroDTO;
import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    @Autowired
    HeroService heroService;

    @GetMapping("/{id}")
    public OutHeroDTO getHero(@PathVariable int id) throws NotFoundException {
        Optional<Hero> hero = heroService.getOne(id);
        if (hero.isEmpty()) throw new NotFoundException();
        return OutHeroDTO.from(hero.get());
    }

    @PatchMapping("/{id}")
    @Transactional
    public OutHeroDTO updateHero(@PathVariable int id, @Valid @RequestBody InHeroDTO source) throws InvalidDataException, NotFoundException {
        return OutHeroDTO.from(heroService.modify(InHeroDTO.from(id, source)));
    }


    @GetMapping
    public List<OutHeroDTO> getAllHeroes() {
        List<OutHeroDTO> heroDtoList = heroService.getAll().stream().map(OutHeroDTO::from).toList();
        return heroDtoList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public OutHeroDTO createHero(@Valid @RequestBody InHeroDTO source) throws InvalidDataException {
        Hero hero = heroService.add(InHeroDTO.from(source));
        return OutHeroDTO.from(hero);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHero(@PathVariable int id) throws NotFoundException {
        if (heroService.getOne(id).isEmpty()) throw new NotFoundException();
        heroService.deleteById(id);
    }
}
