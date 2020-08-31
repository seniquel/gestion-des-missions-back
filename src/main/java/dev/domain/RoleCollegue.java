package dev.domain;

import java.util.UUID;

import javax.persistence.*;

@Entity
public class RoleCollegue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "collegue_id")
    private Collegue collegue;

    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleCollegue() {
    }

    public RoleCollegue(Collegue collegue, Role role) {
        this.collegue = collegue;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collegue getCollegue() {
        return collegue;
    }

    public void setCollegue(Collegue collegue) {
        this.collegue = collegue;
    }
}
