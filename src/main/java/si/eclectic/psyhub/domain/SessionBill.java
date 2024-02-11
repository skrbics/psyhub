package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A SessionBill.
 */
@Entity
@Table(name = "session_bill")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SessionBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "paid")
    private Boolean paid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sessionBills" }, allowSetters = true)
    private Currency currency;

    @JsonIgnoreProperties(value = { "sessionBill", "client" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sessionBill")
    private Session session;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SessionBill id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public SessionBill amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getPaid() {
        return this.paid;
    }

    public SessionBill paid(Boolean paid) {
        this.setPaid(paid);
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public SessionBill currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        if (this.session != null) {
            this.session.setSessionBill(null);
        }
        if (session != null) {
            session.setSessionBill(this);
        }
        this.session = session;
    }

    public SessionBill session(Session session) {
        this.setSession(session);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionBill)) {
            return false;
        }
        return getId() != null && getId().equals(((SessionBill) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SessionBill{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", paid='" + getPaid() + "'" +
            "}";
    }
}
