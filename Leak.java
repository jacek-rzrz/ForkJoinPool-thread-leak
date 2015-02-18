import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Leak {

  public static void main(String[] args) throws Exception {

    Leak leak = new Leak();

    for(int i=0; i<1000; i++) {
      leak.compute();
      if(i % 111 == 0) {
        System.out.println(String.format("Iteration %d: %d threads", i, Thread.activeCount()));
      }
    }

  }

  void compute() throws Exception {

    ForkJoinPool forkJoinPool = new ForkJoinPool(2);
    forkJoinPool.submit(() ->
      IntStream.range(1, 1_000_000).parallel().filter(Leak::isPrime).boxed().collect(Collectors.toList())
    ).get();

  }


  public static boolean isPrime(long n) {
        return n > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }


}
