package com.example.project2.userfile1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.FirebaseServices;
import com.example.project2.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> userList;
    private FirebaseServices fbs;


    public UserAdapter(Context context, ArrayList<User> restList) {
        this.context = context;
        this.userList = restList;
        this.fbs = FirebaseServices.getInstance();
    }


    @NonNull

    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.MyViewHolder(v);
    }


    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.etloction.setText(user.getLocation());
        holder.etLastName.setText(user.getLastname());
        holder.etPhone.setText(user.getPhone());
        holder.etName.setText(user.getName());
    }


    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView etloction, etLastName, etPhone, etName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            etloction = itemView.findViewById(R.id.etloctionUserItem);
            etLastName = itemView.findViewById(R.id.etLastNameUserItem);
            etPhone = itemView.findViewById(R.id.etPhoneUserItem);
            etName = itemView.findViewById(R.id.etnameUserItem);


        }
    }
}


