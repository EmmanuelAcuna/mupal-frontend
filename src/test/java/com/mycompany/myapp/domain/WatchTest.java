package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class WatchTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Watch.class);
        Watch watch1 = new Watch();
        watch1.setId(1L);
        Watch watch2 = new Watch();
        watch2.setId(watch1.getId());
        assertThat(watch1).isEqualTo(watch2);
        watch2.setId(2L);
        assertThat(watch1).isNotEqualTo(watch2);
        watch1.setId(null);
        assertThat(watch1).isNotEqualTo(watch2);
    }
}
