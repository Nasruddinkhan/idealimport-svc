package ca.com.idealimport.thread;

public class TableRunnable implements Runnable {

    private int tableNo;

    TableRunnable(int tableNo) {
        this.tableNo = tableNo;
    }

    @Override
    public void run() {
        for (int i = 1; i < 10; i++) {
            System.out.println(String.format("%s %s * %s = %s", Thread.currentThread().getName(), tableNo, i, tableNo * i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
