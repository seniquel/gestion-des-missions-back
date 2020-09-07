package dev.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dev.DemoApplication;
import dev.domain.Collegue;
import dev.domain.Mission;
import dev.domain.Role;
import dev.domain.SignatureNumerique;
import dev.domain.Statut;
import dev.domain.Transport;
import dev.repository.CollegueRepo;
import dev.security.JWTAuthenticationSuccessHandler;
import dev.security.JWTAuthorizationFilter;
import dev.security.WebSecurityConfig;
import dev.service.CollegueService;

@WebMvcTest(value = CollegueController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfig.class, JWTAuthenticationSuccessHandler.class, JWTAuthorizationFilter.class}))
public class CollegueControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@MockBean
	CollegueService colService;

	protected List<Collegue> collegues = new ArrayList<>();

	@Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                        .webAppContextSetup(context)
                        .apply(springSecurity())
                        .build();
	}

	@BeforeEach
	void setup() {
		Collegue col1 = new Collegue();
		col1.setUuid(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b934"));
		col1.setNom("Admin");
		col1.setPrenom("DEV");
		col1.setEmail("admin@dev.fr");
		col1.setMotDePasse("superpass");
		col1.setRole(Role.ROLE_ADMINISTRATEUR);

		Collegue col2 = new Collegue();
		col2.setUuid(UUID.fromString("ab45410c-575b-4b14-8d8f-6ed13f5644de"));
		col2.setNom("User");
		col2.setPrenom("DEV");
		col2.setEmail("user@dev.fr");
		col2.setMotDePasse("superpass");
		col2.setRole(Role.ROLE_UTILISATEUR);

		Collegue col3 = new Collegue();
		col3.setUuid(UUID.fromString("81c3b8f5-1b00-4e45-9f29-0c98ef1a919f"));
		col3.setNom("Manager");
		col3.setPrenom("DEV");
		col3.setEmail("manager@dev.fr");
		col3.setMotDePasse("superpass");
		col3.setRole(Role.ROLE_MANAGER);
		
		Mission mis1 = new Mission();
		mis1.setSignatureNumerique(new SignatureNumerique(LocalDate.now()));
		mis1.setDateDebut(LocalDate.now());
		mis1.setDateFin(LocalDate.now().plusDays(10));
		mis1.setVilleDepart("Mickeyville");
		mis1.setVilleArrivee("Donaldville");
		mis1.setPrime(BigDecimal.valueOf(1000));
		mis1.setStatut(Statut.VALIDEE);
		mis1.setTransport(Transport.TRAIN);
		col1.addMission(mis1);
		collegues.add(col1);
		collegues.add(col2);
		collegues.add(col3);
	}

	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	void findAllColleguesTest() throws Exception {
		Mockito.when(colService.lister()).thenReturn(collegues);

		mockMvc.perform(MockMvcRequestBuilders.get("/collegues"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].uuid").value("fe8a5705-2e81-4ce7-a891-865bb505b934"))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].uuid").value("ab45410c-575b-4b14-8d8f-6ed13f5644de"))
		.andExpect(MockMvcResultMatchers.jsonPath("$[2].uuid").value("81c3b8f5-1b00-4e45-9f29-0c98ef1a919f"));

	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	void findCollegueParUUIDTest() throws Exception {
		Mockito.when(colService.getCollegue(UUID.fromString("81c3b8f5-1b00-4e45-9f29-0c98ef1a919f"))).thenReturn(Optional.of(collegues.get(2)));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/collegues/81c3b8f5-1b00-4e45-9f29-0c98ef1a919f"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("uuid").value("81c3b8f5-1b00-4e45-9f29-0c98ef1a919f"))
		.andExpect(MockMvcResultMatchers.jsonPath("email").value("manager@dev.fr"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	void CollegueNotFoundParUUIDTest() throws Exception {
		Mockito.when(colService.getCollegue(UUID.fromString("81c3b8f5-1b00-4e45-9f29-0c98ef1a747b"))).thenReturn(Optional.empty());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/collegues/81c3b8f5-1b00-4e45-9f29-0c98ef1a747b"))
		.andExpect(status().isNotFound())
		.andExpect(MockMvcResultMatchers.content().string("{\"code\":\"VALIDATION\",\"message\":\"Ce collegue n'existe pas\"}"));

	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	void findMissionsParUUIDCollegueTest() throws Exception {
		Mockito.when(colService.getCollegue(UUID.fromString("fe8a5705-2e81-4ce7-a891-865bb505b934"))).thenReturn(Optional.of(collegues.get(0)));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/collegues/fe8a5705-2e81-4ce7-a891-865bb505b934/missions"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].villeDepart").value("Mickeyville"));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	void findMissionsVideParUUIDCollegueTest() throws Exception {
		Mockito.when(colService.getCollegue(UUID.fromString("ab45410c-575b-4b14-8d8f-6ed13f5644de"))).thenReturn(Optional.of(collegues.get(1)));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/collegues/ab45410c-575b-4b14-8d8f-6ed13f5644de/missions"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("[]"));
	}
	

}
