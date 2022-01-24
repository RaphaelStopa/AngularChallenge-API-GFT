package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PurchaseItem} entity.
 */
public class PurchaseItemDTO implements Serializable {

    private Long id;

    private Boolean isFinished;

    private Long productQuantity;

    private Double unitaryValue;

    private Double totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Double getUnitaryValue() {
        return unitaryValue;
    }

    public void setUnitaryValue(Double unitaryValue) {
        this.unitaryValue = unitaryValue;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseItemDTO)) {
            return false;
        }

        PurchaseItemDTO purchaseItemDTO = (PurchaseItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, purchaseItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseItemDTO{" +
            "id=" + getId() +
            ", isFinished='" + getIsFinished() + "'" +
            ", productQuantity=" + getProductQuantity() +
            ", unitaryValue=" + getUnitaryValue() +
            ", totalPrice=" + getTotalPrice() +
            "}";
    }
}
