package dev.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.domain.Nature;
import dev.domain.SignatureNumerique;
import dev.repository.NatureRepo;

@Service
public class NatureService {

	private NatureRepo repo;

	NatureService(NatureRepo repo){
		this.repo = repo;
	}

	@Transactional
	public Nature creer(SignatureNumerique signatureNumerique, String libelle, Boolean payee, Boolean versementPrime,
			BigDecimal pourcentagePrime, LocalDate debutValidite, LocalDate finValidite, BigDecimal plafondFrais,
			BigDecimal depassementFrais) {
		Nature nature = new Nature(signatureNumerique, libelle, payee, versementPrime, 
				pourcentagePrime, debutValidite, finValidite, plafondFrais, depassementFrais);

		Nature natureSauvegardee = this.repo.save(nature);

		return natureSauvegardee;
	}


	public List<Nature> lister(){
		return repo.findAll();
	}

	public Optional<Nature> getNature(UUID id){
		return repo.findById(id);
	}

}
