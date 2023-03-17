package com.xuecheng.media;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: BigFileTest
 * @Package: com.xuecheng.media
 * @Description:
 * @Datetime: 2023/3/13   18:31
 * @Author: YuHan.Kang@outlook.com
 */
public class BigFileTest {

    @Test
    public void testChunk() {
        File sourceFile = new File("/Users/kangyuhan/Downloads/TIM-23-01304.pdf");
        String chunkFilePath = "/Users/kangyuhan/Downloads/chunk/";
        int chunkSize = 1024 * 1024;
        int chunkNum = (int) Math.ceil((sourceFile.length() * 1.0 / chunkSize));
        byte[] bytes = new byte[1024];
        try (RandomAccessFile r = new RandomAccessFile(sourceFile, "r")) {
            for (int i = 0; i < chunkNum; i++) {
                File file = new File(chunkFilePath + i);
                try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw")) {
                    int len = -1;
                    while ((len = r.read(bytes)) != -1) {
                        accessFile.write(bytes, 0, len);
                        if (file.length() >= chunkSize) {
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testMerge() {
        File sourceFile = new File("/Users/kangyuhan/Downloads/TIM-23-01304.pdf");
        File mergeFile = new File("/Users/kangyuhan/Downloads/merged.pdf");
        File chunkDir = new File("/Users/kangyuhan/Downloads/chunk");
        File[] files = chunkDir.listFiles();
        List<File> files1 = Arrays.asList(files);
        files1.sort((Comparator.comparingInt(o -> Integer.parseInt(o.getName()))));
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(mergeFile, "rw");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = new byte[1024];
        for (File file : files1) {
            try (RandomAccessFile accessFile = new RandomAccessFile(file, "r")) {
                int len;
                while ((len = accessFile.read(bytes)) != -1) {
                    randomAccessFile.write(bytes, 0, len);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            randomAccessFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileInputStream inputStream = new FileInputStream(mergeFile);
            FileInputStream inputStream1 = new FileInputStream(sourceFile);
            System.out.println(DigestUtils.md5Hex(inputStream1));
            System.out.println(DigestUtils.md5Hex(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
