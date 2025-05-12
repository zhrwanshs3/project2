package com.example.project2.chackoutfile1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.example.project2.cartfile1.CartManager;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutFragment extends Fragment {

    EditText nameEditText, phoneEditText, addressEditText;
    RadioGroup paymentMethodGroup;
    TextView cartItemsTextView, totalPriceTextView;
    Button confirmOrderButton;

    DatabaseReference ordersRef;
    ArrayList<CartManager> cartItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        // ربط العناصر
        nameEditText = view.findViewById(R.id.nameEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        paymentMethodGroup = view.findViewById(R.id.paymentMethodGroup);
        cartItemsTextView = view.findViewById(R.id.cartItemsTextView);
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        confirmOrderButton = view.findViewById(R.id.confirmOrderButton);

        // Firebase
        ordersRef = FirebaseServices.getInstance().getRefernce("Orders");

        // استقبال بيانات السلة
        if (getArguments() != null) {
            cartItems = (ArrayList<CartManager>) getArguments().getSerializable("cartList");
        } else {
            cartItems = new ArrayList<>();
        }

        displayCartItems();

        confirmOrderButton.setOnClickListener(v -> placeOrder());

        return view;
    }

    private void displayCartItems() {
        StringBuilder itemsText = new StringBuilder();
        double totalPrice = 0;

        /*
        for (CartManager item : cartItems) {
            itemsText.append(item.productName)
                    .append(" (").append(item.productType).append(")")
                    .append(" x").append(item.quantity)
                    .append(" - ₪").append(item.price * item.quantity)
                    .append("\n");
            totalPrice += item.price * item.quantity;
        } */

        cartItemsTextView.setText(itemsText.toString());
        totalPriceTextView.setText("المجموع الكلي: ₪" + totalPrice);
    }

    private void placeOrder() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            Toast.makeText(getContext(), "يرجى تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentMethod = "الدفع عند الاستلام";
        String orderId = ordersRef.push().getKey();

        List<Map<String, Object>> itemsList = new ArrayList<>();
        double totalPrice = 0;

        /* TODO: Yamen
        for (CartManager item : cartItems) {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", item.getNameProduct());
            map.put("productType", item.getType());

            map.put("price", item.getPrice());
            map.put("productImage", item.getImage());
            map.put("productNumber", item.getNumberProduct());
            map.put("total", item.getPrice() );
            totalPrice += item.getPrice() ;
            itemsList.add(map);
        } */

        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("name", name);
        orderMap.put("phone", phone);
        orderMap.put("address", address);
        orderMap.put("paymentMethod", paymentMethod);
        orderMap.put("items", itemsList);
        orderMap.put("totalPrice", totalPrice);

        if (orderId != null) {
            ordersRef.child(orderId).setValue(orderMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "تم إرسال الطلب بنجاح!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "فشل في إرسال الطلب", Toast.LENGTH_SHORT).show();
                }
            });
 }
}
}