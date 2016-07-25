package monkey.rising.tomatogo.dataoperate;

/**
 * Created by lizhangfang on 2016/7/19.
 */
public class Clock {
    String id;
    String userid;
    String taskid;
    String taskContent;
    String type;
    int lasttime;
    int timeexp;
    boolean isdone;


    public Clock(String id, String userid, String taskid, int lasttime, int timeexp, String taskContent, String type) {
        this.id = id;
        this.userid = userid;
        this.taskid = taskid;
        this.lasttime = lasttime;
        this.timeexp = timeexp;
        this.taskContent = taskContent;
        this.type = type;
        this.isdone = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLasttime() {
        return lasttime;
    }

    public void setLasttime(int lasttime) {
        this.lasttime = lasttime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }


    public boolean isdone() {
        return isdone;
    }

    public void setIsdone() {
        this.isdone = true;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTimeexp(int timeexp) {
        this.timeexp = timeexp;
    }

    public int getTimeexp() {
        return timeexp;
    }


}