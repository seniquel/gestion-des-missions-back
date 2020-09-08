package dev;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
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
/*
		// Création de missions pour un collègue
		Nature nat1 = new Nature();
		nat1.setLibelle("Formation");
		nat1.setPayee(true);
		nat1.setVersementPrime(true);
		nat1.setPourcentagePrime(BigDecimal.valueOf(10));
		nat1.setDebutValidite(LocalDate.now().minusMonths(10));
		nat1.setPlafondFrais(BigDecimal.valueOf(150));
		nat1.setDepassementFrais(true);

		Nature nat2 = new Nature();
		nat2.setLibelle("Conseil");
		nat2.setPayee(true);
		nat2.setTjm(BigDecimal.valueOf(750));
		nat2.setVersementPrime(true);
		nat2.setPourcentagePrime(BigDecimal.valueOf(3.5));
		nat2.setDebutValidite(LocalDate.now().minusMonths(10));
		nat2.setPlafondFrais(BigDecimal.valueOf(150));
		nat2.setDepassementFrais(true);

		Nature nat3 = new Nature();
		nat3.setLibelle("Expertise technique");
		nat3.setPayee(true);
		nat3.setTjm(BigDecimal.valueOf(1000));
		nat3.setVersementPrime(true);
		nat3.setPourcentagePrime(BigDecimal.valueOf(4));
		nat3.setDebutValidite(LocalDate.now().minusMonths(10));
		nat3.setPlafondFrais(BigDecimal.valueOf(150));
		nat3.setDepassementFrais(true);

		Mission mis1 = new Mission();
		mis1.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));
		mis1.setDateDebut(LocalDate.now());
		mis1.setDateFin(LocalDate.now().plusDays(10));
		mis1.setVilleDepart("Mickeyville");
		mis1.setVilleArrivee("Donaldville");
		mis1.setPrime(BigDecimal.valueOf(1000));
		mis1.setStatut(Statut.VALIDEE);
		mis1.setTransport(Transport.TRAIN);

		Mission mis2 = new Mission();
		mis2.setSignatureNumerique(new SignatureNumerique());
		mis2.setDateDebut(LocalDate.of(2020, 5, 2));
		mis2.setDateFin(LocalDate.of(2020, 5, 8));
		mis2.setVilleDepart("Mickeyville");
		mis2.setVilleArrivee("Donaldville");
		mis2.setPrime(BigDecimal.valueOf(500));
		mis2.setStatut(Statut.VALIDEE);
		mis2.setTransport(Transport.TRAIN);

		Mission mis3 = new Mission();
		mis3.setSignatureNumerique(new SignatureNumerique());
		mis3.setDateDebut(LocalDate.of(2020, 7, 30));
		mis3.setDateFin(LocalDate.of(2020, 8, 6));
		mis3.setVilleDepart("Mickeyville");
		mis3.setVilleArrivee("Donaldville");
		mis3.setPrime(BigDecimal.valueOf(100));
		mis3.setStatut(Statut.VALIDEE);
		mis3.setTransport(Transport.TRAIN);

		Mission mis4 = new Mission();
		mis4.setSignatureNumerique(new SignatureNumerique());
		mis4.setDateDebut(LocalDate.of(2019, 7, 30));
		mis4.setDateFin(LocalDate.of(2019, 8, 6));
		mis4.setVilleDepart("Mickeyville");
		mis4.setVilleArrivee("Donaldville");
		mis4.setPrime(BigDecimal.valueOf(100));
		mis4.setStatut(Statut.VALIDEE);
		mis4.setTransport(Transport.TRAIN);

		Mission mis5 = new Mission();
		mis5.setSignatureNumerique(new SignatureNumerique());
		mis5.setDateDebut(LocalDate.of(2020, 10, 3));
		mis5.setDateFin(LocalDate.of(2020, 10, 16));
		mis5.setVilleDepart("Mickeyville");
		mis5.setVilleArrivee("Donaldville");
		mis5.setStatut(Statut.EN_ATTENTE_VALIDATION);
		mis5.setTransport(Transport.TRAIN);

		Mission mis6 = new Mission();
		mis6.setSignatureNumerique(new SignatureNumerique());
		mis6.setDateDebut(LocalDate.of(2020, 10, 3));
		mis6.setDateFin(LocalDate.of(2020, 10, 16));
		mis6.setVilleDepart("Mickeyville");
		mis6.setVilleArrivee("Donaldville");
		mis6.setStatut(Statut.EN_ATTENTE_VALIDATION);
		mis6.setTransport(Transport.TRAIN);

		Mission mis7 = new Mission();
		mis7.setSignatureNumerique(new SignatureNumerique());
		mis7.setDateDebut(LocalDate.now().plusDays(11));
		mis7.setDateFin(LocalDate.now().plusDays(20));
		mis7.setVilleDepart("Mickeyville");
		mis7.setVilleArrivee("Donaldville");
		mis7.setPrime(BigDecimal.valueOf(1000));
		mis7.setStatut(Statut.INITIALE);
		mis7.setTransport(Transport.TRAIN);

		NoteDeFrais note1 = new NoteDeFrais();
		note1.setDateDeSaisie(LocalDate.now());
		note1.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));

		NoteDeFrais note2 = new NoteDeFrais();
		note2.setDateDeSaisie(LocalDate.now());
		note2.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));

		NoteDeFrais note3 = new NoteDeFrais();
		note3.setDateDeSaisie(LocalDate.now());
		note3.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));

		NoteDeFrais note4 = new NoteDeFrais();
		note4.setDateDeSaisie(LocalDate.now());
		note4.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));

		NoteDeFrais note5 = new NoteDeFrais();
		note5.setDateDeSaisie(LocalDate.now());
		note5.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));

		NoteDeFrais note6 = new NoteDeFrais();
		note6.setDateDeSaisie(LocalDate.now());
		note6.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));

		NoteDeFrais note7 = new NoteDeFrais();
		note7.setDateDeSaisie(LocalDate.now());
		note7.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));

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
		note1.setMission(mis1);
		col1.addMission(mis1);
		mis2.setNature(nat1);
		mis3.setNature(nat1);
		mis4.setNature(nat1);
		mis5.setNature(nat1);
		mis6.setNature(nat1);
		mis7.setNature(nat2);

		mis2.setNoteDeFrais(note2);
		mis3.setNoteDeFrais(note3);
		mis4.setNoteDeFrais(note4);
		mis5.setNoteDeFrais(note5);
		mis6.setNoteDeFrais(note6);
		mis7.setNoteDeFrais(note7);

		col1.addMission(mis2);
		col1.addMission(mis3);
		col1.addMission(mis4);
		col2.addMission(mis5);
		col2.addMission(mis6);
		col1.addMission(mis7);

		this.noteRepo.save(note1);
		this.ligneRepo.save(l1);
		this.ligneRepo.save(l2);
		this.noteRepo.save(note2);
		this.noteRepo.save(note3);
		this.noteRepo.save(note4);
		this.noteRepo.save(note5);
		this.noteRepo.save(note6);
		this.noteRepo.save(note7);
		this.natureRepo.save(nat1);
		this.natureRepo.save(nat2);
		this.natureRepo.save(nat3);
		this.collegueRepo.save(col1);
		this.missionRepo.save(mis1);
		this.missionRepo.save(mis2);
		this.missionRepo.save(mis3);
		this.missionRepo.save(mis4);
		this.missionRepo.save(mis5);
		this.missionRepo.save(mis6);
		this.missionRepo.save(mis7);
*/
	}

}
