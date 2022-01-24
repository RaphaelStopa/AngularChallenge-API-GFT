package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "value")
    private Double value;

    @Column(name = "cep")
    private String cep;

    @OneToOne
    @JoinColumn
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Sale date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getValue() {
        return this.value;
    }

    public Sale value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCep() {
        return this.cep;
    }

    public Sale cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return id != null && id.equals(((Sale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sale{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", value=" + getValue() +
            ", cep='" + getCep() + "'" +
            "}";
    }
}
