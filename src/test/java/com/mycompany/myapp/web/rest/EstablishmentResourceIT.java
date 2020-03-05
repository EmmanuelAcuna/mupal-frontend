package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MupalApp;
import com.mycompany.myapp.domain.Establishment;
import com.mycompany.myapp.repository.EstablishmentRepository;
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
 * Integration tests for the {@link EstablishmentResource} REST controller.
 */
@SpringBootTest(classes = MupalApp.class)
public class EstablishmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_ID = "BBBBBBBBBB";

    @Autowired
    private EstablishmentRepository establishmentRepository;

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

    private MockMvc restEstablishmentMockMvc;

    private Establishment establishment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstablishmentResource establishmentResource = new EstablishmentResource(establishmentRepository);
        this.restEstablishmentMockMvc = MockMvcBuilders.standaloneSetup(establishmentResource)
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
    public static Establishment createEntity(EntityManager em) {
        Establishment establishment = new Establishment()
            .name(DEFAULT_NAME)
            .contactId(DEFAULT_CONTACT_ID);
        return establishment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Establishment createUpdatedEntity(EntityManager em) {
        Establishment establishment = new Establishment()
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID);
        return establishment;
    }

    @BeforeEach
    public void initTest() {
        establishment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstablishment() throws Exception {
        int databaseSizeBeforeCreate = establishmentRepository.findAll().size();

        // Create the Establishment
        restEstablishmentMockMvc.perform(post("/api/establishments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isCreated());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeCreate + 1);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEstablishment.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
    }

    @Test
    @Transactional
    public void createEstablishmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = establishmentRepository.findAll().size();

        // Create the Establishment with an existing ID
        establishment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstablishmentMockMvc.perform(post("/api/establishments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstablishments() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        // Get all the establishmentList
        restEstablishmentMockMvc.perform(get("/api/establishments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establishment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID)));
    }
    
    @Test
    @Transactional
    public void getEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        // Get the establishment
        restEstablishmentMockMvc.perform(get("/api/establishments/{id}", establishment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(establishment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID));
    }

    @Test
    @Transactional
    public void getNonExistingEstablishment() throws Exception {
        // Get the establishment
        restEstablishmentMockMvc.perform(get("/api/establishments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Update the establishment
        Establishment updatedEstablishment = establishmentRepository.findById(establishment.getId()).get();
        // Disconnect from session so that the updates on updatedEstablishment are not directly saved in db
        em.detach(updatedEstablishment);
        updatedEstablishment
            .name(UPDATED_NAME)
            .contactId(UPDATED_CONTACT_ID);

        restEstablishmentMockMvc.perform(put("/api/establishments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstablishment)))
            .andExpect(status().isOk());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEstablishment.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Create the Establishment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc.perform(put("/api/establishments")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeDelete = establishmentRepository.findAll().size();

        // Delete the establishment
        restEstablishmentMockMvc.perform(delete("/api/establishments/{id}", establishment.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
