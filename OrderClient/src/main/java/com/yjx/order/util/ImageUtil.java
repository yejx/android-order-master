package com.yjx.order.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StatFs;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Vinchaos api on 13-12-12.
 * 图片处理工具类
 */
public class ImageUtil {

    /**
     * 图片缩放
     * resizeImage
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Drawable resizeImage(Context context, Bitmap bitmap, int w, int h) {

        if (context == null){
            return null;
        }

        if(bitmap == null){
            return null;
        }

        Bitmap bitmapOrg = bitmap;

        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap reSizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
                height, matrix, true);
        return new BitmapDrawable(context.getResources(), reSizedBitmap);

    }

    /**
     * 图片格式转换
     * drawable2Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if(drawable == null){
            return null;
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap2Drawable
     */
    public static Drawable bitmap2Drawable(Context context, Bitmap bitmap) {
        if (context == null){
            return null;
        }
        if(bitmap == null)return null;
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * Bitmap2Bytes
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        if(bitmap == null)return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * Bytes2Bimap
     *
     * @param b
     * @return
     */
    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * View2Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap view2Bitmap(View view) {
        if(view == null)return null;
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    /**
     * Bitmap2file
     *
     * @param bmp
     * @param fileName
     * @return
     */
    public static boolean saveBitmap2file(Bitmap bmp, String fileName) throws Exception {
        if(bmp == null || StringUtil.isEmpty(fileName))return false;
        Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
        int quality = 100;
        // 判断SDCard状态
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // 错误提示
            Log.e("LKL", "SDCard不可用");
            return false;
        }
        // 检查SDCard空间
        File SDCardRoot = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(SDCardRoot.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = statFs.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = statFs.getAvailableBlocks();
        if (blockSize * freeBlocks < 10000) {
            // 弹出对话框提示用户空间不够
            Log.e("LKL", "存储空间不够");
            return false;
        }
        // 在SDCard创建文件夹及文件
        File bitmapFile = new File(SDCardRoot.getPath() + fileName);
        bitmapFile.getParentFile().mkdirs();
        OutputStream stream = new FileOutputStream(SDCardRoot.getPath() + fileName);
        return bmp.compress(format, quality, stream);
    }

    /**
     * printScreen
     *
     * @return
     */
    public static Bitmap printScreen(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        View view = activity.getWindow().getDecorView();
        view.layout(0, 0, dm.widthPixels, dm.heightPixels);
        view.setDrawingCacheEnabled(true);
        return Bitmap.createBitmap(view.getDrawingCache());
    }

    /**
     * bitmap2String
     *
     * @param bitmap
     * @return
     */
    public static String bitmap2String(Bitmap bitmap) {
        if(bitmap == null) return "";
        String string;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * string2Bitmap
     *
     * @param string
     * @return
     */
    public static Bitmap string2Bitmap(String string) throws Exception {
        if(StringUtil.isEmpty(string)) return null;
        byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }


    /**
     *。保存图片到私有文件夹
     * @param bitmap
     * @return
     */
    public static boolean saveLoacalPhotoImage(Context mContext,String loginName,Bitmap bitmap){
        if(mContext == null || StringUtil.isEmpty(loginName) || bitmap == null) return false;
        try{
            String fileName = loginName.hashCode()+".png";
            FileOutputStream localFileOutputStream1 = mContext.openFileOutput(fileName, 0);
            Bitmap.CompressFormat localCompressFormat = Bitmap.CompressFormat.PNG;
            bitmap.compress(localCompressFormat, 100, localFileOutputStream1);
            localFileOutputStream1.close();
        }catch (Exception e){
            return  false;
        }
        return  true;
    }

    /**
     * 获取私有文件图片文件
     *
     * @param context
     * @return
     */
    public static Bitmap getLoacalPhotoImage(Context context,String loginName) {
        if(context == null || StringUtil.isEmpty(loginName)) return null;
        FileInputStream fis = null;
        Bitmap bitmap = null;
        String fileName = loginName.hashCode()+".png";
        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取Assets目录下存放的图片
     * @param context   上下文
     * @param path      文件路径
     * @return          Drawable对象
     */
    public static Bitmap getBitmapInAssets(Context context, String path){
        if(context == null || StringUtil.isEmpty(path)) return null;
        AssetManager assetManager   = context.getAssets();
        Bitmap bitmap               = null;
        InputStream in              = null;
        try {
            in      = assetManager.open(path);
            bitmap  = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeIO(in);
        }
        return bitmap;
    }

    /**
     *  从文件中加载图片
     * @param path      文件路径
     * @return          Bitmap 对象
     */
    public static Bitmap getBitmapByFile(String path){
        if(StringUtil.isEmpty(path)) return null;
        Bitmap bitmap = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(path);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeIO(in);
        }
        return bitmap;
    }


    /**
     * 根据放在Drawable目录下的图片名字获取图片
     * @param context     上下文
     * @param identifier  文件名字
     * @return   Drawable对象
     */
    public static Bitmap getBitmapByIdentifier(Context context, String identifier){
        if(context == null || StringUtil.isEmpty(identifier)) return null;
        return BitmapFactory.decodeResource(context.getResources(), getDrawableIdentifier(context, identifier));
    }

    /**
     * 根据文件名字获取 R.drawable.identfier 的int值
     * @param context    上下文
     * @param identifier  文件名字
     * @return     Res   int
     */
    public static int getDrawableIdentifier(Context context,String identifier){
        if(context == null || StringUtil.isEmpty(identifier)) return 0;
        Resources resources    = context.getResources();
        String packageName     = context.getPackageName();
        return resources.getIdentifier(identifier, "drawable", packageName);
    }

    /**
     * 转换图片成圆形
     * @param source 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap source) {
        if (source == null)
            return null;

        int width   = source.getWidth();
        int height  = source.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, src, dst, paint);
        return output;
    }

}
