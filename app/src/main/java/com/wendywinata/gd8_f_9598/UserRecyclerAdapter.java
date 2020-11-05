package com.wendywinata.gd8_f_9598;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.RoomViewHolder> implements Filterable {
    private List<UserDAO> dataList;
    private List<UserDAO> filteredDataList;
    private Context context;

    public UserRecyclerAdapter(List<UserDAO> dataList, Context context) {
        this.dataList = dataList;
        this.filteredDataList = dataList;
        this.context = context;
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_adapter_user,parent,false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        final UserDAO brg = filteredDataList.get(position);
        holder.tvNama.setText(brg.getNama());
        holder.tvNim.setText(brg.getNim());

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                DetailsUserFragment dialog = new DetailsUserFragment();
                dialog.show(manager,"dialog");

                Bundle args = new Bundle();
                args.putString("id", brg.getId());
                dialog.setArguments(args);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                if(charSequenceString.isEmpty()){
                    filteredDataList = dataList;
                } else{
                    List<UserDAO> filteredList = new ArrayList<>();
                    for(UserDAO userDAO : dataList){
                        if(userDAO.getNama().toLowerCase().contains(charSequenceString.toLowerCase())){
                            filteredList.add(userDAO);
                        }
                    }
                    filteredDataList = filteredList;
                }
                FilterResults results = new FilterResults();
                results.values = filteredDataList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDataList = (List<UserDAO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama,tvNim;
        private LinearLayout mParent;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNim = itemView.findViewById(R.id.tvNim);
            mParent = itemView.findViewById(R.id.linearLayout);
        }
    }
}
