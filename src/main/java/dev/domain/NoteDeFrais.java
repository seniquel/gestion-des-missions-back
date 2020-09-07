package dev.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class NoteDeFrais extends EntiteModifiable {
	/** dateDeSaisie */
	private LocalDate dateDeSaisie;

	@OneToMany(mappedBy = "noteDeFrais")
	@JsonManagedReference
	private List<LigneDeFrais> lignesDeFrais = new ArrayList<>();

	@OneToOne(mappedBy="noteDeFrais")
	@JsonBackReference
	private Mission mission;
	
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
	}
	
	public void removeLigneFrais(LigneDeFrais ligneDeFrais) {
		this.lignesDeFrais.remove(ligneDeFrais);
		ligneDeFrais.setNoteDeFrais(null);
	}

}
