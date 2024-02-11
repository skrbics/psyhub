package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.OfficeTestSamples.*;
import static si.eclectic.psyhub.domain.TherapistTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class TherapistTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Therapist.class);
        Therapist therapist1 = getTherapistSample1();
        Therapist therapist2 = new Therapist();
        assertThat(therapist1).isNotEqualTo(therapist2);

        therapist2.setId(therapist1.getId());
        assertThat(therapist1).isEqualTo(therapist2);

        therapist2 = getTherapistSample2();
        assertThat(therapist1).isNotEqualTo(therapist2);
    }

    @Test
    void officeTest() throws Exception {
        Therapist therapist = getTherapistRandomSampleGenerator();
        Office officeBack = getOfficeRandomSampleGenerator();

        therapist.setOffice(officeBack);
        assertThat(therapist.getOffice()).isEqualTo(officeBack);

        therapist.office(null);
        assertThat(therapist.getOffice()).isNull();
    }
}
