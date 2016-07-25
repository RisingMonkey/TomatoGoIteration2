package monkey.rising.tomatogo.dataoperate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by lizhangfang on 2016/7/24.
 */
public class TypeControl {
    private SQLiteDatabase database;
    private BDH udh;
    private Context context;
    ArrayList<type> types=new ArrayList<>();

    public TypeControl(Context context){
        this.context=context;
    }

    public ArrayList<type> getTypes() {
        return types;
    }

    public void openDataBase(){
        udh=new BDH(context,"data2.db",null,1);
        try{
            database=udh.getWritableDatabase();
        }catch (SQLException e){
            database=udh.getReadableDatabase();
        }
    }
    public void closeDb(){
        if(database!=null){
            database.close();
        }
    }

    public void loadtype(){
        Cursor result=database.query("type",new String[]{"name","userid"},null,null,null,null,null);
        covertoType(result);
    }

    public ArrayList<String> gettypebyuser(String user){
        ArrayList<String> types=new ArrayList<>();
        types.add("其他");
        types.add("学习");
        types.add("运动");
        types.add("娱乐");
        types.add("工作");
        types.add("休息");

        for(type t:this.types){
            if (t.getUserid().equals(user)){
                if(Istype(types,t.getName())){
                    types.add(t.getName());
                }
            }
        }
        return types;
    }

    public boolean Istype(ArrayList<String> list,String s){
        for(String string:list){
            if(string.equals(s)){
                return false;
            }
        }
        return true;
    }

    public void covertoType(Cursor cursor){
        int resultCounts=cursor.getCount();
        if(resultCounts==0||!cursor.moveToFirst()){
            return ;
        }
        for(int i=0;i<resultCounts;i++){
            types.add(new type(cursor.getString(0),cursor.getString(cursor.getColumnIndex("userid"))));
            cursor.moveToNext();
        }
    }

    public long insertDate(String name,String userid){
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("userid",userid);
        return database.insert("type",null,values);
    }

    public long deletedata(String name){
        return database.delete("type","name='"+name+"'",null);
    }

}
