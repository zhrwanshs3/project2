package com.example.project2.productfile1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllData2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllData2Fragment extends Fragment {
    private FirebaseServices fbs;
    private ArrayList<product> products;
    private RecyclerView rvProducts;
    private ProductAdapter adapter2;
    private TextView gotoAddData2;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllData2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllData2Fragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_data2, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();


        fbs = FirebaseServices.getInstance();
        products = new ArrayList<>();
        rvProducts = getView().findViewById(R.id.rvProductsFragment);
        adapter2 = new ProductAdapter(getActivity(), products);
        rvProducts.setAdapter(adapter2);
        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        fbs.getFire().collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    product rest1 = dataSnapshot.toObject(product.class);
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
       /* gotoAddData2.findViewById(R.id.AddDataAllData);
        gotoAddData2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoAddData2();
            }'
        });*/


    }
    private void GotoAddData2() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new AddData2Fragment());
        ft.commit();
    }
}


