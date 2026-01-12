package com.wombatplanning.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "chantier",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_chantier_client_name",
                        columnNames = { "client_id", "name" }
                )
        }
)
public class Chantier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(
            mappedBy = "chantier",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Set<Intervention> interventions = new HashSet<>();

    // getters / setters
    // -------------------------
    // equals/hashCode
    // -------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chantier)) return false;
        Chantier that = (Chantier) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31; // constant for transient entities
        // OR: return id != null ? id.hashCode() : 0;
    }
}
