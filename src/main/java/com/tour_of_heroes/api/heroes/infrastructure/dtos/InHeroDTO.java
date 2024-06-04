package com.tour_of_heroes.api.heroes.infrastructure.dtos;

import com.tour_of_heroes.api.heroes.domain.Hero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InHeroDTO {
    private String name;

    public static Hero from(InHeroDTO source){
        return new Hero(source.name);
    }
    public static Hero from(int id, InHeroDTO source){
        return new Hero(id, source.name);
    }
}
