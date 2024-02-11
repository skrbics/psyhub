package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.MMPIFeatureTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestCardFeatureTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class MMPIFeatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMPIFeature.class);
        MMPIFeature mMPIFeature1 = getMMPIFeatureSample1();
        MMPIFeature mMPIFeature2 = new MMPIFeature();
        assertThat(mMPIFeature1).isNotEqualTo(mMPIFeature2);

        mMPIFeature2.setId(mMPIFeature1.getId());
        assertThat(mMPIFeature1).isEqualTo(mMPIFeature2);

        mMPIFeature2 = getMMPIFeatureSample2();
        assertThat(mMPIFeature1).isNotEqualTo(mMPIFeature2);
    }

    @Test
    void mMPITestCardFeatureTest() throws Exception {
        MMPIFeature mMPIFeature = getMMPIFeatureRandomSampleGenerator();
        MMPITestCardFeature mMPITestCardFeatureBack = getMMPITestCardFeatureRandomSampleGenerator();

        mMPIFeature.addMMPITestCardFeature(mMPITestCardFeatureBack);
        assertThat(mMPIFeature.getMMPITestCardFeatures()).containsOnly(mMPITestCardFeatureBack);
        assertThat(mMPITestCardFeatureBack.getMMPIFeature()).isEqualTo(mMPIFeature);

        mMPIFeature.removeMMPITestCardFeature(mMPITestCardFeatureBack);
        assertThat(mMPIFeature.getMMPITestCardFeatures()).doesNotContain(mMPITestCardFeatureBack);
        assertThat(mMPITestCardFeatureBack.getMMPIFeature()).isNull();

        mMPIFeature.mMPITestCardFeatures(new HashSet<>(Set.of(mMPITestCardFeatureBack)));
        assertThat(mMPIFeature.getMMPITestCardFeatures()).containsOnly(mMPITestCardFeatureBack);
        assertThat(mMPITestCardFeatureBack.getMMPIFeature()).isEqualTo(mMPIFeature);

        mMPIFeature.setMMPITestCardFeatures(new HashSet<>());
        assertThat(mMPIFeature.getMMPITestCardFeatures()).doesNotContain(mMPITestCardFeatureBack);
        assertThat(mMPITestCardFeatureBack.getMMPIFeature()).isNull();
    }
}
