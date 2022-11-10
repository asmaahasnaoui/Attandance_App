package com.example.youtubepresence;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    ArrayList<StudentItem>studentItems;
    Context context;
    private onItemClickListener onItemClickListener;
    public interface onItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(StudentAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public StudentAdapter(Context context, ArrayList<StudentItem> studentItems) {
        this.context=context;
        this.studentItems = studentItems;

    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);

        return new StudentViewHolder(itemView,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.specialitéAnn.setText(studentItems.get(position).getSpecialité_anné()+"");
        holder.name.setText(studentItems.get(position).getName());
        holder.n_ligne.setText("||" +studentItems.get(position).getN_ligne());

        holder.status.setText(studentItems.get(position).getStatus());
        holder.status.setVisibility(View.GONE);
        holder.presentCount.setText(studentItems.get(position).getPresent()+"");
        holder.absentCount.setText(studentItems.get(position).getAbsent()+"");

        //holder.cardView.setBackgroundColor(getColor(position));
        holder.cardView.setCardBackgroundColor(getColor(position));
        //holder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));



    }

    private int getColor(int position) {
        String status=studentItems.get(position).getStatus();
        if (status.equals("P"))
            return Color.parseColor('#'+Integer.toHexString(ContextCompat.getColor(context,R.color.green)));
        else if(status.equals("A"))
            return Color.parseColor('#'+Integer.toHexString(ContextCompat.getColor(context,R.color.red)));
        return Color.parseColor('#'+Integer.toHexString(ContextCompat.getColor(context,R.color.white)));


    }

    @Override

    public int getItemCount() {
        return studentItems.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView specialitéAnn;
        TextView name;
        TextView n_ligne;

        TextView status;
        CardView cardView;
        TextView presentCount;
        TextView absentCount;


        public StudentViewHolder(@NonNull View itemView,onItemClickListener onItemClickListener) {
            super(itemView);
            specialitéAnn =itemView.findViewById(R.id.specialité_anné);
            name=itemView.findViewById(R.id.name);
            n_ligne =itemView.findViewById(R.id.n_ligne);

            status=itemView.findViewById(R.id.status);
            presentCount =itemView.findViewById(R.id.presentCount);
            absentCount=itemView.findViewById(R.id.apsentCount);
            cardView=itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(v->onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(),0,0,"Modifier");
            contextMenu.add(getAdapterPosition(),1,0,"Supprimer");



        }

    }

}