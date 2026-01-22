package com.wombatplanning.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

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

}
