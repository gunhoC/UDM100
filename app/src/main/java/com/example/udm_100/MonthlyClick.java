package com.example.udm_100;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewLineChartView;

import static com.example.udm_100.RecyclerViewAdapter_Monthly.SelectLine_3;

public class MonthlyClick extends Fragment implements ServerResponse {
    //    private RecyclerView recycler;
//    private LinearLayoutManager llm;
//    private RecyclerViewAdapter_4 recyclerViewAdapter;
    private Spinner spinner;
    private RecyclerView recycler;
    private LinearLayoutManager llm;
    private MonthlyClick thiss;
    static Context context;
    private LineChartView chart;
    private PreviewLineChartView previewlinechart;


    public MonthlyClick() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_line_click, container, false);
        llm = new LinearLayoutManager(getActivity());//
        thiss=this;
        context = getActivity();
        spinner = root.findViewById(R.id.spinner);
        recycler = root.findViewById(R.id.frag_production_rv_datelist);
        chart = root.findViewById(R.id.chart);
        previewlinechart = root.findViewById(R.id.chart_preview);

        llm = new LinearLayoutManager(getActivity());
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), llm.getOrientation()));
        recycler.setLayoutManager(llm);
        final HashMap<String, String> parameter = new HashMap<>();
        parameter.put("line_name", SelectLine_3);
        parameter.put("line_date", Monthly.lineDate);

        new Server().onDb("http://192.168.0.159:4000/sp_car_type", parameter, this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {                  //spinner 선택 시

                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);   //spinner 글자 색 변경 !
                HashMap<String, String> parameter1 = new HashMap<>();
                parameter1.put("month", Monthly.lineDate);
                parameter1.put("chart_line", SelectLine_3);
                parameter1.put("chart_car", String.valueOf(spinner.getSelectedItem()));

                new Server().onDb("http://192.168.0.159:4000/date_chart",parameter1,thiss);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return root;
    }


    private void spinnerSet(String s) {

        try {
            JSONArray jsonArray = new JSONArray(s);

            String[] carType = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++)
                carType[i] = String.valueOf(jsonArray.getJSONObject(i).get("scartype"));

            ArrayList<String> sp_item = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
                sp_item.add(carType[i]);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, sp_item);

            spinner.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void graphSet(String s) {

        try {
            JSONArray jsonArray = new JSONArray(s);


            ArrayList<Integer> chartdate = new ArrayList<>();
            ArrayList<Integer> chartoee = new ArrayList<>();

            int[] date = new int[jsonArray.length()];
            int[] oee = new int[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                date[i] = jsonArray.getJSONObject(i).getInt("date");
                oee[i] = jsonArray.getJSONObject(i).getInt("oee");
            }

            for(int i =0 ; i<date.length; i++){
                chartdate.add(date[i]);
                chartoee.add(oee[i]);
            }

            Linechart Lc = new Linechart(chart, previewlinechart,chartdate,chartoee);
            chart = Lc.getlinechartview();
            previewlinechart = Lc.getPreviewlinechart();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void processFinish(String output) {
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(output);
            String code = jObject.getString("code");

            switch (code) {
                case "sp_car_type":
                    spinnerSet(jObject.getString("data"));
                    break;
                case "date_chart":
                    graphSet(jObject.getString("data"));
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
