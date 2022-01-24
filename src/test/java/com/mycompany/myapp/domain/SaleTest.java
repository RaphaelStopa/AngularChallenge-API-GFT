package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sale.class);
        Sale sale1 = new Sale();
        sale1.setId(1L);
        Sale sale2 = new Sale();
        sale2.setId(sale1.getId());
        assertThat(sale1).isEqualTo(sale2);
        sale2.setId(2L);
        assertThat(sale1).isNotEqualTo(sale2);
        sale1.setId(null);
        assertThat(sale1).isNotEqualTo(sale2);
    }
}
