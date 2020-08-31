package dev.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 */
	public Mission(SignatureNumerique signatureNumerique) {
		super(signatureNumerique);
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

	/** Getter
	 * @return the collegue
	 */
	public Collegue getCollegue() {
		return collegue;
	}

	/** Setter
	 * @param collegue the collegue to set
	 */
	public void setCollegue(Collegue collegue) {
		this.collegue = collegue;
	}

}
