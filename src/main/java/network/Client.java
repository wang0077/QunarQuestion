package network;

/**
 * @Auther: wAnG
 * @Date: 2022/6/29 16:53
 * @Description:
 */

public class Client {
    public static void main(String[] args) {
        new Thread(new ClientHandler(),"Client").start();
    }
}
