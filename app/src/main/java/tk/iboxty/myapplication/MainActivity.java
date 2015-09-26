package tk.iboxty.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static MaterialRefreshLayout materialRefreshLayout;
    private ListView lv;
    private static final int REFRESH_COMPLETE = 0X110;
    private ArrayList<String> al = new ArrayList<String>();
    private ArrayAdapter<String> aa;
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
                VolleySimpleRequest();
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 1200);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉刷新...
            }
        });
        aa=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,al);
        lv = (ListView)findViewById(R.id.lv);
        for(int i=0;i<11;i++) al.add("item");
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("TAG","点击了第"+position+"项");
                switch (position){
                    case 0:
                        Intent it = new Intent();
                        it.putExtra("LoadCase",0);
                        it.setClass(MainActivity.this,LoadImage.class);
                        startActivity(it);
                        break;
                    case 1:
                        it = new Intent();
                        it.putExtra("LoadCase",1);
                        it.setClass(MainActivity.this,LoadImage.class);
                        startActivity(it);
                        break;
                }
            }
        });
    }

    private void VolleySimpleRequest(){
        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        for(int i=0;i<5;i++) al.add("Response is: "+ response.substring(0,20));
                        aa.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                for(int i=0;i<5;i++) al.add("Bad Request");
                aa.notifyDataSetChanged();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        // Request a JSON response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/atad/101230201.html", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        queue.add(jsonObjectRequest);
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

