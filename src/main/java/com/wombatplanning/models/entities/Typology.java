package com.wombatplanning.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "typologies")
public class Typology {

    @Id
    @Column(name = "typology_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "typology")
    private Set<ScheduledTask> scheduledTaskSet = new HashSet<>();

}
