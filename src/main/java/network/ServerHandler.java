package network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wAnG
 * @Date: 2022/6/26 17:45
 * @Description:
 */

public class ServerHandler implements Runnable{

    private Selector selector;

    private ServerSocketChannel channel;

    private volatile boolean stop;

    private ExecutorService threadPool = new ThreadPoolExecutor(10,16,60L,TimeUnit.SECONDS
            ,new ArrayBlockingQueue<Runnable>(100)
            ,new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build()
            ,new ThreadPoolExecutor.AbortPolicy());


    public ServerHandler(int port) {
        try {
            selector = Selector.open();
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress("127.0.0.1",8080),1024);
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey selectionKey = null;
                while (iterator.hasNext()){
                    selectionKey = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(selectionKey);
                    }catch (Exception e){
                        if(selectionKey != null){
                            selectionKey.cancel();
                            if(selectionKey.channel() != null){
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey selectionKey) throws IOException{
        if(selectionKey.isValid()){
            if(selectionKey.isAcceptable()){
                ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel accept = channel.accept();
                System.out.println("连接成功： " + channel.getLocalAddress());
                accept.configureBlocking(false);
                accept.register(selector,SelectionKey.OP_READ);
            }

            if(selectionKey.isReadable()){
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int read = channel.read(buffer);
                if(read > 0){
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println(body);
                    threadPool.execute(new HttpHandler(body,channel));
                }else if(read < 0){
                    selectionKey.cancel();
                    channel.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String currentTime) throws IOException {
        if (currentTime != null && currentTime.trim().length() > 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer write = ByteBuffer.allocate(bytes.length);
            write.put(bytes);
            write.flip();
            channel.write(write);
        }
    }
}
