package com.example.youtubepresence;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class classAdapter extends RecyclerView.Adapter<classAdapter.ClassViewHolder> {
    ArrayList<ClassItem>classItems=new ArrayList<>();
    Context context;
    private onItemClickListener onItemClickListener;
    public interface onItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(classAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public classAdapter(Context context, ArrayList<ClassItem> classItems) {
        this.context=context;
        this.classItems = classItems;

    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,parent,false);

        return new ClassViewHolder(itemView,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.className.setText(classItems.get(position).getClassName());

        holder.CountStudent.setText(classItems.get(position).getNumberStudent());



    }

    @Override
    public int getItemCount() {
        return classItems.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView className;
        TextView classSubject;
        TextView CountStudent;

        public ClassViewHolder(@NonNull View itemView,onItemClickListener onItemClickListener) {
            super(itemView);
            className=itemView.findViewById(R.id.class_tv);
            //classSubject=itemView.findViewById(R.id.subject_tv);
            CountStudent=itemView.findViewById(R.id.countStudent);


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