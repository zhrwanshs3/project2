package com.example.project2.productfile1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.FirebaseServices;
import com.example.project2.Home_Fragment;
import com.example.project2.R;
import com.example.project2.SettingFragment;
import com.example.project2.cartfile1.CartProductFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllData2Fragment extends Fragment {
    private FirebaseServices fbs;
    private ArrayList<Product> products;
    private RecyclerView rvProducts;
    private ProductAdapter adapter2;
    private TextView gotoAddData2;
    private ImageButton b ;
    private ImageButton linkcart;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AllData2Fragment() {}

    public static AllData2Fragment newInstance(String param1, String param2) {
        AllData2Fragment fragment = new AllData2Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_data2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        fbs = FirebaseServices.getInstance();
        fbs.setUserChangeFlag(false);
        products = new ArrayList<>();
        b=getView().findViewById(R.id.backAllData2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotoHome();}
        });
        linkcart=getView().findViewById(R.id.linkCart);
        linkcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {gotoCartProduct();}
        });

        rvProducts = view.findViewById(R.id.RecyclerViewProduct);
        adapter2 = new ProductAdapter(getActivity(), products);
        rvProducts.setAdapter(adapter2);
        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter2.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override //هاد للتفاصيل
            public void onItemClick(int position) {
                String selectedItem = products.get(position).getNameProduct();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putParcelable("product", (Parcelable) products.get(position));
                productDetailsFragment cd = new productDetailsFragment();
                cd.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FrameLayoutMain, cd);
                ft.addToBackStack(null);
                ft.commit();

            }



        });

        fbs.getFire().collection("products").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot dataSnapshot : queryDocumentSnapshots.getDocuments()) {
                            Product rest1 = dataSnapshot.toObject(Product.class);
                            products.add(rest1);
                        }
                        adapter2.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                        Log.e("AllData2Fragment", e.getMessage());
                    }
                });

        // Uncomment when you want to use this
        /*
        gotoAddData2 = view.findViewById(R.id.AddDataAllData);
        gotoAddData2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoAddData2();
            }
        });
        */
    }


   // private void GotoAddData2() {
   //     FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
   //     ft.replace(R.id.FrameLayoutMain, new AddData2Fragment());
   //     ft.commit();
   // }
    private void gotoHome(){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayoutMain, new Home_Fragment());
        transaction.commit();

    }
    private void gotoCartProduct(){
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayoutMain, new CartProductFragment());
        transaction.commit();

    }


    }
