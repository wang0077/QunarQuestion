package network;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: wAnG
 * @Date: 2022/6/29 18:25
 * @Description:
 */

public class HttpHandler implements Runnable{

    private final String URL;

    private final SocketChannel channel;

    private final static String CH = "[\u4e00-\u9fa5]";

    private final static String EN= "[a-zA-Z]";

    private static final String PREFIX = "https://";

    public HttpHandler(String URL,SocketChannel channel) {
        this.URL = URL;
        this.channel = channel;
    }

    @Override
    public void run() {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(1000);
        try {
            GetMethod getMethod = new GetMethod(PREFIX + URL);
            httpClient.executeMethod(getMethod);
            String responseBody = new String(getMethod.getResponseBody());
            charStatistic(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void charStatistic(String body) throws IOException {
        body = body.trim();
        System.out.println(body);
        int CHCount = 0;
        int ENCount = 0;
        for(int  i = 0; i < body.length();i++){
            if(body.substring(i,i + 1).matches(CH)){
                CHCount++;
            }else if(body.substring(i,i + 1).matches(EN)){
                ENCount++;
            }
        }
        String str = "\n总字符数：" + body.length() + "\n" +
                "中文字符数：" + CHCount + "\n" +
                "英文字符数：" + ENCount + "\n" +
                "标点符号数：" + (body.length() - CHCount - ENCount) + "\n";
        byte[] req = str.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        channel.write(byteBuffer);
    }
}
