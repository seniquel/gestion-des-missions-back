package dev.domain;

import java.time.LocalDate;

import javax.persistence.Embeddable;

/**
 * signature numérique des classes
 * 
 * @author groupe 2
 *
 */
@Embeddable
public class SignatureNumerique {
	/** dateMaj */
	private LocalDate dateMaj;

	/**
	 * Constructeur
	 * 
	 */
	public SignatureNumerique() {
	}

	/**
	 * Getter
	 * 
	 * @return the dateMaj
	 */
	public LocalDate getDateMaj() {
		return dateMaj;
	}

	/**
	 * Setter
	 * 
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(LocalDate dateMaj) {
		this.dateMaj = dateMaj;
	}
}
