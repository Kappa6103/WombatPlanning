package com.wombatplanning.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "weeks")
public class Week {

    @Id
    @Column(name = "week_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "working_days")
    private Integer workingDays;

}
