
package findRepeatedValueCuncurrent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

class Finder implements Runnable {

    private String threadName;
    private int[] arr;
    private int start;
    private int end;
    private CyclicBarrier barrier;
    private CountDownLatch runningLatch;
    private static Set<Integer> set = Collections
                                        .synchronizedSet(new TreeSet<>());

    Finder(ArrayData arrData, CyclicBarrier barrier,
                              CountDownLatch runningLatch) {
        this.arr = arrData.getArr();
        this.start = arrData.getStart();
        this.end = arrData.getEnd();
        this.barrier = barrier;
        this.runningLatch = runningLatch;
    }

    @Override
    public void run() {
        threadName = Thread.currentThread().getName();
        try {
            barrier.await();
            System.out.println(threadName + " is started");
            int found;
            found = process(arr, start, end);
            if (found > -1) {
                Thread.sleep((long) (Math.random() * 500L));
                MainTask.repeatedValue.set(found);
                System.out.println(threadName + " count Down!");
                runningLatch.countDown();
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " is interrupted");
        } catch (BrokenBarrierException e) {
            System.out.println(threadName + " Barrier is broke");
        }
    }

    public int process(int[] arr, int start, int end) {
        int[] subArr = Arrays.copyOfRange(arr, start, end); 

        for (int i : subArr) {
                if (!set.add(i)) {
                    System.out.printf("%s found! %d%n", threadName, i);
                    return i;
                }
        }
        return -1;
    }
}
