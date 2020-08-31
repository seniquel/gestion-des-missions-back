package dev.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * classe conceptualisant une mission
 * 
 * @author antoinelabeeuw
 *
 */
@Entity
public class Mission extends EntiteModifiable {
	/** dateDebut */
	private LocalDate dateDebut;
	/** dateFin */
	private LocalDate dateFin;
	/** villeDepart */
	private String villeDepart;
	/** villeArrivee */
	private String villeArrivee;
	/** prime */
	private BigDecimal prime;
	/** nature */
	@ManyToOne
	@JoinColumn(name = "nature_id")
	private Nature nature;

	/** collegue */
	@ManyToOne
	@JoinColumn(name = "collegue_id")
	private Collegue collegue;

	@ManyToOne
	@JoinColumn(name = "statut_id")
	private StatutMission statut;

	@ManyToOne
	@JoinColumn(name = "transport_id")
	private TransportMission transport;

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 */
	public Mission(SignatureNumerique signatureNumerique) {
		super(signatureNumerique);
	}

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 * @param dateDebut
	 * @param dateFin
	 * @param villeDepart
	 * @param villeArrivee
	 * @param prime
	 * @param nature
	 * @param collegue
	 * @param statut
	 * @param transport
	 */
	public Mission(SignatureNumerique signatureNumerique, LocalDate dateDebut, LocalDate dateFin, String villeDepart,
			String villeArrivee, BigDecimal prime, Nature nature, Collegue collegue, StatutMission statut,
			TransportMission transport) {
		super(signatureNumerique);
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.villeDepart = villeDepart;
		this.villeArrivee = villeArrivee;
		this.prime = prime;
		this.nature = nature;
		this.collegue = collegue;
		this.statut = statut;
		this.transport = transport;
	}

	/**
	 * Getter
	 * 
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/**
	 * Setter
	 * 
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Getter
	 * 
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/**
	 * Setter
	 * 
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * Getter
	 * 
	 * @return the villeDepart
	 */
	public String getVilleDepart() {
		return villeDepart;
	}

	/**
	 * Setter
	 * 
	 * @param villeDepart the villeDepart to set
	 */
	public void setVilleDepart(String villeDepart) {
		this.villeDepart = villeDepart;
	}

	/**
	 * Getter
	 * 
	 * @return the villeArrivee
	 */
	public String getVilleArrivee() {
		return villeArrivee;
	}

	/**
	 * Setter
	 * 
	 * @param villeArrivee the villeArrivee to set
	 */
	public void setVilleArrivee(String villeArrivee) {
		this.villeArrivee = villeArrivee;
	}

	/**
	 * Getter
	 * 
	 * @return the prime
	 */
	public BigDecimal getPrime() {
		return prime;
	}

	/**
	 * Setter
	 * 
	 * @param prime the prime to set
	 */
	public void setPrime(BigDecimal prime) {
		this.prime = prime;
	}

	/**
	 * Getter
	 * 
	 * @return the nature
	 */
	public Nature getNature() {
		return nature;
	}

	/**
	 * Setter
	 * 
	 * @param nature the nature to set
	 */
	public void setNature(Nature nature) {
		this.nature = nature;
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
	 * @return the statut
	 */
	public StatutMission getStatut() {
		return statut;
	}

	/**
	 * Setter
	 * 
	 * @param statut the statut to set
	 */
	public void setStatut(StatutMission statut) {
		this.statut = statut;
	}

	/**
	 * Getter
	 * 
	 * @return the transport
	 */
	public TransportMission getTransport() {
		return transport;
	}

	/**
	 * Setter
	 * 
	 * @param transport the transport to set
	 */
	public void setTransport(TransportMission transport) {
		this.transport = transport;
	}

}
