package com.example.udm_100;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter_RealtimeClick extends RecyclerView.Adapter<RecyclerViewAdapter_RealtimeClick.ViewHolder> {
    private Activity activity;
    private List<Compo_OEE> compoOEE;
    private com.github.mikephil.charting.charts.PieChart oee;



    public RecyclerViewAdapter_RealtimeClick(Activity activity, List<Compo_OEE> compoOEE) {
        this.activity = activity;
        this.compoOEE = compoOEE;
    }

    @Override
    public int getItemCount() {
        return compoOEE.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView a;
        TextView p;
        TextView q;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_time);
            oee = itemView.findViewById(R.id.tv_click_oee);
            a = itemView.findViewById(R.id.tv_Aa);
            p = itemView.findViewById(R.id.tv_Pp);
            q = itemView.findViewById(R.id.tv_Qq);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_homeclick, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        Compo_OEE data = compoOEE.get(i);

       //  데이터 결합
        viewHolder.time.setText(data.getName());


        Piechart PCo = new Piechart(data.getOee(), oee);

        viewHolder.a.setText(data.getA() + "%");
        viewHolder.p.setText(data.getP() + "%");
        viewHolder.q.setText(data.getQ() + "%");

        oee = PCo.getPieChart();


    }


    private void removeItemView(int position) {
        compoOEE.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, compoOEE.size()); // 지워진 만큼 다시 채워넣기.
    }

}