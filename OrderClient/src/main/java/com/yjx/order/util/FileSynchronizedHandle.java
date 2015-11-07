package com.yjx.order.util;

import android.os.Build;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 共享文件操作处理，使用同一个锁
 *
 * Created by jerry on 13-12-20.
 */
public class FileSynchronizedHandle {

    /** 操作文件 */
    private File mFile;

    /** 同步锁 */
    private ReentrantLock mReentrantLock;

    public FileSynchronizedHandle(File file) {
        this.mFile = file;
        mReentrantLock = new ReentrantLock();
    }

    /**
     * 向共享文件写入内容
     *
     * @param content 需要记录的内容
     * @return
     */
    public boolean writeAppendContentSynchronized(String content){

        mReentrantLock.lock();

        boolean isSuccess = false;
        try {
            isSuccess = FileUtil.appendConentFile(content,mFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mReentrantLock.unlock();
        }
        return isSuccess;
    }

    /**
     * 读取共享文件内容
     */
    public String readFileSynchronized(){

        mReentrantLock.lock();

        String content = "";
        try {
            content = FileUtil.readFile(mFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mReentrantLock.unlock();
        }
        return content;
    }

    /**
     * 重命名共享文件
     *
     * @param targetFile 重命名后的文件
     */
    public boolean renameFile(File targetFile){

        mReentrantLock.lock();

        boolean isSuccess = false;
        try {
            isSuccess = FileUtil.renameFile(mFile,targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mReentrantLock.unlock();
        }
        return isSuccess;
    }

    /**
     * 向共享文件写入异常信息
     *
     * @param throwable 需要记录的内容
     * @return
     */
    public boolean writeAppendThrowbleInfoSynchronized(Throwable throwable){

        mReentrantLock.lock();

        boolean isSuccess = false;
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(mFile,true));
            throwable.printStackTrace(writer);
            Throwable cause = throwable.getCause();
            while (cause != null ) {
                cause.printStackTrace(writer);
                cause = cause.getCause();
            }
            isSuccess = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            mReentrantLock.unlock();
        }
        return isSuccess;
    }

    /**
     * 收集设备信息保存属性文件(追加致文件末尾)
     *
     * @param deviceCrashInfo   用户信息属性文件实例
     * @param content           保存的内容
     */
    public boolean collectCrashDeviceInfo(
            Properties deviceCrashInfo,
            String content) throws IllegalAccessException, IOException {

        mReentrantLock.lock();

        //使用反射来收集设备信息.在Build类中包含各种设备信息,
        //例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        java.lang.reflect.Field[] fields = Build.class.getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            field.setAccessible(true);
            deviceCrashInfo.put(field.getName(), field.get(null).toString());
        }

        boolean isSuccess = false;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(mFile, true);
            deviceCrashInfo.store(fileOutputStream, content);
            isSuccess = true;
        } finally {
            if (fileOutputStream != null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            mReentrantLock.unlock();
        }
        return isSuccess;
    }

}
