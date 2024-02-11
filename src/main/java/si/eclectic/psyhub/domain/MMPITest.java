package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A MMPITest.
 */
@Entity
@Table(name = "mmpi_test")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MMPITest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "jhi_order")
    private Integer order;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "address", "sessions", "mMPITests" }, allowSetters = true)
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mMPITest")
    @JsonIgnoreProperties(value = { "mMPITest", "mMPITestCard" }, allowSetters = true)
    private Set<MMPIAnswer> mMPIAnswers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MMPITest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return this.order;
    }

    public MMPITest order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public MMPITest date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public MMPITest client(Client client) {
        this.setClient(client);
        return this;
    }

    public Set<MMPIAnswer> getMMPIAnswers() {
        return this.mMPIAnswers;
    }

    public void setMMPIAnswers(Set<MMPIAnswer> mMPIAnswers) {
        if (this.mMPIAnswers != null) {
            this.mMPIAnswers.forEach(i -> i.setMMPITest(null));
        }
        if (mMPIAnswers != null) {
            mMPIAnswers.forEach(i -> i.setMMPITest(this));
        }
        this.mMPIAnswers = mMPIAnswers;
    }

    public MMPITest mMPIAnswers(Set<MMPIAnswer> mMPIAnswers) {
        this.setMMPIAnswers(mMPIAnswers);
        return this;
    }

    public MMPITest addMMPIAnswer(MMPIAnswer mMPIAnswer) {
        this.mMPIAnswers.add(mMPIAnswer);
        mMPIAnswer.setMMPITest(this);
        return this;
    }

    public MMPITest removeMMPIAnswer(MMPIAnswer mMPIAnswer) {
        this.mMPIAnswers.remove(mMPIAnswer);
        mMPIAnswer.setMMPITest(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MMPITest)) {
            return false;
        }
        return getId() != null && getId().equals(((MMPITest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MMPITest{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
