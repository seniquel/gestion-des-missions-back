package dev.controller.vm;

import java.util.UUID;

public class MissionDto extends CreerMissionDto {
	
	private UUID uuid;

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
}
