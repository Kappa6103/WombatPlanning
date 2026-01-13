package com.wombatplanning.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "chantiers")
public class Chantier {

    @Id
    @Column(name = "chantier_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @UniqueElements
    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne(optional = true, fetch = FetchType.LAZY) //Many instances of the current entity (e.g., Chantier) can be associated with one instance of the target entity (Client)
    @JoinColumn(name = "client_id", nullable = true)
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
