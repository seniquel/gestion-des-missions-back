package dev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dev.domain.Collegue;
import dev.domain.LigneDeFrais;
import dev.domain.Mission;
import dev.domain.Nature;
import dev.domain.NoteDeFrais;
import dev.domain.Role;
import dev.domain.SignatureNumerique;
import dev.domain.Statut;
import dev.domain.Transport;
import dev.repository.LigneDeFraisRepo;
import dev.security.JWTAuthenticationSuccessHandler;
import dev.security.JWTAuthorizationFilter;
import dev.security.WebSecurityConfig;

@WebMvcTest(value = LigneDeFraisService.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		WebSecurityConfig.class, JWTAuthenticationSuccessHandler.class, JWTAuthorizationFilter.class }))
@AutoConfigureMockMvc(addFilters = false)
public class LigneDeFraisServiceTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;
	
	@Autowired
	LigneDeFraisService ligService;

	@MockBean
	LigneDeFraisRepo ligRepo;
	
	@MockBean
	NoteDeFraisService noteService;
	
	protected List<LigneDeFrais> lignes;
	Mission mis1 = new Mission();
	NoteDeFrais note1 = new NoteDeFrais(); 

	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	@BeforeEach
	void setup() {
		lignes = new ArrayList<>();
		
		Collegue col1 = new Collegue();
		col1.setNom("Admin");
		col1.setPrenom("DEV");
		col1.setEmail("admin@dev.fr");
		col1.setMotDePasse("superpass");
		col1.setRole(Role.ROLE_ADMINISTRATEUR);
		
		Nature nat3 = new Nature();
		nat3.setLibelle("Expertise technique");
		nat3.setPayee(true);
		nat3.setTjm(BigDecimal.valueOf(1000));
		nat3.setVersementPrime(true);
		nat3.setPourcentagePrime(BigDecimal.valueOf(4));
		nat3.setDebutValidite(LocalDate.now().minusMonths(10));
		nat3.setPlafondFrais(BigDecimal.valueOf(150));
		nat3.setDepassementFrais(true);
		
		LigneDeFrais l1 = new LigneDeFrais();
		l1.setUuid(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b934"));
		l1.setDate(LocalDate.of(2020, 9, 8));
		l1.setNature("hotel bien cher");
		l1.setMontant(BigDecimal.valueOf(200));

		LigneDeFrais l2 = new LigneDeFrais();
		l2.setUuid(UUID.fromString("ab45410c-575b-4b14-8d8f-6ed13f5644de"));
		l2.setDate(LocalDate.of(2020, 9, 7));
		l2.setNature("petit pain");
		l2.setMontant(BigDecimal.valueOf(0.15));
		lignes.add(l1);
		lignes.add(l2);
		
		mis1.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));
		mis1.setDateDebut(LocalDate.now());
		mis1.setDateFin(LocalDate.now().plusDays(10));
		mis1.setVilleDepart("Mickeyville");
		mis1.setVilleArrivee("Donaldville");
		mis1.setPrime(BigDecimal.valueOf(1000));
		mis1.setStatut(Statut.VALIDEE);
		mis1.setTransport(Transport.TRAIN);
		
		note1.setDateDeSaisie(LocalDate.now());
		note1.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));
		note1.setLignesDeFrais(lignes);
		note1.calculerFraisTotal();

		note1.addLigneFrais(l1);
		note1.addLigneFrais(l2);
		mis1.setNoteDeFrais(note1);
		note1.setMission(mis1);
		col1.addMission(mis1);
		mis1.setNature(nat3);



	}
	
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void testerDoublonLigne() {
		assertThat(ligService.verifierDoublonLigne(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b934"),
				LocalDate.of(2020, 9, 7), "petit pain", lignes)).isFalse();
	}
	
	/**
	 * Test si doublon (même infos que l2, uuid de l1)
	 */
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void testerNoDoublonLigne() {
		assertThat(ligService.verifierDoublonLigne(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b777"),
				LocalDate.of(2020, 9, 7), "W/e", lignes)).isTrue();
	}
	
	/**
	 * test pour la modification de montant
	 * montant < prime, test doit etre passant
	 */
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void testerMontantAModifierOk() {
		assertThat(ligService.verifierMontantAModifier(BigDecimal.valueOf(200), BigDecimal.valueOf(30), note1)).isTrue();
	}
	
	/**
	 * test pour la modification de montant
	 * montant < prime, test doit etre passant
	 */
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void testerMontantAModifierTropGrand() {
		assertThat(ligService.verifierMontantAModifier(BigDecimal.valueOf(200), BigDecimal.valueOf(30000), note1)).isFalse();
	}
}
