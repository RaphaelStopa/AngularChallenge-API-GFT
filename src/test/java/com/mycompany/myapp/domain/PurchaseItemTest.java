package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchaseItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseItem.class);
        PurchaseItem purchaseItem1 = new PurchaseItem();
        purchaseItem1.setId(1L);
        PurchaseItem purchaseItem2 = new PurchaseItem();
        purchaseItem2.setId(purchaseItem1.getId());
        assertThat(purchaseItem1).isEqualTo(purchaseItem2);
        purchaseItem2.setId(2L);
        assertThat(purchaseItem1).isNotEqualTo(purchaseItem2);
        purchaseItem1.setId(null);
        assertThat(purchaseItem1).isNotEqualTo(purchaseItem2);
    }
}
