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
	@NotNull
	@Future
	@JsonProperty("dateDebut")
	private LocalDate dateDebut;

	/** dateFin */
	@NotNull
	@Future
	@JsonProperty("dateFin")
	private LocalDate dateFin;
	
	/** villeDepart */
	@NotNull
	@NotBlank
	@Size(min=2)
	@JsonProperty("villeDepart")
	private String villeDepart;
	
	/** villeArrivee */
	@NotNull
	@NotBlank
	@Size(min=2)
	@JsonProperty("villeArrivee")
	private String villeArrivee;
	
	/** prime */
	@NotNull
	@PositiveOrZero
	@JsonProperty("prime")
	private BigDecimal prime;
	
	/** natureUuid */
	@NotNull
	@JsonProperty("natureId")
	private UUID natureId;
	
	/** missionId */
	@NotNull
	@JsonProperty("missionId")
	private UUID missionId;
	
	/** statut */
	@NotNull
	@NotBlank
	@JsonProperty("statut")
	private String statut;
	
	/** transport */
	@NotNull
	@NotBlank
	@JsonProperty("transport")
	private String transport;
	
	/** noteDeFraisId */
	@NotNull
	@JsonProperty("noteDeFraisId")
	private UUID noteDeFraisId;

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

	public UUID getMissionId() {
		return missionId;
	}

	public void setMissionId(UUID missionId) {
		this.missionId = missionId;
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

	public UUID getNoteDeFraisId() {
		return noteDeFraisId;
	}

	public void setNoteDeFraisId(UUID noteDeFraisId) {
		this.noteDeFraisId = noteDeFraisId;
	}
	
	
}
