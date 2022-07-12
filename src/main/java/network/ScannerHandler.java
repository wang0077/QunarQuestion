package network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * @Auther: wAnG
 * @Date: 2022/6/29 17:11
 * @Description:
 */

public class ScannerHandler implements Runnable {

    private final SocketChannel channel;

    public ScannerHandler(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true){
            System.out.print("请输入网址:");
            Scanner scanner = new Scanner(System.in);
            String data = scanner.nextLine();
            byte[] req = data.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
            byteBuffer.put(req);
            byteBuffer.flip();
            try {
                channel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
