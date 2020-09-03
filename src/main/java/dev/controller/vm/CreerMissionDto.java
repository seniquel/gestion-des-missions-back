package dev.controller.vm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreerMissionDto {
	
	/** dateDebut */
	@NotNull(message = "dateDebut ne peut pas être nulle")
	@Future(message = "dateDebut doit être une date future")
	@JsonProperty("dateDebut")
	private LocalDate dateDebut;

	/** dateFin */
	@NotNull(message = "dateFin ne peut pas être nulle")
	@Future(message = "dateFin doit être une date future")
	@JsonProperty("dateFin")
	private LocalDate dateFin;
	
	/** villeDepart */
	@NotNull(message = "villeDepart ne peut pas être nulle")
	@NotBlank(message = "villeDepart ne peut pas être vide")
	@Size(min=2, message = "villeDepart doit compter au moins 2 caractères")
	@JsonProperty("villeDepart")
	private String villeDepart;
	
	/** villeArrivee */
	@NotNull(message = "villeArrivee ne peut pas être nulle")
	@NotBlank(message = "villeArrivee ne peut pas être vide")
	@Size(min=2, message = "villeArrivee doit compter au moins 2 caractères")
	@JsonProperty("villeArrivee")
	private String villeArrivee;
	
	/** prime */
	@NotNull(message = "prime ne peut pas être nulle")
	@PositiveOrZero(message = "prime ne peut pas être négative")
	@JsonProperty("prime")
	private BigDecimal prime;
	
	/** natureUuid */
	@NotNull(message = "natureUuid ne peut pas être nulle")
	@JsonProperty("natureId")
	private UUID natureId;
	
	/** collegueId */
	@NotNull(message = "collegueId ne peut pas être nulle")
	@JsonProperty("collegueId")
	private UUID collegueId;
	
	/** statut */
	@NotNull(message = "statut ne peut pas être nul")
	@NotBlank(message = "statut ne peut pas être vide")
	@JsonProperty("statut")
	private String statut;
	
	/** transport */
	@NotNull(message = "transport ne peut pas être nul")
	@NotBlank(message = "transport ne peut pas être vide")
	@JsonProperty("transport")
	private String transport;


	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public String getVilleDepart() {
		return villeDepart;
	}

	public void setVilleDepart(String villeDepart) {
		this.villeDepart = villeDepart;
	}

	public String getVilleArrivee() {
		return villeArrivee;
	}

	public void setVilleArrivee(String villeArrivee) {
		this.villeArrivee = villeArrivee;
	}

	public BigDecimal getPrime() {
		return prime;
	}

	public void setPrime(BigDecimal prime) {
		this.prime = prime;
	}

	public UUID getNatureId() {
		return natureId;
	}

	public void setNatureId(UUID natureId) {
		this.natureId = natureId;
	}

	public UUID getCollegueId() {
		return collegueId;
	}

	public void setCollegueId(UUID collegueId) {
		this.collegueId = collegueId;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}
	
}
