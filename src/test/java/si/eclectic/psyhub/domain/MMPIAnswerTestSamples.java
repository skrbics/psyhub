package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MMPIAnswerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MMPIAnswer getMMPIAnswerSample1() {
        return new MMPIAnswer().id(1L);
    }

    public static MMPIAnswer getMMPIAnswerSample2() {
        return new MMPIAnswer().id(2L);
    }

    public static MMPIAnswer getMMPIAnswerRandomSampleGenerator() {
        return new MMPIAnswer().id(longCount.incrementAndGet());
    }
}
