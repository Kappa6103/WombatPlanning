package com.wombatplanning.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "interventions")
public class Intervention {

    @Id
    @Column(name = "intervention_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "chantier_id", nullable = false)
    private Worksite worksite;

    @Column(nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "intervention")
    private Set<ScheduledTask> scheduledTasks = new HashSet<>();

    // GETTER
    // MUTATOR

    public void setWorksite(Worksite worksite) {
        this.worksite = worksite;
    }

    // HELPER
}

