package com.tour_of_heroes.api.heroes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "heroes")
public class Hero {
    @Getter
    @Id
    @Column(name = "heroe_id")
    private Integer id;

    @Getter
    @Column(name = "name")
    private String name;

    public Hero(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hero() {
    }

    public Hero merge(Hero target) {
        if (name != null && !name.equals(target.name)) target.name = name;
        return target;
    }
}
