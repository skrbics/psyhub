package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MMPIFeature.
 */
@Entity
@Table(name = "mmpi_feature")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MMPIFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mMPIFeature")
    @JsonIgnoreProperties(value = { "mMPITestCard", "mMPIFeature" }, allowSetters = true)
    private Set<MMPITestCardFeature> mMPITestCardFeatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MMPIFeature id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public MMPIFeature name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MMPITestCardFeature> getMMPITestCardFeatures() {
        return this.mMPITestCardFeatures;
    }

    public void setMMPITestCardFeatures(Set<MMPITestCardFeature> mMPITestCardFeatures) {
        if (this.mMPITestCardFeatures != null) {
            this.mMPITestCardFeatures.forEach(i -> i.setMMPIFeature(null));
        }
        if (mMPITestCardFeatures != null) {
            mMPITestCardFeatures.forEach(i -> i.setMMPIFeature(this));
        }
        this.mMPITestCardFeatures = mMPITestCardFeatures;
    }

    public MMPIFeature mMPITestCardFeatures(Set<MMPITestCardFeature> mMPITestCardFeatures) {
        this.setMMPITestCardFeatures(mMPITestCardFeatures);
        return this;
    }

    public MMPIFeature addMMPITestCardFeature(MMPITestCardFeature mMPITestCardFeature) {
        this.mMPITestCardFeatures.add(mMPITestCardFeature);
        mMPITestCardFeature.setMMPIFeature(this);
        return this;
    }

    public MMPIFeature removeMMPITestCardFeature(MMPITestCardFeature mMPITestCardFeature) {
        this.mMPITestCardFeatures.remove(mMPITestCardFeature);
        mMPITestCardFeature.setMMPIFeature(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MMPIFeature)) {
            return false;
        }
        return getId() != null && getId().equals(((MMPIFeature) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MMPIFeature{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
