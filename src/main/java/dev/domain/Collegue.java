package dev.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * classe conceptualisant un collegue
 * 
 * @author diginamic + groupe 2
 *
 */
@Entity
public class Collegue extends EntiteBase {

	/** nom */
	private String nom;

	/** prenom */
	private String prenom;

	/** email */
	private String email;

	/** motDePasse */
	private String motDePasse;

	/** roles */
	private Role role;

	/** liste des missions d'un collegue */
	@OneToMany(mappedBy = "collegue",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true)
	@JsonManagedReference
	private List<Mission> missions = new ArrayList<Mission>();

	public Collegue() {
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
	public Collegue(String nom, String prenom, String email, String motDePasse,
			Role role, List<Mission> missions) {
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


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
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
	
	public void addMission(Mission mission) {
		this.missions.add(mission);
		mission.setCollegue(this);
	}
	
	public void removeMission(Mission mission) {
		this.missions.remove(mission);
		mission.setCollegue(null);
	}

}
