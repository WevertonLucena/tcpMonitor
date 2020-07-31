import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        TcpMonitor monitor = TcpMonitor.getInstance();

        ExecutorService e = Executors.newFixedThreadPool(4);

        e.execute(monitor);
        Thread.sleep(2000);



        for (int i = 0; i < 20 ; i++) {
            //monitor.sendTcpMessage("1");
            e.execute(() -> monitor.sendTcpMessage("1"));
        }
        e.shutdown();
        System.out.println("final da execução");
    }
}
