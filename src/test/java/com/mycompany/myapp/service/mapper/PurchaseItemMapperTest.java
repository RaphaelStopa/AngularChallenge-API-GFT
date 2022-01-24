package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseItemMapperTest {

    private PurchaseItemMapper purchaseItemMapper;

    @BeforeEach
    public void setUp() {
        purchaseItemMapper = new PurchaseItemMapperImpl();
    }
}
