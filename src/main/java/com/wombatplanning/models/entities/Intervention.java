package com.wombatplanning.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "interventions")
public class Intervention {

    @Id
    @Column(name = "intervention_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chantier_id", nullable = false)
    private Chantier chantier;

    @Column(length = 255)
    private String description;

    @ManyToMany
    @JoinTable(
            name = "intervention_weeks",
            joinColumns = @JoinColumn(name = "intervention_id"),
            inverseJoinColumns = @JoinColumn(name = "week_id")
    )
    private Set<Object> weekSet = new TreeSet<>();
    // getters / setters
}

