package monkey.rising.tomatogo.TaskSystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import monkey.rising.tomatogo.MainActivity.MainActivity;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.dataoperate.Task;
import monkey.rising.tomatogo.dataoperate.TaskControl;

public class quicktask extends AppCompatActivity {

    private ListView listView;
    TaskControl taskControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quicktask);
        listView=(ListView)findViewById(R.id.listView);
        taskControl=new TaskControl(this);
        taskControl.openDataBase();
        taskControl.loadTask();
        SharedPreferences shared=getSharedPreferences("share",MODE_PRIVATE);

       final String username=shared.getString("userid","monkey");

        ArrayList<String> data=new ArrayList<String>();
        for(Task task:taskControl.findtaskbyuser(username)){
            data.add(task.getContent());
        }
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(quicktask.this, MainActivity.class);
                intent.putExtra("taskid",taskControl.findtaskbyuser(username).get(i).getId());
                startActivity(intent);
            }
        });
    }
}
