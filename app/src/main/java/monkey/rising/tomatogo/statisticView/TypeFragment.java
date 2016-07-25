package monkey.rising.tomatogo.statisticView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;
import monkey.rising.tomatogo.R;
import monkey.rising.tomatogo.dataoperate.Clock;
import monkey.rising.tomatogo.dataoperate.ClockControl;

/**
 * Created by Administrator on 2016/7/19.
 */
public class TypeFragment extends Fragment {


    @InjectView(R.id.completeNum)
    TextView completeNum;
    @InjectView(R.id.undoneNum)
    TextView undoneNum;
    @InjectView(R.id.view3)
    PieChartView PieChart;
    private PieChartData pieData;
    private String username;
    private ClockControl clockControl;
    private ArrayList<String> types;
    public TypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.inject(this, view);
        SharedPreferences sp = getContext().getSharedPreferences("share", Context.MODE_PRIVATE);
        clockControl = new ClockControl(getContext());
        clockControl.openDataBase();
        clockControl.loadclock();
        clockControl.closeDb();
        username = sp.getString("userid","monkey");
        int doneNum = clockControl.getDoneNum(username);
        int undone = clockControl.getUndoneNum(username);

        completeNum.setText("" + doneNum);
        undoneNum.setText(""+ undone);

        generatePieData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void generatePieData() {
        types = new ArrayList<>();
        for(Clock c:clockControl.findbyuser(username)){
            if(types.indexOf(c.getType()) == -1)
                types.add(c.getType());
        }
        int numValues = types.size();

        PieChart.cancelDataAnimation();
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues - 1; ++i) {
            SliceValue sliceValue = new SliceValue(0, ChartUtils.pickColor());
            values.add(sliceValue);
        }
        SliceValue sliceValue = new SliceValue(1, ChartUtils.pickColor());
        values.add(sliceValue);

        pieData = new PieChartData(values);
        pieData.setHasLabels(false);
        pieData.setHasLabelsOnlyForSelected(true);
        pieData.setHasLabelsOutside(false);
        pieData.setHasCenterCircle(false);

        PieChart.setPieChartData(pieData);

        if(numValues != 0)
            prepareDataAnimation();
        PieChart.startDataAnimation(2000);
    }

    private void prepareDataAnimation() {
        int i = 0;

        for (SliceValue value : pieData.getValues()) {
            String t = types.get(i);
            int num = clockControl.getbyType(t,username).size();
            value.setLabel(t + " " + num);
            value.setTarget(num);
            i++;
        }
    }
}
