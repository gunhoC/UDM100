package com.example.udm_100;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewLineChartView;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.udm_100.MonthlyClick.car;
import static com.example.udm_100.RecyclerViewAdapter_Monthly.SelectLine_3;


public class Linechart implements ServerResponse{
    public PreviewLineChartView previewChart;
    private ArrayList<Integer> oee;
    private ArrayList<Integer> date;

    Linechart thiss;

    public LineChartView chart;
    private LineChartData data;
    private LineChartData previewdata;

    public Linechart(LineChartView chart,PreviewLineChartView previewChart,
            ArrayList<Integer> date,ArrayList<Integer> oee
    ){
        this.chart=chart;
        this.previewChart=previewChart;
        this.date = date;
        this.oee = oee;

        chart.setBackgroundColor(Color.argb(255,60,60,72));
        previewChart.setBackgroundColor(Color.argb(255,60,60,72));

        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < date.size(); ++i) {
            if(oee.get(i)>100){
                oee.set(i,100);
            }
            values.add(new PointValue(date.get(i),oee.get(i)));
        }

        Line line = new Line(values);
        line.setColor(Color.argb(255,165,228,169));
        line.setHasPoints(true);// too many values so don't draw points.

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        Line line1=new Line(values);
        line1.setHasPoints(false);
        List<Line> lines1 = new ArrayList<Line>();
        lines1.add(line1);
        LineChartData data1 = new LineChartData(lines1);

        data1.setAxisXBottom(new Axis());
        data1.setAxisYLeft(new Axis().setHasLines(true));
        data = new LineChartData(lines);
        data.setAxisXBottom(new Axis());
        data.setAxisYLeft(new Axis().setHasLines(true));

        // prepare preview data, is better to use separate deep copy for preview chart.
        // Set color to grey to make preview area more visible.

        chart.setLineChartData(data);
        chart.setZoomEnabled(true);
        chart.setScrollEnabled(true);
        chart.setOnValueTouchListener(new ValueTouchListener());


        previewdata = new LineChartData(data1);
        previewdata.getLines().get(0).setColor(Color.argb(255,124,236,219));

        previewChart.setLineChartData(previewdata);
        previewChart.setViewportChangeListener(new ViewportListener());
        previewX(true);

    }

    public PreviewLineChartView getPreviewlinechart() {

        return previewChart;
    }

    public LineChartView getlinechartview(){
        return chart;
    }



    private class ViewportListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport newViewport) {
            // don't use animation, it is unnecessary when using preview chart.
            chart.setCurrentViewport(newViewport);
        }

    }

    private void previewX(boolean animate) {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dx = tempViewport.width() / 4;
        tempViewport.inset(dx, 0);
        if (animate) {
            previewChart.setCurrentViewportWithAnimation(tempViewport);
        } else {
            previewChart.setCurrentViewport(tempViewport);
        }
        previewChart.setZoomType(ZoomType.HORIZONTAL);
    }
    private void previewY() {
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        float dy = tempViewport.height() / 4;
        tempViewport.inset(0, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
        previewChart.setZoomType(ZoomType.VERTICAL);
    }


    private void previewXY() {
        // Better to not modify viewport of any chart directly so create a copy.
        Viewport tempViewport = new Viewport(chart.getMaximumViewport());
        // Make temp viewport smaller.
        float dx = tempViewport.width() / 4;
        float dy = tempViewport.height() / 4;
        tempViewport.inset(dx, dy);
        previewChart.setCurrentViewportWithAnimation(tempViewport);
    }
    public class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            int n = (int) value.getX();
//            Toast.makeText(MonthlyClick.context, n + "일", Toast.LENGTH_SHORT).show();

            final HashMap<String, String> parameter = new HashMap<>();
            parameter.put("line_month_name", SelectLine_3);
          //  parameter.put("line_date", Monthly.lineDate);
//            NumberFormat numberFormat = NumberFormat.getIntegerInstance();
//            numberFormat.setMinimumIntegerDigits(2);
         //   parameter.put("line_day",numberFormat.format(n));
            String lineMonthDate = Monthly.lineDate +"-";
            if(n<10)
                lineMonthDate=lineMonthDate+"0"+n;
            else
                lineMonthDate=lineMonthDate+n;
            parameter.put("line_month_date",lineMonthDate);
            parameter.put("line_month_car",car);

            Toast.makeText(MonthlyClick.context, lineMonthDate, Toast.LENGTH_SHORT).show();
            new Dailychart(parameter);
        }
    }

    @Override
    public void processFinish(String output) {

    }

}


