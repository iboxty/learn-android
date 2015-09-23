package tk.iboxty.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static MaterialRefreshLayout materialRefreshLayout;
    private ListView lv;
    private static final int REFRESH_COMPLETE = 0X110;
    //private final ArrayList<String> al = new ArrayList<String>();
    //private final ArrayAdapter<String> aa=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,al);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 1200);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
            }
        });
        ArrayList<String> al = new ArrayList<String>();
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,al);
        lv = (ListView)findViewById(R.id.lv);
        for(int i=0;i<11;i++) al.add("item");
        lv.setAdapter(aa);
    }


    /**
     * learn new handler without leaks warning
     * from http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1106/1922.html
     *
     */
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what){
                    case REFRESH_COMPLETE:
                        materialRefreshLayout.finishRefresh();
                        break;
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

