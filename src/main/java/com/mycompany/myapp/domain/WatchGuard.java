package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A WatchGuard.
 */
@Entity
@Table(name = "watch_guard")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WatchGuard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_guard")
    private String idGuard;

    @Column(name = "assign_date")
    private Instant assignDate;

    @OneToOne
    @JoinColumn(unique = true)
    private Watch idWatch;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdGuard() {
        return idGuard;
    }

    public WatchGuard idGuard(String idGuard) {
        this.idGuard = idGuard;
        return this;
    }

    public void setIdGuard(String idGuard) {
        this.idGuard = idGuard;
    }

    public Instant getAssignDate() {
        return assignDate;
    }

    public WatchGuard assignDate(Instant assignDate) {
        this.assignDate = assignDate;
        return this;
    }

    public void setAssignDate(Instant assignDate) {
        this.assignDate = assignDate;
    }

    public Watch getIdWatch() {
        return idWatch;
    }

    public WatchGuard idWatch(Watch watch) {
        this.idWatch = watch;
        return this;
    }

    public void setIdWatch(Watch watch) {
        this.idWatch = watch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchGuard)) {
            return false;
        }
        return id != null && id.equals(((WatchGuard) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WatchGuard{" +
            "id=" + getId() +
            ", idGuard='" + getIdGuard() + "'" +
            ", assignDate='" + getAssignDate() + "'" +
            "}";
    }
}
