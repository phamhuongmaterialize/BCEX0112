package com.example.phamt.bcex0112;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by phamt on 2018/01/12.
 */

public class DeviceListFragment extends Fragment {

    /**
     * Root View
     */
    private View mRootView = null;

    private ImageButton mAddButton = null;
    private ListView mDeviceListView = null;

    private List<JSONObject> mDeviceList = null;
    private DeviceAdapter mDiviceHistoryAdapter = null;

    private MainActivity main = null;
    private BluetoothService mBluetoothService = null;

    public static DeviceListFragment newInstance() {
        DeviceListFragment mDeviceListFragment = new DeviceListFragment();
        return mDeviceListFragment;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRootView = inflater.inflate(R.layout.device_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        mAddButton = (ImageButton) mRootView.findViewById(R.id.device_add_float_button);
        mDeviceListView = (ListView) mRootView.findViewById(R.id.device_listview);

        main = (MainActivity) getActivity();

        mDeviceList = new ArrayList<>();
        setListValue(mDeviceList);
        mDiviceHistoryAdapter = new DeviceAdapter(getContext(), mDeviceList);
        mDeviceListView.setAdapter(mDiviceHistoryAdapter);
        justifyListViewHeightBasedOnChildren(mDeviceListView);

        mDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                DeviceSettingDialog deviceSettingDialog = DeviceSettingDialog.newInstance();
//                deviceSettingDialog.show(getFragmentManager(), "");
//                main.setmLastclass(getClass());
                Intent intent = new Intent(getActivity(),DeviceDetailActivity.class);
                intent.putExtra("deviceID",i);
                Log.v(DeviceListFragment.class.getSimpleName(),"deviceID :"+ (i+1));
                startActivity(intent);
            }
        });

        mBluetoothService = new BluetoothService(main, null);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBluetoothService.checkBluetoothStatus()) {
                    main.setViewTab(MainActivity.TabFragment.DEVICE_UPLOAD);
                } else {
                    mBluetoothService.bluetoothON();
                }
            }
        });


    }

    private void setListValue(List<JSONObject> mDeviceList) {
        try {
            JSONObject js1 = new JSONObject();
            js1.put("name", "Device1");
            js1.put("total_image", 12);
            js1.put("now_image", 10);

            JSONObject js2 = new JSONObject();
            js2.put("name", "Device2");
            js2.put("total_image", 5);
            js2.put("now_image", 1);

            JSONObject js3 = new JSONObject();
            js3.put("name", "Device3");
            js3.put("total_image", 24);
            js3.put("now_image", 11);

            mDeviceList.add(js1);
            mDeviceList.add(js2);
            mDeviceList.add(js3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void justifyListViewHeightBasedOnChildren(ListView listView) {
        DeviceAdapter adapter = (DeviceAdapter) listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 70;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    private class DeviceAdapter extends ArrayAdapter<JSONObject> {
        private Context mContext = null;
        private LayoutInflater mLayoutInflater = null;
        private List<JSONObject> mHistory = null;

        private DeviceAdapter(Context context, List<JSONObject> item) {
            super(context, 0, item);
            mContext = context;
            mHistory = item;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mHistory.size();
        }

        @Override
        public JSONObject getItem(int position) {
            return mHistory.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            JSONObject item = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.device_data, parent, false);
                holder = new ViewHolder();

                holder.oddLayout = (LinearLayout) convertView.findViewById(R.id.device_data_odd_layout);
                holder.evenLayout = (LinearLayout) convertView.findViewById(R.id.device_data_even_layout);
                holder.nameOdd = (TextView) convertView.findViewById(R.id.divice_data_odd_name_textview);
                holder.nameEven = (TextView) convertView.findViewById(R.id.divice_data_even_name_textview);
                holder.textOdd = (TextView) convertView.findViewById(R.id.divice_data_odd_text_textview);
                holder.textEven = (TextView) convertView.findViewById(R.id.divice_data_even_text_textview);
                holder.quantityOdd = (TextView) convertView.findViewById(R.id.device_data_odd_quantity_textview);
                holder.quantityEven = (TextView) convertView.findViewById(R.id.device_data_even_quantity_textview);
                holder.chartOdd = (PieChart) convertView.findViewById(R.id.device_data_odd_pie_chart);
                holder.chartEven = (PieChart) convertView.findViewById(R.id.device_data_even_pie_chart);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (item == null) {
                return convertView;
            }

            int totalImage = 0;
            int nowImage = 0;

            try {
                totalImage = item.getInt("total_image");
                nowImage = item.getInt("now_image");
                if ((position % 2) == 0) {
                    holder.oddLayout.setVisibility(View.GONE);
                    holder.evenLayout.setVisibility(View.VISIBLE);

                    holder.nameEven.setText(item.getString("name"));
                    holder.chartEven.setTouchEnabled(false);
                    holder.quantityEven.setText(String.valueOf(totalImage));
                    setPieChart(holder.chartEven, totalImage, nowImage);

                } else {
                    holder.oddLayout.setVisibility(View.VISIBLE);
                    holder.evenLayout.setVisibility(View.GONE);

                    holder.nameOdd.setText(item.getString("name"));
                    holder.chartOdd.setTouchEnabled(false);
                    holder.quantityOdd.setText(String.valueOf(totalImage));
                    setPieChart(holder.chartOdd, totalImage, nowImage);

                }

                convertView.setTag(holder);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
        }

        private void setPieChart(PieChart pieChart, int totalImage, int nowImage) {
            if (totalImage <= 0) {
                return;
            }


            pieChart.setUsePercentValues(false);
            pieChart.setDescription(null);

            pieChart.setDrawHoleEnabled(true);          // 中央に穴を空ける
            pieChart.setHoleRadius(88.0f);              // 中央の穴の大きさ(%)
            pieChart.setHoleColor(Color.TRANSPARENT);
            pieChart.setRotationAngle(270.0f);          // 開始位置の調整
            pieChart.setRotationEnabled(false);         // 回転可能
            pieChart.getLegend().setEnabled(false);     // 凡例表示
            pieChart.setTransparentCircleRadius(0.0f);  // 透過部分の割合(%)
            pieChart.setSelected(false);
            pieChart.setTouchEnabled(false);

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

            pieChart.setData(pieData);
            pieChart.setVisibility(View.VISIBLE);
            pieChart.notifyDataSetChanged();
            pieChart.invalidate();

        }

        private class ViewHolder {
            LinearLayout oddLayout;
            LinearLayout evenLayout;
            TextView nameOdd;
            TextView nameEven;
            TextView textOdd;
            TextView textEven;
            TextView quantityOdd;
            TextView quantityEven;
            PieChart chartOdd;
            PieChart chartEven;
        }
    }
}
