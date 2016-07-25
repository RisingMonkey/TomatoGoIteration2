package monkey.rising.tomatogo.dataoperate;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lizhangfang on 2016/7/19.
 */
public class BDH extends SQLiteOpenHelper {
    public static final String sql="create table clock("+"id text primary key,"+"username text,"+"taskid text,"+"lasttime integer,"+"timeexp integer,"+"isdone text,"
            +"content text,"+"type text)";
    public static final String sql1="Create table task("+"id text primary key ,"+"type text,"+"user text,"+"content text,"+"time text,"+"timeexpected text,"
            +"starttime text,"+"priority integer,"+"isdone text)";
    public static final String Create_user="create table user("+"id text primary key,"+"pw text)";
    public static final String sql2="create table type("+"name text primary key,"+"userid text)";
    public BDH(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(Create_user);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}