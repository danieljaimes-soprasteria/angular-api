package com.tour_of_heroes.api.heroes.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "heroes")
public class Hero {
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="heroes_seq")
    @SequenceGenerator(name="heroes_seq",sequenceName="HEROES_SEQ", allocationSize=1)
    @Id
    @Column(name = "heroe_id")
    private Integer id;

    @Getter
    @Setter
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
