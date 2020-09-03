package dev.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class NoteDeFrais extends EntiteModifiable {
	/** dateDeSaisie */
	private LocalDate dateDeSaisie;

	private BigDecimal fraisTotal = BigDecimal.valueOf(0);

	@OneToMany(mappedBy = "noteDeFrais", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<LigneDeFrais> lignesDeFrais = new ArrayList<>();

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 */
	public NoteDeFrais() {
	}

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 * @param dateDeSaisie
	 * @param lignesDeFrais
	 */
	public NoteDeFrais(SignatureNumerique signatureNumerique, LocalDate dateDeSaisie,
			List<LigneDeFrais> lignesDeFrais) {
		this.signatureNumerique = signatureNumerique;
		this.dateDeSaisie = dateDeSaisie;
		this.lignesDeFrais = lignesDeFrais;
		calculerFraisTotal();
	}

	/**
	 * Getter
	 * 
	 * @return the dateDeSaisie
	 */
	public LocalDate getDateDeSaisie() {
		return dateDeSaisie;
	}

	/**
	 * Setter
	 * 
	 * @param dateDeSaisie the dateDeSaisie to set
	 */
	public void setDateDeSaisie(LocalDate dateDeSaisie) {
		this.dateDeSaisie = dateDeSaisie;
	}

	/**
	 * Getter
	 * 
	 * @return the lignesDeFrais
	 */
	public List<LigneDeFrais> getLignesDeFrais() {
		return lignesDeFrais;
	}

	/**
	 * Setter
	 * 
	 * @param lignesDeFrais the lignesDeFrais to set
	 */
	public void setLignesDeFrais(List<LigneDeFrais> lignesDeFrais) {
		this.lignesDeFrais = lignesDeFrais;
	}

	public void addLigneFrais(LigneDeFrais ligneDeFrais) {
		this.lignesDeFrais.add(ligneDeFrais);
		ligneDeFrais.setNoteDeFrais(this);
		this.fraisTotal = this.fraisTotal.add(ligneDeFrais.getMontant());
	}

	public void removeLigneFrais(LigneDeFrais ligneDeFrais) {
		this.lignesDeFrais.remove(ligneDeFrais);
		ligneDeFrais.setNoteDeFrais(null);
		this.fraisTotal = this.fraisTotal.subtract(ligneDeFrais.getMontant());
	}

	/**
	 * Getter
	 * 
	 * @return the fraisTotal
	 */
	public BigDecimal getFraisTotal() {
		return fraisTotal;
	}

	/**
	 * Setter
	 * 
	 * @param fraisTotal the fraisTotal to set
	 */
	public void setFraisTotal(BigDecimal fraisTotal) {
		this.fraisTotal = fraisTotal;
	}

	/**
	 * Calcule automatiquement les frais totaux a partir des lignes de frais de la
	 * note
	 * 
	 */
	public void calculerFraisTotal() {
		if (!this.lignesDeFrais.isEmpty()) {
			for (LigneDeFrais ligneDeFrais : lignesDeFrais) {
				this.fraisTotal = this.fraisTotal.add(ligneDeFrais.getMontant());
			}
		}
	}
}
