package com.lark.common.socket.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 服务端 socket
 */
public class ServeSocket {

    public static void main(String[] args) {
        try {
            new ServeSocket().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(9000);
        while (true) {
            Socket socket = server.accept();
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            ByteArrayOutputStream baos = readData(is);
            System.out.println("接收到客户端传来的数据：" + new String(baos.toByteArray()));

            writeData(os, "hello 你好".getBytes(StandardCharsets.UTF_8));

            is.close();
            os.close();
            socket.close();
        }
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

    /**
     * 向客户端写入数据
     *
     * @param os
     * @param result
     * @throws IOException
     */
    private void writeData(OutputStream os, byte[] result) throws IOException {
        os.write(result);
        os.close();
    }
}
