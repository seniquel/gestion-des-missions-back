package dev.domain;

import javax.persistence.*;
import java.util.List;

/**
 * classe conceptualisant un collegue
 * 
 * @author diginamic + groupe 2
 *
 */
@Entity
public class Collegue extends EntiteModifiable {

	/** nom */
	private String nom;

	/** prenom */
	private String prenom;

	/** email */
	private String email;

	/** motDePasse */
	private String motDePasse;

	/** roles */
	private RoleCollegue role;

	/** liste des missions d'un collegue */
	@OneToMany(mappedBy = "mission")
	private List<Mission> missions;

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 */
	public Collegue(SignatureNumerique signatureNumerique) {
		super(signatureNumerique);
	}

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 * @param nom
	 * @param prenom
	 * @param email
	 * @param motDePasse
	 * @param roles
	 * @param missions
	 */
	public Collegue(SignatureNumerique signatureNumerique, String nom, String prenom, String email, String motDePasse,
			List<RoleCollegue> roles, List<Mission> missions) {
		super(signatureNumerique);
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.role = role;
		this.missions = missions;
	}

	/**
	 * Getter
	 * 
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter
	 * 
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter
	 * 
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Setter
	 * 
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Getter
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter
	 * 
	 * @return the motDePasse
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * Setter
	 * 
	 * @param motDePasse the motDePasse to set
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}


	public RoleCollegue getRole() {
		return role;
	}

	public void setRole(RoleCollegue role) {
		this.role = role;
	}

	/**
	 * Getter
	 * 
	 * @return the missions
	 */
	public List<Mission> getMissions() {
		return missions;
	}

	/**
	 * Setter
	 * 
	 * @param missions the missions to set
	 */
	public void setMissions(List<Mission> missions) {
		this.missions = missions;
	}

}
