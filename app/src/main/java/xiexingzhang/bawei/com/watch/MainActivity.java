package xiexingzhang.bawei.com.watch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private int week;
    private SimpleDateFormat format;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCalendar();
        initView();

    }
    private void initCalendar() {
        long time = System.currentTimeMillis();
        date = new Date(time);
        format = new SimpleDateFormat("EEEE");
        Log.e("time", "time5=" + format.format(date));
    }
    private void initView() {
        TextView data = (TextView) findViewById(R.id.textView);
        data.setText(format.format(date).toString());
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, format.format(date).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
