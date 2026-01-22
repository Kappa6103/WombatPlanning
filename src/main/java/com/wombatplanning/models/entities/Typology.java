package com.wombatplanning.models.entities;

import com.wombatplanning.models.constraints.ColumnConstraints;
import com.wombatplanning.models.constraints.ConstrainedStringChecker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "typologies")
public class Typology {

    @Id
    @Column(name = "typology_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false, length = ColumnConstraints.TYPOLOGY_NAME_FIELD_MAX_LENGTH)
    private String name;

    @ManyToMany(mappedBy = "typologySet", fetch = FetchType.LAZY)
    private Set<ScheduledTask> scheduledTaskSet = new HashSet<>();

    public static Typology create(String name) {
        Typology typology = new Typology();
        typology.setName(name);
        return typology;
    }

    public void changeName(String name) {
        this.setName(name);
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
