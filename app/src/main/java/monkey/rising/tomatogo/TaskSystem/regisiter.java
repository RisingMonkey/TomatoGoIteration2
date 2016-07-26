package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.dataoperate.UserControl;

public class regisiter extends AppCompatActivity {
    Button reg;
    EditText name;
    EditText pw1;
    EditText pw2;
    UserControl userControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.configSP = getSharedPreferences("SettingsFragment",MODE_PRIVATE);
        boolean screenOn = Utils.configSP.getBoolean("lightOn",false);
        boolean fullScreen = Utils.configSP.getBoolean("fullScreen",true);
        if (screenOn){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        if(fullScreen){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Utils.configSP = getSharedPreferences("textSize",MODE_PRIVATE);
        int textSizeLevel = Utils.configSP.getInt("textSizeStatus",3);
        Utils.onActivityCreateSetTheme(this,textSizeLevel);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisiter);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
         reg=(Button)findViewById(R.id.reg) ;
        name=(EditText)findViewById(R.id.name);
        pw1=(EditText)findViewById(R.id.pw1);
        pw2=(EditText)findViewById(R.id.pw2);
        userControl=new UserControl(this);



        name.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if(charSequence.equals(" ")||charSequence.equals("\n"))
                    return "";
                else
                    return null;
            }
        }});
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pw1.getText().toString().equals(pw2.getText().toString())){

                    userControl.openDataBase();
                    userControl.loadUser();
                    if(name.getText().length()==0||pw1.getText().length()==0){
                        Toast.makeText(getApplicationContext(),"请输入完整信息！！",Toast.LENGTH_SHORT).show();
                    }else if(userControl.IsUser(name.getText().toString(),pw1.getText().toString())==2){
                        userControl.insertDate(name.getText().toString(),pw1.getText().toString());
                        userControl.closeDb();
                    Toast.makeText(getApplicationContext(),"注册成功！！",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(regisiter.this,logactivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(),"用户已存在！！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                Toast.makeText(getApplicationContext(),"两次密码不一致！！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
