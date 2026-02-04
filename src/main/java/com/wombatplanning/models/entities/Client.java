package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.models.constraints.ConstrainedStringChecker;
import com.wombatplanning.models.constraints.UserChecker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "clients")
public class Client {

    // FIELDS

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = ColumnConstraints.CLIENT_NAME_FIELD_MAX_LENGTH)
    private String name;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Worksite> worksiteSet = new HashSet<>();

    // FACTORY

    public static Client create(User user, String clientName) {
        Client client = new Client();
        client.setUser(user);
        client.setName(clientName);
        user.addClient(client);
        return client;
    }

    // GETTERS
    public User getUser() {
        return this.user;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUserName() {
        return this.user.getName();
    }

    // MUTATORS

    public void addWorksite(Worksite worksite) {
        worksiteSet.add(worksite);
    }

    private void setName(String name) {
        ConstrainedStringChecker.requireValidString(
                name,
                ColumnConstraints.CLIENT_NAME_FIELD_MAX_LENGTH,
                "Client's name"
        );
        this.name = name;
    }

    private void setUser(User user) {
        UserChecker.requireValidUser(user);
        this.user = user;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                '}';
    }
}