package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.AddressTestSamples.*;
import static si.eclectic.psyhub.domain.CityTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class CityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(City.class);
        City city1 = getCitySample1();
        City city2 = new City();
        assertThat(city1).isNotEqualTo(city2);

        city2.setId(city1.getId());
        assertThat(city1).isEqualTo(city2);

        city2 = getCitySample2();
        assertThat(city1).isNotEqualTo(city2);
    }

    @Test
    void addressTest() throws Exception {
        City city = getCityRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        city.addAddress(addressBack);
        assertThat(city.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCity()).isEqualTo(city);

        city.removeAddress(addressBack);
        assertThat(city.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCity()).isNull();

        city.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(city.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCity()).isEqualTo(city);

        city.setAddresses(new HashSet<>());
        assertThat(city.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCity()).isNull();
    }
}
