package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.WatchGuard;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WatchGuard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchGuardRepository extends JpaRepository<WatchGuard, Long> {

}
