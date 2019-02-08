package com.example.udm_100;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.udm_100.ActivityDialog.button_click;

/**
 *
 * A simple {@link Fragment} subclass.
 */
public class Monthly extends Fragment implements ServerResponse {
    private RecyclerView recyle_month;
    private Button btn_date;
    private LinearLayoutManager llm;
    private RecyclerViewAdapter_Monthly recyclerViewAdapter_Monthly;
    Calendar now = Calendar.getInstance();
    private int currentYear = now.get(Calendar.YEAR);
    private int currentMonth = now.get(Calendar.MONTH) + 1;
    private ImageButton btn_left;
    private ImageButton btn_right;
    public static String lineDate;


    public Monthly() {
        // Required empty public constructor
    }

    View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_line_chart, container, false);
        btn_date = root.findViewById(R.id.btn_date);
        recyle_month = root.findViewById(R.id.recyle_month);
        btn_left = root.findViewById(R.id.month_minus);
        btn_right = root.findViewById(R.id.month_plus);
        //   btn_date.setText(String.format("%d년 %d월", ActivityDialog.year,
        //   ActivityDialog.month)); // keep selected month
        //   connLineConfig(ActivityDialog.year, ActivityDialog.month);

        llm = new LinearLayoutManager(getActivity());

        recyle_month.addItemDecoration(new DividerItemDecoration(getActivity(), llm.getOrientation()));

        recyle_month.setLayoutManager(llm);
// 초기세팅 !!!!!
        btn_date.setText(String.format("%d년 %d월", currentYear,
                currentMonth));
        ActivityDialog.year = currentYear;
        ActivityDialog.month = currentMonth;
        connLineConfig(currentYear, currentMonth);  //초기날짜
//        recyle_month.removeAllViewsInLayout();
        btn_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("asa", "asa");
                ActivityDialog pick = new ActivityDialog(getActivity());
                pick.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pick.getWindow().setGravity(Gravity.CENTER);
                pick.show();

                pick.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (button_click) {
                            recyle_month.removeAllViewsInLayout();
                            connLineConfig(ActivityDialog.year, ActivityDialog.month);
                            btn_date.setText(String.format("%d년 %d월", ActivityDialog.year,
                                    ActivityDialog.month));
                        }
                    }
                });
            }
        });


        btn_left.setOnClickListener(new View.OnClickListener() {   //month minus
            @Override
            public void onClick(View v) {
                if (ActivityDialog.month == 1) {
                    if (ActivityDialog.year != 2013)
                        ActivityDialog.year--;

                    ActivityDialog.month = 12;
                } else if (ActivityDialog.month > 0 && (ActivityDialog.year != 2013 && ActivityDialog.month != 1))
                    ActivityDialog.month--;

                connLineConfig(ActivityDialog.year, ActivityDialog.month);
                btn_date.setText(String.format("%d년 %d월", ActivityDialog.year,
                        ActivityDialog.month));

            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityDialog.month == 12 && ActivityDialog.year < currentYear) {
                    ActivityDialog.year++;
                    ActivityDialog.month = 1;
                } else if (ActivityDialog.year <= currentYear && ActivityDialog.month < 12)
                    ActivityDialog.month++;

                connLineConfig(ActivityDialog.year, ActivityDialog.month);
                btn_date.setText(String.format("%d년 %d월", ActivityDialog.year,
                        ActivityDialog.month));

            }
        });

        return root;
    }


    private void dateSet(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            List<Compo_OEE> compoOEE = new ArrayList<>();

            if (String.valueOf(jsonArray.getJSONObject(0).get("slineid")) != null) {
                for (int i = 0; i < jsonArray.length(); i++)
                    compoOEE.add(new Compo_OEE(0, String.valueOf(jsonArray.getJSONObject(i).get("slineid")),
                            jsonArray.getJSONObject(i).getInt("a"),
                            jsonArray.getJSONObject(i).getInt("p"),
                            jsonArray.getJSONObject(i).getInt("q"),
                            jsonArray.getJSONObject(i).getInt("oee")));
                recyclerViewAdapter_Monthly = new RecyclerViewAdapter_Monthly(getActivity(), compoOEE);
                recyle_month.setAdapter(recyclerViewAdapter_Monthly);
            } else
                recyle_month.removeAllViewsInLayout();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void connLineConfig(int nYear, int nMonth) {
        recyle_month.removeAllViewsInLayout();
        HashMap<String, String> parameter = new HashMap<>();
        parameter.put("date", formatMonth(nYear, nMonth));
        lineDate = formatMonth(nYear,nMonth);
        new Server().onDb("http://192.168.0.159:4000/date", parameter, this);
    }


    private String formatMonth(int y, int m) {

        if (m < 10) {
            String result = y + "-" + "0" + m;
            return result;
        } else {
            String result = y + "-" + m;
            return result;
        }

    }

    @Override
    public void processFinish(String output) {
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(output);
            String code = jObject.getString("code");

            switch (code) {
                case "date":
                    dateSet(jObject.getString("data"));
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
