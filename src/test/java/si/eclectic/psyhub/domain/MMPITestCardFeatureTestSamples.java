package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MMPITestCardFeatureTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MMPITestCardFeature getMMPITestCardFeatureSample1() {
        return new MMPITestCardFeature().id(1L);
    }

    public static MMPITestCardFeature getMMPITestCardFeatureSample2() {
        return new MMPITestCardFeature().id(2L);
    }

    public static MMPITestCardFeature getMMPITestCardFeatureRandomSampleGenerator() {
        return new MMPITestCardFeature().id(longCount.incrementAndGet());
    }
}
