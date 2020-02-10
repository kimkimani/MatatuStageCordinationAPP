package com.example.matatustageapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matatustageapp.Model.StageCordinator;
import com.example.matatustageapp.R;

import java.util.ArrayList;

public class StageCordinatorAdapter  extends RecyclerView.Adapter<StageCordinatorAdapter.MyViewHolder> {

    Context context;
    ArrayList<StageCordinator> profiles;

    public StageCordinatorAdapter(Context c , ArrayList<StageCordinator> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.single_note_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.content.setText(profiles.get(position).getTitle());
        holder.title.setText(profiles.get(position).getContent());


    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView content,title;
        ImageView profilePic;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
           // content = (TextView) itemView.findViewById(R.id.note_content);
            title = (TextView) itemView.findViewById(R.id.note_title);

        }
        public void onClick(final int position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}