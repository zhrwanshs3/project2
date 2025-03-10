package com.example.project2.productfile1;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    ArrayList<product> productsList;
    private FirebaseServices fbs;
    private OnItemClickListener itemClickListener;

    public ProductAdapter(Context context1, ArrayList<product> proList) {
        this.context = context1;
        this.productsList = proList;
        this.fbs = FirebaseServices.getInstance();
    }



    @NonNull

    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return  new ProductAdapter.MyViewHolder(v);
    }


    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position)
    {
        product product = productsList.get(position);
        holder.etnameProduct.setOnClickListener(v -> {
            if(itemClickListener!=null)
                itemClickListener.onItemClick(position);
        });
        if (product.getImage()==null||product.getImage().isEmpty())
            holder.imgp.setImageURI(Uri.parse(product.getImage()));
        else Picasso.get().load(product.getImage()).into(holder.imgp);
        holder.etNumber.setText(product.getNumberProduct());
        holder.etType.setText(product.getType());
        holder.etPrice.setText(product.getPrice());

    }


    public int getItemCount(){
        return productsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView etnameProduct, etType,etNumber,etPrice;
        ImageView imgp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            etnameProduct=itemView.findViewById(R.id.etnameProductProductItem);
            etType=itemView.findViewById(R.id.etTypeProductItem);
            etNumber=itemView.findViewById(R.id.etNumberProductItem);
            etPrice=itemView.findViewById(R.id.etPriceProductItem);
            imgp=itemView.findViewById(R.id.imgpro);


        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener =listener;
}


}




