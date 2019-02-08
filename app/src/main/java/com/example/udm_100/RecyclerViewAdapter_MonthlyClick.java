package com.example.udm_100;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter_MonthlyClick extends RecyclerView.Adapter<RecyclerViewAdapter_MonthlyClick.ViewHolder> {
    private Activity activity;
    private List<Compo_SPE> Compo_SPES;

    public RecyclerViewAdapter_MonthlyClick(Activity activity, List<Compo_SPE> Compo_SPES) {
        this.activity = activity;
        this.Compo_SPES = Compo_SPES;
    }

    @Override
    public int getItemCount() {
        return Compo_SPES.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView start;
        TextView end;
        TextView target;
        TextView oper;
        TextView ok;
        TextView ng;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.item_production_start);
            end = itemView.findViewById(R.id.item_production_end);
            target = itemView.findViewById(R.id.item_production_target);
            oper = itemView.findViewById(R.id.item_production_total);
            ok = itemView.findViewById(R.id.item_production_ok);
            ng = itemView.findViewById(R.id.item_production_ng);
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter_MonthlyClick.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.daily_recycle, viewGroup, false);
        RecyclerViewAdapter_MonthlyClick.ViewHolder viewHolder = new RecyclerViewAdapter_MonthlyClick.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_MonthlyClick.ViewHolder viewHolder, final int i) {
        Compo_SPE data = Compo_SPES.get(i);

        // 데이터 결합
        viewHolder.start.setText(data.getStart());
        viewHolder.end.setText(data.getEnd());
        viewHolder.target.setText(data.getTarget());
        viewHolder.oper.setText(data.getTotal());
        viewHolder.ok.setText(data.getOk());
        viewHolder.ng.setText(data.getNg());

    }


    private void removeItemView(int position) {
        Compo_SPES.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, Compo_SPES.size()); // 지워진 만큼 다시 채워넣기.
    }

}
