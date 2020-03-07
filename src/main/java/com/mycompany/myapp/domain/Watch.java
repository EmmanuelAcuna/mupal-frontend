package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Watch.
 */
@Entity
@Table(name = "watch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Watch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valid_from")
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "start_on")
    private Instant startOn;

    @Column(name = "end_on")
    private Instant endOn;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "watch")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Establishment> idEstablishes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Watch validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Watch validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Instant getStartOn() {
        return startOn;
    }

    public Watch startOn(Instant startOn) {
        this.startOn = startOn;
        return this;
    }

    public void setStartOn(Instant startOn) {
        this.startOn = startOn;
    }

    public Instant getEndOn() {
        return endOn;
    }

    public Watch endOn(Instant endOn) {
        this.endOn = endOn;
        return this;
    }

    public void setEndOn(Instant endOn) {
        this.endOn = endOn;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Watch createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Watch updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Establishment> getIdEstablishes() {
        return idEstablishes;
    }

    public Watch idEstablishes(Set<Establishment> establishments) {
        this.idEstablishes = establishments;
        return this;
    }

    public Watch addIdEstablish(Establishment establishment) {
        this.idEstablishes.add(establishment);
        establishment.setWatch(this);
        return this;
    }

    public Watch removeIdEstablish(Establishment establishment) {
        this.idEstablishes.remove(establishment);
        establishment.setWatch(null);
        return this;
    }

    public void setIdEstablishes(Set<Establishment> establishments) {
        this.idEstablishes = establishments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Watch)) {
            return false;
        }
        return id != null && id.equals(((Watch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Watch{" +
            "id=" + getId() +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", startOn='" + getStartOn() + "'" +
            ", endOn='" + getEndOn() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
