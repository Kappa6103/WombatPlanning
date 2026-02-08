package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.ClientChecker;
import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.models.constraints.ConstrainedStringChecker;
import com.wombatplanning.models.constraints.UserChecker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "worksites")
public class Worksite {

    // FIELDS

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

        // DO THIS IF DB HIT
//    @Column(name = "client_id", insertable = false, updatable = false)
//    private Long clientId;

    @OneToMany(mappedBy = "worksite")
    private Set<Intervention> interventionSet = new HashSet<>();

    // FACTORY

    public static Worksite create(User user, Client client, String name) {
        Worksite worksite = new Worksite();
        worksite.setUser(user);
        worksite.setClient(client);
        worksite.setName(name);
//        user.addWorksite(worksite);
//        client.addWorksite(worksite);
        return worksite;
    }

    // GETTERS

    // CHECK FOR DB HIT
    public User getUser() {
        return this.user;
    }

    public Client getClient() {
        return this.client;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Set<Intervention> getInterventions() {
        return Collections.unmodifiableSet(interventionSet);
    }

    // PUBLIC MUTATORS

    public void changeName(String name) {
        this.setName(name);
    }

    public void addIntervention(Intervention intervention) {
        interventionSet.add(intervention);
    }

    // PRIVATE MUTATORS

    private void setUser(User user) {
        UserChecker.requireValidUser(user);
        this.user = user;
    }

    private void setClient(Client client) {
        ClientChecker.requireValidClient(client);
        this.client = client;
    }

    private void setName(String name) {
        ConstrainedStringChecker. requireValidString(
                name,
                ColumnConstraints.WORKSITE_NAME_FIELD_MAX_LENGTH,
                "Worksite name"
        );
        this.name = name;
    }

    // OVERRIDES


    @Override
    public String toString() {
        return "Worksite{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", client=" + client +
                '}';
    }
}
