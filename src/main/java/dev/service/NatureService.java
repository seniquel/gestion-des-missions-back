package dev.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.domain.Nature;
import dev.repository.NatureRepo;

@Service
public class NatureService {

	private NatureRepo repo;

	NatureService(NatureRepo repo){
		this.repo = repo;
	}

	@Transactional
	public Nature creer(String libelle, Boolean payee, BigDecimal tjm, Boolean versementPrime,
			BigDecimal pourcentagePrime, BigDecimal plafondFrais,
			Boolean depassementFrais) {
		Nature nature = new Nature(libelle, payee, tjm, versementPrime, pourcentagePrime, LocalDate.now(), plafondFrais, depassementFrais);

		Nature natureSauvegardee = this.repo.save(nature);

		return natureSauvegardee;
	}

	public List<Nature> lister(){
		return repo.findAll();
	}

	public Optional<Nature> getNature(UUID id){
		return repo.findById(id);
	}
	
	@Transactional
	public void update(Nature nature) {
		this.repo.save(nature);
	}
	
	@Transactional
	public void delete(Nature nature) {
		this.repo.deleteById(nature.getUuid());;
	}

}
