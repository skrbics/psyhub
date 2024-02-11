package si.eclectic.psyhub.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.psyhub.domain.AddressTestSamples.*;
import static si.eclectic.psyhub.domain.CityTestSamples.*;
import static si.eclectic.psyhub.domain.ClientTestSamples.*;
import static si.eclectic.psyhub.domain.CountryTestSamples.*;
import static si.eclectic.psyhub.domain.OfficeTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.psyhub.web.rest.TestUtil;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void cityTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        City cityBack = getCityRandomSampleGenerator();

        address.setCity(cityBack);
        assertThat(address.getCity()).isEqualTo(cityBack);

        address.city(null);
        assertThat(address.getCity()).isNull();
    }

    @Test
    void countryTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        address.setCountry(countryBack);
        assertThat(address.getCountry()).isEqualTo(countryBack);

        address.country(null);
        assertThat(address.getCountry()).isNull();
    }

    @Test
    void clientTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        address.setClient(clientBack);
        assertThat(address.getClient()).isEqualTo(clientBack);
        assertThat(clientBack.getAddress()).isEqualTo(address);

        address.client(null);
        assertThat(address.getClient()).isNull();
        assertThat(clientBack.getAddress()).isNull();
    }

    @Test
    void officeTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        Office officeBack = getOfficeRandomSampleGenerator();

        address.setOffice(officeBack);
        assertThat(address.getOffice()).isEqualTo(officeBack);
        assertThat(officeBack.getAddress()).isEqualTo(address);

        address.office(null);
        assertThat(address.getOffice()).isNull();
        assertThat(officeBack.getAddress()).isNull();
    }
}
