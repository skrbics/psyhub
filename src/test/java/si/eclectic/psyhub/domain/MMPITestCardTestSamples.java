package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MMPITestCardTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MMPITestCard getMMPITestCardSample1() {
        return new MMPITestCard().id(1L).question("question1");
    }

    public static MMPITestCard getMMPITestCardSample2() {
        return new MMPITestCard().id(2L).question("question2");
    }

    public static MMPITestCard getMMPITestCardRandomSampleGenerator() {
        return new MMPITestCard().id(longCount.incrementAndGet()).question(UUID.randomUUID().toString());
    }
}
