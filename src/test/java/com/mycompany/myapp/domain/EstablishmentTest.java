package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EstablishmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Establishment.class);
        Establishment establishment1 = new Establishment();
        establishment1.setId(1L);
        Establishment establishment2 = new Establishment();
        establishment2.setId(establishment1.getId());
        assertThat(establishment1).isEqualTo(establishment2);
        establishment2.setId(2L);
        assertThat(establishment1).isNotEqualTo(establishment2);
        establishment1.setId(null);
        assertThat(establishment1).isNotEqualTo(establishment2);
    }
}
