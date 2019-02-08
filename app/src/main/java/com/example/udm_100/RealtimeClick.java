package com.example.udm_100;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.udm_100.MainActivity.Title;
import static com.example.udm_100.MainActivity.fm;
import static com.example.udm_100.MainActivity.ft;
import static com.example.udm_100.RecyclerViewAdapter_Realtime.Selecthome;
import static com.example.udm_100.RecyclerViewAdapter_Realtime.Selecttype;
/**
 * A simple {@link Fragment} subclass.
 */
public class RealtimeClick extends Fragment implements ServerResponse, MainActivity.onKeyBackPressedListener {

    private RecyclerView recycler;
    private LinearLayoutManager llm;
    private RecyclerViewAdapter_RealtimeClick recyclerViewAdapter;
    Bundle savedInstancestate;
    List<Compo_OEE> compoOEE = new ArrayList<>();


    public RealtimeClick() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        savedInstancestate=savedInstanceState;
        View root = inflater.inflate(R.layout.fragment_homeclick,container,false);
        recycler = root.findViewById(R.id.recycler_homeclick);
        llm = new LinearLayoutManager(getActivity());
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(),llm.getOrientation()));
        recycler.setLayoutManager(llm);
        //해당 날짜와 lineid를 서버로 넘겨주기
        if(Selecttype==0){//line
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("line",Selecthome);
            new Server().onDb("http://192.168.0.159:4000/line_homeclick",parameter,this);
        }
        if(Selecttype==1){//car
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("car",Selecthome);
            new Server().onDb("http://192.168.0.159:4000/car_homeclick",parameter, this);
        }
        return root;
    }

    public void listTime(String s) {

        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {

                String timeline = String.valueOf(jsonArray.getJSONObject(i).get("tsstart")).substring(11,16)+
                        "~"+
                        String.valueOf(jsonArray.getJSONObject(i).get("tsend")).substring(11,16);
                        compoOEE.add(new Compo_OEE(0, timeline,
                        //slineid 를 시간으로 변경! 새로운 어댑터로 넘겨주기
                        jsonArray.getJSONObject(i).getInt("a"),
                        jsonArray.getJSONObject(i).getInt("p"),
                        jsonArray.getJSONObject(i).getInt("q"),
                        jsonArray.getJSONObject(i).getInt("oee")));
            }
            recyclerViewAdapter = new RecyclerViewAdapter_RealtimeClick(getActivity(), compoOEE);
            recycler.setAdapter(recyclerViewAdapter);

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
                case "line_homeclick":
                    listTime(jObject.getString("data"));
                    break;

                case "car_homeclick":
                    listTime(jObject.getString("data"));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).setOnKeyBackPressedListener(this);
    }



    @Override
    public void onBack() {
            MainActivity activity = (MainActivity) getActivity();
            activity.setOnKeyBackPressedListener(null);
        Title.setText("오늘의 생산효율");
        ft=fm.beginTransaction();
        ft.replace(R.id.frame,new Realtime());
        ft.addToBackStack(null);
        ft.commit();
    }
}
