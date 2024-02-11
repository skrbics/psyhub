package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "house_no")
    private String houseNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses" }, allowSetters = true)
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses" }, allowSetters = true)
    private Country country;

    @JsonIgnoreProperties(value = { "address", "sessions", "mMPITests" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Client client;

    @JsonIgnoreProperties(value = { "address", "therapists" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Office office;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return this.street;
    }

    public Address street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return this.houseNo;
    }

    public Address houseNo(String houseNo) {
        this.setHouseNo(houseNo);
        return this;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Address city(City city) {
        this.setCity(city);
        return this;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Address country(Country country) {
        this.setCountry(country);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        if (this.client != null) {
            this.client.setAddress(null);
        }
        if (client != null) {
            client.setAddress(this);
        }
        this.client = client;
    }

    public Address client(Client client) {
        this.setClient(client);
        return this;
    }

    public Office getOffice() {
        return this.office;
    }

    public void setOffice(Office office) {
        if (this.office != null) {
            this.office.setAddress(null);
        }
        if (office != null) {
            office.setAddress(this);
        }
        this.office = office;
    }

    public Address office(Office office) {
        this.setOffice(office);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return getId() != null && getId().equals(((Address) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", houseNo='" + getHouseNo() + "'" +
            "}";
    }
}
