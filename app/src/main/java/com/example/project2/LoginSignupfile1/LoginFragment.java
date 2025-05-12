package com.example.project2.LoginSignupfile1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.AddProductActivity;
import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.example.project2.productfile1.AddData2Fragment;
import com.example.project2.userfile1.addDataFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private EditText etUsername, etPassword;
    private TextView tvSignupLinkLogin;
    private Button btnLogin;
    private FirebaseServices fbs;
    private TextView tvForgotPasswardLink;
    private TextView Linkuser;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connect();
    }

    public void connect() {
        //connecting components
        fbs = FirebaseServices.getInstance();
        etUsername = getView().findViewById(R.id.etusernamelogin);
        etPassword = getView().findViewById(R.id.etpasswordlogin);
        btnLogin = getView().findViewById(R.id.button2);
        tvForgotPasswardLink=getView().findViewById(R.id.gotoForgetfromLogin);
Linkuser= getView().findViewById(R.id.linkuser);

        tvSignupLinkLogin = getView().findViewById(R.id.tvSignupLinkLogin);
        tvSignupLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignupFregment();
            }
        });
        tvSignupLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignupFregment();
            }
        });
        Linkuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoaddDataFragment();
            }
        });


        tvForgotPasswardLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFrgotPasswordFragment();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Data validation
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (username.isEmpty() && password.isEmpty()) {
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //login procedure
                fbs.getAuth().signInWithEmailAndPassword(username, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override

                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getActivity(), "Successfully signed up!", Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.FrameLayoutMain, new AddData2Fragment());
                        ft.commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });
    }

    private void gotoAddDataFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new addDataFragment());
        ft.commit();
    }


    private void gotoSignupFregment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new SignupFragment());
        ft.commit();
    }

    private void gotoFrgotPasswordFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new ForgotPasswordFragment());
        ft.commit();


    }
    //private void gotoAddData2() {
     //   FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
     //   ft.replace(R.id.FrameLayoutMain, new AddData2Fragment());
       // ft.commit();
  //  }
    private void gotoaddDataFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new addDataFragment());
        ft.commit();


    }
    private void gotoaddProductActivity() {
        Intent intent=new Intent(getActivity(), AddProductActivity.class);
        startActivity(intent);
    }


    }