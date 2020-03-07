package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MupalApp;
import com.mycompany.myapp.domain.Patrol;
import com.mycompany.myapp.repository.PatrolRepository;
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
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PatrolResource} REST controller.
 */
@SpringBootTest(classes = MupalApp.class)
public class PatrolResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PatrolRepository patrolRepository;

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

    private MockMvc restPatrolMockMvc;

    private Patrol patrol;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatrolResource patrolResource = new PatrolResource(patrolRepository);
        this.restPatrolMockMvc = MockMvcBuilders.standaloneSetup(patrolResource)
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
    public static Patrol createEntity(EntityManager em) {
        Patrol patrol = new Patrol()
            .name(DEFAULT_NAME);
        return patrol;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patrol createUpdatedEntity(EntityManager em) {
        Patrol patrol = new Patrol()
            .name(UPDATED_NAME);
        return patrol;
    }

    @BeforeEach
    public void initTest() {
        patrol = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatrol() throws Exception {
        int databaseSizeBeforeCreate = patrolRepository.findAll().size();

        // Create the Patrol
        restPatrolMockMvc.perform(post("/api/patrols")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrol)))
            .andExpect(status().isCreated());

        // Validate the Patrol in the database
        List<Patrol> patrolList = patrolRepository.findAll();
        assertThat(patrolList).hasSize(databaseSizeBeforeCreate + 1);
        Patrol testPatrol = patrolList.get(patrolList.size() - 1);
        assertThat(testPatrol.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPatrolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patrolRepository.findAll().size();

        // Create the Patrol with an existing ID
        patrol.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatrolMockMvc.perform(post("/api/patrols")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrol)))
            .andExpect(status().isBadRequest());

        // Validate the Patrol in the database
        List<Patrol> patrolList = patrolRepository.findAll();
        assertThat(patrolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPatrols() throws Exception {
        // Initialize the database
        patrolRepository.saveAndFlush(patrol);

        // Get all the patrolList
        restPatrolMockMvc.perform(get("/api/patrols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patrol.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getPatrol() throws Exception {
        // Initialize the database
        patrolRepository.saveAndFlush(patrol);

        // Get the patrol
        restPatrolMockMvc.perform(get("/api/patrols/{id}", patrol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patrol.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingPatrol() throws Exception {
        // Get the patrol
        restPatrolMockMvc.perform(get("/api/patrols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatrol() throws Exception {
        // Initialize the database
        patrolRepository.saveAndFlush(patrol);

        int databaseSizeBeforeUpdate = patrolRepository.findAll().size();

        // Update the patrol
        Patrol updatedPatrol = patrolRepository.findById(patrol.getId()).get();
        // Disconnect from session so that the updates on updatedPatrol are not directly saved in db
        em.detach(updatedPatrol);
        updatedPatrol
            .name(UPDATED_NAME);

        restPatrolMockMvc.perform(put("/api/patrols")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatrol)))
            .andExpect(status().isOk());

        // Validate the Patrol in the database
        List<Patrol> patrolList = patrolRepository.findAll();
        assertThat(patrolList).hasSize(databaseSizeBeforeUpdate);
        Patrol testPatrol = patrolList.get(patrolList.size() - 1);
        assertThat(testPatrol.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPatrol() throws Exception {
        int databaseSizeBeforeUpdate = patrolRepository.findAll().size();

        // Create the Patrol

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatrolMockMvc.perform(put("/api/patrols")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patrol)))
            .andExpect(status().isBadRequest());

        // Validate the Patrol in the database
        List<Patrol> patrolList = patrolRepository.findAll();
        assertThat(patrolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatrol() throws Exception {
        // Initialize the database
        patrolRepository.saveAndFlush(patrol);

        int databaseSizeBeforeDelete = patrolRepository.findAll().size();

        // Delete the patrol
        restPatrolMockMvc.perform(delete("/api/patrols/{id}", patrol.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patrol> patrolList = patrolRepository.findAll();
        assertThat(patrolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
