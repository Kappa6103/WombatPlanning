package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "scheduled_tasks")
public class ScheduledTask {

    // FIELDS

    @Id
    @Column(name = "scheduled_task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = ColumnConstraints.SCHEDULED_TASK_DESCRIPTION_FIELD_MAX_LENGTH)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "intervention_id", nullable = false)
    private Intervention intervention;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "week_id", nullable = false)
    private Week week;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "scheduled_task_typologies",
            joinColumns = @JoinColumn(name = "scheduled_task_id"),
            inverseJoinColumns = @JoinColumn(name = "typology_id")
    )
    private Set<Typology> typologySet = new HashSet<>();


    // GETTERS

    public Long getId() {
        return this.id;
    }

    public Set<Typology> getTypologySet() {
        return Collections.unmodifiableSet(typologySet);
    }

    // MUTATORS

    private void addTypologySet(Set<Typology> typologySet) {
        typologySet.forEach(typology -> {
            this.typologySet.add(typology);
            typology.addScheduledTask(this);
        });
    }

    // BUILDER

    private ScheduledTask(Builder builder) {
        this.user = builder.user;
        this.intervention = builder.intervention;
        this.week = builder.week;
        this.description = builder.description;
        addTypologySet(builder.typologySet);
    }

    public static class Builder {
        private User user;
        private Intervention intervention;
        private Week week;
        private String description;
        private Set<Typology> typologySet;

        public Builder user(User user) {
            this.user = user;
            return this;
        }
        public Builder intervention(Intervention intervention) {
            this.intervention = intervention;
            return this;
        }
        public Builder week(Week week) {
            this.week = week;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder typologySet(Set<Typology> typologSet) {
            this.typologySet = typologSet;
            return this;
        }
        public ScheduledTask build() {
            UserChecker.requireValidUser(user);
            InterventionChecker.requireValidIntervention(intervention);
            WeekChecker.requireValidWeek(week);
            TypologySetChecker.requireValidTypologySet(typologySet);
            ConstrainedStringChecker.requireValidDescription(
                    description,
                    ColumnConstraints.SCHEDULED_TASK_DESCRIPTION_FIELD_MAX_LENGTH,
                    "Scheduled task's description");
            ScheduledTask scheduledTask = new ScheduledTask(this);
            this.user.addScheduledTask(scheduledTask);
            this.intervention.addScheduledTask(scheduledTask);
            this.week.addScheduledTask(scheduledTask);

            return scheduledTask;
        }
    }

}
