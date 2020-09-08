package dev.controller.vm;

import java.util.UUID;

import dev.domain.NoteDeFrais;

public class MissionDto extends CreerMissionDto {
	
	private UUID uuid;
	private NoteDeFrais noteDeFrais;

	/** Getter
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/** Setter
	 * @param uuid the uuid to set
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public NoteDeFrais getNoteDeFrais() {
		return noteDeFrais;
	}

	public void setNoteDeFrais(NoteDeFrais noteDeFrais) {
		this.noteDeFrais = noteDeFrais;
	}
	
}
