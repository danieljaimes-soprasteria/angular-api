package com.tour_of_heroes.api.heroes.infrastructure.dtos;

import com.tour_of_heroes.api.heroes.domain.Hero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutHeroDTO {
    private Integer id;
    private String name;

    public static OutHeroDTO from(Hero source) {
        return new OutHeroDTO(source.getId(), source.getName());
    }
}
