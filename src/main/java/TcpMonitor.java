import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TcpMonitor  implements Runnable{

    private static TcpMonitor instance;
    private static  SocketChannel socketChannel;
    private volatile String response = "";

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private TcpMonitor(){}

    public static TcpMonitor getInstance(){
        if (instance == null)
            instance = new TcpMonitor();
        return instance;
    }

    public void openConnection(){
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(2323));
            System.out.println("openConnection");
        } catch (IOException e) {
            System.out.println("Erro ao abrir conexao");
        }
    }

    public synchronized void sendTcpMessage(String msg){
        lock.lock();
        try {
            System.out.println("enviando msg: " + msg);
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            condition.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run(){

        openConnection();
        while (true){
            int bytesRead = -1;
            do {
                ByteBuffer data = ByteBuffer.wrap(new byte[52]);

                try {

                    bytesRead = socketChannel.read(data);
                    lock.lock();
                    response = new String(data.array());
                    System.out.println(response + " <<<-----");
                    condition.signalAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            } while (bytesRead > 0);

        }

    }


}
