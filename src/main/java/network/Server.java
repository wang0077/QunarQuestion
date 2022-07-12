package network;

/**
 * @Auther: wAnG
 * @Date: 2022/6/26 17:44
 * @Description:
 */

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        ServerHandler serverHandler = new ServerHandler(port);
        new Thread(serverHandler,"001").start();
    }
}
