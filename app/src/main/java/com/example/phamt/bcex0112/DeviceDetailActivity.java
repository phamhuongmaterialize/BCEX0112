package com.example.phamt.bcex0112;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by phamt on 2018/01/29.
 */

public class DeviceDetailActivity extends AppCompatActivity {

    private GridView mImageGridview = null;

    private JSONObject mDevice = null;

    private TextView mDeviceNameTextview = null;
    private TextView mQuantityTextView = null;
    private PieChart mPieChart = null;
    private Button mSettingButton = null;

    Integer[] imageArray = {
            R.drawable.pic7, R.drawable.pic8,
            R.drawable.pic9, R.drawable.pic10, R.drawable.pic11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail);

        mDeviceNameTextview = (TextView) findViewById(R.id.divice_detail_name_textview);
        mQuantityTextView = (TextView) findViewById(R.id.device_detail_quantity_textview) ;
        mPieChart = (PieChart) findViewById(R.id.device_detail_pie_chart);
        mSettingButton = (Button) findViewById(R.id.device_detail_setting);

        Intent intent = getIntent();
        int id = intent.getIntExtra("deviceID",0);
//        Log.v(DeviceDetailActivity.class.getSimpleName(),"deviceID :"+ intent.getStringExtra("deviceID"));
//        Toast.makeText(this,"deviceID :"+ intent.getStringExtra("deviceID"),Toast.LENGTH_SHORT);
        setDevice(id);

        try {

            mDeviceNameTextview.setText(mDevice.getString("name"));
            int totalImage = mDevice.getInt("total_image");
            int nowImage = mDevice.getInt("now_image");
            mQuantityTextView.setText(String.valueOf(totalImage));
            setPieChart(totalImage, nowImage);
        } catch (JSONException e){
            e.printStackTrace();
        }

        mImageGridview = (GridView) findViewById(R.id.device_detail_image_gridview);
        mImageGridview.setAdapter(new ImageAdapterGridView(this));

        mImageGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {

            }
        });

        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceSettingDialog deviceSettingDialog = DeviceSettingDialog.newInstance();
                deviceSettingDialog.show(getSupportFragmentManager(), "");
            }
        });

    }

    private void setPieChart(int totalImage, int nowImage) {
        if (totalImage <= 0) {
            return;
        }


        mPieChart.setUsePercentValues(false);
        mPieChart.setDescription(null);

        mPieChart.setDrawHoleEnabled(true);          // 中央に穴を空ける
        mPieChart.setHoleRadius(88.0f);              // 中央の穴の大きさ(%)
        mPieChart.setHoleColor(Color.TRANSPARENT);
        mPieChart.setRotationAngle(270.0f);          // 開始位置の調整
        mPieChart.setRotationEnabled(false);         // 回転可能
        mPieChart.getLegend().setEnabled(false);     // 凡例表示
        mPieChart.setTransparentCircleRadius(0.0f);  // 透過部分の割合(%)
        mPieChart.setSelected(false);
        mPieChart.setTouchEnabled(false);

        // 円グラフに表示するデータ
        float currentPercentage = 0.0f;
        if (nowImage >= totalImage) {
            currentPercentage = 100.0f;
        } else {
            currentPercentage = ((float) nowImage / (float) totalImage) * 100.0f;
        }
        List<PieEntry> entries = Arrays.asList(
                new PieEntry(currentPercentage, 0),
                new PieEntry(100.0f - currentPercentage, 1)
        );
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(Arrays.asList(
                Color.parseColor("#1a237e"),
                Color.parseColor("#E8E8E8")));
        dataSet.setDrawValues(true);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(false);

        mPieChart.setData(pieData);
        mPieChart.setVisibility(View.VISIBLE);
        mPieChart.notifyDataSetChanged();
        mPieChart.invalidate();
    }

    private void setDevice(int id) {
        try {
            switch (id) {
                case 1:
                    JSONObject js1 = new JSONObject();
                    js1.put("name", "Device1");
                    js1.put("total_image", 12);
                    js1.put("now_image", 10);
                    mDevice = js1;
                    break;
                case 2:
                    JSONObject js2 = new JSONObject();
                    js2.put("name", "Device2");
                    js2.put("total_image", 5);
                    js2.put("now_image", 1);
                    mDevice = js2;
                    break;
                case 3:
                    JSONObject js3 = new JSONObject();
                    js3.put("name", "Device3");
                    js3.put("total_image", 24);
                    js3.put("now_image", 11);
                    mDevice = js3;
                    break;
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imageArray.length - 1;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(350, 350));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
//            if(position>imageArray.length){
//                return null;
//            } else {
            mImageView.setImageResource(imageArray[position]);
            Log.v("UploadFragment", "test :" + imageArray[position]);
            return mImageView;
//            }
        }
    }
}
