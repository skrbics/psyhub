package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @JsonIgnoreProperties(value = { "city", "country", "client", "office" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonIgnoreProperties(value = { "sessionBill", "client" }, allowSetters = true)
    private Set<Session> sessions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonIgnoreProperties(value = { "client", "mMPIAnswers" }, allowSetters = true)
    private Set<MMPITest> mMPITests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Client firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Client middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Client lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Client phone(String phone) {
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

    public Client address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<Session> getSessions() {
        return this.sessions;
    }

    public void setSessions(Set<Session> sessions) {
        if (this.sessions != null) {
            this.sessions.forEach(i -> i.setClient(null));
        }
        if (sessions != null) {
            sessions.forEach(i -> i.setClient(this));
        }
        this.sessions = sessions;
    }

    public Client sessions(Set<Session> sessions) {
        this.setSessions(sessions);
        return this;
    }

    public Client addSession(Session session) {
        this.sessions.add(session);
        session.setClient(this);
        return this;
    }

    public Client removeSession(Session session) {
        this.sessions.remove(session);
        session.setClient(null);
        return this;
    }

    public Set<MMPITest> getMMPITests() {
        return this.mMPITests;
    }

    public void setMMPITests(Set<MMPITest> mMPITests) {
        if (this.mMPITests != null) {
            this.mMPITests.forEach(i -> i.setClient(null));
        }
        if (mMPITests != null) {
            mMPITests.forEach(i -> i.setClient(this));
        }
        this.mMPITests = mMPITests;
    }

    public Client mMPITests(Set<MMPITest> mMPITests) {
        this.setMMPITests(mMPITests);
        return this;
    }

    public Client addMMPITest(MMPITest mMPITest) {
        this.mMPITests.add(mMPITest);
        mMPITest.setClient(this);
        return this;
    }

    public Client removeMMPITest(MMPITest mMPITest) {
        this.mMPITests.remove(mMPITest);
        mMPITest.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return getId() != null && getId().equals(((Client) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
