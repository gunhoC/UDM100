package com.example.udm_100;


import android.os.Bundle;
import android.app.Fragment;
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
import java.util.List;

import static com.example.udm_100.MainActivity.Title;


/**
 * A simple {@link Fragment} subclass.
 */
public class Realtime extends Fragment implements ServerResponse  {

    private RecyclerView recycler;
    private LinearLayoutManager llm;
    private RecyclerViewAdapter_Realtime recyclerViewAdapter;

    List<Compo_OEE> compoOEE = new ArrayList<>();


    public Realtime() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home,container,false);
        recycler = root.findViewById(R.id.recycler_home);
        llm = new LinearLayoutManager(getActivity());
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(),llm.getOrientation()));
        recycler.setLayoutManager(llm);
        Title.setTextSize(35);
        new Server().onDb("http://192.168.0.159:4000/line",null,  this);
        //new Server().onDb("http://192.168.0.159:4000/car",null,  this);

        // Inflate the layout for this fragment
        return root;

    }
    public void listLine(String s) {

        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++)
                compoOEE.add(new Compo_OEE(0,String.valueOf(jsonArray.getJSONObject(i).get("slineid")),
                        jsonArray.getJSONObject(i).getInt("a"),
                        jsonArray.getJSONObject(i).getInt("p"),
                        jsonArray.getJSONObject(i).getInt("q"),
                        jsonArray.getJSONObject(i).getInt("oee")));
            recyclerViewAdapter = new RecyclerViewAdapter_Realtime(getActivity(), compoOEE);
            recycler.setAdapter(recyclerViewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void listCar(String s) {

        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++)
                compoOEE.add(new Compo_OEE(1,String.valueOf(jsonArray.getJSONObject(i).get("scartype")),
                        jsonArray.getJSONObject(i).getInt("a"),
                        jsonArray.getJSONObject(i).getInt("p"),
                        jsonArray.getJSONObject(i).getInt("q"),
                        jsonArray.getJSONObject(i).getInt("oee")));
            recyclerViewAdapter = new RecyclerViewAdapter_Realtime(getActivity(), compoOEE);
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
                case "line":
                    listLine(jObject.getString("data"));
                    break;
                case "car":
                    listCar(jObject.getString("data"));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
