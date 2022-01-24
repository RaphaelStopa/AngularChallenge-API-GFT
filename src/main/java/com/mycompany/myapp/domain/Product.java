package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.UnitMeasurement;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "quantity_stock")
    private Long quantityStock;

    @Column(name = "unit_measurement")
    @Enumerated(EnumType.STRING)
    private UnitMeasurement unitMeasurement;

    @Lob
    @Column(name = "photo_file")
    //    @DiffIgnore
    private byte[] photo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitMeasurement getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(UnitMeasurement unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public Product unitPrice(Double unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getQuantityStock() {
        return this.quantityStock;
    }

    public Product quantityStock(Long quantityStock) {
        this.setQuantityStock(quantityStock);
        return this;
    }

    public void setQuantityStock(Long quantityStock) {
        this.quantityStock = quantityStock;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", quantityStock=" + getQuantityStock() +
            "}";
    }
}
