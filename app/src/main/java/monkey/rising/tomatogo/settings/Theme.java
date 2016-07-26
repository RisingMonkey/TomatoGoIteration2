package monkey.rising.tomatogo.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.Utils;

public class Theme extends AppCompatActivity {
    private RadioButton white;
    private RadioButton grey;

    private RadioButton yellow;
    private RadioButton lightblue;

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
        setContentView(R.layout.activity_theme);

        final SharedPreferences.Editor editor = getSharedPreferences("color1", MODE_PRIVATE).edit();
        final SharedPreferences pref = getSharedPreferences("color1", MODE_PRIVATE);
        int a = pref.getInt("background", 0);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setBackgroundColor(a);
        RadioGroup group = (RadioGroup) findViewById(R.id.radiogroup);
        white = (RadioButton) findViewById(R.id.white);
        yellow = (RadioButton) findViewById(R.id.yellow);
        grey = (RadioButton) findViewById(R.id.grey);
        lightblue = (RadioButton) findViewById(R.id.lightblue);


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (white.getId() == i) {
                    editor.remove("background");
                    editor.commit();
                    editor.putInt("background", 0xffffffff);
                    editor.commit();
                    int c = pref.getInt("background", 0);
                    layout.setBackgroundColor(c);
                } else if (grey.getId() == i) {
                    editor.remove("background");
                    editor.commit();
                    editor.putInt("background", 0xffdfdfdf);
                    editor.commit();
                    int c = pref.getInt("background", 0);
                    layout.setBackgroundColor(c);
                } else if (lightblue.getId() == i) {
                    editor.remove("background");
                    editor.commit();
                    editor.putInt("background", 0xff00ddff);
                    editor.commit();
                    int c = pref.getInt("background", 0);
                    layout.setBackgroundColor(c);
                }
                else if(yellow.getId()==i)
                {
                    editor.remove("background");
                    editor.commit();
                    editor.putInt("background",0xFFF6FD23);
                    editor.commit();
                    int c=pref.getInt("background",0);
                    layout.setBackgroundColor(c);
                }
            }
        });
    }
    @Override
    public void onBackPressed(){

Intent intent = new Intent();
        setResult(2,intent);
        finish();
    }
}

