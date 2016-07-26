package monkey.rising.tomatogo.TaskSystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.dataoperate.TaskControl;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {


    @InjectView(R.id.tasklist)
    slideview tasklist;
    @InjectView(R.id.calender)
    ImageView calender;
    @InjectView(R.id.add)
    Button add;
    @InjectView(R.id.cant)
    TextView cant;
    @InjectView(R.id.spin)
    Spinner spin;
    private slideview slide;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> data=new ArrayList<String>();
    String userid;
    Button button;
    TaskControl taskControl;
    ImageView taskImage;
    ImageView can;
    ImageView settingsImage;
    ImageView imageView;
    Spinner spinner;
    private ArrayList<String> list=new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    Calendar c;
    TextView cant;
    int chosey;
    int chosem;
    int chosed;

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.inject(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
