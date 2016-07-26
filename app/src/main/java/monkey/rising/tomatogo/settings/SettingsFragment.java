package monkey.rising.tomatogo.settings;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import monkey.rising.tomatogo.MainActivity.MainActivity;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.AchievementView;
import monkey.rising.tomatogo.config.ConfigView;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.statisticView.StatisticActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    @InjectView(R.id.skin)
    Button skin;
    @InjectView(R.id.achievement)
    Button achievement;
    @InjectView(R.id.tomato)
    Button tomato;
    @InjectView(R.id.setting)
    Button setting;
    @InjectView(R.id.label)
    Button label;
    @InjectView(R.id.exit)
    Button exitButton;
    @InjectView(R.id.layout_settings)
    RelativeLayout layout;
    @InjectView(R.id.nickname)
    Button nickname;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            final SharedPreferences pref = getActivity().getSharedPreferences("color1", getActivity().MODE_PRIVATE);
            int a = pref.getInt("background", 0);
            layout.setBackgroundColor(a);
        }
    }

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_new_settings, container, false);
        ButterKnife.inject(this, view);
        Utils.configSP = getActivity().getSharedPreferences("SettingsFragment", getActivity().MODE_PRIVATE);
        boolean screenOn = Utils.configSP.getBoolean("lightOn", false);
        boolean fullScreen = Utils.configSP.getBoolean("fullScreen", true);
        if (screenOn) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        if (fullScreen) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Utils.configSP = getActivity().getSharedPreferences("textSize", getActivity().MODE_PRIVATE);
        int textSizeLevel = Utils.configSP.getInt("textSizeStatus", 3);
        Utils.onActivityCreateSetTheme(getActivity(), textSizeLevel);
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this,view);

        final SharedPreferences pref = getActivity().getSharedPreferences("color1", getActivity().MODE_PRIVATE);
        int a = pref.getInt("background", 0);
        layout.setBackgroundColor(a);
        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Skin.class);
                startActivityForResult(intent, 2);

            }
        });
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Label.class);

                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ConfigView.class);
                startActivity(intent);
            }
        });
        achievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AchievementView.class);
                startActivity(intent);
            }
        });
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StatisticActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share", getActivity().MODE_PRIVATE);
        String uid = sharedPreferences.getString("userid", "monkey");
        nickname.setText(uid);
        if (uid.equals("monkey"))
            exitButton.setVisibility(View.INVISIBLE);
        else
            exitButton.setVisibility(View.VISIBLE);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share", AppCompatActivity.MODE_PRIVATE);
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putString("userid", "monkey");
                e.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
