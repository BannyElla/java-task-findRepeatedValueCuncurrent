/*
 * В массиве найти повторяющийся элемент. Задача должна решаться в режиме многопоточности.
 * Как только один из потоков нашел повторяющийся элемент, все оставшиеся потоки должны быть остановлены.
 */
package findRepeatedValueCuncurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Ella
 */
public class MainTask {
    public static AtomicInteger repeatedValue = new AtomicInteger(-1);
    private static final int ARRAY_SIZE = 25;
    private static final int TRESHOLD = 4;
    private static final int BARRIER_COUNT = (int)Math.ceil(ARRAY_SIZE/TRESHOLD);

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch runningLatch = new CountDownLatch(1);
        CyclicBarrier barrier = new CyclicBarrier(BARRIER_COUNT);

        int[] arr = ArrayGenerator.generate(ARRAY_SIZE);
        ExecutorService service = Executors.newCachedThreadPool();

        for (int i = 0; i < arr.length; i += TRESHOLD) {
            int start = i;
            int end = start + TRESHOLD >= arr.length ? arr.length
                                                     : start + TRESHOLD;
            ArrayData arrData = new ArrayData(arr, start, end);
            service.execute(new Finder(arrData, barrier, runningLatch));
        }

        runningLatch.await(500*BARRIER_COUNT, TimeUnit.MILLISECONDS);
        service.shutdownNow();
        String message = repeatedValue.get() == -1 
                                        ? "All values are unique" 
                                        : "REPEATED VALUE IS " + repeatedValue;
        System.out.println(message);
    }
}
