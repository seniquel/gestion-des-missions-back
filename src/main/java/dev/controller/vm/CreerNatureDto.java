package dev.controller.vm;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreerNatureDto {
	
	/** libelle */
	@NotNull
	@Size(min=2)
	@JsonProperty("libelle")
	private String libelle;
	
	/** payee */
	@NotNull
	@JsonProperty("payee")
	private Boolean payee;
	
	/** versementPrime */
	@NotNull
	@JsonProperty("versementPrime")
	private Boolean versementPrime;
	
	/** Taux journalier */
	@NotNull
	@JsonProperty("tjm")
	private BigDecimal tjm;
	
	/** pourcentagePrime */
	@NotNull
	@DecimalMax("10")
	@JsonProperty("pourcentagePrime")
	private BigDecimal pourcentagePrime;
	
	/** debutValidite */
	@NotNull
	@JsonProperty("debutValidite")
	private LocalDate debutValidite;
	
	/** plafondFrais */
	@NotNull
	@PositiveOrZero
	@JsonProperty("plafondFrais")
	private BigDecimal plafondFrais;
	
	/** depassementFrais */
	@NotNull
	@JsonProperty("depassementFrais")
	private Boolean depassementFrais;
	
	public CreerNatureDto() {
		this.debutValidite = LocalDate.now();
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public Boolean getPayee() {
		return payee;
	}
	
	public void setPayee(Boolean payee) {
		this.payee = payee;
	}
	
	public Boolean getVersementPrime() {
		return versementPrime;
	}
	
	public void setVersementPrime(Boolean versementPrime) {
		this.versementPrime = versementPrime;
	}
	
	public BigDecimal getTjm() {
		return tjm;
	}
	
	public void setTjm(BigDecimal tjm) {
		this.tjm = tjm;
	}
	
	public BigDecimal getPourcentagePrime() {
		return pourcentagePrime;
	}
	
	public void setPourcentagePrime(BigDecimal pourcentagePrime) {
		this.pourcentagePrime = pourcentagePrime;
	}
	
	public LocalDate getDebutValidite() {
		return debutValidite;
	}
	
	public BigDecimal getPlafondFrais() {
		return plafondFrais;
	}
	
	public void setPlafondFrais(BigDecimal plafondFrais) {
		this.plafondFrais = plafondFrais;
	}
	
	public Boolean getDepassementFrais() {
		return depassementFrais;
	}
	
	public void setDepassementFrais(Boolean depassementFrais) {
		this.depassementFrais = depassementFrais;
	}

}
