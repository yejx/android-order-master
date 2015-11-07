package com.yjx.order.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.yjx.order.bean.User;

/**
 * 用户信息表
 *
 * Created by jerry on 14-2-28.
 */
public class UserDao extends BaseDao {

    private static final String TABLE_NAME = "t_user";
    private static final String KEY_LOGIN_NAME = "mobile";
    private static final String KEY_IS_LOGIN = "isLogin";

    private static UserDao mUserDao;

    //创建用户表sql语句
    private final String sql_t_user = "create table if not exists t_user(" +
            "id integer autoincrement,"+      //id,默认自增
            "mobile text primary key," +    //手机号，登录用户名
            "pwd text not null," +
            "userName text," +
            "isLogin text,"+                //是否当前登录用户
            "role_id integer," +
            "sex text," +
            "telephone text," +
            "email text," +
            "province text," +
            "city text)" ;

    public static synchronized UserDao getInstance(){
        if (mUserDao == null){
            mUserDao = new UserDao();
        }
        return mUserDao;
    }

    private UserDao() {
        super();
        createTable();
    }

    private void createTable(){
        db.execSQL(sql_t_user);
    }

    /**
     * 是否存在用户信息
     * @param loginName 登录名
     * @return true 存在 false 不存在
     */
    public synchronized boolean isExistUser(String loginName){
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{"mobile"},
                "mobile = ?",
                new String[]{loginName}, null, null, null);
        boolean flag = false;
        if (cursor.getCount() > 0){
            flag = true;
        }
        cursor.close();
        return flag;
    }

    /**
     * 保存用户信息
     * @param user 用户对象
     */
    public synchronized void saveUser(User user){

//        ApplicationEx.getInstance().getSession().setUser(user);

        String mobile = user.getMobile();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mobile",mobile);
        contentValues.put("pwd",user.getPwd());
        contentValues.put("userName",user.getUserName());
        contentValues.put("isLogin","1");

        setAllUserLoginFalse();
        if(isExistUser(mobile)){
            db.update(TABLE_NAME, contentValues, "mobile = ?", new String[]{mobile});
        }else {
            db.insert(TABLE_NAME,null,contentValues);
        }
    }

    /**
     * 获得登录用户对象
     * @return
     */
    public synchronized User getLoginUser(){
        User user = new User();

        Cursor cursor = db.query(TABLE_NAME,null,"isLogin = ?",new String[]{"1"},null,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
                String userName = cursor.getString(cursor.getColumnIndex("userName"));

                user.setMobile(mobile);
                user.setPwd(pwd);
                user.setUserName(userName);
            }
        }
        if (cursor!=null){
            cursor.close();
            cursor = null;
        }
        return  user;
    }

    /**
     * 是否有用户登录信息记录
     * @return
     */
    public boolean isHasLoginUser(){
        boolean result = false;

        Cursor cursor = db.query(TABLE_NAME,new String[]{"isLogin"},"isLogin = ?",new String[]{"1"},null,null,null);
        if(cursor.getCount() > 0){
            result = true;
        }
        cursor.close();

        return result;
    }

    /**
     * 设置所有用户最后一次登录标志为false
     */
    public synchronized void setAllUserLoginFalse(){
        String sql = "UPDATE "+TABLE_NAME+" SET "+KEY_IS_LOGIN
                +" = REPLACE("+KEY_IS_LOGIN+",'1','0')";
        db.execSQL(sql);
    }

}
