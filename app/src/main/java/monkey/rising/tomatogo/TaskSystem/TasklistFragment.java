package monkey.rising.tomatogo.TaskSystem;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.dataoperate.Task;
import monkey.rising.tomatogo.dataoperate.TaskControl;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasklistFragment extends Fragment implements slideview.RemoveListener {


    @InjectView(R.id.tasklist)
    slideview slide;
    @InjectView(R.id.calender)
    ImageView can;
    @InjectView(R.id.add)
    Button button;
    @InjectView(R.id.cant)
    TextView cant;
    @InjectView(R.id.spin)
    Spinner spinner;


    private ArrayList<String> list=new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> data=new ArrayList<String>();
    String userid;
    TaskControl taskControl;
    Calendar c;
    int chosey;
    int chosem;
    int chosed;

    public TasklistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        ButterKnife.inject(this, view);
        Utils.configSP = getActivity().getSharedPreferences("SettingsFragment",getActivity().MODE_PRIVATE);
        boolean screenOn = Utils.configSP.getBoolean("lightOn",false);
        boolean fullScreen = Utils.configSP.getBoolean("fullScreen",true);
        if (screenOn){
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else{
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        if(fullScreen){
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Utils.configSP = getActivity().getSharedPreferences("textSize",getActivity().MODE_PRIVATE);
        int textSizeLevel = Utils.configSP.getInt("textSizeStatus",3);
        Utils.onActivityCreateSetTheme(getActivity(),textSizeLevel);


        list.add("按日期查看");
        list.add("总备忘录");
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        slide.setRemoveListener(this);
        taskControl=new TaskControl(getActivity());
        taskControl.openDataBase();
        taskControl.loadTask();
        taskControl.closeDb();
        c=Calendar.getInstance();
        c.setTime(new Date());

        chosey=c.get(Calendar.YEAR);
        chosem=c.get(Calendar.MONTH);
        chosed=c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String curdate=format.format(new java.util.Date());
        cant.setText(curdate);

        SharedPreferences sp = getActivity().getSharedPreferences("share",getActivity().MODE_PRIVATE);
        userid=sp.getString("userid","monkey");

        for (Task task:taskControl.findbyday(cant.getText().toString(),userid)) {
            data.add(task.getContent());
        }
        arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.item,R.id.item,data);
        slide.setAdapter(arrayAdapter);

        //ArrayList<String> str=new ArrayList<>();

        // for (Task task:
        //  taskControl.findtaskbyuser(userid) ) {
        //    if(!task.isDone)
        //  str.add(task.getContent());

        //  }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(list.get(i)=="按日期查看"){
                    can.setVisibility(View.VISIBLE);
                    cant.setVisibility(View.VISIBLE);
                    data.clear();
                    for (Task task:taskControl.findbyday(cant.getText().toString(),userid)) {
                        data.add(task.getContent());
                        Log.e("内容",task.getContent());
                    }
                    arrayAdapter.notifyDataSetChanged();

                }
                else {
                    data.clear();
                    can.setVisibility(View.INVISIBLE);
                    cant.setVisibility(View.INVISIBLE);
                    for (Task task:taskControl.findtaskbyuser(userid)) {

                        data.add(task.getContent());
                        Log.e("内容",task.getContent());
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c=Calendar.getInstance();
                        c.set(i,i1,i2);
                        chosed=i2;
                        chosem=i1;
                        Log.e("月",i1+"");
                        chosey=i;
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                        String setdate=format.format(c.getTime());
                        cant.setText(setdate);

                        data.clear();
                        for (Task task:taskControl.findbyday(cant.getText().toString(),userid)) {
                            data.add(task.getContent());
                            Log.e("内容",task.getContent());
                        }
                        arrayAdapter.notifyDataSetChanged();

                    }
                },chosey,chosem,chosed);
                dialog.show();
            }
        });

        slide.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),taskControl.findtaskbyuser(userid).get(position).timeexpexted,Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getActivity(),EditTask.class);
                intent1.putExtra("id",taskControl.findtaskbyuser(userid).get(position).getId());
                startActivity(intent1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),AddTask.class);
                intent1.putExtra("userid",userid);
                startActivity(intent1);
            }
        });
        return view;
    }

    @Override
    public void removeItem(slideview.RemoveDirection direction, int position) {

        arrayAdapter.remove(arrayAdapter.getItem(position));
        taskControl.openDataBase();
        taskControl.deletedata(taskControl.findtaskbyuser(userid).get(position).getId());
        taskControl.closeDb();
        switch (direction){
            case RIGHT:
                Toast.makeText(getActivity(),"已删除",Toast.LENGTH_SHORT).show();
                break;
            case LEFT:
                Toast.makeText(getActivity(),"已删除",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
