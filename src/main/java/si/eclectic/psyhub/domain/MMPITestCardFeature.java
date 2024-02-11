package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A MMPITestCardFeature.
 */
@Entity
@Table(name = "mmpi_test_card_feature")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MMPITestCardFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "answer_yes")
    private Boolean answerYes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "mMPIAnswers", "mMPITestCardFeatures" }, allowSetters = true)
    private MMPITestCard mMPITestCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "mMPITestCardFeatures" }, allowSetters = true)
    private MMPIFeature mMPIFeature;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MMPITestCardFeature id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAnswerYes() {
        return this.answerYes;
    }

    public MMPITestCardFeature answerYes(Boolean answerYes) {
        this.setAnswerYes(answerYes);
        return this;
    }

    public void setAnswerYes(Boolean answerYes) {
        this.answerYes = answerYes;
    }

    public MMPITestCard getMMPITestCard() {
        return this.mMPITestCard;
    }

    public void setMMPITestCard(MMPITestCard mMPITestCard) {
        this.mMPITestCard = mMPITestCard;
    }

    public MMPITestCardFeature mMPITestCard(MMPITestCard mMPITestCard) {
        this.setMMPITestCard(mMPITestCard);
        return this;
    }

    public MMPIFeature getMMPIFeature() {
        return this.mMPIFeature;
    }

    public void setMMPIFeature(MMPIFeature mMPIFeature) {
        this.mMPIFeature = mMPIFeature;
    }

    public MMPITestCardFeature mMPIFeature(MMPIFeature mMPIFeature) {
        this.setMMPIFeature(mMPIFeature);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MMPITestCardFeature)) {
            return false;
        }
        return getId() != null && getId().equals(((MMPITestCardFeature) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MMPITestCardFeature{" +
            "id=" + getId() +
            ", answerYes='" + getAnswerYes() + "'" +
            "}";
    }
}
