package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Auther: wAnG
 * @Date: 2022/6/26 18:01
 * @Description:
 */

public class ClientHandler implements Runnable{

    private Selector selector = null;

    static Charset charset = StandardCharsets.UTF_8;

    private volatile boolean stop = false;

    private FutureTask<String> futureTask;

    public void run() {
        try {
            selector = Selector.open();
            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);
            if (channel.connect(new InetSocketAddress("127.0.0.1", 8080))) {
                channel.register(selector, SelectionKey.OP_READ);
                doWrite(channel, "123123");
            } else {
                channel.register(selector, SelectionKey.OP_CONNECT);
            }

            while (!stop) {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey selectionKey = null;
                while (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    iterator.remove();
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    if (selectionKey.isConnectable()) {
                        if (socketChannel.finishConnect()) {
                            selectionKey.interestOps(SelectionKey.OP_READ);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            new Thread(new ScannerHandler(channel)).start();
                        } else {
                            System.exit(1);
                        }
                    } else if (selectionKey.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int readBytes = socketChannel.read(buffer);
                        if(readBytes == 0){
                            continue;
                        }
                        String content = "";
                        if (readBytes > 0) {
                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            content += new String(bytes);
//                        stop = true;
                        } else if (readBytes < 0) {
                            //对端链路关闭
                            selectionKey.channel();
                            socketChannel.close();
                        }
                        System.out.println(content);
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    } else if(selectionKey.isWritable()){
                        doWrite(channel,"+++++++++");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(SocketChannel sc, String data) throws IOException {
        byte[] req = data.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        sc.write(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            System.out.println("Send 2 client successed");
        }
    }
}
