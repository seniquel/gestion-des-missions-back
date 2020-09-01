package dev.domain;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntiteBase {

    @Id
    private UUID uuid;

    public EntiteBase() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
