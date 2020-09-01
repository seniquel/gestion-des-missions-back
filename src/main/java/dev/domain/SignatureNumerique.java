package dev.domain;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
		LocalDate.now();
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
