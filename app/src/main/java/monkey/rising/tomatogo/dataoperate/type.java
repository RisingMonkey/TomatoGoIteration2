package monkey.rising.tomatogo.dataoperate;

/**
 * Created by lizhangfang on 2016/7/24.
 */
public class type {
    private String name;
    private String userid;
    public type(String name,String userid){
        this.name=name;
        this.userid=userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
