package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.AddressTestSamples.*;
import static si.eclectic.psyhub.domain.ClientTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestTestSamples.*;
import static si.eclectic.psyhub.domain.SessionTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void addressTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        client.setAddress(addressBack);
        assertThat(client.getAddress()).isEqualTo(addressBack);

        client.address(null);
        assertThat(client.getAddress()).isNull();
    }

    @Test
    void sessionTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Session sessionBack = getSessionRandomSampleGenerator();

        client.addSession(sessionBack);
        assertThat(client.getSessions()).containsOnly(sessionBack);
        assertThat(sessionBack.getClient()).isEqualTo(client);

        client.removeSession(sessionBack);
        assertThat(client.getSessions()).doesNotContain(sessionBack);
        assertThat(sessionBack.getClient()).isNull();

        client.sessions(new HashSet<>(Set.of(sessionBack)));
        assertThat(client.getSessions()).containsOnly(sessionBack);
        assertThat(sessionBack.getClient()).isEqualTo(client);

        client.setSessions(new HashSet<>());
        assertThat(client.getSessions()).doesNotContain(sessionBack);
        assertThat(sessionBack.getClient()).isNull();
    }

    @Test
    void mMPITestTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        MMPITest mMPITestBack = getMMPITestRandomSampleGenerator();

        client.addMMPITest(mMPITestBack);
        assertThat(client.getMMPITests()).containsOnly(mMPITestBack);
        assertThat(mMPITestBack.getClient()).isEqualTo(client);

        client.removeMMPITest(mMPITestBack);
        assertThat(client.getMMPITests()).doesNotContain(mMPITestBack);
        assertThat(mMPITestBack.getClient()).isNull();

        client.mMPITests(new HashSet<>(Set.of(mMPITestBack)));
        assertThat(client.getMMPITests()).containsOnly(mMPITestBack);
        assertThat(mMPITestBack.getClient()).isEqualTo(client);

        client.setMMPITests(new HashSet<>());
        assertThat(client.getMMPITests()).doesNotContain(mMPITestBack);
        assertThat(mMPITestBack.getClient()).isNull();
    }
}
