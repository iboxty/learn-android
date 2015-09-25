package tk.iboxty.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by 天宇 on 2015/9/26.
 * Simple Volley picture get
 */
public class LoadImage extends AppCompatActivity{

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_image);
        iv = (ImageView)findViewById(R.id.imageView);
        SetImageContent();
    }

    private void SetImageContent() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(
                "http://epaper.wuhunews.cn/whrb/images/2015-09/24/RB08/p27_b.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //iv.setImageResource(R.drawable.default_image);
                Log.d("TAG", error.toString());
            }
        });
        mQueue.add(imageRequest);
    }
}

