package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SessionBillTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SessionBill getSessionBillSample1() {
        return new SessionBill().id(1L);
    }

    public static SessionBill getSessionBillSample2() {
        return new SessionBill().id(2L);
    }

    public static SessionBill getSessionBillRandomSampleGenerator() {
        return new SessionBill().id(longCount.incrementAndGet());
    }
}
