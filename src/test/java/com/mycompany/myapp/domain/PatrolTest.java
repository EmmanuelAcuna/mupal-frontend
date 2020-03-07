package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PatrolTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patrol.class);
        Patrol patrol1 = new Patrol();
        patrol1.setId(1L);
        Patrol patrol2 = new Patrol();
        patrol2.setId(patrol1.getId());
        assertThat(patrol1).isEqualTo(patrol2);
        patrol2.setId(2L);
        assertThat(patrol1).isNotEqualTo(patrol2);
        patrol1.setId(null);
        assertThat(patrol1).isNotEqualTo(patrol2);
    }
}
