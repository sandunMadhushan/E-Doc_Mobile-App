package com.s22010304.e_doc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersVideoAdapter extends RecyclerView.Adapter<UsersVideoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UserVideoCall> arrayList;
    private OnItemClickListener onItemClickListener;

    public UsersVideoAdapter(Context context, ArrayList<UserVideoCall> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_call_user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserVideoCall user = arrayList.get(position);
        holder.userNameTV.setText(user.getUserName());
        holder.userIdTV.setText(user.getUserID());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(ArrayList<UserVideoCall> newData) {
        this.arrayList = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTV, userIdTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTV = itemView.findViewById(R.id.list_item_user_name);
            userIdTV = itemView.findViewById(R.id.list_item_user_id);
        }
    }

    public interface OnItemClickListener {
        void onClick(UserVideoCall user);
    }
}
