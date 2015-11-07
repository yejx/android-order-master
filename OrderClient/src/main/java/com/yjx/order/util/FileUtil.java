package com.yjx.order.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.yjx.order.encryption.Digest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by jerry on 13-12-12.
 * <p/>
 * 提供文件处理工具类
 * 提供静态方法处理文件如（创建文件，删除文件，删除文件夹等操作）
 */
public class FileUtil {

    /**
     * 文件是否存在
     *
     * @param file 文件
     *             return true:存在 false:不存在
     */
    public static boolean isExist(File file) {
        return file != null && file.exists();
    }

    /**
     * 文件是否存在
     *
     * @param filePath 文件路径
     *                 return true:存在 false:不存在
     */
    public static boolean isExist(String filePath) {
        return !StringUtil.isEmpty(filePath) && new File(filePath).exists();
    }

    /**
     * 创建文件夹
     *
     * @param file 文件夹
     */
    public static boolean createDirectory(File file) {
        return file != null && (file.exists() || file.mkdirs());
    }

    /**
     * 创建文件夹(重载方法)
     *
     * @param path 文件夹路径
     */
    public static boolean createDirectory(String path) {
        return !StringUtil.isEmpty(path) && createDirectory(new File(path));
    }

    /**
     * 创建文件
     *
     * @param file 文件
     */
    public static boolean createFile(File file) throws IOException {
        if (file == null)
            return false;
        if (file.exists())
            return true;

        //文件夹不存在
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            return parentFile.mkdirs() && file.createNewFile();
        }

