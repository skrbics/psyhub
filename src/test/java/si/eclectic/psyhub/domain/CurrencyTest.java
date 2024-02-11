package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.CurrencyTestSamples.*;
import static si.eclectic.psyhub.domain.SessionBillTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class CurrencyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Currency.class);
        Currency currency1 = getCurrencySample1();
        Currency currency2 = new Currency();
        assertThat(currency1).isNotEqualTo(currency2);

        currency2.setId(currency1.getId());
        assertThat(currency1).isEqualTo(currency2);

        currency2 = getCurrencySample2();
        assertThat(currency1).isNotEqualTo(currency2);
    }

    @Test
    void sessionBillTest() throws Exception {
        Currency currency = getCurrencyRandomSampleGenerator();
        SessionBill sessionBillBack = getSessionBillRandomSampleGenerator();

        currency.addSessionBill(sessionBillBack);
        assertThat(currency.getSessionBills()).containsOnly(sessionBillBack);
        assertThat(sessionBillBack.getCurrency()).isEqualTo(currency);

        currency.removeSessionBill(sessionBillBack);
        assertThat(currency.getSessionBills()).doesNotContain(sessionBillBack);
        assertThat(sessionBillBack.getCurrency()).isNull();

        currency.sessionBills(new HashSet<>(Set.of(sessionBillBack)));
        assertThat(currency.getSessionBills()).containsOnly(sessionBillBack);
        assertThat(sessionBillBack.getCurrency()).isEqualTo(currency);

        currency.setSessionBills(new HashSet<>());
        assertThat(currency.getSessionBills()).doesNotContain(sessionBillBack);
        assertThat(sessionBillBack.getCurrency()).isNull();
    }
}
