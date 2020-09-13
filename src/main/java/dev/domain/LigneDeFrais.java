package dev.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * classe conceptualisant une ligne de frais
 * 
 * @author antoinelabeeuw
 *
 */
@Entity
public class LigneDeFrais extends EntiteBase {
	/** date */
	private LocalDate date;

	/** nature */
	private String nature;

	/** montant */
	private BigDecimal montant;

	/** noteDeFrais */
	@ManyToOne
	@JoinColumn(name = "noteDeFrais_id")
	@JsonBackReference
	private NoteDeFrais noteDeFrais;

	/**
	 * Constructeur
	 * 
	 */
	public LigneDeFrais() {
		super();
	}

	/**
	 * Getter
	 * 
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Setter
	 * 
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Getter
	 * 
	 * @return the nature
	 */
	public String getNature() {
		return nature;
	}

	/**
	 * Setter
	 * 
	 * @param nature the nature to set
	 */
	public void setNature(String nature) {
		this.nature = nature;
	}

	/**
	 * Getter
	 * 
	 * @return the montant
	 */
	public BigDecimal getMontant() {
		return montant;
	}

	/**
	 * Setter
	 * 
	 * @param montant the montant to set
	 */
	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	/**
	 * Getter
	 * 
	 * @return the noteDeFrais
	 */
	public NoteDeFrais getNoteDeFrais() {
		return noteDeFrais;
	}

	/**
	 * Setter
	 * 
	 * @param noteDeFrais the noteDeFrais to set
	 */
	public void setNoteDeFrais(NoteDeFrais noteDeFrais) {
		this.noteDeFrais = noteDeFrais;
	}

}
