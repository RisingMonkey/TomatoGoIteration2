package monkey.rising.tomatogo.MainActivity;


import android.app.Service;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.TaskSystem.logactivity;
import monkey.rising.tomatogo.TaskSystem.quicktask;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.dataoperate.ClockControl;
import monkey.rising.tomatogo.dataoperate.Task;
import monkey.rising.tomatogo.dataoperate.TaskControl;
import monkey.rising.tomatogo.settings.SettingsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockFragment extends Fragment {

    @InjectView(R.id.mytext2)
    TextView currentTaskTextView;
    @InjectView(R.id.mytext3)
    TextView mytext3;
    @InjectView(R.id.mytext5)
    TextView mytext5;
    @InjectView(R.id.waterView)
    monkey.rising.tomatogo.MainActivity.waterView waterView;
    @InjectView(R.id.mytext1)
    TextView userTextView;
    @InjectView(R.id.minutePicker)
    NumberPicker minutePicker;
    @InjectView(R.id.imageView5)
    ImageView curTaskView;

    private int recLen;
    private int second;
    private int minute;
    private int totalSec;
    private float rate;
    private boolean bell;
    private int notification;
    private boolean shake;
    private String m, s;
    private String username;
    private String curTask;
    private Vibrator vibrator;
    private SharedPreferences mySharedPreference;
    private ArrayList<String> path;
    private boolean isDoing;
    private String startTime;
    private Task currentTask;
    private boolean isResting;

    public ClockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_clock, container, false);
        ButterKnife.inject(this, view);
        isResting = false;
        waterView.setFlowNum("选择番茄钟时间");
        waterView.setmWaterLevel(1.0f);
        vibrator = (Vibrator) getContext().getSystemService(Service.VIBRATOR_SERVICE);
        /*
        应用设置
         */
        Utils.configSP = getContext().getSharedPreferences("SettingsFragment", getContext().MODE_PRIVATE);
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
        /*
        快速开始图标跳转
         */
        curTaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editCurrent = new Intent(getActivity(), quicktask.class);
                startActivity(editCurrent);
            }
        });

        /*
        获取当前任务
         */
        Intent taskChangedIntent = getActivity().getIntent();
        Bundle taskExtras = taskChangedIntent.getExtras();
        if (taskExtras != null)
            curTask = taskExtras.getString("taskid");
        if (curTask == null) {
            curTask = "eat";
        }
        TaskControl tc = new TaskControl(getActivity());
        tc.openDataBase();
        tc.loadTask();
        tc.closeDb();
        currentTask = tc.findByTaskId(curTask);

        /*
        获取用户名，未登录则默认名为“monkey”
         */
        mySharedPreference = getActivity().getSharedPreferences("share", getActivity().MODE_PRIVATE);
        username = mySharedPreference.getString("userid", "monkey");
        if (username.equals("monkey")) {
            //未登录点击文字跳转至登录界面，否则跳转至个人信息界面
            userTextView.setText("登录/注册");
            userTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logIntent = new Intent();
                    logIntent.setClass(getActivity(), logactivity.class);
                    startActivity(logIntent);
                }
            });
        } else {
            userTextView.setText(username);
            userTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent settingIntent = new Intent();
                    settingIntent.setClass(getActivity(), SettingsFragment.class);
                    startActivity(settingIntent);
                }
            });
        }

        currentTaskTextView.setText("当前任务：" + ((currentTask == null) ? "空闲" : currentTask.getContent().toString()));
        currentTaskTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至当前任务编辑页面
                Intent taskEditIntent = new Intent();
                taskEditIntent.setClass(getActivity(), quicktask.class);
                startActivity(taskEditIntent);
            }
        });

        String[] gaps = {"0", "5", "10", "15", "20", "25", "30"};
        minutePicker.setDisplayedValues(gaps);
        minutePicker.setMaxValue(gaps.length - 1);
        minutePicker.setMinValue(0);
        minutePicker.setValue(0);
        waterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minutePicker.setEnabled(false);
                currentTaskTextView.setEnabled(false);
                if (!isDoing) {
                    isDoing = true;
                    curTaskView.setClickable(false);
                    TomatoGo(Integer.parseInt(minutePicker.getDisplayedValues()[minutePicker.getValue()]));
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    if(!isResting){
                        dialog.setTitle("半途而废？");
                        dialog.setMessage("确定放弃本次番茄钟？");
                    }
                    else{
                        dialog.setTitle("中断休息？");
                        dialog.setMessage("不再休息一会嘛(￣▽￣)");
                    }
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            handler.removeCallbacks(runnable);
                            curTaskView.setClickable(true);
                            waterView.stopWave();
                            isDoing = false;
                            isResting = false;
                            waterView.setFlowNum("");
                            waterView.setmWaterLevel(1F);
                            ClockControl cc = new ClockControl(getActivity().getApplicationContext());
                            cc.openDataBase();
                            cc.insertOneClock(startTime, username, curTask, (totalSec - recLen) / 60, totalSec / 60, false);
                            cc.closeDb();
                            currentTaskTextView.setEnabled(true);
                            minutePicker.setEnabled(true);

                        }
                    });
                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                }
            }
        });
        return view;
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            if (recLen >= 0) {
                minute = recLen / 60;
                second = recLen % 60;
                m = Integer.toString(minute);
                s = Integer.toString(second);
                if (minute < 10)
                    m = "0" + minute;
                if (second < 10)
                    s = "0" + second;
                waterView.setFlowNum(m + ":" + s);
                rate = (float) recLen / totalSec;
                waterView.setmWaterLevel(rate);
                handler.postDelayed(this, 1000);
            } else {
                mySharedPreference = getActivity().getSharedPreferences("SettingsFragment", getActivity().MODE_PRIVATE);
                shake = mySharedPreference.getBoolean("shake", true);
                if (shake) {
                    vibrator.vibrate(3000);
                    vibrator.vibrate(new long[]{1000, 10, 1000, 100}, -1);//震动服务
                }
                bell = mySharedPreference.getBoolean("bell", true);
                notification = mySharedPreference.getInt("notification", 2);
                if (bell) {
                    path = new ArrayList<>();
                    scannerMediaFile();
                    Uri uri = Uri.parse(path.get(notification));
                    MediaPlayer mp = MediaPlayer.create(getActivity(), uri);
                    mp.start();
                }
                currentTaskTextView.setEnabled(true);
                ClockControl cc = new ClockControl(getActivity().getApplicationContext());
                if (!isResting) {
                    cc.openDataBase();
                    cc.insertOneClock(startTime, username, curTask, (totalSec - recLen) / 60, totalSec / 60, true);
                    cc.closeDb();
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(false);
                if (isResting) {
                    dialog.setTitle("休息完成");
                    dialog.setMessage("继续工作吧！");
                    dialog.setPositiveButton("再休息一会吧QAQ(5min)", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isResting = true;
                            TomatoGo(5);
                        }
                    });
                    dialog.setNegativeButton("继续工作!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isResting = false;
                            curTaskView.setClickable(false);
                            TomatoGo(Integer.parseInt(minutePicker.getDisplayedValues()[minutePicker.getValue()]));
                        }
                    });
                    dialog.setNeutralButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            isResting = false;
                            isDoing = false;
                            waterView.setmWaterLevel(1.0f);
                            waterView.setFlowNum("");
                        }
                    });
                    dialog.show();
                } else {
                    dialog.setTitle("番茄完成");
                    dialog.setMessage("你工作了" + totalSec / 60 + " 分钟!");
                    CheckBox cb = new CheckBox(getActivity().getApplicationContext());
                    String content;
                    if (currentTask == null)
                        content = "";
                    else
                        content = currentTask.getContent();
                    cb.setText("任务：" + content + "完成");
                    dialog.setView(cb);
                    dialog.setPositiveButton("放松一下☺(5min)", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            curTaskView.setClickable(true);
                            isResting = true;
                            TomatoGo(5);
                        }
                    });
                    dialog.setNegativeButton("继续工作", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            curTaskView.setClickable(false);
                            isResting = false;
                            TomatoGo(Integer.parseInt(minutePicker.getDisplayedValues()[minutePicker.getValue()]));
                        }
                    });
                    dialog.setNeutralButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            curTaskView.setClickable(true);
                            isDoing = false;
                            waterView.setmWaterLevel(1.0f);
                            waterView.setFlowNum("");
                        }
                    });
                    dialog.show();
                }
            }
        }
    };

    private void scannerMediaFile() {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.TITLE}, "is_notification != ?",
                new String[]{"0"}, "_id asc");
        if (cursor == null) {
            return;
        }
        while (cursor.moveToNext()) {
            path.add(cursor.getString(1));
        }
    }

    private void TomatoGo(int mt) {
        startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
        minute = mt;
        totalSec = 60 * minute;
        if (totalSec == 0)
            totalSec = 5;
        recLen = totalSec;
        waterView.setFlowNum("Start!");
        waterView.setmWaterLevel(1F);
        waterView.startWave();
        handler.postDelayed(runnable, 1000);
        isDoing = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
