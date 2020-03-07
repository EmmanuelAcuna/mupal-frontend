package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.WatchGuard;
import com.mycompany.myapp.repository.WatchGuardRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.WatchGuard}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WatchGuardResource {

    private final Logger log = LoggerFactory.getLogger(WatchGuardResource.class);

    private static final String ENTITY_NAME = "watchGuard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchGuardRepository watchGuardRepository;

    public WatchGuardResource(WatchGuardRepository watchGuardRepository) {
        this.watchGuardRepository = watchGuardRepository;
    }

    /**
     * {@code POST  /watch-guards} : Create a new watchGuard.
     *
     * @param watchGuard the watchGuard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watchGuard, or with status {@code 400 (Bad Request)} if the watchGuard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/watch-guards")
    public ResponseEntity<WatchGuard> createWatchGuard(@RequestBody WatchGuard watchGuard) throws URISyntaxException {
        log.debug("REST request to save WatchGuard : {}", watchGuard);
        if (watchGuard.getId() != null) {
            throw new BadRequestAlertException("A new watchGuard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WatchGuard result = watchGuardRepository.save(watchGuard);
        return ResponseEntity.created(new URI("/api/watch-guards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /watch-guards} : Updates an existing watchGuard.
     *
     * @param watchGuard the watchGuard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watchGuard,
     * or with status {@code 400 (Bad Request)} if the watchGuard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watchGuard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/watch-guards")
    public ResponseEntity<WatchGuard> updateWatchGuard(@RequestBody WatchGuard watchGuard) throws URISyntaxException {
        log.debug("REST request to update WatchGuard : {}", watchGuard);
        if (watchGuard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WatchGuard result = watchGuardRepository.save(watchGuard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watchGuard.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /watch-guards} : get all the watchGuards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watchGuards in body.
     */
    @GetMapping("/watch-guards")
    public ResponseEntity<List<WatchGuard>> getAllWatchGuards(Pageable pageable) {
        log.debug("REST request to get a page of WatchGuards");
        Page<WatchGuard> page = watchGuardRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watch-guards/:id} : get the "id" watchGuard.
     *
     * @param id the id of the watchGuard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watchGuard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/watch-guards/{id}")
    public ResponseEntity<WatchGuard> getWatchGuard(@PathVariable Long id) {
        log.debug("REST request to get WatchGuard : {}", id);
        Optional<WatchGuard> watchGuard = watchGuardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(watchGuard);
    }

    /**
     * {@code DELETE  /watch-guards/:id} : delete the "id" watchGuard.
     *
     * @param id the id of the watchGuard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/watch-guards/{id}")
    public ResponseEntity<Void> deleteWatchGuard(@PathVariable Long id) {
        log.debug("REST request to delete WatchGuard : {}", id);
        watchGuardRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
