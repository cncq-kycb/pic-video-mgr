package cn.alberto.fileupload.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author AlbertoWang
 * @email AlbertoWang@FoxMail.com
 * @date 2021/5/31 00:13
 * @description 文件操作相关工具类
 **/

public class FileUtils {
    public static void uploadFile(byte[] fileContent, String storePath, String fileName) throws IOException {
        File targetFile = new File(storePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(storePath + fileName);
        out.write(fileContent);
        out.flush();
        out.close();
    }

    public static boolean inDir(String fileName, String dir) {
        File file = new File(dir);
        if (!file.isDirectory())
            return false;
        String[] fileNames = file.list();
        List<String> fileNamesList = Arrays.asList(fileNames);
        return fileNamesList.contains(fileName);
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory())
            return false;
        file.delete();
        return true;
    }
}
