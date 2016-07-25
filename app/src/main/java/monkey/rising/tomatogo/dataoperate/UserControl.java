package monkey.rising.tomatogo.dataoperate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by lizhangfang on 2016/7/13.
 */
public class UserControl {

    private SQLiteDatabase database;
   private BDH udh;
    private Context context;
    ArrayList<User> users=new ArrayList<>();

    public UserControl(Context context){
        this.context=context;
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


    public ArrayList<User> getUsers() {
        return users;
    }


    public int IsUser(String id, String pw){
        for (User user:users){
            if(id.equals(user.getId())){
                if (pw.equals(user.getPw())){
                    return 1;
                }
                return 3;
            }
        }
       return 2;
    }

   void AddUser(String id,String pw){

        users.add(new User(id,pw));


    }

    public long insertDate(String id,String pw){
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("pw",pw);
        return database.insert("user",null,values);
    }

    public void loadUser(){
        Cursor result=database.query("user",new String[]{"id","pw"},null,null,null,null,null);
        covertoTree(result);
    }

    public void covertoTree(Cursor cursor){
        int resultCounts=cursor.getCount();
        if(resultCounts==0||!cursor.moveToFirst()){
            return ;
        }
        for(int i=0;i<resultCounts;i++){
            users.add(new User(cursor.getString(0),cursor.getString(cursor.getColumnIndex("pw"))));
            cursor.moveToNext();
        }
    }


}
