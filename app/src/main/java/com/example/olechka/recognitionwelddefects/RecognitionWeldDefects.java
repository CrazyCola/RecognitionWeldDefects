package com.example.olechka.recognitionwelddefects;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.io.File;
import java.io.FileNotFoundException;

public class RecognitionWeldDefects extends AppCompatActivity implements Imageutils.ImageAttachmentListener {

    final int RQS_IMAGE = 1;
    ImageView iv_attachment;
    Imageutils imageutils;

    private Button mLoadImageButton;
    private SeekBar mSaturationSeekbar;
    private TextView mSaturationTextView;
    private Uri mSource;
    private Bitmap bitmap;
    private String file_name;
    private DonutProgress donutProgress;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_attachment);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mSaturationTextView = (TextView) findViewById(R.id.textViewSaturation);
        imageutils = new Imageutils(this);
        iv_attachment = (ImageView) findViewById(R.id.imageView);
        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);
            }
        });
        mSaturationSeekbar = (SeekBar) findViewById(R.id.seekBarSaturation);
        mSaturationSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE:
                    mSource = data.getData();
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(mSource));
                        mSaturationSeekbar.setProgress(256);
                        loadSaturationBitmap();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            loadSaturationBitmap();
        }
    };

    private void loadSaturationBitmap() {
        if (bitmap != null) {
            int progressSat = mSaturationSeekbar.getProgress();
            // Saturation, 0=gray-scale. 1=identity
            float saturation = (float) progressSat / 256;
            mSaturationTextView.setText("Saturation: "
                    + String.valueOf(saturation));
            iv_attachment.setImageBitmap(updateSaturation(bitmap,
                    saturation));
        }
    }

    private Bitmap updateSaturation(Bitmap src, float settingSat) {
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap bitmapResult = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvasResult = new Canvas(bitmapResult);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(settingSat);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
        canvasResult.drawBitmap(src, 0, 0, paint);

        return bitmapResult;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap = file;
        this.file_name = filename;
        iv_attachment.setImageBitmap(file);

        String path = Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file, filename, path, false);

    }

    public void recogniteDefect(View view){
        if(file_name != null) {
            donutProgress.setProgress(0);
            donutProgress.setVisibility(View.VISIBLE);
            new LoadingTask().execute();
        }
    }

    class LoadingTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            for(int i = 0; i < 100; i++){
                try {
                    Thread.sleep(10);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            donutProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            donutProgress.setVisibility(View.INVISIBLE);
            if(!file_name.isEmpty()){
                try {
                    Log.d("mylogs", file_name);

                    String path = Environment.getExternalStorageDirectory()
                            + File.separator + "Pictures"
                            + File.separator + file_name.split("\\.")[0] + "_r." + file_name.split("\\.")[1];
                    File imgFile = new File(path);

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        iv_attachment.setImageBitmap(myBitmap);

                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    Log.e("mylogs", e.getMessage());
                }

            }
        }
    }
}

