package ca.com.idealimport.thread;

/**
 * 1. Understanding thread  done
 * What is thread ? done
 * Why use threads? done
 * how many way to create the thread done
 * Thread life cycle ?
 * thread methods ?
 * Executor framework ?
 * Concurrency Utilities
 * Best Practice & others.
 */
public class TestThread {

    static {
        System.out.println(Thread.currentThread().getName());
        System.out.println("Hello");
        // System.exit(0);

    }

    public static void main(String[] args) throws InterruptedException {
        //0
        System.out.println(Thread.currentThread().getPriority());
        //  ThreadByClass threadByClass = new ThreadByClass();
        // threadByClass.start();

        // Thread t2 = new Thread(new TableRunnable());
        // t2.start();
/*
        LocalDateTime localDateTime = LocalDateTime.now();
        printTable(5);
        printTable(10);
        printTable(15);
        long seconds = ChronoUnit.SECONDS.between(localDateTime, LocalDateTime.now());
        System.out.println("seconds :::" + seconds);
*/
        //start via thread.
        System.out.println("Starting via thread");
        Thread t1 = new Thread(new TableRunnable(5));
        t1.setName("Table-5");
        // t1.setPriority(10);
        t1.start();
        t1.join();

        Thread t2 = new Thread(new TableRunnable(10));
        t2.setName("Table-10");
        t2.setPriority(10);
        t2.start();
        t2.join();


        Thread t3 = new Thread(new TableRunnable(15));
        t3.setName("Table-15");
        t3.start();

    }

    public static void printTable(int tableNo) throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            System.out.println(tableNo * i);
            Thread.sleep(1000);
        }
    }
}
