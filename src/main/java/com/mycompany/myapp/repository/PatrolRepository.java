package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Patrol;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Patrol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatrolRepository extends JpaRepository<Patrol, Long> {

}