        return file.createNewFile();
    }

    /**
     * 创建文件（重载方法）
     *
     * @param filePath 文件路径
     */
    public static boolean createFile(String filePath) throws IOException {
        if (filePath == null || filePath.equals(""))
            return false;
        return createFile(new File(filePath));
    }

    /**
     * 删除文件（1.单个文件直接删除2.文件夹递归删除）
     *
     * @param file 需要删除的文件
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file == null || !file.exists())
            return false;

        //文件夹递归删除
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (File childFile : childFiles) {
                deleteFile(childFile);
            }
        }

        return file.delete();
    }

    /**
     * 删除文件(重载方法)（1.单个文件直接删除2.文件夹递归删除）
     *
     * @param filePath 文件路径
     */
    public static boolean deleteFile(String filePath) {
        if (filePath == null || filePath.equals(""))
            return false;

        File file = new File(filePath);
        return deleteFile(file);
    }

    /**
     * 重命名文件
     *
     * @param file       需要修改的文件
     * @param targetFile 修改后的文件
     * @return
     * @throws java.io.IOException
     */
    public static boolean renameFile(File file, File targetFile) throws IOException {
        if (file == null || targetFile == null || !file.exists()) {
            return false;
        }

        if (createFile(targetFile)) {
            return file.renameTo(targetFile);
        }
        return false;
    }

    /**
     * 重命名文件
     *
     * @param file       需要修改的文件
     * @param targetPath 修改后的文件
     * @return
     * @throws java.io.IOException
     */
    public static boolean renameFile(File file, String targetPath) throws IOException {
        if (targetPath == null || targetPath.equals(""))
            return false;
        return renameFile(file, new File(targetPath));
    }

    /**
     * 复制单个文件(直接覆盖已经存在的文件)
     *
     * @param curFilePath 当前要复制的文件
     * @param targetPath  目标文件夹,""代表当前文件夹
     */
    private static boolean copySingleFile(String curFilePath, String targetPath) throws IOException {
        if (curFilePath == null || curFilePath.equals("") || targetPath == null)
            return false;

        File curFile = new File(curFilePath);
        if (!curFile.exists())
            throw new FileNotFoundException("需要复制的文件不存在");
        if (!curFile.isFile())
            throw new FileNotFoundException("需要复制的可能是文件夹,请使用copyFile()");

        //获取当前目录
        if (targetPath.equals("")) {
            String curParentFilePath = curFile.getParent();
            if (curParentFilePath != null && !curParentFilePath.equals("")) {
                targetPath = curParentFilePath + File.separator;
            }
        } else {
            targetPath = targetPath + File.separator;
        }

        String tempTarget = "";
        if (targetPath.endsWith("/")) {
            tempTarget = targetPath;
        } else {
            tempTarget = targetPath + File.separator;
        }
        File targetFile = new File(tempTarget + curFile.getName());

        createDirectory(targetPath);

        boolean isSuccess = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(curFile);
            fileOutputStream = new FileOutputStream(targetFile);
            //缓存数据
            byte[] bytes = new byte[1024 * 5];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            fileOutputStream.flush();
            isSuccess = true;
        } catch (Exception e) {
            throw new FileNotFoundException("复制文件异常");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return isSuccess;
    }

    /**
     * 复制文件（文件夹）
     *
     * @param curFilePath 当前要复制的文件（文件夹）
     * @param targetPath  目标文件夹,""代表当前文件夹
     */
    public static boolean copyFile(String curFilePath, String targetPath) throws IOException {
        if (curFilePath == null || curFilePath.equals("") || targetPath == null)
            return false;

        File curFile = new File(curFilePath);
        if (!curFile.exists()) return false;

        //获取当前目录
        if (targetPath.equals("")) {
            String curParentFilePath = curFile.getParent();
            if (curParentFilePath != null && !curParentFilePath.equals("")) {
                targetPath = curParentFilePath + File.separator;
            }
        } else {
            targetPath = targetPath + File.separator;
        }

        boolean isSuccess = false;
        if (curFile.isDirectory()) {
            String tempTarget = "";
            if (targetPath.endsWith("/")) {
                tempTarget = targetPath;
            } else {
                tempTarget = targetPath + File.separator;
            }
            File targetFile = new File(tempTarget + curFile.getName());
            createDirectory(targetFile);

            String[] fileChildNames = curFile.list();
            for (String childfileName : fileChildNames) {
                String oldChildPath = curFilePath + File.separator + childfileName;
                copyFile(oldChildPath, targetFile.getPath());
            }
            isSuccess = true;
        } else {
            isSuccess = copySingleFile(curFile.getPath(), targetPath);
        }
        return isSuccess;
    }

    /**
     * 复制文件（文件夹）
     *
     * @param curFilePath 当前要复制的文件（文件夹）
     * @param targetPath  目标文件夹,""代表当前文件夹
     */
    public static boolean copyFile(File curFilePath, String targetPath) throws IOException {
        if (curFilePath == null || targetPath == null)
            return false;
        return copyFile(curFilePath.getPath(), targetPath);
    }

    /**
     * 读取文件内容（File -> String）
     *
     * @param file 文件
     * @return 字符内容
     */
    public static String readFile(File file) throws IOException {
        if (file == null)
            return "";
        if (!file.exists())
            throw new FileNotFoundException("读取文件不存在");
        if (!file.isFile())
            throw new FileNotFoundException("读取文件非法");

        BufferedReader inputStream = null;
        StringBuffer sb = new StringBuffer();
        try {
            inputStream = new BufferedReader(new FileReader(file));
            char[] bytes = new char[1024 * 5];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                sb.append(bytes,0,len);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return sb.toString().trim();
    }

    /**
     * 读取文件为字节数组
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] readFile2Byte(File file) throws IOException {
        if (file == null)
            return new byte[]{};
        if (!file.exists())
            throw new FileNotFoundException("读取文件不存在");
        if (!file.isFile())
            throw new FileNotFoundException("读取文件非法");

        FileInputStream inputStream = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024 * 5];
            int len = 0;
            while ((len = (inputStream.read(bytes))) != -1) {
                out.write(bytes,0,len);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return out.toByteArray();
    }


    /**
     * 读取文件内容（File -> String）
     *
     * @param path 文件路径
     * @return 字符内容
     */
    public static String readFile(String path) throws IOException {
        if (path == null || path.equals(""))
            return "";
        return readFile(new File(path));
    }

    /**
     * 写文件（String -> File）
     *
     * @param content 需要写入文件的内容
     * @param file    写入文件文件
     * @return 字符内容
     */
    public static boolean writeFile(String content, File file) throws IOException {
        if (content == null || file == null)
            return false;

        createFile(file);

        boolean isSuccess = false;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            isSuccess = true;
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
        return isSuccess;
    }

    /**
     * 写文件（String -> File）
     *
     * @param content  需要写入文件的内容
     * @param filePath 写入文件文件位置
     * @return 是否写入成功
     */
    public static boolean writeFile(String content, String filePath) throws IOException {
        if (content == null || filePath == null || filePath.equals(""))
            return false;
        return writeFile(content, new File(filePath));
    }

    /**
     * 文件末尾追加内容（String -> File）
     *
     * @param content 需要写入文件的内容
     * @param file    写入文件文件
     * @return 是否追加成功
     */
    public static boolean appendConentFile(String content, File file) throws IOException {
        if (content == null || file == null)
            return false;

        createFile(file);

        boolean isSuccess = false;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(content);
            bufferedWriter.flush();
            isSuccess = true;
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
        return isSuccess;
    }

    /**
     * 文件末尾追加内容（String -> File）
     *
     * @param content  需要写入文件的内容
     * @param filePath 写入文件文件
     * @return 是否追加成功
     */
    public static boolean appendConentFile(String content, String filePath) throws IOException {
        if (content == null || filePath == null || filePath.equals(""))
            return false;
        return appendConentFile(content, new File(filePath));
    }

    /**
     * 获取不包含后缀的文件名
     *
     * @param file 文件
     * @return 包含后缀的文件名
     */
    public static String getRealName(File file) {
        if (file == null || file.equals(""))
            return "";

        String name = file.getName();
        if (file.isDirectory()) {
            return name;
        }

        int index = name.lastIndexOf(".");
        String realName = "";
        if (name.startsWith(".") || index == -1) {
            realName = name;
        } else {
            realName = name.substring(0, name.lastIndexOf("."));
        }
        return realName;
    }

    /**
     * 获取不包含后缀的文件名
     *
     * @param filePath 文件路径
     * @return 包含后缀的文件名
     */
    public static String getRealName(String filePath) {
        if (filePath == null || filePath.equals(""))
            return "";
        return getRealName(new File(filePath));
    }

    /**
     * 获取文件后缀名
     *
     * @param file 文件
     * @return 返回文件后缀名
     */
    public static String getSuffix(File file) {
        if (file == null || !file.exists() || file.isDirectory())
            return "";

        String name = file.getName();
        int lastIndex = name.lastIndexOf(".");
        if (name.startsWith(".") || lastIndex == -1)
            return "";

        return name.substring(lastIndex + 1, name.length());
    }

    /**
     * 获取文件后缀名
     *
     * @param path 文件路径
     * @return 返回文件后缀名
     */
    public static String getSuffix(String path) {
        if (path == null || path.equals(""))
            return "";
        return getSuffix(new File(path));
    }

    /**
     * 压缩文件
     *
     * @param file              需要压缩的文件
     * @param zipFileTargetPath 压缩到指定目标目录,""代表当前文件夹
     * @return
     */
    public static boolean compressFileToZip(File file, String zipFileTargetPath) throws IOException {
        //需要解压的文件不存在时
        if (file == null || zipFileTargetPath == null)
            return false;
        if (!file.exists())
            throw new FileNotFoundException("压缩文件不存在");

        //创建目标目录
        createDirectory(zipFileTargetPath);
        //需要压缩的文件
        String realName = getRealName(file);
        //获取当前目录
        if (zipFileTargetPath.equals("")) {
            String fileParentPath = file.getParent();
            if (fileParentPath != null && !fileParentPath.equals("")) {
                zipFileTargetPath = fileParentPath + File.separator;
            }
        } else {
            zipFileTargetPath = zipFileTargetPath + File.separator;
        }
        //压缩后的zip文件
        String zipFilePath = zipFileTargetPath + realName + ".zip";
        boolean isSuccess = false;
        //创建Zip包
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));
            //压缩文件
            zip(file, file.getName(), zipOutputStream);
            isSuccess = true;
        } catch (Exception e) {
            throw new FileNotFoundException("压缩文件异常");
        } finally {
            if (zipOutputStream != null) {
                zipOutputStream.closeEntry();
                zipOutputStream.close();
            }
        }
        return isSuccess;
    }

    /**
     * 压缩文件
     *
     * @param filePath          需要压缩的文件
     * @param zipFileTargetPath 压缩到指定目标目录,""代表当前文件夹
     * @return
     */
    public static boolean compressFileToZip(String filePath, String zipFileTargetPath) throws IOException {
        if (filePath == null || filePath.equals("") || zipFileTargetPath == null)
            return false;
        return compressFileToZip(new File(filePath), zipFileTargetPath);
    }

    /**
     * 压缩文件
     *
     * @param file            需要压缩的文件
     * @param zipOutputStream 压缩包输出流
     * @return
     */
    private static void zip(File file, String zipRootDir, ZipOutputStream zipOutputStream) throws IOException {
        if (file.isFile()) {
            FileInputStream in = null;
            try {
                in = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(zipRootDir);
                zipOutputStream.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024 * 5];
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    zipOutputStream.write(bytes, 0, len);
                }
                zipOutputStream.flush();
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("压缩文件异常");
            } finally {
                if (zipOutputStream != null) {
                    zipOutputStream.closeEntry();
                }
                if (in != null) {
                    in.close();
                }
            }
        } else {
            File[] files = file.listFiles();
            if (files.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(zipRootDir + "/");
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.closeEntry();
            } else {
                for (File childFile : files) {
                    String childZipEntryPath =
                            zipRootDir +
                                    File.separator +
                                    childFile.getName();
                    zip(childFile, childZipEntryPath, zipOutputStream);
                }
            }
        }
    }

    /**
     * 解压缩文件
     *
     * @param zipFile    压缩包
     * @param targetPath 解压缩文件目标目录,""代表当前文件夹
     */
    public static boolean decompressZip(File zipFile, String targetPath) throws IOException {
        //解压文件不存在
        if (zipFile == null || targetPath == null)
            return false;
        if (!zipFile.exists())
            throw new FileNotFoundException("解压zip文件不存在");
        if (!getSuffix(zipFile).equals("zip"))
            throw new FileNotFoundException("压缩文件非法");

        //创建目标目录
        createDirectory(targetPath);
        //获取当前目录
        if (targetPath.equals("")) {
            String zipParentPath = zipFile.getParent();
            if (zipParentPath != null && !zipParentPath.equals("")) {
                targetPath = zipParentPath + File.separator;
            }
        } else {
            targetPath = targetPath + File.separator;
        }
        //标志是否成功
        boolean isSuccess = false;
        //获取zip包输入流
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
            //解压缩
            unZip(targetPath, zipInputStream);
            isSuccess = true;
        } finally {
            if (zipInputStream != null) {
                zipInputStream.closeEntry();
                zipInputStream.close();
            }
        }
        return isSuccess;
    }

    /**
     * 解压缩文件
     *
     * @param zipFilePath 压缩包
     * @param targetPath  解压缩文件目标目录,""代表当前文件夹
     */
    public static boolean decompressZip(String zipFilePath, String targetPath) throws IOException {
        if (zipFilePath == null || targetPath == null)
            return false;
        return decompressZip(new File(zipFilePath), targetPath);
    }

    /**
     * 解压缩文件
     *
     * @param fileTargetPath 解压缩文件目标目录
     * @param zipInputStream 压缩包输入流
     */
    private static void unZip(String fileTargetPath, ZipInputStream zipInputStream) throws IOException {
        if (!fileTargetPath.equals("")) {
            fileTargetPath = fileTargetPath + File.separator;
        }

        ZipEntry entry      = null;
        String entryName    = "";
        while ((entry = zipInputStream.getNextEntry()) != null) {
            entryName = entry.getName();
            if (entry.isDirectory()) {
                entryName = entryName.substring(0, entryName.length() - 1);
                createDirectory(fileTargetPath + entryName);
            } else {
                File file = new File(fileTargetPath + entryName);
                createFile(file);
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                    byte[] bytes = new byte[1024 * 5];
                    int len = 0;
                    while ((len = zipInputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, len);
                    }
                    fileOutputStream.flush();
                } finally {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                }
            }
        }
    }

    /**
     * 获取程序路径
     *
     * @param context 应用程序上下文
     * @return 程序的存储路径
     */
    public static String getInnerPath(Context context) {
        if (context == null) return "";
        //获取程序路径
        String innerPath = context.getPackageResourcePath();
        return innerPath;
    }

    /**
     * 获取SD卡路径
     *
     * @return SD卡存储路径
     */
    public static String getSDCardPath() {
        if (sdCardAvailable())
            return Environment.getExternalStorageDirectory().getPath();
        else
            return "";
    }

    /**
     * 判断sd卡是否存在
     *
     * @return true 存在   </br>  flase  未安装SD卡
     */
    public static boolean sdCardAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算SD卡的剩余空间
     *
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getSDCardFreeDiskSpace() {
        boolean existCard = sdCardAvailable();
        long freeSpace = 0;
        if (existCard) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize / 1024;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return -1;
        }
        return (freeSpace);
    }

    /**
     * 向SD卡文件写入
     *
     * @param content  需要写入文件的内容
     * @param filePath 写入SD卡文件文件位置
     * @return 是否写入成功
     */
    public static boolean writeFileToSDCard(String content, String filePath) throws IOException {
        if (content == null || filePath == null || filePath.equals(""))
            return false;
        //SD卡成功挂载不成功
        if (!sdCardAvailable()) {
            throw new FileNotFoundException("SD卡不存在");
        }
        //SD卡空间不足
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir.getPath() + File.separator + filePath);
        if (file.length() > getSDCardFreeDiskSpace()) {
            throw new IOException("SD卡空间不足");
        }
        return writeFile(content, file);
    }

    /**
     * 读取SD卡文件
     *
     * @param content  需要写入文件的内容
     * @param filePath SD卡下文件文件位置
     * @return 是否写入成功
     */
    public static String readFileToSDCard(String content, String filePath) throws IOException {
        if (content == null || filePath == null || filePath.equals(""))
            return "";
        //SD卡成功挂载不成功
        if (!sdCardAvailable()) {
            return "";
        }
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir.getPath() + File.separator + filePath);
        return readFile(file);
    }

    /**
     * 向数据包下写入文件
     *
     * @param context  应用程序上下文
     * @param content  需要写入文件的内容
     * @param filePath 写入数据包files目录文件位置
     * @return 字符内容
     */
    public static boolean writeFileToApplicationDataPackage(Context context, String content, String filePath) throws IOException {
        if (context == null || content == null || filePath == null || filePath.equals(""))
            return false;

        File file = new File(context.getFilesDir() + File.separator + filePath);
        String parentPath = file.getParent();
        if (parentPath == null)
            return false;
        createDirectory(parentPath);
        boolean isSuccess = false;
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(filePath, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.flush();
            isSuccess = true;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return isSuccess;
    }

    /**
     * 写数据到缓存目录（临时目录），如果外部存储器存在则写到外部存储器，否则写到内部存储器中。
     * 如果写入成功则返回一个临时文件全路径，如果写失败则返回 null。
     * @param context      应用程序上下文
     * @param data         需要写入文件的内容
     * @param subdir       子目录（未尾不含分隔符）
     * @return 文件全路径
     */
    public static String writeDataToCacheDir(Context context,byte[] data,String subdir){
        if (context == null)
            return null;

        File cacheDir = context.getExternalCacheDir();

        if (cacheDir == null){
            //外部存储器无效，使用内部存储器
            cacheDir = context.getCacheDir();
        }

        //生成随机文件名
        Calendar calendar = Calendar.getInstance();
        String temp = String.format("cache%tY%tm%td%tH%tM%tS%s",
                calendar,
                calendar,
                calendar,
                calendar,
                calendar,
                calendar,
                StringUtil.getRandom(5)
        );

        String filename = Digest.md5(temp);
        String filepath;

        if (subdir == null || subdir.length() == 0){
            filepath = String.format("%s/%s", cacheDir.getAbsolutePath(), filename);
        }
        else{
            if (!subdir.endsWith("/")){
                subdir = subdir.concat("/");
            }

            createDirectory(String.format("%s/%s", cacheDir.getAbsolutePath(), subdir));

            filepath = String.format("%s/%s%s", cacheDir.getAbsolutePath(), subdir, filename);
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filepath,false);
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e){
            e.printStackTrace();
            filepath = null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
        }

        return filepath;
    }

    /**
     * 从assets目录下读取文本文件转换成string
     *
     * @param fileName 文件名
     * @return String
     */
    public static String readStringFromAssetsFile(Context context, String fileName) throws IOException {
        InputStream inputStream = null;
        String string = null;
        try {
            inputStream = context.getAssets().open(fileName);
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            string = new String(buffer);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return string;
    }

    /**
     * 关闭流
     *
     * @param ioStream 流
     */
    public static void closeIO(Closeable ioStream) {
        try {
            if (ioStream != null)
                ioStream.close();
        } catch (IOException e) {
            LogUtil.print(e.getClass().getSimpleName(), " Can't be closed !");
        }
    }

    /**
     * 复制assets中的文件到指定目录下
     *
     * @param context
     * @param assetsFileName  assets相对路径
     * @param targetFilePath  目标文件绝对路径
     * @return
     */
    public static boolean copyAssetFile(Context context, String assetsFileName, String targetFilePath) {
        try {
            InputStream inputStream = context.getAssets().open(assetsFileName);
            File file = new File(targetFilePath);

            if (!file.exists()){
                createFile(file);
            }

            FileOutputStream output = new FileOutputStream(file);
            byte[] buf              = new byte[10240];
            int count               = 0;
            while ((count = inputStream.read(buf)) > 0) {
                output.write(buf, 0, count);
            }
            output.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 复制assets中的文件到指定目录下
     *
     * @param context
     * @param assetsFileName
     * @param targetFolderPath
     * @return
     */
    private static boolean copyAssetFile1(Context context, String assetsFileName, String targetFolderPath) {
        try {
            InputStream inputStream = context.getAssets().open(assetsFileName);
            File file = new File(targetFolderPath + File.separator + assetsFileName);

            if (!file.exists()){
                createFile(file);
            }

            FileOutputStream output = new FileOutputStream(file);
            byte[] buf              = new byte[10240];
            int count               = 0;
            while ((count = inputStream.read(buf)) > 0) {
                output.write(buf, 0, count);
            }
            output.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 复制assets中的目录到指定目录
     *
     * @param context
     * @param dirName
     * @param targetFolder
     */
    public static void copyAssetsDir(Context context, String dirName, String targetFolder) {
        try {
            File folder = new File(targetFolder + File.separator + dirName);
            if (!folder.exists() && !folder.isDirectory())
                folder.mkdirs();

            String[] fileNames      = context.getAssets().list(dirName);
            InputStream inputStream = null;
            for (String fileName : fileNames) {
                String name = dirName + File.separator + fileName;
                //如果是文件，则直接拷贝，如果是文件夹，就会抛出异常，捕捉后递归拷贝
                try {
                    inputStream = context.getAssets().open(name);
                    inputStream.close();
                    copyAssetFile1(context, name, targetFolder);
                } catch (Exception e) {
                    //如果是文件夹会直接跳到异常处,然后开始进行递归
                    copyAssetsDir(context, name, targetFolder);
                } finally {
                    FileUtil.closeIO(inputStream);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
