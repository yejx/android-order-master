package com.yjx.order.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yjx.order.common.ApplicationEx;
import com.yjx.order.util.DBHelper;

/**
 * 数据操作对象基础类
 * 对数据库的操作，需要继承此类，使用db对象执行sql语句
 * 如果需要更换默认数据库，请重写getDataBaseFile方法
 * Created by xyz on 13-12-20.
 */
public abstract class BaseDao {

    protected SQLiteDatabase db;

    public Context context;

    public BaseDao(){
        this.context = ApplicationEx.getInstance();
        db = DBHelper.getInstance(context).getWritableDatabase();
    }

    public void closeDB() {
        if(db != null){
            db.close();
            db = null;
        }
    }

    /**
     * 判断表是否存在
     * @param tableName 表名
     * @return true: 存在 false: 不存在
     */
    public boolean isExist(String tableName){
        boolean isExist = false ;
        if(tableName == null || "".equals(tableName)){
            return isExist;
        }
        String sql = "SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {"table", tableName});

        if (cursor.moveToFirst()){
            int count = cursor.getInt(0);
            isExist = count > 0;
        }
        cursor.close();
        return isExist;
    }

}
