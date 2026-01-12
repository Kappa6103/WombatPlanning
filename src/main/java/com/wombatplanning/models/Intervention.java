package com.wombatplanning.models;

import jakarta.persistence.*;

@Entity
@Table(name = "intervention")
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chantier_id", nullable = false)
    private Chantier chantier;

    // getters / setters
}

