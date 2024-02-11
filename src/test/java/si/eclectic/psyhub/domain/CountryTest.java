package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.AddressTestSamples.*;
import static si.eclectic.psyhub.domain.CountryTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class CountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = getCountrySample1();
        Country country2 = new Country();
        assertThat(country1).isNotEqualTo(country2);

        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);

        country2 = getCountrySample2();
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    void addressTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        country.addAddress(addressBack);
        assertThat(country.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCountry()).isEqualTo(country);

        country.removeAddress(addressBack);
        assertThat(country.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCountry()).isNull();

        country.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(country.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCountry()).isEqualTo(country);

        country.setAddresses(new HashSet<>());
        assertThat(country.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCountry()).isNull();
    }
}
