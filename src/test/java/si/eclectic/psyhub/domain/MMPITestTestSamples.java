package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MMPITestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MMPITest getMMPITestSample1() {
        return new MMPITest().id(1L).order(1);
    }

    public static MMPITest getMMPITestSample2() {
        return new MMPITest().id(2L).order(2);
    }

    public static MMPITest getMMPITestRandomSampleGenerator() {
        return new MMPITest().id(longCount.incrementAndGet()).order(intCount.incrementAndGet());
    }
}
