package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A PurchaseItem.
 */
@Entity
@Table(name = "purchase_item")
public class PurchaseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_finished")
    private Boolean isFinished = false;

    @Column(name = "product_quantity")
    private Long productQuantity;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToOne
    private User user;

    @OneToOne
    private Product product;

    @OneToOne
    private Sale sale;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PurchaseItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsFinished() {
        return this.isFinished;
    }

    public PurchaseItem isFinished(Boolean isFinished) {
        this.setIsFinished(isFinished);
        return this;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Long getProductQuantity() {
        return this.productQuantity;
    }

    public PurchaseItem productQuantity(Long productQuantity) {
        this.setProductQuantity(productQuantity);
        return this;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public PurchaseItem totalPrice(Double totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseItem)) {
            return false;
        }
        return id != null && id.equals(((PurchaseItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseItem{" +
            "id=" + getId() +
            ", isFinished='" + getIsFinished() + "'" +
            ", productQuantity=" + getProductQuantity() +
            ", totalPrice=" + getTotalPrice() +
            "}";
    }
}
