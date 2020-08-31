package dev.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * classe permettant de mapper un statut a une mission
 * 
 * @author antoinelabeeuw
 *
 */
public class StatutMission {
	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@OneToMany(mappedBy = "statut")
	private List<Mission> missions;

	/** statut */
	@Enumerated(EnumType.STRING)
	private Statut statut;

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

	/**
	 * Getter
	 * 
	 * @return the statut
	 */
	public Statut getStatut() {
		return statut;
	}

	/**
	 * Setter
	 * 
	 * @param statut the statut to set
	 */
	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	/**
	 * Constructeur
	 * 
	 */
	public StatutMission() {
		super();
	}

}
