package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.CurrencyTestSamples.*;
import static si.eclectic.psyhub.domain.SessionBillTestSamples.*;
import static si.eclectic.psyhub.domain.SessionTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class SessionBillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionBill.class);
        SessionBill sessionBill1 = getSessionBillSample1();
        SessionBill sessionBill2 = new SessionBill();
        assertThat(sessionBill1).isNotEqualTo(sessionBill2);

        sessionBill2.setId(sessionBill1.getId());
        assertThat(sessionBill1).isEqualTo(sessionBill2);

        sessionBill2 = getSessionBillSample2();
        assertThat(sessionBill1).isNotEqualTo(sessionBill2);
    }

    @Test
    void currencyTest() throws Exception {
        SessionBill sessionBill = getSessionBillRandomSampleGenerator();
        Currency currencyBack = getCurrencyRandomSampleGenerator();

        sessionBill.setCurrency(currencyBack);
        assertThat(sessionBill.getCurrency()).isEqualTo(currencyBack);

        sessionBill.currency(null);
        assertThat(sessionBill.getCurrency()).isNull();
    }

    @Test
    void sessionTest() throws Exception {
        SessionBill sessionBill = getSessionBillRandomSampleGenerator();
        Session sessionBack = getSessionRandomSampleGenerator();

        sessionBill.setSession(sessionBack);
        assertThat(sessionBill.getSession()).isEqualTo(sessionBack);
        assertThat(sessionBack.getSessionBill()).isEqualTo(sessionBill);

        sessionBill.session(null);
        assertThat(sessionBill.getSession()).isNull();
        assertThat(sessionBack.getSessionBill()).isNull();
    }
}
