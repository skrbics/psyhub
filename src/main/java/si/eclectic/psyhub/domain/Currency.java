package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Currency.
 */
@Entity
@Table(name = "currency")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "currency")
    @JsonIgnoreProperties(value = { "currency", "session" }, allowSetters = true)
    private Set<SessionBill> sessionBills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Currency id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Currency name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Currency code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<SessionBill> getSessionBills() {
        return this.sessionBills;
    }

    public void setSessionBills(Set<SessionBill> sessionBills) {
        if (this.sessionBills != null) {
            this.sessionBills.forEach(i -> i.setCurrency(null));
        }
        if (sessionBills != null) {
            sessionBills.forEach(i -> i.setCurrency(this));
        }
        this.sessionBills = sessionBills;
    }

    public Currency sessionBills(Set<SessionBill> sessionBills) {
        this.setSessionBills(sessionBills);
        return this;
    }

    public Currency addSessionBill(SessionBill sessionBill) {
        this.sessionBills.add(sessionBill);
        sessionBill.setCurrency(this);
        return this;
    }

    public Currency removeSessionBill(SessionBill sessionBill) {
        this.sessionBills.remove(sessionBill);
        sessionBill.setCurrency(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Currency)) {
            return false;
        }
        return getId() != null && getId().equals(((Currency) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Currency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
