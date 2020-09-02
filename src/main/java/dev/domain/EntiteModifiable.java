package dev.domain;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 * classe de base modifiable
 * 
 * @author antoinelabeeuw
 *
 */
@MappedSuperclass
public class EntiteModifiable extends EntiteBase {
	/** signatureNumerique */
	@Embedded
	protected SignatureNumerique signatureNumerique;

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 */
	public EntiteModifiable() {
	}

	/**
	 * Getter
	 * 
	 * @return the signatureNumerique
	 */
	public SignatureNumerique getSignatureNumerique() {
		return signatureNumerique;
	}

	/**
	 * Setter
	 * 
	 * @param signatureNumerique the signatureNumerique to set
	 */
	public void setSignatureNumerique(SignatureNumerique signatureNumerique) {
		this.signatureNumerique = signatureNumerique;
	}
}
