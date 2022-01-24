package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchaseItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseItemDTO.class);
        PurchaseItemDTO purchaseItemDTO1 = new PurchaseItemDTO();
        purchaseItemDTO1.setId(1L);
        PurchaseItemDTO purchaseItemDTO2 = new PurchaseItemDTO();
        assertThat(purchaseItemDTO1).isNotEqualTo(purchaseItemDTO2);
        purchaseItemDTO2.setId(purchaseItemDTO1.getId());
        assertThat(purchaseItemDTO1).isEqualTo(purchaseItemDTO2);
        purchaseItemDTO2.setId(2L);
        assertThat(purchaseItemDTO1).isNotEqualTo(purchaseItemDTO2);
        purchaseItemDTO1.setId(null);
        assertThat(purchaseItemDTO1).isNotEqualTo(purchaseItemDTO2);
    }
}
