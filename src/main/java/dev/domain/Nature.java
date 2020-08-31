package dev.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
/** classe conceptualisant une nature
 * @author antoinelabeeuw
 *
 */
@Entity
public class Nature extends EntiteModifiable {
	/** payee */
	@NotNull
	private Boolean payee;
	/** versementPrime */
	@NotNull
	private Boolean versementPrime;
	/** pourcentagePrime */
	private BigDecimal pourcentagePrime;
	/** debutValidite */
	@NotNull
	private LocalDate debutValidite;
	/** finValidite */
	@NotNull
	private LocalDate finValidite;
	/** plafondFrais */
	@NotNull
	private BigDecimal plafondFrais;
	/** depassementFrais */
	@NotNull
	private BigDecimal depassementFrais;
	
	public Nature(SignatureNumerique signatureNumerique) {
		super(signatureNumerique);
	}

	/** Getter
	 * @return the payee
	 */
	public Boolean getPayee() {
		return payee;
	}

	/** Setter
	 * @param payee the payee to set
	 */
	public void setPayee(Boolean payee) {
		this.payee = payee;
	}

	/** Getter
	 * @return the versementPrime
	 */
	public Boolean getVersementPrime() {
		return versementPrime;
	}

	/** Setter
	 * @param versementPrime the versementPrime to set
	 */
	public void setVersementPrime(Boolean versementPrime) {
		this.versementPrime = versementPrime;
	}

	/** Getter
	 * @return the pourcentagePrime
	 */
	public BigDecimal getPourcentagePrime() {
		return pourcentagePrime;
	}

	/** Setter
	 * @param pourcentagePrime the pourcentagePrime to set
	 */
	public void setPourcentagePrime(BigDecimal pourcentagePrime) {
		this.pourcentagePrime = pourcentagePrime;
	}

	/** Getter
	 * @return the debutValidite
	 */
	public LocalDate getDebutValidite() {
		return debutValidite;
	}

	/** Setter
	 * @param debutValidite the debutValidite to set
	 */
	public void setDebutValidite(LocalDate debutValidite) {
		this.debutValidite = debutValidite;
	}

	/** Getter
	 * @return the finValidite
	 */
	public LocalDate getFinValidite() {
		return finValidite;
	}

	/** Setter
	 * @param finValidite the finValidite to set
	 */
	public void setFinValidite(LocalDate finValidite) {
		this.finValidite = finValidite;
	}

	/** Getter
	 * @return the plafondFrais
	 */
	public BigDecimal getPlafondFrais() {
		return plafondFrais;
	}

	/** Setter
	 * @param plafondFrais the plafondFrais to set
	 */
	public void setPlafondFrais(BigDecimal plafondFrais) {
		this.plafondFrais = plafondFrais;
	}

	/** Getter
	 * @return the depassementFrais
	 */
	public BigDecimal getDepassementFrais() {
		return depassementFrais;
	}

	/** Setter
	 * @param depassementFrais the depassementFrais to set
	 */
	public void setDepassementFrais(BigDecimal depassementFrais) {
		this.depassementFrais = depassementFrais;
	}
	
}
