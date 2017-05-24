package com.example.olechka.recognitionwelddefects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    File directory;
    final int TYPE_PHOTO = 1;
    final int REQUEST_CODE_PHOTO = 1;
    final String TAG = "myLogs";
    ImageView ivPhoto;
    Button button_web_browser;
    Button button_info_def;
    Button button_otch;

    private Button loadButton;
    private ImageView image;
    private static final int REQUEST = 1;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        button_otch = (Button) findViewById(R.id.button_otch);
//        View.OnClickListener otchDef = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }};
//        button_otch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(MenuActivity.this, ReportActivity.class);
//                startActivity(intent1);
//            }
//        });

        Button startOtch = (Button) findViewById(R.id.button_otch);
        startOtch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent_2= new Intent(MenuActivity.this, ReportActivity.class);
                startActivity(intent_2);

            }
        });

        //        Получаем ссылку на кнопку
       button_info_def = (Button) findViewById(R.id.button_info_def);
        View.OnClickListener oclBtnDef = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        };
        button_info_def.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//    Метод выполняется при нажатии на кнопк
//    Создать обьект класса Intent
                Intent intent_1 = new Intent(MenuActivity.this, Info_Reference.class);
//    Запуск нового Activity
                startActivity(intent_1);

            }
        });
        Button startCaG = (Button) findViewById(R.id.button_catalog);
        startCaG.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent_1 = new Intent(MenuActivity.this, RecognitionWeldDefects.class);
                startActivity(intent_1);

            }
        });
        button_web_browser = (Button) findViewById(R.id.button_web_browser);
        button_web_browser.setOnClickListener(this);

        createDirectory();
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton mail = (FloatingActionButton) findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button_web_browser:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.ua"));
                startActivity(intent);
                break;
        }
    }

    public void onClickPhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        } else {
            openCameraActivity();
        }
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
        //startActivityForResult(intent, REQUEST_CODE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraActivity();
                }
                return;
            }
        }
    }

    private void openCameraActivity(){
        Intent intent = new Intent(MenuActivity.this, CameraActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (intent == null) {
                    Log.d(TAG, "Intent is null");
                } else {
                    Log.d(TAG, "Photo uri: " + intent.getData());
                    Bundle bndl = intent.getExtras();
                    if (bndl != null) {
                        Object obj = intent.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            ivPhoto.setImageBitmap(bitmap);
                        }
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Canceled");
            }
        }
    }



    private Uri generateFileUri(int type) {
        File file = null;
        switch (type) {
            case TYPE_PHOTO:
                file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");
                break;
        }
        Log.d(TAG, "fileName = " + file);
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyFolder");
        if (!directory.exists())
            directory.mkdirs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //      метод - отклика на пункт меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
//       Если пользователь настойки
            case R.id.action_settings:
//                Действие
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Натсройки");
                intent.putExtra(Intent.EXTRA_TEXT, "Натсройки");
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                                Uri.parse("android-app://com.example.olechka.recognitionwelddefects/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.olechka.recognitionwelddefects/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}


// Camera _ OPEN.CV

//import android.app.Activity;
//import android.os.Bundle;
//import android.view.SurfaceView;
//import android.view.WindowManager;
//
//import org.opencv.android.CameraBridgeViewBase;
//import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
//import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
//import org.opencv.android.OpenCVLoader;
//import org.opencv.core.Mat;


//public class MainActivity extends Activity implements CvCameraViewListener2 {
//    private CameraBridgeViewBase mOpenCvCameraView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setContentView(R.layout.activity_main);
//        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.view);
//        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
//        mOpenCvCameraView.setCvCameraViewListener(this);
//    }
//
//    @Override
//    public void onPause()
//    {
//        super.onPause();
//        if (mOpenCvCameraView != null)
//            mOpenCvCameraView.disableView();
//    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        OpenCVLoader.initDebug();
//        mOpenCvCameraView.enableView();
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
//        if (mOpenCvCameraView != null)
//            mOpenCvCameraView.disableView();
//    }
//
//    public void onCameraViewStarted(int width, int height) {
//    }
//
//    public void onCameraViewStopped() {
//    }
//
//    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
//        return inputFrame.rgba();
//    }
//}
