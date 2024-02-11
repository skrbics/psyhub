package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.AddressTestSamples.*;
import static si.eclectic.psyhub.domain.OfficeTestSamples.*;
import static si.eclectic.psyhub.domain.TherapistTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class OfficeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Office.class);
        Office office1 = getOfficeSample1();
        Office office2 = new Office();
        assertThat(office1).isNotEqualTo(office2);

        office2.setId(office1.getId());
        assertThat(office1).isEqualTo(office2);

        office2 = getOfficeSample2();
        assertThat(office1).isNotEqualTo(office2);
    }

    @Test
    void addressTest() throws Exception {
        Office office = getOfficeRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        office.setAddress(addressBack);
        assertThat(office.getAddress()).isEqualTo(addressBack);

        office.address(null);
        assertThat(office.getAddress()).isNull();
    }

    @Test
    void therapistTest() throws Exception {
        Office office = getOfficeRandomSampleGenerator();
        Therapist therapistBack = getTherapistRandomSampleGenerator();

        office.addTherapist(therapistBack);
        assertThat(office.getTherapists()).containsOnly(therapistBack);
        assertThat(therapistBack.getOffice()).isEqualTo(office);

        office.removeTherapist(therapistBack);
        assertThat(office.getTherapists()).doesNotContain(therapistBack);
        assertThat(therapistBack.getOffice()).isNull();

        office.therapists(new HashSet<>(Set.of(therapistBack)));
        assertThat(office.getTherapists()).containsOnly(therapistBack);
        assertThat(therapistBack.getOffice()).isEqualTo(office);

        office.setTherapists(new HashSet<>());
        assertThat(office.getTherapists()).doesNotContain(therapistBack);
        assertThat(therapistBack.getOffice()).isNull();
    }
}
