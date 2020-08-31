package dev.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Collegue extends EntiteModifiable {

	private String nom;

	private String prenom;

	private String email;

	private String motDePasse;

	@OneToMany(mappedBy = "collegue", cascade = CascadeType.PERSIST)
	private List<RoleCollegue> roles;
	
	/** liste des missions d'un collegue */
	@OneToMany(mappedBy = "mission")
	private List<Mission> missions;

	public Collegue(SignatureNumerique signatureNumerique) {
		super(signatureNumerique);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public List<RoleCollegue> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleCollegue> roles) {
		this.roles = roles;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/** Getter
	 * @return the missions
	 */
	public List<Mission> getMissions() {
		return missions;
	}

	/** Setter
	 * @param missions the missions to set
	 */
	public void setMissions(List<Mission> missions) {
		this.missions = missions;
	}
	
}
