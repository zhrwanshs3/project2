package com.example.project2.cartfile1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.productfile1.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartItems;
    private CartViewHolder.OnCartChangeListener listener;

    public CartAdapter(List<Product> cartItems) {
        this.cartItems = cartItems;
    }
    

    public CartAdapter(List<Product> cartItems, CartViewHolder.OnCartChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);

        holder.nameTextView.setText(product.getNameProduct());
        holder.priceTextView.setText(product.getPrice() + " ₪");
        holder.imageView.setImageResource(Integer.parseInt(product.getImage()));

        holder.removeButton.setOnClickListener(v -> {
            CartManager.getInstance().removeItem(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            if (listener != null) listener.onCartChanged();

        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        ImageView imageView;
        Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.cart_item_name);
            priceTextView = itemView.findViewById(R.id.cart_item_price);
            imageView = itemView.findViewById(R.id.cart_item_image);
            removeButton = itemView.findViewById(R.id.cart_item_remove);
 }
        public interface OnCartChangeListener {
            void onCartChanged();
        }

    }
}
