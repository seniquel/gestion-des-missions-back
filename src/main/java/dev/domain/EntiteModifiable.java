package dev.domain;

import javax.persistence.MappedSuperclass;

/**
 * @author antoinelabeeuw
 *
 */
@MappedSuperclass
public class EntiteModifiable {
	/** signatureNumerique */
	private SignatureNumerique signatureNumerique;

	/**
	 * Constructeur
	 * 
	 * @param signatureNumerique
	 */
	public EntiteModifiable(SignatureNumerique signatureNumerique) {
		super();
		this.signatureNumerique = signatureNumerique;
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
