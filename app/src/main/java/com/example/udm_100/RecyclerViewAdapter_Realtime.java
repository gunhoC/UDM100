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
import static com.example.udm_100.MainActivity.Title;

public class RecyclerViewAdapter_Realtime extends RecyclerView.Adapter<RecyclerViewAdapter_Realtime.ViewHolder> {
    private Activity activity;
    private List<Compo_OEE> compoOEE;
    private com.github.mikephil.charting.charts.PieChart oee;

    static public String Selecthome;
    static public int Selecttype;
    private String temphome;
    private int temptype;


    public RecyclerViewAdapter_Realtime(Activity activity, List<Compo_OEE> compoOEE) {
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
            name = itemView.findViewById(R.id.tv_line);
            oee = itemView.findViewById(R.id.tv_oee);
            a = itemView.findViewById(R.id.aaa);             //TextView
            p = itemView.findViewById(R.id.ppp);            //TextView
            q = itemView.findViewById(R.id.qqq);                //TextView

            aa = itemView.findViewById(R.id.aa);                //Progress
            pp = itemView.findViewById(R.id.pp);                 //Progress
            qq = itemView.findViewById(R.id.qq);           //Progress
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_home, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        Compo_OEE data = compoOEE.get(i);

        // 데이터 결합
        viewHolder.name.setText(data.getName());
        viewHolder.aa.setProgress(data.getA());
        viewHolder.pp.setProgress(data.getP());
        viewHolder.qq.setProgress(data.getQ());
        Piechart PC=new Piechart(data.getOee(), oee);
        oee = PC.getPieChart();

        viewHolder.a.setText(data.getA() + "%");
        viewHolder.p.setText(data.getP() + "%");
        viewHolder.q.setText(data.getQ() + "%");

        Drawable progressDrawable1 = viewHolder.aa.getProgressDrawable().mutate();
        progressDrawable1.setColorFilter(Color.argb(255,165,228,169), PorterDuff.Mode.SRC_IN);
        Drawable progressDrawable2 = viewHolder.pp.getProgressDrawable().mutate();
        progressDrawable2.setColorFilter(Color.argb(255,124,236,219), PorterDuff.Mode.SRC_IN);
        Drawable progressDrawable3 = viewHolder.qq.getProgressDrawable().mutate();
        progressDrawable3.setColorFilter(Color.argb(255,204,214,117), PorterDuff.Mode.SRC_IN);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Compo_OEE data = compoOEE.get(i);

                Selecthome=data.getName();
                Selecttype=data.getType();

                Title.setText(Selecthome+" 생산효율");
                Title.setTextSize(20);
                ft=fm.beginTransaction();
                ft.replace(R.id.frame,new RealtimeClick());
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