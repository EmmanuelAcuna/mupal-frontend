package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Establishment;
import com.mycompany.myapp.repository.EstablishmentRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Establishment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstablishmentResource {

    private final Logger log = LoggerFactory.getLogger(EstablishmentResource.class);

    private static final String ENTITY_NAME = "establishment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentResource(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    /**
     * {@code POST  /establishments} : Create a new establishment.
     *
     * @param establishment the establishment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new establishment, or with status {@code 400 (Bad Request)} if the establishment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/establishments")
    public ResponseEntity<Establishment> createEstablishment(@RequestBody Establishment establishment) throws URISyntaxException {
        log.debug("REST request to save Establishment : {}", establishment);
        if (establishment.getId() != null) {
            throw new BadRequestAlertException("A new establishment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Establishment result = establishmentRepository.save(establishment);
        return ResponseEntity.created(new URI("/api/establishments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /establishments} : Updates an existing establishment.
     *
     * @param establishment the establishment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated establishment,
     * or with status {@code 400 (Bad Request)} if the establishment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the establishment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/establishments")
    public ResponseEntity<Establishment> updateEstablishment(@RequestBody Establishment establishment) throws URISyntaxException {
        log.debug("REST request to update Establishment : {}", establishment);
        if (establishment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Establishment result = establishmentRepository.save(establishment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, establishment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /establishments} : get all the establishments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of establishments in body.
     */
    @GetMapping("/establishments")
    public List<Establishment> getAllEstablishments() {
        log.debug("REST request to get all Establishments");
        return establishmentRepository.findAll();
    }

    /**
     * {@code GET  /establishments/:id} : get the "id" establishment.
     *
     * @param id the id of the establishment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the establishment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/establishments/{id}")
    public ResponseEntity<Establishment> getEstablishment(@PathVariable Long id) {
        log.debug("REST request to get Establishment : {}", id);
        Optional<Establishment> establishment = establishmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(establishment);
    }

    /**
     * {@code DELETE  /establishments/:id} : delete the "id" establishment.
     *
     * @param id the id of the establishment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/establishments/{id}")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable Long id) {
        log.debug("REST request to delete Establishment : {}", id);
        establishmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
