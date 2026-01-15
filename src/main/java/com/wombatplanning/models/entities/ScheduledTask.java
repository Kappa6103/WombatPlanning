package com.wombatplanning.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "scheduled_tasks")
public class ScheduledTask {

    @Id
    @Column(name = "scheduled_task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 255)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "intervention_id", nullable = false)
    private Intervention intervention;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id", nullable = false)
    private Week week;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "typology_id")
    private Typology typology;

}
