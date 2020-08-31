package dev.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * classe permettant de mapper un transport a une mission
 * 
 * @author antoinelabeeuw
 *
 */
@Entity
public class TransportMission {
	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;

	@OneToMany(mappedBy = "transport")
	private List<Mission> missions;

	/** transport */
	@Enumerated(EnumType.STRING)
	private Transport transport;

	/**
	 * Constructeur
	 * 
	 */
	public TransportMission() {
		super();
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
	 * @return the transport
	 */
	public Transport getTransport() {
		return transport;
	}

	/**
	 * Setter
	 * 
	 * @param transport the transport to set
	 */
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

}
