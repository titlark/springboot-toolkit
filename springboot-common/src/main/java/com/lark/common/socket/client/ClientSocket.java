package com.lark.common.socket.client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 客户端 socket
 */
public class ClientSocket {

    public static void main(String[] args) {
        try {
            new ClientSocket().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
        /*BufferedReader bw = new BufferedReader(new InputStreamReader(is));
        String s = null;
        while ((s = bw.readLine()) != null) {
            System.out.println("接收的数据:" + s);
        }*/
            OutputStream os = socket.getOutputStream();
            os.write("哈哈哈".getBytes(StandardCharsets.UTF_8));
            InputStream is = socket.getInputStream();
            ByteArrayOutputStream baos = readData(is);
            System.out.println("从服务端接收到的数据：" + new String(baos.toByteArray()));
            is.close();
            os.close();
            socket.close();
    }

    /**
     * 从客户端读取数据
     *
     * @param is
     * @return
     * @throws IOException
     */
    private ByteArrayOutputStream readData(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] data = new byte[1024];
        while ((len = is.read(data)) != -1) {
            baos.write(data, 0, len);
        }
        return baos;
    }
}
