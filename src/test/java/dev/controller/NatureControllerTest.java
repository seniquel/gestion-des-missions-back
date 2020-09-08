package dev.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import dev.domain.Nature;
import dev.security.JWTAuthenticationSuccessHandler;
import dev.security.JWTAuthorizationFilter;
import dev.security.WebSecurityConfig;
import dev.service.NatureService;

@WebMvcTest(value = NatureController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		WebSecurityConfig.class, JWTAuthenticationSuccessHandler.class, JWTAuthorizationFilter.class }))
@AutoConfigureMockMvc(addFilters = false)
public class NatureControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@MockBean
	NatureService natService;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	private List<Nature> natures = new ArrayList<>();

	@BeforeEach
	void setup() {
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

		natures.add(nat1);
		natures.add(nat2);
		natures.add(nat3);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void findAllNaturesTest() throws Exception {
		Mockito.when(natService.lister()).thenReturn(natures);

		mockMvc.perform(MockMvcRequestBuilders.get("/natures")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].libelle").value("Formation"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].tjm").value("750"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].pourcentagePrime").value("4"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void AjouterNatureValideTest() throws Exception {
		Nature nat4 = new Nature();
		nat4.setLibelle("test");
		nat4.setPayee(true);
		nat4.setTjm(BigDecimal.valueOf(200));
		nat4.setVersementPrime(true);
		nat4.setPourcentagePrime(BigDecimal.valueOf(7));
		nat4.setDebutValidite(LocalDate.of(2020, 9, 6));
		nat4.setPlafondFrais(BigDecimal.valueOf(300));
		nat4.setDepassementFrais(true);
		Mockito.when(natService.lister()).thenReturn(natures);
		Mockito.when(natService.creer("test", true, BigDecimal.valueOf(200), true, BigDecimal.valueOf(7),
				LocalDate.of(2020, 9, 6), BigDecimal.valueOf(300), true)).thenReturn(nat4);
		String jsonBody = "{ \"libelle\": \"test\", \"payee\": true, \"versementPrime\": true, \"tjm\": 200, \"pourcentagePrime\": 7, \"debutValidite\": \"2020-09-06\", \"plafondFrais\": 300, \"depassementFrais\": true }";
		mockMvc.perform(MockMvcRequestBuilders.post("/natures").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonBody)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("tjm").value(200));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	void AjouterNatureInvalideTest() throws Exception {
		Nature nat4 = new Nature();
		nat4.setLibelle("test");
		nat4.setPayee(true);
		nat4.setTjm(BigDecimal.valueOf(200));
		nat4.setVersementPrime(true);
		nat4.setPourcentagePrime(BigDecimal.valueOf(7));
		nat4.setDebutValidite(LocalDate.of(2020, 9, 6));
		nat4.setPlafondFrais(BigDecimal.valueOf(300));
		nat4.setDepassementFrais(true);
		Mockito.when(natService.lister()).thenReturn(natures);
		Mockito.when(natService.creer("Formation", true, BigDecimal.valueOf(200), true, BigDecimal.valueOf(7),
				LocalDate.of(2020, 9, 6), BigDecimal.valueOf(300), true)).thenReturn(nat4);
		String jsonBody = "{ \"libelle\": \"Formation\", \"payee\": true, \"versementPrime\": true, \"tjm\": 200, \"pourcentagePrime\": 7, \"debutValidite\": \"2020-09-06\", \"plafondFrais\": 300, \"depassementFrais\": true }";
		mockMvc.perform(MockMvcRequestBuilders.post("/natures").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonBody)).andExpect(MockMvcResultMatchers.status().isConflict())
				.andExpect(MockMvcResultMatchers.content().string("Nature existante"));
	}

}
