package dev.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dev.domain.LigneDeFrais;
import dev.security.JWTAuthenticationSuccessHandler;
import dev.security.JWTAuthorizationFilter;
import dev.security.WebSecurityConfig;
import dev.service.LigneDeFraisService;

@WebMvcTest(value = LigneDeFraisController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		WebSecurityConfig.class, JWTAuthenticationSuccessHandler.class, JWTAuthorizationFilter.class }))
@AutoConfigureMockMvc(addFilters = false)
public class LigneDeFraisControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@MockBean
	LigneDeFraisService ligService;

	protected List<LigneDeFrais> lignes;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@BeforeEach
	void setup() {
		lignes = new ArrayList<>();
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
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void findAllLignessTest() throws Exception {
		Mockito.when(ligService.lister()).thenReturn(lignes);
		mockMvc.perform(MockMvcRequestBuilders.get("/lignesDeFrais")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nature").value("hotel bien cher"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].montant").value("0.15"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void SupprimerLigneTest() throws Exception {
		Mockito.when(ligService.getLigneByUuid(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b934")))
				.thenReturn(Optional.of(lignes.get(0)));
		mockMvc.perform(MockMvcRequestBuilders.delete("/lignesDeFrais/fe8a5705-2e81-4ce7-a891-865bb505b934")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	/**
	 * Test si doublon (même infos que l2, uuid de l1)
	 */
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void testerDoublonLigne() {
		Mockito.when(ligService
				.verifierDoublonLigne(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b934"),
				LocalDate.of(2020, 9, 7), "petit pain", lignes)).thenReturn(false);
		assertThat(ligService.verifierDoublonLigne(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b934"),
				LocalDate.of(2020, 9, 7), "petit pain", lignes)).isFalse();
	}

	/**
	 * Test si doublon (même infos que l2, uuid de l1)
	 */
	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void testerNoDoublonLigne() {
		Mockito.when(ligService.verifierDoublonLigne(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b777"),
				LocalDate.of(2020, 9, 7), "W/e", lignes)).thenReturn(true);
		assertThat(ligService.verifierDoublonLigne(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b777"),
				LocalDate.of(2020, 9, 7), "W/e", lignes)).isTrue();
	}
}
