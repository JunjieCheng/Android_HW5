package cjunjie.hw5;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String IMAGE = "Image";

    private Button startButton;
    private FrameLayout canvasLayout;

    private String pictureImagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.startButton = (Button) findViewById(R.id.start_button);
        this.canvasLayout = (FrameLayout) findViewById(R.id.canvas_layout);

        startButton.setVisibility(View.VISIBLE);
        canvasLayout.setVisibility(View.GONE);

        startButton.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", resultCode + "");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new File(pictureImagePath);
            Log.e("onActivityResult", pictureImagePath);

            if (imgFile.exists()) {
                Log.e("onActivityResult", "get photo");
                Intent intent = new Intent(this, CanvasActivity.class);
                intent.putExtra(IMAGE, imgFile.getAbsolutePath());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == this.startButton.getId()) {
            int permissionRead = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
            int permissionWrite = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);

            if (permissionRead != PackageManager.PERMISSION_GRANTED
                    || permissionWrite != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 2);
            } else {
                openCamera();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("Requst Permission", Arrays.toString(grantResults));
        switch (requestCode) {
            case 2:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                }
        }
    }

    public void openCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);

        Intent picIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        picIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        if (picIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(picIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
