package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;





public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            OkHttpClient client = new OkHttpClient();
            String url;
            url = "https://www.reddit.com/.json";
            Request req = new Request.Builder().url(url).build();
            ArrayList<String> link = new ArrayList<String>();
            ListView lv = findViewById(R.id.lv);



            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        Response response = client.newCall(req).execute();
                        String text = response.body().string();

                        JSONObject object = (JSONObject) new JSONTokener(text).nextValue();

                        JSONArray listings = object.getJSONObject("data").getJSONArray("children");

                        ArrayList<String> titles = new ArrayList<>(listings.length());


                        for (int j=0; j<listings.length(); j++) {
                            JSONObject item = listings.getJSONObject(j);
                            titles.add(item.getJSONObject("data").getString("title"));
                            link.add(item.getJSONObject("data").getString("permalink"));
                        }


                        runOnUiThread(() -> {
                            String result = titles.stream().reduce("", (a, b) -> a += "\n" + b);
                            titles.add(result);
                            ArrayAdapter ad = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, titles);
                            lv.setAdapter(ad);
                        });



                    }  catch (IOException | JSONException e) {
                        runOnUiThread(()->{
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        });
                    }
                }
            };

            t.start();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String passUrl = link.get(position);
                Log.d("link is this", String.valueOf(passUrl));

                Intent i = new Intent(MainActivity.this, click.class);
                i.putExtra("link", passUrl);
                startActivity(i);

            }
        });

    }
}

