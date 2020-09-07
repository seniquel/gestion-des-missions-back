package dev.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;

/**
 * classe conceptualisant une nature
 * 
 * @author antoinelabeeuw
 *
 */
@Entity
public class Nature extends EntiteModifiable {
	
	/** libelle */
	private String libelle;
	
	/** payee */
	private Boolean payee;
	
	/** versementPrime */
	private Boolean versementPrime;
	
	/** taux journalier */
	private BigDecimal tjm;
	
	/** pourcentagePrime */
	private BigDecimal pourcentagePrime;
	
	/** debutValidite */
	private LocalDate debutValidite;
	
	/** finValidite */
	private LocalDate finValidite;
	
	/** plafondFrais */
	private BigDecimal plafondFrais;
	
	/** depassementFrais */
	private Boolean depassementFrais;
	
	
	/**
	 * Constructeur
	 * 
	 */
	public Nature() {
	}

	/**
	 * Constructeur
	 * 
	 * @param tjm
	 * @param libelle
	 * @param payee
	 * @param versementPrime
	 * @param pourcentagePrime
	 * @param debutValidite
	 * @param plafondFrais
	 * @param depassementFrais
	 * @param missions
	 */
	public Nature(String libelle, Boolean payee, BigDecimal tjm, Boolean versementPrime,
			BigDecimal pourcentagePrime, LocalDate debutValidite, BigDecimal plafondFrais,
			Boolean depassementFrais) {
		this.libelle = libelle;
		this.payee = payee;
		this.tjm = tjm;
		this.versementPrime = versementPrime;
		this.pourcentagePrime = pourcentagePrime;
		this.debutValidite = debutValidite;
		this.plafondFrais = plafondFrais;
		this.depassementFrais = depassementFrais;
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public BigDecimal getTjm() {
		return tjm;
	}

	/**
	 * Setter
	 * 
	 * @param tjm
	 */
	public void setTjm(BigDecimal tjm) {
		this.tjm = tjm;
	}

	/**
	 * Getter
	 * 
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Setter
	 * 
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * Getter
	 * 
	 * @return the payee
	 */
	public Boolean getPayee() {
		return payee;
	}

	/**
	 * Setter
	 * 
	 * @param payee the payee to set
	 */
	public void setPayee(Boolean payee) {
		this.payee = payee;
	}

	/**
	 * Getter
	 * 
	 * @return the versementPrime
	 */
	public Boolean getVersementPrime() {
		return versementPrime;
	}

	/**
	 * Setter
	 * 
	 * @param versementPrime the versementPrime to set
	 */
	public void setVersementPrime(Boolean versementPrime) {
		this.versementPrime = versementPrime;
	}

	/**
	 * Getter
	 * 
	 * @return the pourcentagePrime
	 */
	public BigDecimal getPourcentagePrime() {
		return pourcentagePrime;
	}

	/**
	 * Setter
	 * 
	 * @param pourcentagePrime the pourcentagePrime to set
	 */
	public void setPourcentagePrime(BigDecimal pourcentagePrime) {
		this.pourcentagePrime = pourcentagePrime;
	}

	/**
	 * Getter
	 * 
	 * @return the debutValidite
	 */
	public LocalDate getDebutValidite() {
		return debutValidite;
	}

	/**
	 * Setter
	 * 
	 * @param debutValidite the debutValidite to set
	 */
	public void setDebutValidite(LocalDate debutValidite) {
		this.debutValidite = debutValidite;
	}

	/**
	 * Getter
	 * 
	 * @return the finValidite
	 */
	public LocalDate getFinValidite() {
		return finValidite;
	}

	/**
	 * Setter
	 * 
	 * @param finValidite the finValidite to set
	 */
	public void setFinValidite(LocalDate finValidite) {
		this.finValidite = finValidite;
	}

	/**
	 * Getter
	 * 
	 * @return the plafondFrais
	 */
	public BigDecimal getPlafondFrais() {
		return plafondFrais;
	}

	/**
	 * Setter
	 * 
	 * @param plafondFrais the plafondFrais to set
	 */
	public void setPlafondFrais(BigDecimal plafondFrais) {
		this.plafondFrais = plafondFrais;
	}

	/**
	 * Getter
	 * 
	 * @return the depassementFrais
	 */
	public Boolean getDepassementFrais() {
		return depassementFrais;
	}

	/**
	 * Setter
	 * 
	 * @param depassementFrais the depassementFrais to set
	 */
	public void setDepassementFrais(Boolean depassementFrais) {
		this.depassementFrais = depassementFrais;
	}

}
