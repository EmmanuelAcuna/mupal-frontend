package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Patrol.
 */
@Entity
@Table(name = "patrol")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patrol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "patrol")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Establishment> idEstablishes = new HashSet<>();

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

    public Patrol name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Establishment> getIdEstablishes() {
        return idEstablishes;
    }

    public Patrol idEstablishes(Set<Establishment> establishments) {
        this.idEstablishes = establishments;
        return this;
    }

    public Patrol addIdEstablish(Establishment establishment) {
        this.idEstablishes.add(establishment);
        establishment.setPatrol(this);
        return this;
    }

    public Patrol removeIdEstablish(Establishment establishment) {
        this.idEstablishes.remove(establishment);
        establishment.setPatrol(null);
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
        if (!(o instanceof Patrol)) {
            return false;
        }
        return id != null && id.equals(((Patrol) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Patrol{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
