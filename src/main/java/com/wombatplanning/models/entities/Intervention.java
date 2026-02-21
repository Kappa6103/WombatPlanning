package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "interventions")
public class Intervention { //TODO is name appropriate ? shouldn't be InterventionScheduler ? or Scheduler ?

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

    //TODO can i remove it ? kinda redundant.
    @Column(nullable = false)
    private Integer occurrence;

    @Column(name = "occurrence_done", nullable = false)
    private Integer occurrenceDone;

    @Column(name = "occurrence_remaining", nullable = false)
    private Integer occurrenceRemaining;

    @Column(name = "occurrence_skipped", nullable = false)
    private Integer occurrenceSkipped;

    @Column(name = "starting_week", nullable = false)
    private Integer startingWeek;

    @Column(name = "ending_week", nullable = false)
    private Integer endingWeek;

    @OneToMany(mappedBy = "intervention")
    private Set<ScheduledTask> scheduledTasks = new HashSet<>();

    // FACTORY

    public static Intervention create(User user, Worksite worksite, Integer year, Integer occurrence, Integer startingWeek, Integer endingWeek) {
        Intervention intervention = new Intervention();
        intervention.setUser(user);
        intervention.setWorksite(worksite);
        intervention.setYear(year);
        intervention.setOccurrence(occurrence);
        intervention.setOccurrenceDone();
        intervention.setOccurrenceSkipped();
        intervention.setStartingWeek(startingWeek);
        intervention.setEndingWeek(endingWeek);
        return intervention;
    }

    // GETTERS

    public Integer getOccurrenceNumber() {
        return this.occurrence;
    }
    public Long getId() {
        return this.id;
    }
    public Worksite getWorksite() {
        return this.worksite;
    }
    public User getUser() {
        return this.user;
    }
    public Integer getYear() {
        return this.year;
    }

    // PRIVATE MUTATORS

    private void setEndingWeek(Integer endingWeek) {
        WeekChecker.requireValidEndingWeek(endingWeek);
        this.endingWeek = endingWeek;
    }

    //TODO does not check if starting week is after ending week;
    private void setStartingWeek(Integer startingWeek) {
        WeekChecker.requireValidStartingWeek(startingWeek);
        this.startingWeek = startingWeek;
    }

    private void setOccurrenceDone() {
        this.occurrenceDone = 0;
    }

    private void setOccurrenceSkipped() {
        this.occurrenceSkipped = 0;
    }

    private void setOccurrence(Integer occurrence) {
        OccurrenceChecker.requireValidOccurrence(occurrence);
        this.occurrence = occurrence;
        this.occurrenceRemaining = occurrence;
    }

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

