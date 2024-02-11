package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.MMPIAnswerTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestCardTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class MMPIAnswerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMPIAnswer.class);
        MMPIAnswer mMPIAnswer1 = getMMPIAnswerSample1();
        MMPIAnswer mMPIAnswer2 = new MMPIAnswer();
        assertThat(mMPIAnswer1).isNotEqualTo(mMPIAnswer2);

        mMPIAnswer2.setId(mMPIAnswer1.getId());
        assertThat(mMPIAnswer1).isEqualTo(mMPIAnswer2);

        mMPIAnswer2 = getMMPIAnswerSample2();
        assertThat(mMPIAnswer1).isNotEqualTo(mMPIAnswer2);
    }

    @Test
    void mMPITestTest() throws Exception {
        MMPIAnswer mMPIAnswer = getMMPIAnswerRandomSampleGenerator();
        MMPITest mMPITestBack = getMMPITestRandomSampleGenerator();

        mMPIAnswer.setMMPITest(mMPITestBack);
        assertThat(mMPIAnswer.getMMPITest()).isEqualTo(mMPITestBack);

        mMPIAnswer.mMPITest(null);
        assertThat(mMPIAnswer.getMMPITest()).isNull();
    }

    @Test
    void mMPITestCardTest() throws Exception {
        MMPIAnswer mMPIAnswer = getMMPIAnswerRandomSampleGenerator();
        MMPITestCard mMPITestCardBack = getMMPITestCardRandomSampleGenerator();

        mMPIAnswer.setMMPITestCard(mMPITestCardBack);
        assertThat(mMPIAnswer.getMMPITestCard()).isEqualTo(mMPITestCardBack);

        mMPIAnswer.mMPITestCard(null);
        assertThat(mMPIAnswer.getMMPITestCard()).isNull();
    }
}
