package com.wombatplanning.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
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

    /*
    In JPA, mappedBy is used to define the "inverse" side of a bidirectional relationship.
    It tells Hibernate:
    "Hey, don't look for a new column in this table.
    The relationship is already defined by the field named 'user' inside the Client class."
    mappedBy always points to the field name in the other class.
    It signifies that this side is "read-only" for the relationship;
    changes to the relationship must be made via the Client.setUser() method to be saved to the database correctly.
     */
    @OneToMany(mappedBy = "user")
    private Set<Client> clientSet = new HashSet<>();

}
