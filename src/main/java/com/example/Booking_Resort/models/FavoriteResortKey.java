package com.example.Booking_Resort.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class FavoriteResortKey implements Serializable {

    private String id_user;
    private String id_rs;

    // equals & hashCode rất quan trọng trong composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteResortKey)) return false;
        FavoriteResortKey that = (FavoriteResortKey) o;
        return Objects.equals(id_user, that.id_user) &&
                Objects.equals(id_rs, that.id_rs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_user, id_rs);
    }
}
