package dev;

import dev.domain.Collegue;
import dev.domain.Role;
import dev.domain.RoleCollegue;
import dev.repository.CollegueRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import javax.transaction.Transactional;

/**
 * Code de démarrage de l'application.
 * Insertion de jeux de données.
 */
@Component
@Transactional
public class StartupListener {

    private String appVersion;
    private PasswordEncoder passwordEncoder;
    private CollegueRepo collegueRepo;

    public StartupListener(@Value("${app.version}") String appVersion, PasswordEncoder passwordEncoder, CollegueRepo collegueRepo) {
        this.appVersion = appVersion;
        this.passwordEncoder = passwordEncoder;
        this.collegueRepo = collegueRepo;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onStart() {

        // Création de deux utilisateurs

        Collegue col1 = new Collegue(null);
        col1.setNom("Admin");
        col1.setPrenom("DEV");
        col1.setEmail("admin@dev.fr");
        col1.setMotDePasse(passwordEncoder.encode("superpass"));
        col1.setRole(new RoleCollegue(col1, Role.ROLE_ADMINISTRATEUR));
        this.collegueRepo.save(col1);

        Collegue col2 = new Collegue(null);
        col2.setNom("User");
        col2.setPrenom("DEV");
        col2.setEmail("user@dev.fr");
        col2.setMotDePasse(passwordEncoder.encode("superpass"));
        col2.setRole(new RoleCollegue(col2, Role.ROLE_UTILISATEUR));
        this.collegueRepo.save(col2);
        
        Collegue col3 = new Collegue(null);
        col2.setNom("Manager");
        col2.setPrenom("DEV");
        col2.setEmail("manager@dev.fr");
        col2.setMotDePasse(passwordEncoder.encode("superpass"));
        col2.setRole(new RoleCollegue(col3, Role.ROLE_MANAGER));
        this.collegueRepo.save(col3);
    }

}
