package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Office.
 */
@Entity
@Table(name = "office")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "website")
    private String website;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @JsonIgnoreProperties(value = { "city", "country", "client", "office" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office")
    @JsonIgnoreProperties(value = { "office" }, allowSetters = true)
    private Set<Therapist> therapists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Office id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficeName() {
        return this.officeName;
    }

    public Office officeName(String officeName) {
        this.setOfficeName(officeName);
        return this;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getWebsite() {
        return this.website;
    }

    public Office website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return this.email;
    }

    public Office email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Office phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Office address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<Therapist> getTherapists() {
        return this.therapists;
    }

    public void setTherapists(Set<Therapist> therapists) {
        if (this.therapists != null) {
            this.therapists.forEach(i -> i.setOffice(null));
        }
        if (therapists != null) {
            therapists.forEach(i -> i.setOffice(this));
        }
        this.therapists = therapists;
    }

    public Office therapists(Set<Therapist> therapists) {
        this.setTherapists(therapists);
        return this;
    }

    public Office addTherapist(Therapist therapist) {
        this.therapists.add(therapist);
        therapist.setOffice(this);
        return this;
    }

    public Office removeTherapist(Therapist therapist) {
        this.therapists.remove(therapist);
        therapist.setOffice(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Office)) {
            return false;
        }
        return getId() != null && getId().equals(((Office) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Office{" +
            "id=" + getId() +
            ", officeName='" + getOfficeName() + "'" +
            ", website='" + getWebsite() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
