package com.tsystems.javaschool.medical.backend.entities;

import com.tsystems.javaschool.medical.backend.entities.enums.IsDeleted;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "rooms", schema = "public" )
public class RoomsEntity {
    private BigInteger id;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private IsDeleted deleted;

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "rooms_item_id", sequenceName = "rooms_item_id", allocationSize = 1)
    @GeneratedValue(generator = "rooms_item_id", strategy = GenerationType.SEQUENCE)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "created_at")
    @CreationTimestamp
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at")
    @UpdateTimestamp
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "deleted")
    public IsDeleted getDeleted() {
        return deleted;
    }

    public void setDeleted(IsDeleted deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomsEntity that = (RoomsEntity) o;
        return id == that.id &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createdAt, updatedAt, deleted);
    }
}
