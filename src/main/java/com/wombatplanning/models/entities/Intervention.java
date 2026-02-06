package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.UserChecker;
import com.wombatplanning.models.constraints.WorksiteChecker;
import com.wombatplanning.models.constraints.YearChecker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "interventions")
public class Intervention {

    // FIELDS

    @Id
    @Column(name = "intervention_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "worksite_id", nullable = false)
    private Worksite worksite;

    @Column(nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "intervention")
    private Set<ScheduledTask> scheduledTasks = new HashSet<>();

    // FACTORY

    public static Intervention create(User user, Worksite worksite, Integer year) {
        Intervention intervention = new Intervention();
        intervention.setUser(user);
        intervention.setWorksite(worksite);
        intervention.setYear(year);
//        user.addIntervention(intervention);
//        worksite.addIntervention(intervention);
        return intervention;
    }

    // GETTERS

    public Long getId() {
        return this.id;
    }

    public Long getWorksiteId() {
        return this.worksite.getId();
    }
    public Long getUserId() {
        return this.user.getId();
    }
    public Integer getYear() {
        return this.year;
    }

    // MUTATORS

    private void setUser(User user) {
        UserChecker.requireValidUser(user);
        this.user = user;
    }

    private void setWorksite(Worksite worksite) {
        WorksiteChecker.requireValidWorksite(worksite);
        this.worksite = worksite;
    }

    private void setYear(Integer year) {
        YearChecker.requireValidYear(year);
        this.year = year;
    }

    public void addScheduledTask(ScheduledTask scheduledTask) {
        this.scheduledTasks.add(scheduledTask);
    }
}

