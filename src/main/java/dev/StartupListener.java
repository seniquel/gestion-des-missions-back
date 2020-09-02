package dev;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.domain.Collegue;
import dev.domain.Role;
import dev.domain.SignatureNumerique;
import dev.repository.CollegueRepo;

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

        Collegue col1 = new Collegue();
        col1.setSignatureNumerique(new SignatureNumerique());
        col1.setNom("Admin");
        col1.setPrenom("DEV");
        col1.setEmail("admin@dev.fr");
        col1.setMotDePasse(passwordEncoder.encode("superpass"));
        col1.setRole(Role.ROLE_ADMINISTRATEUR);
        this.collegueRepo.save(col1);

        Collegue col2 = new Collegue();
        col2.setSignatureNumerique(new SignatureNumerique());
        col2.setNom("User");
        col2.setPrenom("DEV");
        col2.setEmail("user@dev.fr");
        col2.setMotDePasse(passwordEncoder.encode("superpass"));
        col2.setRole(Role.ROLE_UTILISATEUR);
        this.collegueRepo.save(col2);
        
        Collegue col3 = new Collegue();
        col3.setSignatureNumerique(new SignatureNumerique());
        col2.setNom("Manager");
        col2.setPrenom("DEV");
        col2.setEmail("manager@dev.fr");
        col2.setMotDePasse(passwordEncoder.encode("superpass"));
        col2.setRole(Role.ROLE_MANAGER);
        this.collegueRepo.save(col3);
    }

}
