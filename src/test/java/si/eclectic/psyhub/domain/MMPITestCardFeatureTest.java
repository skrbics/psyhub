package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.MMPIFeatureTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestCardFeatureTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestCardTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class MMPITestCardFeatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMPITestCardFeature.class);
        MMPITestCardFeature mMPITestCardFeature1 = getMMPITestCardFeatureSample1();
        MMPITestCardFeature mMPITestCardFeature2 = new MMPITestCardFeature();
        assertThat(mMPITestCardFeature1).isNotEqualTo(mMPITestCardFeature2);

        mMPITestCardFeature2.setId(mMPITestCardFeature1.getId());
        assertThat(mMPITestCardFeature1).isEqualTo(mMPITestCardFeature2);

        mMPITestCardFeature2 = getMMPITestCardFeatureSample2();
        assertThat(mMPITestCardFeature1).isNotEqualTo(mMPITestCardFeature2);
    }

    @Test
    void mMPITestCardTest() throws Exception {
        MMPITestCardFeature mMPITestCardFeature = getMMPITestCardFeatureRandomSampleGenerator();
        MMPITestCard mMPITestCardBack = getMMPITestCardRandomSampleGenerator();

        mMPITestCardFeature.setMMPITestCard(mMPITestCardBack);
        assertThat(mMPITestCardFeature.getMMPITestCard()).isEqualTo(mMPITestCardBack);

        mMPITestCardFeature.mMPITestCard(null);
        assertThat(mMPITestCardFeature.getMMPITestCard()).isNull();
    }

    @Test
    void mMPIFeatureTest() throws Exception {
        MMPITestCardFeature mMPITestCardFeature = getMMPITestCardFeatureRandomSampleGenerator();
        MMPIFeature mMPIFeatureBack = getMMPIFeatureRandomSampleGenerator();

        mMPITestCardFeature.setMMPIFeature(mMPIFeatureBack);
        assertThat(mMPITestCardFeature.getMMPIFeature()).isEqualTo(mMPIFeatureBack);

        mMPITestCardFeature.mMPIFeature(null);
        assertThat(mMPITestCardFeature.getMMPIFeature()).isNull();
    }
}
