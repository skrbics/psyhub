package si.eclectic.psyhub.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CurrencyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Currency getCurrencySample1() {
        return new Currency().id(1L).name("name1").code("code1");
    }

    public static Currency getCurrencySample2() {
        return new Currency().id(2L).name("name2").code("code2");
    }

    public static Currency getCurrencyRandomSampleGenerator() {
        return new Currency().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).code(UUID.randomUUID().toString());
    }
}
