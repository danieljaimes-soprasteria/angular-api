package com.tour_of_heroes.api.heroes.domain;

import com.tour_of_heroes.api.shared.core.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends BaseRepository<Hero, Integer> {
}
