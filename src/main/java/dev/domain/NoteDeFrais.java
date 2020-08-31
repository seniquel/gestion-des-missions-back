package dev.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class NoteDeFrais extends EntiteModifiable {
	/** dateDeSaisie */
	private LocalDate dateDeSaisie;

	@OneToMany(mappedBy = "noteDeFrais")
	private List<LigneDeFrais> lignesDeFrais;

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 */
	public NoteDeFrais(SignatureNumerique signatureNumerique) {
		super(signatureNumerique);
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
		super(signatureNumerique);
		this.dateDeSaisie = dateDeSaisie;
		this.lignesDeFrais = lignesDeFrais;
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

}
