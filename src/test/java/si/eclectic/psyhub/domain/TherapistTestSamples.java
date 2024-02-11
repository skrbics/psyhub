package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TherapistTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Therapist getTherapistSample1() {
        return new Therapist().id(1L).firstName("firstName1").middleName("middleName1").lastName("lastName1").userid("userid1");
    }

    public static Therapist getTherapistSample2() {
        return new Therapist().id(2L).firstName("firstName2").middleName("middleName2").lastName("lastName2").userid("userid2");
    }

    public static Therapist getTherapistRandomSampleGenerator() {
        return new Therapist()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .middleName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .userid(UUID.randomUUID().toString());
    }
}
