package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Establishment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Establishment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

}
