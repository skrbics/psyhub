package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.MMPIAnswerTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestCardFeatureTestSamples.*;
import static si.eclectic.psyhub.domain.MMPITestCardTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class MMPITestCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMPITestCard.class);
        MMPITestCard mMPITestCard1 = getMMPITestCardSample1();
        MMPITestCard mMPITestCard2 = new MMPITestCard();
        assertThat(mMPITestCard1).isNotEqualTo(mMPITestCard2);

        mMPITestCard2.setId(mMPITestCard1.getId());
        assertThat(mMPITestCard1).isEqualTo(mMPITestCard2);

        mMPITestCard2 = getMMPITestCardSample2();
        assertThat(mMPITestCard1).isNotEqualTo(mMPITestCard2);
    }

    @Test
    void mMPIAnswerTest() throws Exception {
        MMPITestCard mMPITestCard = getMMPITestCardRandomSampleGenerator();
        MMPIAnswer mMPIAnswerBack = getMMPIAnswerRandomSampleGenerator();

        mMPITestCard.addMMPIAnswer(mMPIAnswerBack);
        assertThat(mMPITestCard.getMMPIAnswers()).containsOnly(mMPIAnswerBack);
        assertThat(mMPIAnswerBack.getMMPITestCard()).isEqualTo(mMPITestCard);

        mMPITestCard.removeMMPIAnswer(mMPIAnswerBack);
        assertThat(mMPITestCard.getMMPIAnswers()).doesNotContain(mMPIAnswerBack);
        assertThat(mMPIAnswerBack.getMMPITestCard()).isNull();

        mMPITestCard.mMPIAnswers(new HashSet<>(Set.of(mMPIAnswerBack)));
        assertThat(mMPITestCard.getMMPIAnswers()).containsOnly(mMPIAnswerBack);
        assertThat(mMPIAnswerBack.getMMPITestCard()).isEqualTo(mMPITestCard);

        mMPITestCard.setMMPIAnswers(new HashSet<>());
        assertThat(mMPITestCard.getMMPIAnswers()).doesNotContain(mMPIAnswerBack);
        assertThat(mMPIAnswerBack.getMMPITestCard()).isNull();
    }

    @Test
    void mMPITestCardFeatureTest() throws Exception {
        MMPITestCard mMPITestCard = getMMPITestCardRandomSampleGenerator();
        MMPITestCardFeature mMPITestCardFeatureBack = getMMPITestCardFeatureRandomSampleGenerator();

        mMPITestCard.addMMPITestCardFeature(mMPITestCardFeatureBack);
        assertThat(mMPITestCard.getMMPITestCardFeatures()).containsOnly(mMPITestCardFeatureBack);
        assertThat(mMPITestCardFeatureBack.getMMPITestCard()).isEqualTo(mMPITestCard);

        mMPITestCard.removeMMPITestCardFeature(mMPITestCardFeatureBack);
        assertThat(mMPITestCard.getMMPITestCardFeatures()).doesNotContain(mMPITestCardFeatureBack);
        assertThat(mMPITestCardFeatureBack.getMMPITestCard()).isNull();

        mMPITestCard.mMPITestCardFeatures(new HashSet<>(Set.of(mMPITestCardFeatureBack)));
        assertThat(mMPITestCard.getMMPITestCardFeatures()).containsOnly(mMPITestCardFeatureBack);
        assertThat(mMPITestCardFeatureBack.getMMPITestCard()).isEqualTo(mMPITestCard);

        mMPITestCard.setMMPITestCardFeatures(new HashSet<>());
        assertThat(mMPITestCard.getMMPITestCardFeatures()).doesNotContain(mMPITestCardFeatureBack);
        assertThat(mMPITestCardFeatureBack.getMMPITestCard()).isNull();
    }
}
