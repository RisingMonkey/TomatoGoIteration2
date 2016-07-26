package monkey.rising.tomatogo.config;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import monkey.rising.tomatogo.R;


public class textSizeChangeView extends AppCompatActivity{
    SeekBar textSizeBar;
    Button ensureButton;
    TextView textViewMini;
    TextView textViewSmall;
    TextView textViewMiddle;
    TextView textViewLarge;
    TextView textViewHuge;
    TextView textExample;
    int textSizeLevel;
    int progress;
    int initialTextSizeLevel;


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
        textSizeLevel=Utils.configSP.getInt("textSizeStatus",3);
        initialTextSizeLevel = textSizeLevel;

        progress=Utils.configSP.getInt("progressValue",50);
       // Utils.onActivityCreateSetTheme(this, Utils.textSizeStatus);
        Utils.onActivityCreateSetTheme(this,textSizeLevel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_size_change_view);
        textSizeBar = (SeekBar)findViewById(R.id.seek_bar_size);
        //ensureButton = (Button)findViewById(R.id.ensure_button);
        textViewMini = (TextView)findViewById(R.id.textView_mini);
        textViewSmall = (TextView)findViewById(R.id.textView_small);
        textViewLarge = (TextView)findViewById(R.id.textView_large);
        textViewMiddle = (TextView)findViewById(R.id.textView_middle);
        textViewHuge = (TextView)findViewById(R.id.textView_huge);
        textSizeBar.setProgress(progress);
        textExample = (TextView)findViewById(R.id.text_view_size);
        textSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int currentProgress = seekBar.getProgress();
                if (currentProgress <= 20) {
                   // Utils.textSizeStatus = 1;
                    textSizeLevel = 1;
                    textExample.setTextSize(10);
                } else if (currentProgress > 20 && currentProgress <= 40) {
                  //  Utils.textSizeStatus = 2;
                    textSizeLevel = 2;
                    textExample.setTextSize(15);
                } else if (currentProgress > 40 && currentProgress <= 60) {
                    //Utils.textSizeStatus = 3;
                    textSizeLevel = 3;
                    textExample.setTextSize(20);
                } else if (currentProgress > 60 && currentProgress <= 80) {
                   // Utils.textSizeStatus = 4;
                    textSizeLevel =4;
                    textExample.setTextSize(25);
                } else if (currentProgress > 80 && currentProgress <= 100) {
                    //Utils.textSizeStatus = 5;
                    textSizeLevel = 5;
                    textExample.setTextSize(30);
                }
               // Utils.progressValue = currentProgress;
                progress = currentProgress;
            }
        });

 /*       ensureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.changeTheme(textSizeChangeView.this);
                switch (v.getId()){
                    case R.id.ensure_button:
                AlertDialog.Builder warningDialog = new AlertDialog.Builder(textSizeChangeView.this);
                warningDialog.setTitle("注意！");
                warningDialog.setMessage("我们需要重启应用使设置生效，请点击\"继续\"按钮继续");
                warningDialog.setCancelable(true);
                warningDialog.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.editor = Utils.configSP.edit();
                        Utils.editor.putInt("progressValue",progress);
                        Utils.editor.putInt("textSizeStatus",textSizeLevel);
                        Utils.editor.commit();
                        Utils.restartApp(textSizeChangeView.this);
                    }
                });
                warningDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                        warningDialog.show();
                        break;
                    default:
                        break;
                //Utils.restartApp(textSizeChangeView.this);
            }
            }
        });*/
        textViewMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.textSizeStatus=1;
                textSizeLevel = 1;
                textSizeBar.setProgress(0);
                //Utils.progressValue=0;
                progress = 0;
                textExample.setTextSize(10);
            }
        });
        textViewSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.textSizeStatus=2;
                textSizeLevel = 2;
                textSizeBar.setProgress(25);
                //Utils.progressValue=25;
                progress = 25;
                textExample.setTextSize(15);
            }
        });
        textViewMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.textSizeStatus = 3;
                textSizeLevel = 3;
                textSizeBar.setProgress(50);
                //Utils.progressValue = 50;
                progress = 50;
                textExample.setTextSize(20);
            }
        });
        textViewLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Utils.textSizeStatus = 4;
                textSizeLevel = 4;
                textSizeBar.setProgress(75);
               // Utils.progressValue = 75;
                progress = 75;
                textExample.setTextSize(25);
            }
        });
        textViewHuge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.textSizeStatus=5;
                textSizeLevel = 5;
                textSizeBar.setProgress(100);
               // Utils.progressValue=100;
                progress = 100;
                textExample.setTextSize(30);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if(initialTextSizeLevel==textSizeLevel){
                finish();
            }
            else{
                Utils.editor = Utils.configSP.edit();
                Utils.editor.putInt("progressValue",progress);
                Utils.editor.putInt("textSizeStatus",textSizeLevel);
                Utils.editor.commit();
                Utils.restartApp(textSizeChangeView.this);
                Utils.restartApp(this);
            }
        }
        return false;
    }
}
