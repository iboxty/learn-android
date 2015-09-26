package tk.iboxty.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by 天宇 on 2015/9/26.
 * Simple Volley picture get
 */
public class LoadImage extends AppCompatActivity{

    private ImageView iv;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_image);
        iv = (ImageView)findViewById(R.id.imageView);
        bt = (Button)findViewById(R.id.button);
        Intent it = getIntent();
        final int c = it.getIntExtra("LoadCase",0);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (c){
                    //Load image with image request
                    case 0:
                        SetImageContent();
                        break;
                    //Load image with image loader
                    case 1:
                        SetImageContentWithCache();
                        break;
                }
            }
        });
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
                //iv.setImageResource(R.drawable.default);
                iv.setImageResource(R.drawable.d);
                Log.d("TAG", error.toString());
            }
        });
        mQueue.add(imageRequest);
    }

    private void SetImageContentWithCache(){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv,
                R.drawable.d, R.drawable.d);
        imageLoader.get("http://epaper.wuhunews.cn/whrb/images/2015-09/24/RB08/p27_b.jpg",
                listener, 200, 200);
    }
}

