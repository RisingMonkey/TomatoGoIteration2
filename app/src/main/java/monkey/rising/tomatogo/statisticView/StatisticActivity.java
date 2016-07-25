package monkey.rising.tomatogo.statisticView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabWidget;

import butterknife.ButterKnife;
import butterknife.InjectView;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.Utils;

public class StatisticActivity extends AppCompatActivity {
    @InjectView(android.R.id.tabs)
    TabWidget tabs;
    @InjectView(R.id.tabHost)
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.configSP = getSharedPreferences("Settings",MODE_PRIVATE);
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
        setContentView(R.layout.activity_statistic);
        ButterKnife.inject(this);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("分类统计").setContent(R.id.linearLayout));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("日常统计").setContent(R.id.linearLayout2));
        tabHost.getTabWidget().getChildAt(0).getLayoutParams().width = 250;
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().width = 100;

    }
}
