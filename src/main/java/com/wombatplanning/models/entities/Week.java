package com.wombatplanning.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "weeks")
public class Week {

    @Id
    @Column(name = "week_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(name = "is_holiday", nullable = false)
    private Boolean isHoliday;

    @Column(name = "year", nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "week", fetch = FetchType.LAZY)
    private Set<ScheduledTask> scheduledTaskList = new TreeSet<>();


}
