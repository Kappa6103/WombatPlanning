package com.wombatplanning.models.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weeks")
public class Week implements Comparable {

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

    @OneToMany(mappedBy = "week", fetch = FetchType.LAZY)
    private List<ScheduledTask> scheduledTaskList = new ArrayList<>();

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
