package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MupalApp;
import com.mycompany.myapp.domain.Watch;
import com.mycompany.myapp.repository.WatchRepository;
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
 * Integration tests for the {@link WatchResource} REST controller.
 */
@SpringBootTest(classes = MupalApp.class)
public class WatchResourceIT {

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_START_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WatchRepository watchRepository;

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

    private MockMvc restWatchMockMvc;

    private Watch watch;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WatchResource watchResource = new WatchResource(watchRepository);
        this.restWatchMockMvc = MockMvcBuilders.standaloneSetup(watchResource)
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
    public static Watch createEntity(EntityManager em) {
        Watch watch = new Watch()
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .startOn(DEFAULT_START_ON)
            .endOn(DEFAULT_END_ON)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return watch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Watch createUpdatedEntity(EntityManager em) {
        Watch watch = new Watch()
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .startOn(UPDATED_START_ON)
            .endOn(UPDATED_END_ON)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return watch;
    }

    @BeforeEach
    public void initTest() {
        watch = createEntity(em);
    }

    @Test
    @Transactional
    public void createWatch() throws Exception {
        int databaseSizeBeforeCreate = watchRepository.findAll().size();

        // Create the Watch
        restWatchMockMvc.perform(post("/api/watches")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watch)))
            .andExpect(status().isCreated());

        // Validate the Watch in the database
        List<Watch> watchList = watchRepository.findAll();
        assertThat(watchList).hasSize(databaseSizeBeforeCreate + 1);
        Watch testWatch = watchList.get(watchList.size() - 1);
        assertThat(testWatch.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testWatch.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testWatch.getStartOn()).isEqualTo(DEFAULT_START_ON);
        assertThat(testWatch.getEndOn()).isEqualTo(DEFAULT_END_ON);
        assertThat(testWatch.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testWatch.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createWatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = watchRepository.findAll().size();

        // Create the Watch with an existing ID
        watch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWatchMockMvc.perform(post("/api/watches")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watch)))
            .andExpect(status().isBadRequest());

        // Validate the Watch in the database
        List<Watch> watchList = watchRepository.findAll();
        assertThat(watchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWatches() throws Exception {
        // Initialize the database
        watchRepository.saveAndFlush(watch);

        // Get all the watchList
        restWatchMockMvc.perform(get("/api/watches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(watch.getId().intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].startOn").value(hasItem(DEFAULT_START_ON.toString())))
            .andExpect(jsonPath("$.[*].endOn").value(hasItem(DEFAULT_END_ON.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getWatch() throws Exception {
        // Initialize the database
        watchRepository.saveAndFlush(watch);

        // Get the watch
        restWatchMockMvc.perform(get("/api/watches/{id}", watch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(watch.getId().intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.startOn").value(DEFAULT_START_ON.toString()))
            .andExpect(jsonPath("$.endOn").value(DEFAULT_END_ON.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWatch() throws Exception {
        // Get the watch
        restWatchMockMvc.perform(get("/api/watches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWatch() throws Exception {
        // Initialize the database
        watchRepository.saveAndFlush(watch);

        int databaseSizeBeforeUpdate = watchRepository.findAll().size();

        // Update the watch
        Watch updatedWatch = watchRepository.findById(watch.getId()).get();
        // Disconnect from session so that the updates on updatedWatch are not directly saved in db
        em.detach(updatedWatch);
        updatedWatch
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .startOn(UPDATED_START_ON)
            .endOn(UPDATED_END_ON)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restWatchMockMvc.perform(put("/api/watches")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWatch)))
            .andExpect(status().isOk());

        // Validate the Watch in the database
        List<Watch> watchList = watchRepository.findAll();
        assertThat(watchList).hasSize(databaseSizeBeforeUpdate);
        Watch testWatch = watchList.get(watchList.size() - 1);
        assertThat(testWatch.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testWatch.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testWatch.getStartOn()).isEqualTo(UPDATED_START_ON);
        assertThat(testWatch.getEndOn()).isEqualTo(UPDATED_END_ON);
        assertThat(testWatch.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testWatch.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingWatch() throws Exception {
        int databaseSizeBeforeUpdate = watchRepository.findAll().size();

        // Create the Watch

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWatchMockMvc.perform(put("/api/watches")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(watch)))
            .andExpect(status().isBadRequest());

        // Validate the Watch in the database
        List<Watch> watchList = watchRepository.findAll();
        assertThat(watchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWatch() throws Exception {
        // Initialize the database
        watchRepository.saveAndFlush(watch);

        int databaseSizeBeforeDelete = watchRepository.findAll().size();

        // Delete the watch
        restWatchMockMvc.perform(delete("/api/watches/{id}", watch.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Watch> watchList = watchRepository.findAll();
        assertThat(watchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
