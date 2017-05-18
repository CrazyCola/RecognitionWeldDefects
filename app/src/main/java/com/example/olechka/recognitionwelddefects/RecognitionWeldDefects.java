package com.example.olechka.recognitionwelddefects;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class RecognitionWeldDefects extends AppCompatActivity {

    final int RQS_IMAGE = 1;

    private Button mLoadImageButton;
    private ImageView mGaleryImageView;
    private SeekBar mSaturationSeekbar;
    private TextView mSaturationTextView;

    private Uri mSource;
    private Bitmap mBitmap;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_photo_filter);


        mLoadImageButton = (Button) findViewById(R.id.buttonLoadimage);
        mSaturationTextView = (TextView) findViewById(R.id.textViewSaturation);
        mGaleryImageView = (ImageView) findViewById(R.id.imageView);

        mLoadImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE);
            }
        });


        mSaturationSeekbar = (SeekBar) findViewById(R.id.seekBarSaturation);
        mSaturationSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE:
                    mSource = data.getData();

                    try {
                        mBitmap = BitmapFactory.decodeStream(getContentResolver()
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
        if (mBitmap != null) {
            int progressSat = mSaturationSeekbar.getProgress();
            // Saturation, 0=gray-scale. 1=identity
            float saturation = (float) progressSat / 256;
            mSaturationTextView.setText("Saturation: "
                    + String.valueOf(saturation));
            mGaleryImageView.setImageBitmap(updateSaturation(mBitmap,
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
}
