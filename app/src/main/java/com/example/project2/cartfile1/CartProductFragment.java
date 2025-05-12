package com.example.project2.cartfile1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project2.FirebaseServices;
import com.example.project2.R;

public class CartProductFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView totalTextView;
    private CartAdapter cartAdapter;
    private FirebaseServices fbs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_product, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fbs = FirebaseServices.getInstance();
        View view = getView();
        if (view == null) return;

        recyclerView = view.findViewById(R.id.cartRecyclerView);
        totalTextView = view.findViewById(R.id.totalTextView);

        // إعداد الـ Adapter و RecyclerView
        cartAdapter = new CartAdapter(fbs.getCartProducts());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(cartAdapter);

        // حساب وعرض المجموع
        updateTotal();
        cartAdapter = new CartAdapter(fbs.getCartProducts(), this::updateTotal);

    }

    @Override
    public void onResume() {
        super.onResume();
        // تحديث القائمة والمجموع عند الرجوع لهذا الفراجمنت
        if (cartAdapter != null) {
            cartAdapter.notifyDataSetChanged();
            updateTotal();
        }
    }

    private void updateTotal() {
        double total = CartManager.getInstance().getTotalPrice();
        totalTextView.setText("sum: " + total + "₪");
    }

}