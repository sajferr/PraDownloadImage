package com.example.m.pradownloadimage;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    String linkDoObrazka = "http://tech21info.com/admin/wp-content/uploads/2013/03/chrome-logo-200x200.png";
    ImageView imageView;
    TextView tekst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.imageView);
        tekst=(TextView)findViewById(R.id.textView);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

        .cacheInMemory(true)
                .cacheOnDisk(true)

        .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(defaultOptions)
        .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

    }


    public void startDownload(View view) {

        DownloadTask task = new DownloadTask();
        task.execute(linkDoObrazka);

    }


    public class DownloadTask extends AsyncTask<String,Integer,Void>{
        ProgressDialog progressDialog;
        int file_content;
        byte [] bajty;
        OutputStream outFile;
        File fajl;
        int total =0;
        int costam=0;
        int alojzy2=15;
        int alojzy = 30;
        BufferedInputStream reader;
        int ilee=0;
        long czaspo=0;

        @Override
        protected Void doInBackground(String... params) {
            String link = params[0];

            try {
                Log.d("Uwaga","11");
                URL url = new URL(link);
                Log.d("Uwaga","2");
                URLConnection connect = url.openConnection();
                connect.connect();
                file_content=connect.getContentLength();
                 reader = new BufferedInputStream(url.openStream());
               bajty = new byte[64000];
                ilee=reader.available();
                int ile=0;
                fajl = new File("sdcard/photoalbum");
                if (!fajl.exists()){
                    fajl.mkdir();

                }
                File fajl2= new File(fajl,"asd.png");
                Log.d("Uwaga","7");
                 outFile = new FileOutputStream(fajl2);

                Log.d("Uwaga","8");
                long czasprzed = System.currentTimeMillis();

                while ((costam=reader.read(bajty))!=-1){
                    ile++;
                    Log.d("Uwaga",String.valueOf(ile));
                    total+=costam;
                    Log.d("Uwaga","1000");
                    outFile.write(bajty,0,costam);
                    Log.d("Uwaga","100220");
                   // Log.d("Uwaga","10003232");
                   // alojzy+=alojzy2;
                    //publishProgress(alojzy);

                }
                 czaspo = System.currentTimeMillis()-czasprzed;
                reader.close();
                Log.d("Uwaga","10");
                outFile.close();

            } catch (MalformedURLException e) {
                Log.d("Uwaga","mal");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("Uwaga","ma2");
                e.printStackTrace();
            }

            Log.d("Uwaga","1222");
            return null;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMax(100);
            Log.d("Uwaga","2");
            progressDialog.setTitle("Progresek");
            progressDialog.setProgress(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();



        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
            ImageLoader.getInstance().displayImage(linkDoObrazka, imageView);
            Log.d("Uwaga","lol");
            int alojzy = 50;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("Uwaga","onpost");
            super.onPostExecute(aVoid);
            Log.d("Uwaga","222");
           progressDialog.hide();
            tekst.setText(String.valueOf(czaspo));
            String path = "sdcard/photoalbum/zdd.png";
            imageView.setImageDrawable(Drawable.createFromPath(path));
            Toast.makeText(MainActivity.this,"Gotowe",Toast.LENGTH_LONG).show();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }




}
