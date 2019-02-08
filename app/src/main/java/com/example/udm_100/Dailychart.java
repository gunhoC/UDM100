package com.example.udm_100;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.udm_100.MainActivity.mainActivity;


public class Dailychart extends AppCompatActivity implements ServerResponse{

    private RecyclerView recycler;
    private LinearLayoutManager llm;
    private RecyclerViewAdapter_MonthlyClick recyclerViewAdapter;
    private HashMap<String, String> parameter = new HashMap<>();

    List<Compo_SPE> compoSPE = new ArrayList<>();

    public Dailychart(HashMap<String, String> parameter){
        this.parameter=parameter;
        recycler = findViewById(R.id.frag_production_rv_datelist);
        llm = new LinearLayoutManager(this);
        recycler.addItemDecoration(new DividerItemDecoration(this,llm.getOrientation()));
        recycler.setLayoutManager(llm);

        new Server().onDb("http://192.168.0.159:4000/Monthlyclick",parameter, this);

    }
    public void listSPE(String s) {

        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                //SPE(String start, String end, int target, int total, int ok, int ng)
                compoSPE.add(new Compo_SPE(String.valueOf(jsonArray.getJSONObject(i).get("tsstart")).substring(11,16),
                        String.valueOf(jsonArray.getJSONObject(i).get("tsend")).substring(11,16),
                        jsonArray.getJSONObject(i).getInt("target"),
                        jsonArray.getJSONObject(i).getInt("total"),
                        jsonArray.getJSONObject(i).getInt("ok"),
                        jsonArray.getJSONObject(i).getInt("ng")));
            }

            recyclerViewAdapter = new RecyclerViewAdapter_MonthlyClick(this, compoSPE);
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
                case "line_Monthlyclick":
                    listSPE(jObject.getString("data"));
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
