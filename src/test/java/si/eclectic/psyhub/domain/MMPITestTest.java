package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.ClientTestSamples.*;
import static si.eclectic.psyhub.domain.MMPIAnswerTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class MMPITestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMPITest.class);
        MMPITest mMPITest1 = getMMPITestSample1();
        MMPITest mMPITest2 = new MMPITest();
        assertThat(mMPITest1).isNotEqualTo(mMPITest2);

        mMPITest2.setId(mMPITest1.getId());
        assertThat(mMPITest1).isEqualTo(mMPITest2);

        mMPITest2 = getMMPITestSample2();
        assertThat(mMPITest1).isNotEqualTo(mMPITest2);
    }

    @Test
    void clientTest() throws Exception {
        MMPITest mMPITest = getMMPITestRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        mMPITest.setClient(clientBack);
        assertThat(mMPITest.getClient()).isEqualTo(clientBack);

        mMPITest.client(null);
        assertThat(mMPITest.getClient()).isNull();
    }

    @Test
    void mMPIAnswerTest() throws Exception {
        MMPITest mMPITest = getMMPITestRandomSampleGenerator();
        MMPIAnswer mMPIAnswerBack = getMMPIAnswerRandomSampleGenerator();

        mMPITest.addMMPIAnswer(mMPIAnswerBack);
        assertThat(mMPITest.getMMPIAnswers()).containsOnly(mMPIAnswerBack);
        assertThat(mMPIAnswerBack.getMMPITest()).isEqualTo(mMPITest);

        mMPITest.removeMMPIAnswer(mMPIAnswerBack);
        assertThat(mMPITest.getMMPIAnswers()).doesNotContain(mMPIAnswerBack);
        assertThat(mMPIAnswerBack.getMMPITest()).isNull();

        mMPITest.mMPIAnswers(new HashSet<>(Set.of(mMPIAnswerBack)));
        assertThat(mMPITest.getMMPIAnswers()).containsOnly(mMPIAnswerBack);
        assertThat(mMPIAnswerBack.getMMPITest()).isEqualTo(mMPITest);

        mMPITest.setMMPIAnswers(new HashSet<>());
        assertThat(mMPITest.getMMPIAnswers()).doesNotContain(mMPIAnswerBack);
        assertThat(mMPIAnswerBack.getMMPITest()).isNull();
    }
}
