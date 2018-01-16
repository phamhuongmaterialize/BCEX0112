package com.example.phamt.bcex0112;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    private DeviceHistoryAdapter mDiviceHistoryAdapter = null;

    private MainActivity main = null;

    public static DeviceListFragment newInstance() {
        DeviceListFragment mDeviceActivity = new DeviceListFragment();
        return mDeviceActivity;
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
        mDiviceHistoryAdapter = new DeviceHistoryAdapter(getContext(), mDeviceList);
        mDeviceListView.setAdapter(mDiviceHistoryAdapter);
        justifyListViewHeightBasedOnChildren(mDeviceListView);

        mDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DeviceSettingDialog deviceSettingDialog = DeviceSettingDialog.newInstance();
                deviceSettingDialog.show(getFragmentManager(), "");
                main.setmLastclass(getClass());
            }
        });


    }

    private void setListValue(List<JSONObject> mDeviceList) {
        try {
            JSONObject js1 = new JSONObject();
            js1.put("name", "Device1");

            JSONObject js2 = new JSONObject();
            js2.put("name", "Device2");

            JSONObject js3 = new JSONObject();
            js3.put("name", "Device3");

            mDeviceList.add(js1);
            mDeviceList.add(js2);
            mDeviceList.add(js3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void justifyListViewHeightBasedOnChildren(ListView listView) {
        DeviceHistoryAdapter adapter = (DeviceHistoryAdapter) listView.getAdapter();

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

    private class DeviceHistoryAdapter extends ArrayAdapter<JSONObject> {
        private Context mContext = null;
        private LayoutInflater mLayoutInflater = null;
        private List<JSONObject> mHistory = null;

        private DeviceHistoryAdapter(Context context, List<JSONObject> item) {
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

            try {
                if ((position % 2) == 0) {
                    holder.oddLayout.setVisibility(View.GONE);
                    holder.evenLayout.setVisibility(View.VISIBLE);

                    holder.nameEven.setText(item.getString("name"));
                    holder.chartEven.setTouchEnabled(false);

                } else {
                    holder.oddLayout.setVisibility(View.VISIBLE);
                    holder.evenLayout.setVisibility(View.GONE);

                    holder.nameOdd.setText(item.getString("name"));
                    holder.chartOdd.setTouchEnabled(false);

                }

                convertView.setTag(holder);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
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
