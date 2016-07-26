package monkey.rising.tomatogo.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.Utils;

public class Skin extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
if(requestCode==1)
        {
            final SharedPreferences pref=getSharedPreferences("color1",MODE_PRIVATE);
            int a=pref.getInt("background",0);
            final RelativeLayout layout =(RelativeLayout)findViewById(R.id.layout_skin);
            layout.setBackgroundColor(a);
        }

    }
private Button picture;

    private Button theme;
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
        setContentView(R.layout.skin);
        final SharedPreferences pref=getSharedPreferences("color1",MODE_PRIVATE);
        int a=pref.getInt("background",0);
        final RelativeLayout layout =(RelativeLayout)findViewById(R.id.layout_skin);
        layout.setBackgroundColor(a);
        picture = (Button)findViewById(R.id.picture);
        theme = (Button)findViewById(R.id.theme);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Skin.this,Picture.class);
                startActivity(intent);
            }
        });
        theme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                Intent intent = new Intent(Skin.this,Theme.class);
                startActivityForResult(intent,1);
            }

        });

    }

@Override
    public  void  onBackPressed(){
    Intent intent =new Intent();
    setResult(3,intent);
    finish();
}
}
