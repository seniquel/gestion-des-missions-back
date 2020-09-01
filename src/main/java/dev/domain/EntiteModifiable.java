package dev.domain;

import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * classe de base modifiable
 * 
 * @author antoinelabeeuw
 *
 */
@MappedSuperclass
public class EntiteModifiable {
	/** id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected UUID id;
	/** signatureNumerique */
	protected SignatureNumerique signatureNumerique;

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
