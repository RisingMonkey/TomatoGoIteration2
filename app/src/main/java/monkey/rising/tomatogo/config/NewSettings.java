package monkey.rising.tomatogo.config;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.TaskSystem.logactivity;
import monkey.rising.tomatogo.settings.Label;
import monkey.rising.tomatogo.settings.Picture;
import monkey.rising.tomatogo.settings.Theme;
import monkey.rising.tomatogo.statisticView.StatisticActivity;

public class NewSettings extends AppCompatActivity {
private ImageView picture;
    private ImageView theme;
    private  ImageView label;
    private ImageView achievement;
    private ImageView tomato;
    private ImageView config;
    private Button login;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_settings);
        picture = (ImageView)findViewById(R.id.image_background_image);
        theme=(ImageView)findViewById(R.id.image_theme);
        label=(ImageView)findViewById(R.id.image_note);
        achievement=(ImageView)findViewById(R.id.image_achievement);
        tomato=(ImageView)findViewById(R.id.image_accounting);
        config = (ImageView)findViewById(R.id.image_config);
        login = (Button)findViewById(R.id.login_button);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSettings.this, Picture.class);
                startActivity(intent);
            }
        });
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSettings.this,Theme.class);
                startActivity(intent);
            }
        });
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSettings.this,Label.class);
                startActivity(intent);

            }
        });
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSettings.this,AchievementView.class);
                startActivity(intent);
            }});
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSettings.this, StatisticActivity.class);
                startActivity(intent);
            }
        });
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSettings.this,ConfigView.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewSettings.this,logactivity.class);
                startActivity(intent);
            }
        });
    }
}
