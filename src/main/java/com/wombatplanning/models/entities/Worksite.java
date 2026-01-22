package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.models.constraints.ConstrainedStringChecker;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "worksites")
public class Worksite {

    @Id
    @Column(name = "worksite_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = ColumnConstraints.WORKSITE_NAME_FIELD_MAX_LENGTH)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "worksite")
    private Set<Intervention> interventionHashSet = new HashSet<>();

    // GETTER

    public Set<Intervention> getInterventions() {
        return Collections.unmodifiableSet(interventionHashSet);
    }

    // MUTATOR

    public static Worksite create(String name) {
        Worksite worksite = new Worksite();
        worksite.setName(name);
        return worksite;
    }

    public void changeName(String name) {
        this.setName(name);
    }

    public void addIntervention(Intervention intervention) {
        interventionHashSet.add(intervention);
        intervention.setWorksite(this);
    }

    public void removeIntervention(Intervention intervention) {
        interventionHashSet.remove(intervention);
        intervention.setWorksite(null);
    }

    // HELPER

    private void setName(String name) {
        ConstrainedStringChecker. requireValidString(
                name,
                ColumnConstraints.WORKSITE_NAME_FIELD_MAX_LENGTH,
                "Worksite name"
        );
        this.name = name;
    }

}
