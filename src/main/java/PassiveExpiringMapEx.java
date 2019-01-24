import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections4.map.PassiveExpiringMap;

import java.util.concurrent.TimeUnit;

/**
 * Demonstrate simple usage of PassiveExpiringMap with Expiration policy.
 */
public class PassiveExpiringMapEx {
    // Expiration policy(4 seconds)
    PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<String, String>
            expirePeriod = new PassiveExpiringMap.ConstantTimeToLiveExpirationPolicy<>(
            4, TimeUnit.SECONDS);

    private PassiveExpiringMap<String, String> map = new PassiveExpiringMap<>(expirePeriod);

    public PassiveExpiringMapEx() {
        writeMapValues();
        printMapsToJSON();
        readMapValues();
    }

    /**
     * Prints out map values to JSON format.
     */
    private void printMapsToJSON() {
        String json = new GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(map, new TypeToken<PassiveExpiringMap<String, String>>() { }.getType());
        System.out.println("Printing map values: \n" + json);
    }

    /**
     * Read map values and print out them. For each loop, values will expire one by one.
     */
    private void readMapValues() {
        while (map.size() > 0) {
            System.out.println(map.values());
            sleepOneSecond();
        }
    }

    /**
     * Put values to map. Sleep 1 second for each put method call.
     * It will take total 4 seconds so the first value "referrer" will never printed out.
     * Because expiration policy is set to 4 seconds.
     */
    private void writeMapValues() {
        map.put("referrer", "referrer_test");
        sleepOneSecond();
        map.put("utm_a", "utm_a_test");
        sleepOneSecond();
        map.put("utm_b", "utm_b_test");
        sleepOneSecond();
        map.put("utm_c", "utm_c_test");
        sleepOneSecond();
    }

    /**
     * Sleeps one second.
     */
    private void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new PassiveExpiringMapEx();
    }
}
