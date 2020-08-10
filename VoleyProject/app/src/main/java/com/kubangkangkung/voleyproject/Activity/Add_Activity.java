package com.kubangkangkung.voleyproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kubangkangkung.voleyproject.Helper.RequestHandler;
import com.kubangkangkung.voleyproject.R;
import com.kubangkangkung.voleyproject.Util.Constant;

import java.util.HashMap;
import java.util.Map;

public class Add_Activity extends AppCompatActivity {
    private EditText teknama, teksadress,teksphone;
    Button addbtn;
    private static final String TAG = "Add_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);
        teknama=findViewById(R.id.id_nama);
        teksphone=findViewById(R.id.id_phone);
        teksadress=findViewById(R.id.id_address);
        addbtn=findViewById(R.id.add_tambah);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Add_Activity.this, "Cek", Toast.LENGTH_SHORT).show();
                add_voley();
            }
        });


    }

    private void add_voley(){
        final String nama = teknama.getText().toString();
        final String alamat = teksadress.getText().toString();
        final String telepon = teksphone.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UrlPostPerson, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);

                startActivity(new Intent(Add_Activity.this,MainActivity.class));
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param = new HashMap<>();
                param.put("name",nama);
                param.put("address",alamat);
                param.put("phone",telepon);
                System.out.println(param);
                return param;

            }
        };
        RequestHandler.getInstance(Add_Activity.this).addToRequestQueue(stringRequest);
    }


}