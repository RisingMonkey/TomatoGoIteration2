package monkey.rising.tomatogo.MainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.TabWidget;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.Bmob;
import monkey.rising.tomatogo.R;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tabHost2)
    TabHost tabHost2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);

        tabHost2.setup();
        tabHost2.addTab((tabHost2.newTabSpec("maintab").setIndicator("",getResources().getDrawable(R.drawable.tab_main)).setContent(R.id.linearLayout3)));
        tabHost2.addTab((tabHost2.newTabSpec("tasktab").setIndicator("",getResources().getDrawable(R.drawable.tab_task)).setContent(R.id.linearLayout4)));
        tabHost2.addTab((tabHost2.newTabSpec("configtab").setIndicator("",getResources().getDrawable(R.drawable.tab_config)).setContent(R.id.linearLayout5)));
    }
}
