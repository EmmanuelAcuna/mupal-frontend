package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class WatchGuardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WatchGuard.class);
        WatchGuard watchGuard1 = new WatchGuard();
        watchGuard1.setId(1L);
        WatchGuard watchGuard2 = new WatchGuard();
        watchGuard2.setId(watchGuard1.getId());
        assertThat(watchGuard1).isEqualTo(watchGuard2);
        watchGuard2.setId(2L);
        assertThat(watchGuard1).isNotEqualTo(watchGuard2);
        watchGuard1.setId(null);
        assertThat(watchGuard1).isNotEqualTo(watchGuard2);
    }
}
