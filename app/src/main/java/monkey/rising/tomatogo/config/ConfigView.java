package monkey.rising.tomatogo.config;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import monkey.rising.tomatogo.R;

public class ConfigView extends AppCompatActivity {
    Switch shake;
    Switch bell;
    Switch light;
    Switch fullScreen;

    //ImageView bellImage;
    LinearLayout textSizeChange;
    LinearLayout aboutUs;
    LinearLayout bellChoice;
    TextView textSizeHint;
    TextView aboutHint;
    TextView bellText;
    int screenOnClickCount = 0;
    int fullScreenOnClickCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.configSP = getSharedPreferences("textSize",MODE_PRIVATE);
        int textSizeLevel = Utils.configSP.getInt("textSizeStatus",3);
        Utils.onActivityCreateSetTheme(this,textSizeLevel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_view);
        shake = (Switch)findViewById(R.id.switch_shake);
        bell = (Switch)findViewById(R.id.switch_bell);
        //bellImage = (ImageView)findViewById(R.id.bell_image);
        light = (Switch)findViewById(R.id.switch_light_switch);
        fullScreen = (Switch)findViewById(R.id.switch_full_screen);
        textSizeChange = (LinearLayout)findViewById(R.id.config_size);
        aboutUs = (LinearLayout)findViewById(R.id.config_about);
        bellChoice =(LinearLayout)findViewById(R.id.config_bell);
        textSizeHint = (TextView)findViewById(R.id.text_size_hint);
        aboutHint = (TextView)findViewById(R.id.about_us_hint);
        bellText = (TextView)findViewById(R.id.bell_text);
        setInitialState();
/*        Utils.configSP = getSharedPreferences("Settings",MODE_PRIVATE);
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
        */
        changeContentBasedOnSize(textSizeLevel);
        shake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Utils.configSP = getSharedPreferences("Settings",MODE_PRIVATE);
                Utils.editor = Utils.configSP.edit();
                if(b){
                    Utils.editor.putBoolean("shake",true);
                    Utils.editor.commit();
                }else{
                    Utils.editor.putBoolean("shake",false);
                    Utils.editor.commit();
                }
            }
        });
        bell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Utils.configSP = getSharedPreferences("Settings",MODE_PRIVATE);
                Utils.editor = Utils.configSP.edit();
                if(b){
                    Utils.editor.putBoolean("bell",true);
                    Utils.editor.commit();
                }else{
                    Utils.editor.putBoolean("bell",false);
                    Utils.editor.commit();
                }
            }
        });
        bellChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(ConfigView.this,BellView.class);
                startActivity(intent);
            }
        });
        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.configSP = getSharedPreferences("Settings",MODE_PRIVATE);
                Utils.editor = Utils.configSP.edit();
                if (isChecked) {
                    Utils.editor.putBoolean("lightOn",true);
                    Utils.editor.commit();
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    screenOnClickCount++;

                } else {
                    Utils.editor.putBoolean("lightOn",false);
                    Utils.editor.commit();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    screenOnClickCount++;
                }
            }
       });
        fullScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Utils.configSP = getSharedPreferences("Settings",MODE_PRIVATE);
                Utils.editor = Utils.configSP.edit();

                if (isChecked){
                    Utils.editor.putBoolean("fullScreen",true);
                    Utils.editor.commit();
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    fullScreenOnClickCount++;
                }
                else{
                    Utils.editor.putBoolean("fullScreen",false);
                    Utils.editor.commit();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    fullScreenOnClickCount++;
                }

            }
        });
        textSizeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigView.this, textSizeChangeView.class);
                startActivity(intent);
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigView.this,AboutView.class);
                startActivity(intent);
            }
        });

    }

    private void changeContentBasedOnSize(int theme){
        switch (theme){
            case 1:
                break;
            case 2:
                break;
            case 3:
                shake.setText("   振动");
                bellText.setText("   铃声");
                light.setText("   屏幕常亮");
                fullScreen.setText( "   全屏");
                textSizeHint.setText("   字体大小");
                aboutHint.setText("   关于");
                break;
            case 4:
                shake.setText(" 振动");
                bellText.setText(" 铃声");
                light.setText(" 常亮");
                fullScreen.setText( " 全屏");
                textSizeHint.setText(" 字体大小");
                aboutHint.setText(" 关于");
                break;
            case 5:
                shake.setText(" 振动");
                bellText.setText(" 铃声");
                light.setText(" 常亮");
                fullScreen.setText( " 全屏");
                textSizeHint.setText(" 字体大小");
                aboutHint.setText(" 关于");
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if(keyCode==KeyEvent.KEYCODE_BACK){
           if((fullScreenOnClickCount%2==1)||(screenOnClickCount%2==1)){
           AlertDialog.Builder dialog = new AlertDialog.Builder(ConfigView.this);
           dialog.setTitle("注意");
           dialog.setMessage("您需要保存这些设置吗？");
           dialog.setCancelable(false);
           dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   Utils.restartApp(ConfigView.this);
               }
           });
           dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                  finish();
               }
           });
           dialog.show();}

           else{
               finish();
           }
       }
        return false;
    }

    private void setInitialState(){
        Utils.configSP = getSharedPreferences("Settings",MODE_PRIVATE);
        boolean shakeStatus = Utils.configSP.getBoolean("shake",true);
        boolean bellStatus = Utils.configSP.getBoolean("bell",true);
        boolean lightOnStatus = Utils.configSP.getBoolean("lightOn",false);
        boolean fullScreenStatus = Utils.configSP.getBoolean("fullScreen",false);
        if(shakeStatus){
            shake.setChecked(true);
        }else{
            shake.setChecked(false);
        }
        if(bellStatus){
            bell.setChecked(true);
        }else{
            bell.setChecked(false);
        }
        if(lightOnStatus){
            light.setChecked(true);
        }else{
            light.setChecked(false);
        }
        if(fullScreenStatus){
            fullScreen.setChecked(true);
        }else{
            fullScreen.setChecked(false);
        }
    }
}
