package dev.domain;

import java.util.UUID;

import javax.persistence.*;

/**
 * Roles d'un collegue
 * 
 * @author antoinelabeeuw
 *
 */
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

	/**
	 * Constructeur
	 * 
	 */
	public RoleCollegue() {
	}

	/**
	 * Constructeur
	 * 
	 * @param collegue
	 * @param role
	 */
	public RoleCollegue(Collegue collegue, Role role) {
		this.collegue = collegue;
		this.role = role;
	}

	/**
	 * Getter
	 * 
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Setter
	 * 
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Getter
	 * 
	 * @return the collegue
	 */
	public Collegue getCollegue() {
		return collegue;
	}

	/**
	 * Setter
	 * 
	 * @param collegue the collegue to set
	 */
	public void setCollegue(Collegue collegue) {
		this.collegue = collegue;
	}

	/**
	 * Getter
	 * 
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Setter
	 * 
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

}
