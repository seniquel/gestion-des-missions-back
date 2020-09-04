package dev.controller.vm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreerLigneFraisDto {
	/** date de la ligne de frais */
	@NotNull
	@PastOrPresent
	@JsonProperty("Date")
	private LocalDate date;
	
	@NotBlank
	@JsonProperty("nature")
	private String nature;
	
	@NotNull
	@Positive
	@JsonProperty("montant")
	private BigDecimal montant;

	/** Constructeur
	 * 
	 */
	public CreerLigneFraisDto() {
		super();
	}

	/** Getter
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/** Setter
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/** Getter
	 * @return the nature
	 */
	public String getNature() {
		return nature;
	}

	/** Setter
	 * @param nature the nature to set
	 */
	public void setNature(String nature) {
		this.nature = nature;
	}

	/** Getter
	 * @return the montant
	 */
	public BigDecimal getMontant() {
		return montant;
	}

	/** Setter
	 * @param montant the montant to set
	 */
	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}
	
}
