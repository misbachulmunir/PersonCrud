package com.kubangkangkung.voleyproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kubangkangkung.voleyproject.Adapter.AdapterPerson;
import com.kubangkangkung.voleyproject.Helper.RequestHandler;
import com.kubangkangkung.voleyproject.Model.ModelPerson;
import com.kubangkangkung.voleyproject.R;
import com.kubangkangkung.voleyproject.Util.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerView;
SwipeRefreshLayout refresh;
FloatingActionButton fab;
private List<ModelPerson>dataperson = new ArrayList<>();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.id_recycle);
        refresh=findViewById(R.id.id_swipe);
        fab=findViewById(R.id.floatingActionButton);

//        ModelPerson dummy1= new ModelPerson();
//        dummy1.setNama("Pertama");
//        dummy1.setPhone("089888");
//        dataperson.add(dummy1);
        ambi_data_volley();
        recyclerView.setAdapter(new AdapterPerson(MainActivity.this,dataperson));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                // ambilData();
                ambi_data_volley();
                refresh.setRefreshing(false);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Add_Activity.class));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void ambi_data_volley(){
        dataperson.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constant.UrlMain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
         //       Log.d(TAG, "ADA RESPONSE: "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("person");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        ModelPerson item = new ModelPerson(
                                jsonObject1.getString("id"),
                                jsonObject1.getString("name"),
                                jsonObject1.getString("address"),
                                jsonObject1.getString("phone")


                        );
                        dataperson.add(item);
                        recyclerView.setAdapter(new AdapterPerson(MainActivity.this,dataperson));
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        System.out.println(item);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("data", "no");
                return params;
            }
        };
        RequestHandler.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }
}