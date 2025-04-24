package com.example.project2.userfile1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
 * Use the {@link addDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addDataFragment extends Fragment {
    private EditText etName, etLastName, etloction, etPhone;
    private Button btnAdd;
    private String name;
    private String lastName;
    private String phone;
    private String loction;
    private FirebaseServices fbs;
    private Button add1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addDataFragment newInstance(String param1, String param2) {
        addDataFragment fragment = new addDataFragment();
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
        return inflater.inflate(R.layout.fragment_add_data, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        connect();

    }

    private void connect() {
        etName = getView().findViewById(R.id.etname);
        fbs = FirebaseServices.getInstance();
        etloction = getView().findViewById(R.id.etloction);
        etPhone = getView().findViewById(R.id.etPhone);
        etLastName = getView().findViewById(R.id.etLastName);
        btnAdd = getView().findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from screen
                String name1, phone1, loction1, lastName1;
                name1 = etName.getText().toString();
                phone1 = etPhone.getText().toString();
                loction1 = etloction.getText().toString();
                lastName1 = etLastName.getText().toString();


                //check data
                if (name1.isEmpty() || phone1.isEmpty() || loction1.isEmpty() || lastName1.isEmpty()) {
                    Toast.makeText(getActivity(), "something is missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                //add to firbase

                User us = new User(name1, lastName1, loction1, phone1);
                fbs.getFire().collection("users").add(us).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(getActivity(), "success!", Toast.LENGTH_SHORT).show();
                       gotoِAllData();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "faild", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private void gotoِAllData() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new AllData1Fragment());
        ft.commit();


    }

}
