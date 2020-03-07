package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Watch;
import com.mycompany.myapp.repository.WatchRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Watch}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WatchResource {

    private final Logger log = LoggerFactory.getLogger(WatchResource.class);

    private static final String ENTITY_NAME = "watch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WatchRepository watchRepository;

    public WatchResource(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }

    /**
     * {@code POST  /watches} : Create a new watch.
     *
     * @param watch the watch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new watch, or with status {@code 400 (Bad Request)} if the watch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/watches")
    public ResponseEntity<Watch> createWatch(@RequestBody Watch watch) throws URISyntaxException {
        log.debug("REST request to save Watch : {}", watch);
        if (watch.getId() != null) {
            throw new BadRequestAlertException("A new watch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Watch result = watchRepository.save(watch);
        return ResponseEntity.created(new URI("/api/watches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /watches} : Updates an existing watch.
     *
     * @param watch the watch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated watch,
     * or with status {@code 400 (Bad Request)} if the watch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the watch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/watches")
    public ResponseEntity<Watch> updateWatch(@RequestBody Watch watch) throws URISyntaxException {
        log.debug("REST request to update Watch : {}", watch);
        if (watch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Watch result = watchRepository.save(watch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, watch.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /watches} : get all the watches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of watches in body.
     */
    @GetMapping("/watches")
    public ResponseEntity<List<Watch>> getAllWatches(Pageable pageable) {
        log.debug("REST request to get a page of Watches");
        Page<Watch> page = watchRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /watches/:id} : get the "id" watch.
     *
     * @param id the id of the watch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the watch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/watches/{id}")
    public ResponseEntity<Watch> getWatch(@PathVariable Long id) {
        log.debug("REST request to get Watch : {}", id);
        Optional<Watch> watch = watchRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(watch);
    }

    /**
     * {@code DELETE  /watches/:id} : delete the "id" watch.
     *
     * @param id the id of the watch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/watches/{id}")
    public ResponseEntity<Void> deleteWatch(@PathVariable Long id) {
        log.debug("REST request to delete Watch : {}", id);
        watchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
