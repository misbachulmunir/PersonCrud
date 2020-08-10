package com.kubangkangkung.voleyproject.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kubangkangkung.voleyproject.Helper.RequestHandler;
import com.kubangkangkung.voleyproject.R;
import com.kubangkangkung.voleyproject.Util.Constant;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.kubangkangkung.voleyproject.Activity.DetailActiviity.DATAALAMATT;
import static com.kubangkangkung.voleyproject.Activity.DetailActiviity.DATANAMAA;
import static com.kubangkangkung.voleyproject.Activity.DetailActiviity.DATATELEPONN;
import static com.kubangkangkung.voleyproject.Activity.DetailActiviity.DATA_ID;

public class ActivityUpdate extends AppCompatActivity {
    private  TextView teksnama,teksnomer,teksalamat,foto;
    private static final int REQUEST_GALLERY = 200;
    ImageView img;
    String file_path=null;
  // ProgressBar progressBar;
     private static final int PERMISSION_REQUEST_CODE = 1;
    String id;
    private static final String TAG = "ActivityUpdate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        teksnama=findViewById(R.id.id_nama_updt);
        teksnomer=findViewById(R.id.id_phone_updt);
        teksalamat=findViewById(R.id.id_address_updt);
        foto=findViewById(R.id.teksgantifoto);
        img=findViewById(R.id.id_gambar_main);

        id= getIntent().getStringExtra(DATA_ID);




        String nama =(getIntent().getStringExtra(DATANAMAA));
        String alamat =(getIntent().getStringExtra(DATAALAMATT));
        String telepon =(getIntent().getStringExtra(DATATELEPONN));
        Log.d(TAG, "ini id: "+id +" nama"+nama + alamat+telepon);
        teksnama.setText(String.valueOf(nama));
        teksalamat.setText(String.valueOf(alamat));
        teksnomer.setText(String.valueOf(telepon));
  //     teksalamat.setText(alamat);
//        teksnomer.setText(telepon);



load_gambar();

        //ketika di di tekan ganti foto
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    filePicker();
                } catch (Exception e){
                    e.printStackTrace();
                }
              //  Toast.makeText(ActivityUpdate.this, "Hai", Toast.LENGTH_SHORT).show();
                //check permission greater than equal to marshmeellow we used run time permission
                if(Build.VERSION.SDK_INT>=23){
                    if(checkPermission()){
                        filePicker();
                    }
                    else{
                        requestPermission();
                    }
                }
                else{
                    filePicker();
                }
            }
        });


    }



    //load gambar
    private void load_gambar(){
        try {
            Picasso.get().load("http://192.168.43.234/rest-api-native/uploadfile/uploads/"+id+"Profile").into(img);
        }catch (Exception e ){

        }

    }
    //ambil file / gambar
    private void filePicker(){

        //.Now Permission Working
        Toast.makeText(ActivityUpdate.this, "File Picker Call", Toast.LENGTH_SHORT).show();
        //Let's Pick File
        Intent opengallery=new Intent(Intent.ACTION_PICK);
        opengallery.setType("image/*");
        startActivityForResult(opengallery,REQUEST_GALLERY);
    }

    //permission request permission
    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(ActivityUpdate.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(ActivityUpdate.this, "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(ActivityUpdate.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }


    //cek permission
    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(ActivityUpdate.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }
    public String getRealPathFromUri(Uri uri, Activity activity){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor=activity.getContentResolver().query(uri,proj,null,null,null);
        if(cursor==null){
            return uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }

    //uplad methdo
    private void UploadFile() {
        UploadTask uploadTask=new UploadTask();
        uploadTask.execute(new String[]{file_path});
        Log.d(TAG, "UploadFile: "+file_path);
    }

    private void update_pake_voley(){

        final String nama = teksnama.getText().toString();
        final String alamat = teksalamat.getText().toString();
        final String telepon = teksnomer.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.UrlPutPerson, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "ada response: "+response);
                startActivity(new Intent(ActivityUpdate.this,MainActivity.class));
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
                param.put("id",id);
                param.put("name",nama);
                param.put("address",alamat);
                param.put("phone",telepon);
                System.out.println(param);
                return param;

            }
        };
        RequestHandler.getInstance(ActivityUpdate.this).addToRequestQueue(stringRequest);
    }

    public void UPLOAD(View view) {
        update_pake_voley();
        if(file_path!=null){
            UploadFile();

        }
        else{
          //  Toast.makeText(ActivityUpdate.this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }


    
    private  void delete_pakevoley(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UrlDeletePerson, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(ActivityUpdate.this,MainActivity.class));
                finish();
                Log.d(TAG, "onResponse: "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("id",id);
//
                System.out.println(param);
                return param;

            }
        };
        RequestHandler.getInstance(ActivityUpdate.this).addToRequestQueue(stringRequest);
    }

    public void DELETEDATA(View view) {
        delete_pakevoley();
    }


    //upload Task
    public class UploadTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
      //      progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(ActivityUpdate.this, "File uploaded", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ActivityUpdate.this, "Failed Upload", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
           if(uploadFile(strings[0])){
                return "true";
            }
            else{
                return "failed";
            }
        }
        //upload file
        private boolean uploadFile(String path){
            Log.d(TAG, "Cek Path: "+path);
            File file=new File(path);
            try{

                RequestBody requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                        // .addFormDataPart("files",file.getName(),RequestBody.create(MediaType.parse("image/*"),file))
                        .addFormDataPart("files",id+"Profile",RequestBody.create(MediaType.parse("image/*"),file))
                        .addFormDataPart("some_key","some_value")
                        .addFormDataPart("submit","submit")
                        .build();

                okhttp3.Request request=new okhttp3.Request.Builder()
                        .url("http://192.168.43.234/rest-api-native/uploadfile/upload.php")
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "MultionFailure: "+e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        Log.d(TAG, "Multi: "+response);
                    }
                });
                return true;
            }
            catch (Exception e){
                Log.d(TAG, "Gagal: "+e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GALLERY && resultCode== Activity.RESULT_OK){
            String filePath=getRealPathFromUri(data.getData(),ActivityUpdate.this);
            Log.d("File Path : "," "+filePath);
            //now we will upload the file
            //lets import okhttp first
            this.file_path=filePath;
            Uri imguri = data.getData();

            File file=new File(filePath);
            Log.d(TAG, "onActivityResult: "+imguri);
            img.setImageURI(imguri);
           // file_name.setText(file.getName());

        }
    }
}