package si.eclectic.psyhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Session.
 */
@Entity
@Table(name = "session")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "location")
    private String location;

    @Column(name = "notes")
    private String notes;

    @Column(name = "completed")
    private Boolean completed;

    @JsonIgnoreProperties(value = { "currency", "session" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private SessionBill sessionBill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "address", "sessions", "mMPITests" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Session id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Session date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return this.location;
    }

    public Session location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return this.notes;
    }

    public Session notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public Session completed(Boolean completed) {
        this.setCompleted(completed);
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public SessionBill getSessionBill() {
        return this.sessionBill;
    }

    public void setSessionBill(SessionBill sessionBill) {
        this.sessionBill = sessionBill;
    }

    public Session sessionBill(SessionBill sessionBill) {
        this.setSessionBill(sessionBill);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Session client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return getId() != null && getId().equals(((Session) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", location='" + getLocation() + "'" +
            ", notes='" + getNotes() + "'" +
            ", completed='" + getCompleted() + "'" +
            "}";
    }
}
