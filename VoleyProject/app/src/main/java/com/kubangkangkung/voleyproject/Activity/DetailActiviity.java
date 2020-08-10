package com.kubangkangkung.voleyproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kubangkangkung.voleyproject.Adapter.AdapterPerson;
import com.kubangkangkung.voleyproject.Model.ModelPerson;
import com.kubangkangkung.voleyproject.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.kubangkangkung.voleyproject.Adapter.AdapterPerson.DATA_PERSON;

public class DetailActiviity extends AppCompatActivity {
    public static final String DATA_ID = "data_id";
    public static final String DATANAMAA = "datanama";
    public static final String DATAALAMATT = "dataalamat";
    public static final String DATATELEPONN = "datatelepn";
    TextView teknama,teksalamat,teksphone;
    String id,nama,alamat,telepon;
    ImageView imgm;
    private static final String TAG = "DetailActiviity";
  private ModelPerson data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activiity);
        teknama=findViewById(R.id.id_namadet);
        teksalamat=findViewById(R.id.id_alamatdet);
        teksphone=findViewById(R.id.id_phonedet);
        imgm=findViewById(R.id.id_gambar_maxin);



        Bundle bundle=getIntent().getBundleExtra(AdapterPerson.DATA_EXTRA);
        data = Parcels.unwrap(bundle.getParcelable(DATA_PERSON));
        id=data.getId();
        
        teknama.setText(data.getNama());
        teksalamat.setText(data.getAlamat());
        teksphone.setText(data.getPhone());


        Log.d(TAG, "onCreate: "+id);



    }

    public void EDITDATA(View view) {
        id=data.getId();
        nama=teknama.getText().toString();
        alamat=teksalamat.getText().toString();
        telepon=teksphone.getText().toString();

        Intent pindah = new Intent(DetailActiviity.this, ActivityUpdate.class);
        pindah.putExtra(DATA_ID,id);
        pindah.putExtra(DATANAMAA,nama);
        pindah.putExtra(DATAALAMATT,alamat);
        pindah.putExtra(DATATELEPONN,telepon);
        startActivity(pindah);
    }



    @Override
    protected void onStart() {
        super.onStart();
        load_gambar();
    }

    //load gambar
    private void load_gambar(){
        try {
          //  Picasso.get().load("http://192.168.43.234/rest-api-native/uploadfile/uploads/"+id+"Profile").into(imgm);
            Picasso.get().load("http://192.168.43.234/rest-api-native/uploadfile/uploads/"+id+"Profile").networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(imgm);
           // Glide.with(this).load("http://192.168.43.234/rest-api-native/uploadfile/uploads/"+id+"Profile").into(imgm);
        }catch (Exception e ){

        }

    }
}