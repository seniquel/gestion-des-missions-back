package dev.domain;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author groupe 2
 *
 */
@Entity
public class SignatureNumerique {
	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID id;
	/** dateMaj */
	private LocalDate dateMaj;

	/**
	 * Constructeur
	 * 
	 */
	public SignatureNumerique() {
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
