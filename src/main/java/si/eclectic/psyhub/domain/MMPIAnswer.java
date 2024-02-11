package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A MMPIAnswer.
 */
@Entity
@Table(name = "mmpi_answer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MMPIAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "answered_yes")
    private Boolean answeredYes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "client", "mMPIAnswers" }, allowSetters = true)
    private MMPITest mMPITest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "mMPIAnswers", "mMPITestCardFeatures" }, allowSetters = true)
    private MMPITestCard mMPITestCard;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MMPIAnswer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAnsweredYes() {
        return this.answeredYes;
    }

    public MMPIAnswer answeredYes(Boolean answeredYes) {
        this.setAnsweredYes(answeredYes);
        return this;
    }

    public void setAnsweredYes(Boolean answeredYes) {
        this.answeredYes = answeredYes;
    }

    public MMPITest getMMPITest() {
        return this.mMPITest;
    }

    public void setMMPITest(MMPITest mMPITest) {
        this.mMPITest = mMPITest;
    }

    public MMPIAnswer mMPITest(MMPITest mMPITest) {
        this.setMMPITest(mMPITest);
        return this;
    }

    public MMPITestCard getMMPITestCard() {
        return this.mMPITestCard;
    }

    public void setMMPITestCard(MMPITestCard mMPITestCard) {
        this.mMPITestCard = mMPITestCard;
    }

    public MMPIAnswer mMPITestCard(MMPITestCard mMPITestCard) {
        this.setMMPITestCard(mMPITestCard);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MMPIAnswer)) {
            return false;
        }
        return getId() != null && getId().equals(((MMPIAnswer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MMPIAnswer{" +
            "id=" + getId() +
            ", answeredYes='" + getAnsweredYes() + "'" +
            "}";
    }
}
