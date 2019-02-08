package com.example.udm_100;
import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import static com.example.udm_100.MainActivity.blueline;
import static com.example.udm_100.MainActivity.redline;

public class Piechart {
    private int value;
    private com.github.mikephil.charting.charts.PieChart piechart;

    public Piechart(int value, com.github.mikephil.charting.charts.PieChart pieChart){
        this.value = value;
        this.piechart = pieChart;

    }


    public com.github.mikephil.charting.charts.PieChart getPieChart() {

        ArrayList<PieEntry> entries = new ArrayList<>();


        piechart.setDrawCenterText(true);
        piechart.setCenterTextSize(13f);
        piechart.setTransparentCircleColor(Color.TRANSPARENT);
        piechart.setBackgroundColor(Color.TRANSPARENT);
        piechart.setCenterText(value+"%");

        piechart.getDescription().setEnabled(false);
        piechart.getLegend().setEnabled(false);

        entries.add(new PieEntry(value,0));
        entries.add(new PieEntry(100-value,1));

        PieDataSet piedataset = new PieDataSet(entries,"");
        piedataset.setDrawIcons(false);
        ArrayList<Integer> colors = new ArrayList<>();
        if(value>=blueline) {
            colors.add(Color.argb(255, 54, 94, 205));
            piechart.setCenterTextColor(Color.argb(255, 54, 94, 205));
        }
        else if(value>=redline) {
            colors.add(Color.argb(255, 49, 205, 76));
            piechart.setCenterTextColor(Color.argb(255, 49, 205, 76));
        }
        else {
            colors.add(Color.argb(255, 205, 36, 71));
            piechart.setCenterTextColor(Color.argb(255, 205, 36, 71));
        }
        colors.add(Color.argb(255,72,72,81));
        piedataset.setColors(colors);
        PieData data = new PieData(piedataset);

        data.setValueTextSize(0f);
        piechart.setTransparentCircleRadius(75f);
        piechart.setHoleRadius(75f);
        piechart.setEntryLabelColor(Color.TRANSPARENT);
        piechart.setHoleColor(Color.TRANSPARENT);
        data.setValueTextColor(Color.TRANSPARENT);
        piechart.setCenterTextSize(16f);
        piechart.setTransparentCircleColor(Color.TRANSPARENT);
        piechart.setData(data);

        return piechart;
    }
}

