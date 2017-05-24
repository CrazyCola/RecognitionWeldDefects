package com.example.olechka.recognitionwelddefects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olechka on 21.05.2017.
 */
public class ReportListAdapter extends BaseAdapter {


    Context context;
    List<ReportModel> reports;

    public ReportListAdapter(Context context) {
        this.context = context;
        reports = new ArrayList<ReportModel>();
        reports.add(new ReportModel("Дефект ширины шва.", "Неравномерная ширина шва,отклонение ширины от установленного значения вдоль сварного шва.", "res/drawable/width.jpg"));
        reports.add(new ReportModel("Дефект брызги металла.", "Внешние признаки: мелкие капли электродного металла, осевшие на лицевую сторону сварного соединения и плотно сцепившиеся с поверхностью основного металла. Дефект виден невооруженным глазом при визуальном контроле.", ""));
        reports.add(new ReportModel("title2", "desc2", ""));
        reports.add(new ReportModel("title2", "desc2", ""));
        reports.add(new ReportModel("title2", "desc2", ""));
        reports.add(new ReportModel("title2", "desc2", ""));
        reports.add(new ReportModel("title2", "desc2", ""));
        reports.add(new ReportModel("title2", "desc2", ""));
    }

    public ReportListAdapter(List<ReportModel> reports, Context context) {
        this.reports = reports;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reports.size();
    }

    @Override
    public Object getItem(int position) {
        return reports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.report_item, parent, false);

            TextView title = (TextView) view.findViewById(R.id.report_title);
            title.setText(reports.get(position).getTitle());

            TextView description = (TextView) view.findViewById(R.id.report_description);
            description.setText(reports.get(position).getDescription());


            if (!reports.get(position).getImage().isEmpty()) {
                ImageView image = (ImageView) view.findViewById(R.id.report_image);
                File imgFile = new File("/sdcard/Pictures/RecognitionWeldDegects/" + reports.get(position).getImage());

                if (imgFile.exists()) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    image.setImageBitmap(myBitmap);

                }
            }

        }
        return view;
    }
}
