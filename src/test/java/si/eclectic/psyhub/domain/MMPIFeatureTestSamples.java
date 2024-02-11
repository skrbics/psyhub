package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MMPIFeatureTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MMPIFeature getMMPIFeatureSample1() {
        return new MMPIFeature().id(1L).name("name1");
    }

    public static MMPIFeature getMMPIFeatureSample2() {
        return new MMPIFeature().id(2L).name("name2");
    }

    public static MMPIFeature getMMPIFeatureRandomSampleGenerator() {
        return new MMPIFeature().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
