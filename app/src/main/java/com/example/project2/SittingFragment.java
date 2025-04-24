package com.example.project2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.project2.userfile1.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SittingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SittingFragment extends Fragment {
    private EditText name,age;
    private FirebaseServices fbs ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SittingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SittingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SittingFragment newInstance(String param1, String param2) {
        SittingFragment fragment = new SittingFragment();
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
        return inflater.inflate(R.layout.fragment_sitting, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        coneect();
    }
    public void coneect ()
    {
        name=getView().findViewById(R.id.etNamePr);
        age=getView().findViewById(R.id.etAgePr);
        fbs=new FirebaseServices();
        User user =fbs.getCurrentUser();
        if (user != null) {
            String phone = user.getPhone();
            String name1 = getNameFromEmail(phone);
            name.setText(name1);
            fbs.getUserDataByPhone(phone, new OnSuccessListener<QueryDocumentSnapshot>() {
                @Override
                public void onSuccess(QueryDocumentSnapshot queryDocumentSnapshot) {


                    age.setText(queryDocumentSnapshot.getString("age"));
                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        };


    }
    public static String getNameFromEmail(String email) {
        if (email == null || !email.contains("@"))
            return "Invalid email";
        String namePart = email.split("@")[0];
        return namePart;
    }


}

