package com.example.project2.cartfile1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.example.project2.chackoutfile1.CheckoutFragment;
import com.example.project2.productfile1.AllData2Fragment;
import com.example.project2.productfile1.Product;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartProductFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView totalTextView;
    private CartAdapter cartAdapter;
    private FirebaseServices fbs;
    private ImageButton backall;
    private Button paybtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart_product, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connect();
    }

    private void connect() {


        fbs = FirebaseServices.getInstance();


        recyclerView = getView().findViewById(R.id.cartRecyclerView);
        totalTextView = getView().findViewById(R.id.totalTextView);

        // إعداد الـ Adapter و RecyclerView
        // cartAdapter = new CartAdapter(fbs.getCartProducts());
        cartAdapter = new CartAdapter(CartManager.getInstance().getCartItems(), this::updateTotal);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(cartAdapter);

        // حساب وعرض المجموع
        updateTotal();
        // cartAdapter=new CartAdapter(fbs.getCartProducts(),this::updateTotal);

        backall = getView().findViewById(R.id.bactall);
        backall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAllData2();
            }
        });
        paybtn = getView().findViewById(R.id.paybtn);
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Product> cartItems=new ArrayList<>(CartManager.getInstance().getCartItems());
                fbs.saveCartToFirebase(cartItems);




                gotoCheckout();
            }
        });


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

    private void gotoAllData2() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new AllData2Fragment());
        ft.commit();
    }

    private void gotoCheckout() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new CheckoutFragment());
        ft.commit();


    }
   /* private void saveCartToFirebase() {
        String userId = fbs.getAuth().getUid();
        if (userId == null) {
            Toast.makeText(getContext(), "يجب تسجيل الدخول أولا", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference cartRef = fbs.getRefernce("Cart").child(userId);
        List<Product> cartItems = CartManager.getInstance().getCartItems();

        List<Map<String, Object>> cartList = new ArrayList<>();
        for (Product item : cartItems) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("productName", item.getNameProduct());
            itemMap.put("productType", item.getType());
            itemMap.put("price", item.getPrice());
            itemMap.put("number", item.getNumberProduct());
            itemMap.put("image", item.getImage());
            cartList.add(itemMap);
        }

        cartRef.setValue(cartList).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "تم حفظ السلة بنجاح", Toast.LENGTH_SHORT).show();
                gotoCheckout();
            } else {
                Toast.makeText(getContext(), "فشل في حفظ السلة", Toast.LENGTH_SHORT).show();
   }
});
    }*/
}




