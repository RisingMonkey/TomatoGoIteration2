package monkey.rising.tomatogo.dataoperate;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lizhangfang on 2016/7/19.
 */
public class ClockControl {
    ArrayList<Clock> clocks=new ArrayList<>();
    private SQLiteDatabase db;
    private BDH BDH;
    private Context context;
    public ClockControl(Context context){
        this.context=context;
    }
    public void openDataBase(){
        BDH =new BDH(context,"data2.db",null,1);
        try{
            db= BDH.getWritableDatabase();
        }catch (SQLException e){
            db= BDH.getReadableDatabase();
        }
    }
    public void closeDb(){
        if(db!=null){
            db.close();
        }
    }


    public long deletebyuser(String user){
        return db.delete("clock","username='"+user+"'",null);
    }
    public ArrayList<Clock> getbyType(String type,String user){
        ArrayList<Clock> clocks=new ArrayList<Clock>();
        for (Clock clock:findbyuser(user)) {
            if(clock.getType().equals(type)){
                clocks.add(clock);
            }
        }
        return clocks;
    }

    public  void loadclock(){
        Cursor cursor=db.query("clock",new String[]{"id","username","taskid","lasttime","timeexp","isdone","content","type"},null,null,null,null,null);
        covertoClock(cursor);
    }

    public  int getDayByMonth(int year,int month){
        int day = 0;
        month--;
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,1);

        while(c.get(Calendar.MONTH) == month){
            c.add(c.DATE,1);
            day++;
        }

        return day;

    }


    public ArrayList<Integer> countbymonth(int m,String user) throws ParseException {

        ArrayList<Integer> result=new ArrayList<>();
        Calendar c=Calendar.getInstance();
        String day = "2016-" + m;
        if(m<10)
            day = "2016-0" + m;
        Date d=new SimpleDateFormat("yyyy-MM").parse(day);
        c.setTime(d);
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH) + 1;
        Log.e("coountbymonth:","month:" + month);
        c.set(Calendar.DAY_OF_MONTH,1);
        for (int i=0;i< getDayByMonth(year,month);i++){
            Date date=c.getTime();
            c.add(c.DATE,1);
            String s=new SimpleDateFormat("yyyy-MM-dd").format(date);
            int j=findbyday(s,user).size();
            result.add(j);
        }
        Log.e("count:",m + " day:" + result.size());
        return result;
    }

    public void covertoClock(Cursor cursor){
        int resultCounts=cursor.getCount();
        if(resultCounts==0||!cursor.moveToFirst()) {
            return;
        }
        for (int i=0;i<resultCounts;i++){
            Clock clock= new Clock(cursor.getString(0),cursor.getString(cursor.getColumnIndex("username")),cursor.getString(cursor.getColumnIndex("taskid")),cursor.getInt(cursor.getColumnIndex("lasttime"))
            ,cursor.getInt(cursor.getColumnIndex("timeexp")),cursor.getString(cursor.getColumnIndex("content")),cursor.getString(cursor.getColumnIndex("type")));
            if(cursor.getString(cursor.getColumnIndex("isdone")).equals("true")){
                clock.setIsdone();
            }
            clocks.add(clock);
            cursor.moveToNext();
        }
    }

    public ArrayList<Clock> findbymonth(String day,String user){
        ArrayList<Clock> cLockbymonth=new ArrayList<>();
        Date d=null;
        try {
            for (Clock clock:findbyuser(user)) {
                d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(clock.getId());
                String s=new SimpleDateFormat("yyyy-MM").format(d);
                if (s.equals(day)){
                    cLockbymonth.add(clock);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cLockbymonth;
    }


    public ArrayList<Clock> findbyday(String day,String user){
        ArrayList<Clock> clocksbyday=new ArrayList<>();
        Date d=null;
        int[] res = {0,0,0,0,0,0};
        try {
            for (Clock clock:findbyuser(user)) {
                d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(clock.getId());
                String s=new SimpleDateFormat("yyyy-MM-dd").format(d);
                if (s.equals(day)){
                    clocksbyday.add(clock);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return clocksbyday;
    }

    public ArrayList<Clock> findbyuser(String user){
        ArrayList<Clock> clocksbyuser=new ArrayList<>();
        for (Clock clock:clocks) {
            if(clock.getUserid().equals(user)){
                clocksbyuser.add(clock);
            }
        }
        return clocksbyuser;
    }

    public int getDoneNum(String user){
        int res = 0;
        for(Clock clock:findbyuser(user)){
            if(clock.isdone())
                res++;
        }
        return res;
    }
    public int getUndoneNum(String user){
        int res = 0;
        for(Clock clock:findbyuser(user)){
            if(!clock.isdone())
                res++;
        }
        return res;
    }

    public long insertOneClock(String id,String userid,String taskid,int lasttime,int timeexp,Boolean isDone){
        ContentValues values=new ContentValues();
        values.put("id",id);
        values.put("username",userid);
        values.put("taskid",taskid);
        values.put("lasttime",lasttime);
        values.put("timeexp",timeexp);
        values.put("isdone",isDone.toString());

        TaskControl tc = new TaskControl(context);
        tc.openDataBase();
        tc.loadTask();
        tc.closeDb();
        Task currentTask = tc.findByTaskId(taskid);
        String type;
        String content;
        if(currentTask!=null){
            content = currentTask.getContent();
            type = currentTask.getType();
        }else{
            content = "其他";
            type = "其他";
        }
        values.put("content",content);
        values.put("type",type);
        Log.e("insert:",values.toString());
        return db.insert("clock",null,values);
    }
}
