package com.dao;

import java.io.*;

public class SPCommandDao {
    public static void startDoMessage(String m) throws IOException {
        //写入文件
        File directory = new File("");// 参数为空
        //String courseFile = directory.getCanonicalPath();
        FileWriter writer;
        try {
            writer = new FileWriter( "D:\\2.txt");
            //writer = new FileWriter(courseFile+"/token.txt");
            writer.write(m);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
