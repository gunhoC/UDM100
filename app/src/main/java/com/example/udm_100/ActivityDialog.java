package com.example.udm_100;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class ActivityDialog extends Dialog {

    static int year, month;
    static boolean button_click;

    public ActivityDialog(@NonNull Context context) {

        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // fade out window
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
//        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 1;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.dialog_date);

        // calendar
        Calendar cal = new GregorianCalendar();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;

        // year picker
        final NumberPicker pick_year = findViewById(R.id.pick_year);
        //pick_year.tint
        pick_year.setMinValue(2013);
        pick_year.setMaxValue(y);
        pick_year.setValue(y); // now year
        pick_year.setWrapSelectorWheel(false); // no circular value
        TextView text_year = findViewById(R.id.text_year);

        // month picker
        final NumberPicker pick_month = findViewById(R.id.pick_month);
        pick_month.setMinValue(1);
        pick_month.setMaxValue(12);
        pick_month.setValue(m); // now month
        TextView text_month = findViewById(R.id.text_month);

        // no button
        Button btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_click = false;
                ActivityDialog.this.dismiss();
            }
        });

        // yes button
        Button btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                year = pick_year.getValue();
                month = pick_month.getValue();
                //FragmentOperation.text_month.setText(String.format("%d년 %d월", year, month));

                button_click = true;
                ActivityDialog.this.dismiss();
            }
        });
    }
}