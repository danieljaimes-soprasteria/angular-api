package com.tour_of_heroes.api.controllers;

import com.tour_of_heroes.api.heroes.domain.Hero;
import com.tour_of_heroes.api.heroes.domain.HeroService;
import com.tour_of_heroes.api.shared.exceptions.InvalidDataException;
import com.tour_of_heroes.api.shared.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/heroes")
public class HeroeController {

    @Autowired
    HeroService heroService;

    @GetMapping("/{id}")
    public Hero getHero(@PathVariable int id) throws NotFoundException {
        Optional<Hero> heroe = heroService.getOne(id);
        if (heroe.isEmpty()) throw new NotFoundException();
        return heroe.get();
    }

    @PatchMapping("/{id}")
    @Transactional
    public Hero updateHero(@Valid @RequestBody Hero hero) throws InvalidDataException, NotFoundException {
        return heroService.modify(hero);
    }


    @GetMapping
    public List<Hero> getAllHeroes() {
        List<Hero> heroList = heroService.getAll();
        return heroList;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteHero(@PathVariable int id) throws NotFoundException {
        if (heroService.getOne(id).isEmpty()) throw new NotFoundException();
        heroService.deleteById(id);
    }
}
