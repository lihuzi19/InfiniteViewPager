package com.example.lihuzi.infiniteviewpager.ui.activity;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lihuzi.infiniteviewpager.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpDownloadActivity extends AppCompatActivity {


    private TextView downloadTv;
    private ImageView iv;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_download);

        handler = new Handler();
        iv = findViewById(R.id.act_okhttp_download_iv);
        downloadTv = findViewById(R.id.act_okhttp_download_tv);
        downloadTv.setOnClickListener(v -> startDownload());

        checkCameraPermission();
    }

    private void checkCameraPermission() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkPermission(permissions)) {
        } else {
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    private boolean checkPermission(String[] permissions) {
        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(this, permission) == PermissionChecker.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    private void startDownload() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://pictest.sqplus.cn/shequnjia/minpro/userpic/1000000003_1524881901002.jpg";
        final Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final File file = getFilePath();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                InputStream is = response.body().byteStream();
                int len = -1;
                while ((len = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
                fos.flush();
                handler.post(() -> showPicture(file));
            }
        });
    }

    private void showPicture(File file) {
        iv.setImageURI(Uri.fromFile(file));
    }

    private File getFilePath() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), System.currentTimeMillis() + ".jpg");
        return file;
    }
}
