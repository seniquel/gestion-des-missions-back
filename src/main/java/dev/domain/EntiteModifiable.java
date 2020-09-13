package dev.domain;

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
	protected SignatureNumerique signatureNumerique;
	
	public EntiteModifiable() {
		super();
		this.signatureNumerique = new SignatureNumerique();
	}

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
