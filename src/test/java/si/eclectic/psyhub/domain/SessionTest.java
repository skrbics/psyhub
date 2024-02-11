package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.ClientTestSamples.*;
import static si.eclectic.psyhub.domain.SessionBillTestSamples.*;
import static si.eclectic.psyhub.domain.SessionTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class SessionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Session.class);
        Session session1 = getSessionSample1();
        Session session2 = new Session();
        assertThat(session1).isNotEqualTo(session2);

        session2.setId(session1.getId());
        assertThat(session1).isEqualTo(session2);

        session2 = getSessionSample2();
        assertThat(session1).isNotEqualTo(session2);
    }

    @Test
    void sessionBillTest() throws Exception {
        Session session = getSessionRandomSampleGenerator();
        SessionBill sessionBillBack = getSessionBillRandomSampleGenerator();

        session.setSessionBill(sessionBillBack);
        assertThat(session.getSessionBill()).isEqualTo(sessionBillBack);

        session.sessionBill(null);
        assertThat(session.getSessionBill()).isNull();
    }

    @Test
    void clientTest() throws Exception {
        Session session = getSessionRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        session.setClient(clientBack);
        assertThat(session.getClient()).isEqualTo(clientBack);

        session.client(null);
        assertThat(session.getClient()).isNull();
    }
}
