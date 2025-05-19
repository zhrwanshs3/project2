package com.example.project2.chackoutfile1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project2.FirebaseServices;
import com.example.project2.Home_Fragment;
import com.example.project2.LoginSignupfile1.ForgotPasswordFragment;
import com.example.project2.R;
import com.example.project2.cartfile1.CartAdapter;
import com.example.project2.cartfile1.CartManager;
import com.example.project2.cartfile1.CartProductFragment;
import com.example.project2.productfile1.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutFragment extends Fragment {

    private   EditText nameEditText, phoneEditText, addressEditText;
    private RadioGroup paymentMethodGroup;
    private TextView cartItemsTextView, totalPriceTextView;
    private Button confirmOrderButton;
    private ImageButton backtocart;
    private Button btnPayment;


    DatabaseReference ordersRef;
    List<Product> cartItems;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public  CheckoutFragment(){
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        connect();
    }

    private void connect() {

        nameEditText = getView().findViewById(R.id.nameEditText);
        phoneEditText = getView().findViewById(R.id.phoneEditText);
        addressEditText = getView().findViewById(R.id.addressEditText);
        paymentMethodGroup = getView().findViewById(R.id.paymentMethodGroup);
        cartItemsTextView = getView().findViewById(R.id.cartItemsTextView);
        totalPriceTextView = getView().findViewById(R.id.totalPriceTextView);
        confirmOrderButton = getView().findViewById(R.id.btnPayment);
        backtocart= getView().findViewById(R.id.backtocart1);
        btnPayment= getView().findViewById(R.id.btnPayment);

        backtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotocart();
            }
        });

        double totalPrice=CartManager.getInstance().getTotalPrice();
        totalPriceTextView = getView().findViewById(R.id.totalPriceTextView);
        totalPriceTextView.setText("Total:"+totalPrice+"₪");





        // Firebase reference
        ordersRef = FirebaseServices.getInstance().getRefernce("Orders");

        // جلب السلة من CartManager
        cartItems = CartManager.getInstance().getCartItems();

        displayCartItems();

        confirmOrderButton.setOnClickListener(v -> placeOrder());


           /* btnPayment.setOnClickListener(v -> {
                List<Product> cartList = CartManager.getInstance().getCartItems();

                if (cartList.isEmpty()) {
                    Toast.makeText(getContext(), "السلة فارغة", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> order = new HashMap<>();
                order.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                order.put("timestamp", System.currentTimeMillis());
                order.put("status", "pending");

                List<Map<String, Object>> productList = new ArrayList<>();
                for (Product product : cartList) {
                    Map<String, Object> productMap = new HashMap<>();
                    productMap.put("name", product.getNameProduct());
                    productMap.put("price", product.getPrice());
                    productMap.put("image", product.getImage());
                    productMap.put("type", product.getType());
                    productList.add(productMap);
                }

                order.put("products", productList);

                FirebaseFirestore.getInstance()
                        .collection("orders")
                        .add(order)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(getContext(), "تم استلام الطلب", Toast.LENGTH_SHORT).show();
                            CartManager.getInstance().clearCart();

                            // الرجوع إلى صفحة Home
                            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                            ft.replace(R.id.FrameLayoutMain, new Home_Fragment());
                            ft.commit();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "فشل في الاستلام: " + e.getMessage(), Toast.LENGTH_SHORT).show();
   });
            });*/
        btnPayment.setOnClickListener(v -> {
            List<Product> cartList = CartManager.getInstance().getCartItems();

            if (cartList.isEmpty()) {
                Toast.makeText(getContext(), "السلة فارغة", Toast.LENGTH_SHORT).show();
                return;
            }

            // نأخذ معلومات المستخدم
            String name = nameEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getContext(), "يرجى تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> order = new HashMap<>();
            //order.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
            // order.put("timestamp", System.currentTimeMillis());
            order.put("status", "pending");
            order.put("customerName", name);
            order.put("customerAddress", address);
            order.put("customerPhone", phone);
            order.put("totalPrice", CartManager.getInstance().getTotalPrice());


            List<Map<String, Object>> productList = new ArrayList<>();
            for (Product product : cartList) {
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("name", product.getNameProduct());
                productMap.put("price", product.getPrice());
                productMap.put("image", product.getImage());
                productMap.put("numberProduct", product.getNumberProduct());
                productList.add(productMap);
            }

            order.put("products", productList);

            FirebaseFirestore.getInstance()
                    .collection("orders")
                    .add(order)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getContext(), "تم استلام الطلب", Toast.LENGTH_SHORT).show();
                        CartManager.getInstance().clearCart();

                        // الرجوع إلى صفحة Home
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.FrameLayoutMain, new Home_Fragment());
                        ft.commit();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "فشل في الاستلام: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void displayCartItems() {
        StringBuilder itemsText = new StringBuilder();
        double totalPrice =  CartManager.getInstance().getTotalPrice();


        for (Product item : cartItems) {
            String name = item.getNameProduct();
            // String type = item.getType();
            // int quantity = parseInt(item.getNumberProduct());
            double price = parseDouble(item.getPrice());
            double itemTotal = price; //* quantity;

            itemsText.append(name)
                    //  .append(" (").append(type).append(")")
                    //.append(" x").append(quantity)
                    .append(" - ₪").append(itemTotal)
                    .append("\n");

            // totalPrice += itemTotal;
        }

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

        for (Product item : cartItems) {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", item.getNameProduct());
            //  map.put("productType", item.getType());
            map.put("price", item.getPrice());
            map.put("productImage", item.getImage());
            // map.put("productNumber", item.getNumberProduct());

            // int quantity = parseInt(item.getNumberProduct());
            double price = parseDouble(item.getPrice());
            double itemTotal = price; //* quantity;
            map.put("total", itemTotal);

            totalPrice += itemTotal;
            itemsList.add(map);
        }

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
                    CartManager.getInstance().clearCart();
                } else {
                    Toast.makeText(getContext(), "فشل في إرسال الطلب", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }
    private void gotocart() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new CartProductFragment());
        ft.commit();
    }




}

