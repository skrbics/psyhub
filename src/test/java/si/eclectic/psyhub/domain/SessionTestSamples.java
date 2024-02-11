package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SessionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Session getSessionSample1() {
        return new Session().id(1L).location("location1").notes("notes1");
    }

    public static Session getSessionSample2() {
        return new Session().id(2L).location("location2").notes("notes2");
    }

    public static Session getSessionRandomSampleGenerator() {
        return new Session().id(longCount.incrementAndGet()).location(UUID.randomUUID().toString()).notes(UUID.randomUUID().toString());
    }
}
