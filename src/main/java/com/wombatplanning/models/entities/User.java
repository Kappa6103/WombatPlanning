package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.models.constraints.ConstrainedStringChecker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    // FIELDS

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = ColumnConstraints.USER_NAME_FIELD_MAX_LENGTH)
    private String name;

    @Column(nullable = false, unique = true, length = ColumnConstraints.USER_NAME_FIELD_MAX_LENGTH)
    private String email;

    @Column(nullable = false, length = ColumnConstraints.USER_PASSWORD_FIELD_MAX_LENGTH)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @OneToMany(mappedBy = "user")
    private Set<Client> clientSet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Worksite> worksiteSet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Intervention> interventionsSet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ScheduledTask> scheduledTaskSet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Week> weekSet = new TreeSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Typology> typologySet = new HashSet<>();

    // FACTORY

    public static User create(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setIsAdmin(false);
        return user;
    }

    // GETTERS

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public Set<Client> getClientSet() {
        return Collections.unmodifiableSet(clientSet);
    }

    public Set<Typology> getTypologySet() {
        return Collections.unmodifiableSet(typologySet);
    }

    public Set<Week> getWeekSet() {
        return Collections.unmodifiableSet(weekSet);
    }

    public Set<ScheduledTask> getScheduledTaskSet() {
        return Collections.unmodifiableSet(scheduledTaskSet);
    }

    public Set<Intervention> getInterventionsSet() {
        return Collections.unmodifiableSet(interventionsSet);
    }

    public Set<Worksite> getWorksiteSet() {
        return Collections.unmodifiableSet(worksiteSet);
    }

    // MUTATORS

    public void addWeek(Week week) {
        weekSet.add(week);
    }

    public void addClient(Client client) {
        clientSet.add(client);
    }

    public void addWorksite(Worksite worksite) {
        worksiteSet.add(worksite);
    }

    private void setName(String name) {
        ConstrainedStringChecker.requireValidString(
                name, ColumnConstraints.USER_NAME_FIELD_MAX_LENGTH, "User's name"
        );
        this.name = name;
    }

    private void setEmail(String email) {
        ConstrainedStringChecker.requireValidString(
                email, ColumnConstraints.USER_EMAIL_FIELD_MAX_LENGTH, "User's email"
        );
        this.email = email;
    }

    private void setPassword(String password) {
        ConstrainedStringChecker.requireValidString(
                password, ColumnConstraints.USER_PASSWORD_FIELD_MAX_LENGTH, "User's password"
        );
        this.password = password;
    }

    private void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


    public void addIntervention(Intervention intervention) {
        interventionsSet.add(intervention);
    }

    public void addScheduledTask(ScheduledTask scheduledTask) {
        this.scheduledTaskSet.add(scheduledTask);
    }
}
