package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Patrol;
import com.mycompany.myapp.repository.PatrolRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Patrol}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PatrolResource {

    private final Logger log = LoggerFactory.getLogger(PatrolResource.class);

    private static final String ENTITY_NAME = "patrol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatrolRepository patrolRepository;

    public PatrolResource(PatrolRepository patrolRepository) {
        this.patrolRepository = patrolRepository;
    }

    /**
     * {@code POST  /patrols} : Create a new patrol.
     *
     * @param patrol the patrol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patrol, or with status {@code 400 (Bad Request)} if the patrol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patrols")
    public ResponseEntity<Patrol> createPatrol(@RequestBody Patrol patrol) throws URISyntaxException {
        log.debug("REST request to save Patrol : {}", patrol);
        if (patrol.getId() != null) {
            throw new BadRequestAlertException("A new patrol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Patrol result = patrolRepository.save(patrol);
        return ResponseEntity.created(new URI("/api/patrols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patrols} : Updates an existing patrol.
     *
     * @param patrol the patrol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patrol,
     * or with status {@code 400 (Bad Request)} if the patrol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patrol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patrols")
    public ResponseEntity<Patrol> updatePatrol(@RequestBody Patrol patrol) throws URISyntaxException {
        log.debug("REST request to update Patrol : {}", patrol);
        if (patrol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Patrol result = patrolRepository.save(patrol);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patrol.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patrols} : get all the patrols.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patrols in body.
     */
    @GetMapping("/patrols")
    public ResponseEntity<List<Patrol>> getAllPatrols(Pageable pageable) {
        log.debug("REST request to get a page of Patrols");
        Page<Patrol> page = patrolRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patrols/:id} : get the "id" patrol.
     *
     * @param id the id of the patrol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patrol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patrols/{id}")
    public ResponseEntity<Patrol> getPatrol(@PathVariable Long id) {
        log.debug("REST request to get Patrol : {}", id);
        Optional<Patrol> patrol = patrolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(patrol);
    }

    /**
     * {@code DELETE  /patrols/:id} : delete the "id" patrol.
     *
     * @param id the id of the patrol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patrols/{id}")
    public ResponseEntity<Void> deletePatrol(@PathVariable Long id) {
        log.debug("REST request to delete Patrol : {}", id);
        patrolRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
