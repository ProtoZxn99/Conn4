package com.stts.conn4;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RVadapter extends RecyclerView.Adapter<RVadapter.RVviewholder> {

    public static ArrayList<Record> listscore = new ArrayList<>();

    private static RVclick r;

    public RVadapter (RVclick r){
        this.r = r;
    }

    @Override
    public RVviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_score,parent,false);
        return new RVviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RVviewholder holder, int position) {

        holder.name.setText(listscore.get(position).getName());
        holder.score.setText(listscore.get(position).getScore()+"");

        System.out.println(position + " - " + listscore.get(position).rank);

        /*if(listscore.get(position).rank==0){
            holder.name.setTextColor(Color.parseColor("#FFD700"));
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            holder.name.setTypeface(null, Typeface.BOLD);
            holder.score.setTextColor(Color.parseColor("#FFD700"));
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            holder.score.setTypeface(null, Typeface.BOLD);
        }
        else if(listscore.get(position).rank==1){
            holder.name.setTextColor(Color.parseColor("#C0C0C0"));
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            holder.name.setTypeface(null, Typeface.BOLD);
            holder.score.setTextColor(Color.parseColor("#C0C0C0"));
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            holder.score.setTypeface(null, Typeface.BOLD);
        }
        else if(listscore.get(position).rank==2){
            holder.name.setTextColor(Color.parseColor("#CD7F32"));
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            holder.score.setTextColor(Color.parseColor("#CD7F32"));
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        }
        else if(listscore.get(position).rank<10){
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.score.setTextColor(Color.parseColor("#FFFF00"));
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }*/

        if(position ==0){
            holder.name.setTextColor(Color.parseColor("#FFD700"));
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            holder.name.setTypeface(null, Typeface.BOLD);
            holder.score.setTextColor(Color.parseColor("#FFD700"));
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
            holder.score.setTypeface(null, Typeface.BOLD);
            System.out.println("nol");
        }
        else if(position==1){
            holder.name.setTextColor(Color.parseColor("#C0C0C0"));
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            holder.name.setTypeface(null, Typeface.BOLD);
            holder.score.setTextColor(Color.parseColor("#C0C0C0"));
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            holder.score.setTypeface(null, Typeface.BOLD);
            System.out.println("satu");
        }
        else if(position==2){
            holder.name.setTextColor(Color.parseColor("#CD7F32"));
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            holder.name.setTypeface(null, Typeface.NORMAL);
            holder.score.setTextColor(Color.parseColor("#CD7F32"));
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            holder.score.setTypeface(null, Typeface.NORMAL);
            System.out.println("dua");
        }
        else if(position<10){
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.name.setTextColor(Color.parseColor("#FFFFFF"));
            holder.name.setTypeface(null, Typeface.NORMAL);
            holder.score.setTextColor(Color.parseColor("#FFFF00"));
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            holder.score.setTypeface(null, Typeface.NORMAL);
            System.out.println("lebih kecil 10");
        } else {
            holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.name.setTextColor(Color.parseColor("#FFFFFF"));
            holder.name.setTypeface(null, Typeface.NORMAL);
            holder.score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            holder.score.setTextColor(Color.parseColor("#FFFFFF"));
            holder.score.setTypeface(null, Typeface.NORMAL);
            System.out.println("lebih BESAR = 10");
        }
    }

    @Override
    public int getItemCount() {
        return (listscore!=null)?listscore.size():0;
    }

    public class RVviewholder extends RecyclerView.ViewHolder{
        TextView name, score;
        public RVviewholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            score = itemView.findViewById(R.id.score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    r.Click(v, RVviewholder.this.getLayoutPosition());
                }
            });
        }
    }
}
