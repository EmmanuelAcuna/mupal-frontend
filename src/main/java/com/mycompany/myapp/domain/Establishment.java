package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Establishment.
 */
@Entity
@Table(name = "establishment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Establishment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "payd_date")
    private Long paydDate;

    @Column(name = "lat")
    private Long lat;

    @Column(name = "lng")
    private Long lng;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contactId;

    @ManyToOne
    @JsonIgnoreProperties("idEstablishes")
    private Watch watch;

    @ManyToOne
    @JsonIgnoreProperties("idEstablishes")
    private Patrol patrol;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Establishment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPaydDate() {
        return paydDate;
    }

    public Establishment paydDate(Long paydDate) {
        this.paydDate = paydDate;
        return this;
    }

    public void setPaydDate(Long paydDate) {
        this.paydDate = paydDate;
    }

    public Long getLat() {
        return lat;
    }

    public Establishment lat(Long lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLng() {
        return lng;
    }

    public Establishment lng(Long lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

    public Contact getContactId() {
        return contactId;
    }

    public Establishment contactId(Contact contact) {
        this.contactId = contact;
        return this;
    }

    public void setContactId(Contact contact) {
        this.contactId = contact;
    }

    public Watch getWatch() {
        return watch;
    }

    public Establishment watch(Watch watch) {
        this.watch = watch;
        return this;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }

    public Patrol getPatrol() {
        return patrol;
    }

    public Establishment patrol(Patrol patrol) {
        this.patrol = patrol;
        return this;
    }

    public void setPatrol(Patrol patrol) {
        this.patrol = patrol;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Establishment)) {
            return false;
        }
        return id != null && id.equals(((Establishment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Establishment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", paydDate=" + getPaydDate() +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            "}";
    }
}
