package findRepeatedValueCuncurrent;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Ella
 */
public class ArrayGenerator {
    private static final int FROM = 0;
    private static final int TO = 25;

    public static int[] generate(int size) {
        int[] arr = new Random().ints(size, FROM, TO).toArray();
        System.out.println(Arrays.toString(arr));
        return arr;
    }
}
