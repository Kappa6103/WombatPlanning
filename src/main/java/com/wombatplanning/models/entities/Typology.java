package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.models.constraints.ConstrainedStringChecker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "typologies")
public class Typology {

    // FIELDS

    @Id
    @Column(name = "typology_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = false, length = ColumnConstraints.TYPOLOGY_NAME_FIELD_MAX_LENGTH)
    private String name;

    @ManyToMany(mappedBy = "typologySet", fetch = FetchType.LAZY)
    private Set<ScheduledTask> scheduledTaskSet = new HashSet<>();

    // FACTORY

    public static Typology create(String name) {
        Typology typology = new Typology();
        typology.setName(name);
        return typology;
    }

    // GETTERS

    public Long getId() {
        return this.id;
    }

    // MUTATORS

    public void addScheduledTask(ScheduledTask scheduledTask) {
        scheduledTaskSet.add(scheduledTask);
    }

    private void setName(String name) {
        ConstrainedStringChecker.requireValidString(
                name,
                ColumnConstraints.TYPOLOGY_NAME_FIELD_MAX_LENGTH,
                "Typology name"
        );
        this.name = name;
    }

}
