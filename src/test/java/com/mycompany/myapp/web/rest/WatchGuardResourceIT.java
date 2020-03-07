package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MupalApp;
import com.mycompany.myapp.domain.WatchGuard;
import com.mycompany.myapp.repository.WatchGuardRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WatchGuardResource} REST controller.
 */
@SpringBootTest(classes = MupalApp.class)
public class WatchGuardResourceIT {

    private static final String DEFAULT_ID_GUARD = "AAAAAAAAAA";
    private static final String UPDATED_ID_GUARD = "BBBBBBBBBB";

    private static final Instant DEFAULT_ASSIGN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSIGN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WatchGuardRepository watchGuardRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restWatchGuardMockMvc;

    private WatchGuard watchGuard;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WatchGuardResource watchGuardResource = new WatchGuardResource(watchGuardRepository);
        this.restWatchGuardMockMvc = MockMvcBuilders.standaloneSetup(watchGuardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchGuard createEntity(EntityManager em) {
        WatchGuard watchGuard = new WatchGuard()
            .idGuard(DEFAULT_ID_GUARD)
            .assignDate(DEFAULT_ASSIGN_DATE);
        return watchGuard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WatchGuard createUpdatedEntity(EntityManager em) {
        WatchGuard watchGuard = new WatchGuard()
            .idGuard(UPDATED_ID_GUARD)
            .assignDate(UPDATED_ASSIGN_DATE);
        return watchGuard;
    }

    @BeforeEach
    public void initTest() {
        watchGuard = createEntity(em);
    }

    @Test
    @Transactional
    public void createWatchGuard() throws Exception {
        int databaseSizeBeforeCreate = watchGuardRepository.findAll().size();

        // Create the WatchGuard
        restWatchGuardMockMvc.perform(post("/api/watch-guards")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchGuard)))
            .andExpect(status().isCreated());

        // Validate the WatchGuard in the database
        List<WatchGuard> watchGuardList = watchGuardRepository.findAll();
        assertThat(watchGuardList).hasSize(databaseSizeBeforeCreate + 1);
        WatchGuard testWatchGuard = watchGuardList.get(watchGuardList.size() - 1);
        assertThat(testWatchGuard.getIdGuard()).isEqualTo(DEFAULT_ID_GUARD);
        assertThat(testWatchGuard.getAssignDate()).isEqualTo(DEFAULT_ASSIGN_DATE);
    }

    @Test
    @Transactional
    public void createWatchGuardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = watchGuardRepository.findAll().size();

        // Create the WatchGuard with an existing ID
        watchGuard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchGuardMockMvc.perform(post("/api/watch-guards")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchGuard)))
            .andExpect(status().isBadRequest());

        // Validate the WatchGuard in the database
        List<WatchGuard> watchGuardList = watchGuardRepository.findAll();
        assertThat(watchGuardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWatchGuards() throws Exception {
        // Initialize the database
        watchGuardRepository.saveAndFlush(watchGuard);

        // Get all the watchGuardList
        restWatchGuardMockMvc.perform(get("/api/watch-guards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watchGuard.getId().intValue())))
            .andExpect(jsonPath("$.[*].idGuard").value(hasItem(DEFAULT_ID_GUARD)))
            .andExpect(jsonPath("$.[*].assignDate").value(hasItem(DEFAULT_ASSIGN_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getWatchGuard() throws Exception {
        // Initialize the database
        watchGuardRepository.saveAndFlush(watchGuard);

        // Get the watchGuard
        restWatchGuardMockMvc.perform(get("/api/watch-guards/{id}", watchGuard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watchGuard.getId().intValue()))
            .andExpect(jsonPath("$.idGuard").value(DEFAULT_ID_GUARD))
            .andExpect(jsonPath("$.assignDate").value(DEFAULT_ASSIGN_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWatchGuard() throws Exception {
        // Get the watchGuard
        restWatchGuardMockMvc.perform(get("/api/watch-guards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWatchGuard() throws Exception {
        // Initialize the database
        watchGuardRepository.saveAndFlush(watchGuard);

        int databaseSizeBeforeUpdate = watchGuardRepository.findAll().size();

        // Update the watchGuard
        WatchGuard updatedWatchGuard = watchGuardRepository.findById(watchGuard.getId()).get();
        // Disconnect from session so that the updates on updatedWatchGuard are not directly saved in db
        em.detach(updatedWatchGuard);
        updatedWatchGuard
            .idGuard(UPDATED_ID_GUARD)
            .assignDate(UPDATED_ASSIGN_DATE);

        restWatchGuardMockMvc.perform(put("/api/watch-guards")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWatchGuard)))
            .andExpect(status().isOk());

        // Validate the WatchGuard in the database
        List<WatchGuard> watchGuardList = watchGuardRepository.findAll();
        assertThat(watchGuardList).hasSize(databaseSizeBeforeUpdate);
        WatchGuard testWatchGuard = watchGuardList.get(watchGuardList.size() - 1);
        assertThat(testWatchGuard.getIdGuard()).isEqualTo(UPDATED_ID_GUARD);
        assertThat(testWatchGuard.getAssignDate()).isEqualTo(UPDATED_ASSIGN_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingWatchGuard() throws Exception {
        int databaseSizeBeforeUpdate = watchGuardRepository.findAll().size();

        // Create the WatchGuard

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchGuardMockMvc.perform(put("/api/watch-guards")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watchGuard)))
            .andExpect(status().isBadRequest());

        // Validate the WatchGuard in the database
        List<WatchGuard> watchGuardList = watchGuardRepository.findAll();
        assertThat(watchGuardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWatchGuard() throws Exception {
        // Initialize the database
        watchGuardRepository.saveAndFlush(watchGuard);

        int databaseSizeBeforeDelete = watchGuardRepository.findAll().size();

        // Delete the watchGuard
        restWatchGuardMockMvc.perform(delete("/api/watch-guards/{id}", watchGuard.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WatchGuard> watchGuardList = watchGuardRepository.findAll();
        assertThat(watchGuardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
