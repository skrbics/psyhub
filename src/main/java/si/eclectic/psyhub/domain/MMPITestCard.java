package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MMPITestCard.
 */
@Entity
@Table(name = "mmpi_test_card")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MMPITestCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "question")
    private String question;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mMPITestCard")
    @JsonIgnoreProperties(value = { "mMPITest", "mMPITestCard" }, allowSetters = true)
    private Set<MMPIAnswer> mMPIAnswers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mMPITestCard")
    @JsonIgnoreProperties(value = { "mMPITestCard", "mMPIFeature" }, allowSetters = true)
    private Set<MMPITestCardFeature> mMPITestCardFeatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MMPITestCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public MMPITestCard question(String question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<MMPIAnswer> getMMPIAnswers() {
        return this.mMPIAnswers;
    }

    public void setMMPIAnswers(Set<MMPIAnswer> mMPIAnswers) {
        if (this.mMPIAnswers != null) {
            this.mMPIAnswers.forEach(i -> i.setMMPITestCard(null));
        }
        if (mMPIAnswers != null) {
            mMPIAnswers.forEach(i -> i.setMMPITestCard(this));
        }
        this.mMPIAnswers = mMPIAnswers;
    }

    public MMPITestCard mMPIAnswers(Set<MMPIAnswer> mMPIAnswers) {
        this.setMMPIAnswers(mMPIAnswers);
        return this;
    }

    public MMPITestCard addMMPIAnswer(MMPIAnswer mMPIAnswer) {
        this.mMPIAnswers.add(mMPIAnswer);
        mMPIAnswer.setMMPITestCard(this);
        return this;
    }

    public MMPITestCard removeMMPIAnswer(MMPIAnswer mMPIAnswer) {
        this.mMPIAnswers.remove(mMPIAnswer);
        mMPIAnswer.setMMPITestCard(null);
        return this;
    }

    public Set<MMPITestCardFeature> getMMPITestCardFeatures() {
        return this.mMPITestCardFeatures;
    }

    public void setMMPITestCardFeatures(Set<MMPITestCardFeature> mMPITestCardFeatures) {
        if (this.mMPITestCardFeatures != null) {
            this.mMPITestCardFeatures.forEach(i -> i.setMMPITestCard(null));
        }
        if (mMPITestCardFeatures != null) {
            mMPITestCardFeatures.forEach(i -> i.setMMPITestCard(this));
        }
        this.mMPITestCardFeatures = mMPITestCardFeatures;
    }

    public MMPITestCard mMPITestCardFeatures(Set<MMPITestCardFeature> mMPITestCardFeatures) {
        this.setMMPITestCardFeatures(mMPITestCardFeatures);
        return this;
    }

    public MMPITestCard addMMPITestCardFeature(MMPITestCardFeature mMPITestCardFeature) {
        this.mMPITestCardFeatures.add(mMPITestCardFeature);
        mMPITestCardFeature.setMMPITestCard(this);
        return this;
    }

    public MMPITestCard removeMMPITestCardFeature(MMPITestCardFeature mMPITestCardFeature) {
        this.mMPITestCardFeatures.remove(mMPITestCardFeature);
        mMPITestCardFeature.setMMPITestCard(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MMPITestCard)) {
            return false;
        }
        return getId() != null && getId().equals(((MMPITestCard) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MMPITestCard{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            "}";
    }
}
