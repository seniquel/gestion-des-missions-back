package dev;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.domain.Collegue;
import dev.domain.LigneDeFrais;
import dev.domain.Mission;
import dev.domain.Nature;
import dev.domain.NoteDeFrais;
import dev.domain.Role;
import dev.domain.SignatureNumerique;
import dev.domain.Statut;
import dev.domain.Transport;
import dev.repository.CollegueRepo;
import dev.repository.LigneDeFraisRepo;
import dev.repository.MissionRepo;
import dev.repository.NatureRepo;
import dev.repository.NoteDeFraisRepo;

/**
 * Code de démarrage de l'application. Insertion de jeux de données.
 */
@Component
@Transactional
public class StartupListener {

	private String appVersion;
	private PasswordEncoder passwordEncoder;
	private CollegueRepo collegueRepo;
	private NatureRepo natureRepo;
	private MissionRepo missionRepo;
	private NoteDeFraisRepo noteRepo;
	private LigneDeFraisRepo ligneRepo;

	public StartupListener(@Value("${app.version}") String appVersion, PasswordEncoder passwordEncoder,
			CollegueRepo collegueRepo, NatureRepo natureRepo, MissionRepo missionRepo, NoteDeFraisRepo noteRepo,
			LigneDeFraisRepo ligneRepo) {
		this.appVersion = appVersion;
		this.passwordEncoder = passwordEncoder;
		this.collegueRepo = collegueRepo;
		this.natureRepo = natureRepo;
		this.missionRepo = missionRepo;
		this.noteRepo = noteRepo;
		this.ligneRepo = ligneRepo;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void onStart() {

		// Création de deux utilisateurs
		Collegue col1 = new Collegue();
		// col1.setSignatureNumerique(new SignatureNumerique());
		col1.setNom("Admin");
		col1.setPrenom("DEV");
		col1.setEmail("admin@dev.fr");
		col1.setMotDePasse(passwordEncoder.encode("superpass"));
		col1.setRole(Role.ROLE_ADMINISTRATEUR);

		Collegue col2 = new Collegue();
		// col2.setSignatureNumerique(new SignatureNumerique());
		col2.setNom("User");
		col2.setPrenom("DEV");
		col2.setEmail("user@dev.fr");
		col2.setMotDePasse(passwordEncoder.encode("superpass"));
		col2.setRole(Role.ROLE_UTILISATEUR);
		this.collegueRepo.save(col2);

		Collegue col3 = new Collegue();
		// col3.setSignatureNumerique(new SignatureNumerique());
		col3.setNom("Manager");
		col3.setPrenom("DEV");
		col3.setEmail("manager@dev.fr");
		col3.setMotDePasse(passwordEncoder.encode("superpass"));
		col3.setRole(Role.ROLE_MANAGER);
		this.collegueRepo.save(col3);

		// Création de missions pour un collègue
		Nature nat1 = new Nature();
		nat1.setLibelle("Formation");
		nat1.setPayee(true);
		nat1.setVersementPrime(true);
		nat1.setPourcentagePrime(BigDecimal.valueOf(10));
		nat1.setDebutValidite(LocalDate.now().minusMonths(10));
		nat1.setFinValidite(LocalDate.now().plusMonths(3));
		nat1.setPlafondFrais(BigDecimal.valueOf(150));
		nat1.setDepassementFrais(true);

		Mission mis1 = new Mission();
		mis1.setSignatureNumerique(new SignatureNumerique());
		mis1.setDateDebut(LocalDate.now());
		mis1.setDateFin(LocalDate.now().plusDays(10));
		mis1.setVilleDepart("Mickeyville");
		mis1.setVilleArrivee("Donaldville");
		mis1.setPrime(BigDecimal.valueOf(1000));
		mis1.setStatut(Statut.INITIALE);
		mis1.setTransport(Transport.TRAIN);

		NoteDeFrais note1 = new NoteDeFrais();
		note1.setDateDeSaisie(LocalDate.now());
		note1.setSignatureNumerique(new SignatureNumerique());
		this.noteRepo.save(note1);


		LigneDeFrais l1 = new LigneDeFrais();
		l1.setDate(LocalDate.now());
		l1.setNature("hotel bien cher");
		l1.setMontant(BigDecimal.valueOf(200));
		l1.setNoteDeFrais(note1);

		LigneDeFrais l2 = new LigneDeFrais();
		l2.setDate(LocalDate.now());
		l2.setNature("petit pain");
		l2.setMontant(BigDecimal.valueOf(0.15));
		l2.setNoteDeFrais(note1);

		mis1.setNature(nat1);
		note1.addLigneFrais(l1);
		note1.addLigneFrais(l2);
		mis1.setNoteDeFrais(note1);
		col1.addMission(mis1);

		this.ligneRepo.save(l1);
		this.ligneRepo.save(l2);
		this.natureRepo.save(nat1);
		this.collegueRepo.save(col1);
		this.missionRepo.save(mis1);

	}

}
