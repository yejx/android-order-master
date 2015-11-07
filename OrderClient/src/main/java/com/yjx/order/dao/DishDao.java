package com.yjx.order.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;

import com.yjx.order.bean.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2015/05/29 - 12:04
 * Created by yejinxin.
 */
public class DishDao extends BaseDao {
    private static final String TABLE_NAME = "t_menu_message";
    //创建用户表sql语句
    private static final String SQL_CREATE_TABLE = "create table if not exists " + TABLE_NAME + "(" +
        "id integer primary key autoincrement,"+      //id,默认自增
        "userName text," +
        "tag text," +
        "type text," +
        "drawableId integer," +
        "title text," +
        "content text," +
        "date date,"+
        "isRead integer)";

    private static DishDao instance;

    public static DishDao getInstance() {
        if (instance == null) {
            instance = new DishDao();
        }
        return instance;
    }

    private DishDao() {
        super();
        db.execSQL(SQL_CREATE_TABLE);
    }

    public void add(Dish dish){
        ContentValues  cv = new ContentValues();
        cv.put("name", dish.getName());
        cv.put("DISH_type", dish.getType());
        cv.put("price", dish.getPrice());
        cv.put("currency_type", dish.getCurrency());
        cv.put("unit", dish.getUnit());
        cv.put("photo", dish.getPhoto());
        long pkID = db.insert("T_DISH_INFO", null, cv);
        if(pkID != -1 ){
            Toast.makeText(context, "id=" + String.valueOf(pkID), Toast.LENGTH_LONG).show();
        }
    }

    public void  update(Dish dish){
        ContentValues  cv = new ContentValues();
        cv.put("DISH_type", dish.getType());
        cv.put("price", dish.getPrice());
        cv.put("currency_type", dish.getCurrency());
        cv.put("unit", dish.getUnit());
        cv.put("photo", dish.getPhoto());
        int iRows = db.update("T_DISH_INFO", cv, "name='"+dish.getName()+"'", null);
        if(iRows>0){
            Toast.makeText(context, "iRows=" + String.valueOf(iRows), Toast.LENGTH_LONG).show();
        }
    }

    public void delete(Dish dish){
        int iRows = db.delete("T_Dish_INFO","name='"+dish.getName()+"'", null);
        if (iRows > 0) {
            Toast.makeText(context, "iRows=" + String.valueOf(iRows),Toast.LENGTH_SHORT).show();
        }
    }

    public Dish findDish(Dish dish){
        Dish dh = null;
        StringBuffer sb = new StringBuffer();
        sb
                .append("select  tdi.[id], tdi.[name],(select tdd.[name] from T_DATA_DICTIONARY tdd where tdi.[DISH_type]=tdd.[id]) type," +
                        "tdi.[price],(select tdd.[name] from T_DATA_DICTIONARY tdd where tdi.[currency_type]=tdd.[id]) currency," +
                        "(select tdd.[name] from T_DATA_DICTIONARY tdd where tdi.[unit]=tdd.[id]) unit,tdi.[photo] from T_DISH_INFO tdi where name='")
                .append(dish.getName())
                .append("'");
        Cursor c = db.rawQuery(sb.toString(), null);
        if(c != null && c.moveToFirst()){
            do {
//				Map<String,String> map = new HashMap<String, String>();
//				String id = String.valueOf(c.getInt(c.getColumnIndex("id")));
                String name = c.getString(c.getColumnIndex("name"));
                String type = c.getString(c.getColumnIndex("type"));
                String price = String.valueOf(c.getInt(c.getColumnIndex("price")));
                String currency = c.getString(c.getColumnIndex("currency"));
                String unit = c.getString(c.getColumnIndex("unit"));
                String photo = c.getString(c.getColumnIndex("photo"));
                String image = photo.substring(photo.lastIndexOf("/")+1);
                dh = new Dish(name, type, price, currency, unit, image);
            } while (c.moveToNext());
        }
        return dh;
    }

    public List<String> findMenus(){
        List<String> lstMenu = new ArrayList<String>();
        Cursor c = db.rawQuery("select * from T_DATA_DICTIONARY tdd where tdd.[type]= 2;", null);
        if(c != null && c.moveToFirst()){
            do {
                String id = String.valueOf(c.getInt(c.getColumnIndex("id")));
//				int code = c.getInt(c.getColumnIndex("CODE"));
                String name = c.getString(c.getColumnIndex("name"));
                lstMenu.add(name);
            } while (c.moveToNext());
        }
        return lstMenu;

    }

    public List<Map<String,String>> findDishs(){
        List<Map<String,String>> lstDish = new ArrayList<Map<String,String>>();
        Cursor c = db.rawQuery("select  tdi.[id], tdi.[name],(select tdd.[name] from T_DATA_DICTIONARY tdd where tdi.[DISH_type]=tdd.[id]) type," +
                "tdi.[price],(select tdd.[name] from T_DATA_DICTIONARY tdd where tdi.[currency_type]=tdd.[id]) currency," +
                "(select tdd.[name] from T_DATA_DICTIONARY tdd where tdi.[unit]=tdd.[id]) unit,tdi.[photo] from T_DISH_INFO tdi;", null);
        if(c != null && c.moveToFirst()){
            do {
                Map<String,String> map = new HashMap<String, String>();
                String id = String.valueOf(c.getInt(c.getColumnIndex("id")));
                String name = c.getString(c.getColumnIndex("name"));
                String type = c.getString(c.getColumnIndex("type"));
                String price = String.valueOf(c.getInt(c.getColumnIndex("price")));
                String currency = c.getString(c.getColumnIndex("currency"));
                String unit = c.getString(c.getColumnIndex("unit"));
                String photo = c.getString(c.getColumnIndex("photo"));
                String image = photo.substring(photo.lastIndexOf("/")+1);
                map.put("id",id);
                map.put("name", name);
                map.put("type", type);
                map.put("price", price);
                map.put("currency", currency);
                map.put("unit", unit);
                map.put("photo", photo);
                map.put("number", "0");
                map.put("order", "");
                map.put("image", image);

                lstDish.add(map);
            } while (c.moveToNext());
        }
        return lstDish;

    }

}
