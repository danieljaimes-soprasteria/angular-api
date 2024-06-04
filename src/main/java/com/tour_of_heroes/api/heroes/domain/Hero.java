package com.tour_of_heroes.api.heroes.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "heroes")
public class Hero {
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Hero(String name) {
        this.name = name;
    }

    public Hero() {
    }

    public Hero merge(Hero target) {
        if (name != null && !name.equals(target.name)) target.name = name;
        return target;
    }
}
