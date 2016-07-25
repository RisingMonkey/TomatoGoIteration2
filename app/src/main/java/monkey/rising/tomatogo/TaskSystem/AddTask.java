package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.config.Utils;
import monkey.rising.tomatogo.dataoperate.TaskControl;
import monkey.rising.tomatogo.dataoperate.TypeControl;

public class AddTask extends AppCompatActivity {
    private EditText content;
    String user;
    private EditText type;
    private EditText time;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private Button submit;
    private Button back;
    private String id;

    private ArrayList<String> list=new ArrayList<String>();
    public TaskControl taskControl;
    public TypeControl typeControl;
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


        setContentView(R.layout.activity_add_task);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        content=(EditText)findViewById(R.id.content);
        type=(EditText)findViewById(R.id.type);
        time=(EditText)findViewById(R.id.timeexp);
        submit=(Button)findViewById(R.id.submit);
        back=(Button)findViewById(R.id.back) ;
        taskControl=new TaskControl(this);
        typeControl=new TypeControl(this);
        typeControl.openDataBase();
        typeControl.loadtype();
        typeControl.closeDb();

        Intent intent=getIntent();
        user=intent.getStringExtra("userid");

        content.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if(charSequence.equals(" ")||charSequence.equals("\n"))
                    return "";
                else
                    return null;
            }
        }});

        type.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if(charSequence.equals(" ")||charSequence.equals("\n"))
                    return "";
                else
                    return null;
            }
        }});

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        id=format.format(new java.util.Date());

        list=typeControl.gettypebyuser(user);
        spinner=(Spinner)findViewById(R.id.spinner) ;
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(list.get(position).equals("其他")){
                    type.setEnabled(true);
                }else {
                    type.setEnabled(false);
                }
                type.setText(list.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.getText().length()!=0&&content.getText().length()!=0&&time.getText().length()!=0){
                taskControl.openDataBase();
                 taskControl.insertData(id,type.getText().toString(),user,content.getText().toString(),"null",time.getText().toString(),"null",1);
                taskControl.closeDb();
                    typeControl.openDataBase();
                    if(typeControl.Istype(list,type.getText().toString())){
                        typeControl.insertDate(type.getText().toString(),user);
                    }
                    typeControl.closeDb();
                Intent intent=new Intent(AddTask.this,tasklist.class);
                 intent.putExtra("userid",user);
                 startActivity(intent);

            }else {
                    Toast.makeText(getApplicationContext()," 请完善信息",Toast.LENGTH_SHORT).show();}
        }});

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               finish();
           }
       });
    }

}
