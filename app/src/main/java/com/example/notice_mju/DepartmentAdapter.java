package com.example.notice_mju;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {

    private Context context;
    private List<DepartmentModel> departmentList;

    public DepartmentAdapter(Context context, List<DepartmentModel> departmentList) {
        this.context = context;
        this.departmentList = departmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_department, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DepartmentModel department = departmentList.get(position);

        holder.name.setText(department.getName());
        holder.org.setText(department.getOrg());
        if (department.getUniv() != null) {
            holder.univ.setText(department.getUniv());
            holder.univ.setVisibility(View.VISIBLE);
        } else {
            holder.univ.setVisibility(View.GONE);
        }

        Glide.with(context)
                .load(department.getProfileUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.profileImage);

        Log.d("DepartmentAdapter", "Image URL: " + department.getProfileUrl());


        // 디버깅 로그 추가
        System.out.println("Glide Image URL: " + department.getProfileUrl());
    }


    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, org, univ;
        ImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_name);
            org = itemView.findViewById(R.id.text_org);
            univ = itemView.findViewById(R.id.text_univ);
            profileImage = itemView.findViewById(R.id.image_profile);
        }
    }
}
