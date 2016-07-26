package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import monkey.rising.tomatogo.MainActivity.MainActivity;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.dataoperate.UserControl;

public class logactivity extends AppCompatActivity {
       public Button log;
    public Button reg;
    public EditText id;
    public EditText pw;
   public Switch aSwitch;
    public UserControl userControl;

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


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        log=(Button)findViewById(R.id.log) ;
        reg=(Button)findViewById(R.id.register);
        id=(EditText)findViewById(R.id.userid);
        pw=(EditText)findViewById(R.id.pw) ;
        aSwitch=(Switch) findViewById(R.id.switch1);
        userControl=new UserControl(this);
        userControl.openDataBase();
        userControl.loadUser();
        userControl.closeDb();

        id .setFilters(new InputFilter[]{new InputFilter() {
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
           Intent intent2=new Intent(logactivity.this,regisiter.class);

              startActivity(intent2);
          }
      });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userControl.IsUser(id.getText().toString(),pw.getText().toString())==1){
                    Toast.makeText(getApplicationContext(),"登录成功！！",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("share", AppCompatActivity.MODE_PRIVATE);
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putString("userid",id.getText().toString());
                    e.commit();
                    Intent intent=new Intent(logactivity.this,MainActivity.class);
                    startActivity(intent);

                }
                else { Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else {
                    pw.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
