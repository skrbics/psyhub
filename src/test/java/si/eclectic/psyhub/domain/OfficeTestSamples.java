package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OfficeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Office getOfficeSample1() {
        return new Office().id(1L).officeName("officeName1").website("website1").email("email1").phone("phone1");
    }

    public static Office getOfficeSample2() {
        return new Office().id(2L).officeName("officeName2").website("website2").email("email2").phone("phone2");
    }

    public static Office getOfficeRandomSampleGenerator() {
        return new Office()
            .id(longCount.incrementAndGet())
            .officeName(UUID.randomUUID().toString())
            .website(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString());
    }
}
