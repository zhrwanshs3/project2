package com.example.project2.productfile1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddData2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddData2Fragment extends Fragment {
    private EditText etNameProduct, etType, etNumber, etPrice;

    private Button btnAdd2;
    private String NameProduct;
    private String Type;
    private Double Price;
    private Integer NumberProduct;
    private FirebaseServices fbs;
    private Button add2;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddData2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddData2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddData2Fragment newInstance(String param1, String param2) {
        AddData2Fragment fragment = new AddData2Fragment();
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
        return inflater.inflate(R.layout.fragment_add_data2, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        connect();

    }

    private void connect() {
        etNameProduct = getView().findViewById(R.id.etnameProduct);
        fbs = FirebaseServices.getInstance();
        etNumber = getView().findViewById(R.id.etNumber);
        etType = getView().findViewById(R.id.etType);
        etPrice = getView().findViewById(R.id.etPrice);
        btnAdd2 = getView().findViewById(R.id.btnAdd2);
        btnAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from screen
                String Nameproduct1, number1, type1, price1;
                Nameproduct1 = etNameProduct.getText().toString();
                number1 = etNumber.getText().toString();
                type1 = etType.getText().toString();
                price1 = etPrice.getText().toString();


                //check data
                if (Nameproduct1.isEmpty() || number1.isEmpty() || type1.isEmpty() || price1.isEmpty()) {
                    Toast.makeText(getActivity(), "something is missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                //add to firbase

                product pro = new product(number1 ,type1,Nameproduct1,price1);
                fbs.getFire().collection("products").add(pro).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Failure!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });
    }

}