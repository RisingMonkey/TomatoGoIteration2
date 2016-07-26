package monkey.rising.tomatogo.config;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import monkey.rising.tomatogo.R;

public class BellView extends AppCompatActivity {
    List<String> data ;
    List<String> path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data = new ArrayList<String>();
        path = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bell_view);
        scannerMediaFile();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BellView.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView)findViewById(R.id.bell_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(path.get(i));
                MediaPlayer mp = MediaPlayer.create(BellView.this,uri);
                mp.start();
                Utils.configSP = getSharedPreferences("SettingsFragment",MODE_PRIVATE);
                Utils.editor = Utils.configSP.edit();
                Utils.editor.putInt("notification",i);
                Utils.editor.commit();
            }
        });
    }

    private void scannerMediaFile() {
        ContentResolver cr = this.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.TITLE }, "is_notification != ?",
                new String[] { "0" }, "_id asc");

        if (cursor == null) {
            return;
        }

        while (cursor.moveToNext()) {
            path.add(cursor.getString(1));
            data.add(cursor.getString(2));
        }
    }


}
