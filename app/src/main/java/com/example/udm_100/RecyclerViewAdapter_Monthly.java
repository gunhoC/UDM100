package com.example.udm_100;

import android.app.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; //
import android.widget.ProgressBar;
import android.widget.TextView;////


import java.util.List;

import static com.example.udm_100.MainActivity.fm;
import static com.example.udm_100.MainActivity.ft;

public class RecyclerViewAdapter_Monthly extends RecyclerView.Adapter<RecyclerViewAdapter_Monthly.ViewHolder> {
    private Activity activity;
    private List<Compo_OEE> compoOEE;
    private com.github.mikephil.charting.charts.PieChart oee;
    static public String SelectLine_3;


    private String tempLine;


    public RecyclerViewAdapter_Monthly(Activity activity, List<Compo_OEE> compoOEE) {
        this.activity = activity;
        this.compoOEE = compoOEE;


    }

    @Override
    public int getItemCount() {
        return compoOEE.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView a;
        TextView p;
        TextView q;
        ProgressBar aa;
        ProgressBar pp;
        ProgressBar qq;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_line_c);
            oee = itemView.findViewById(R.id.tv_oee_c);
            a = itemView.findViewById(R.id.aaa_c);             //TextView
            p = itemView.findViewById(R.id.ppp_c);            //TextView
            q = itemView.findViewById(R.id.qqq_c);                //TextView

            aa = itemView.findViewById(R.id.aa_c);                //Progress
            pp = itemView.findViewById(R.id.pp_c);                 //Progress
            qq = itemView.findViewById(R.id.qq_c);           //Progress
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_chart, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Compo_OEE data = compoOEE.get(i);

        // 데이터 결합
        viewHolder.name.setText(data.getName());
        viewHolder.aa.setProgress(data.getA());
        viewHolder.pp.setProgress(data.getP());
        viewHolder.qq.setProgress(data.getQ());
        Piechart PC = new Piechart(data.getOee(), oee);
        oee = PC.getPieChart();

        Drawable progressDrawable1 = viewHolder.aa.getProgressDrawable().mutate();
        progressDrawable1.setColorFilter(Color.argb(255, 103, 213, 181), PorterDuff.Mode.SRC_IN);
        Drawable progressDrawable2 = viewHolder.pp.getProgressDrawable().mutate();
        progressDrawable2.setColorFilter(Color.argb(255, 58, 201, 185), PorterDuff.Mode.SRC_IN);
        Drawable progressDrawable3 = viewHolder.qq.getProgressDrawable().mutate();
        progressDrawable3.setColorFilter(Color.argb(255, 51, 157, 158), PorterDuff.Mode.SRC_IN);


        viewHolder.a.setText(data.getA() + "%");
        viewHolder.p.setText(data.getP() + "%");
        viewHolder.q.setText(data.getQ() + "%");


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compo_OEE data = compoOEE.get(i);
                SelectLine_3 = data.getName();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, new MonthlyClick());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }


    private void removeItemView(int position) {
        compoOEE.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, compoOEE.size()); // 지워진 만큼 다시 채워넣기.
    }

}