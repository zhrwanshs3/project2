package com.example.project2.productfile1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.example.project2.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;


public class AddData2Fragment extends Fragment {
    private EditText etNameProduct, etType, etNumber, etPrice;

    private Button btnAdd2;
    private String NameProduct;
    private String Type;
    private Double Price;
    private Integer NumberProduct;
    private FirebaseServices fbs;
    private Button add2;
    private ImageView img;
    private static  final int GALLARY_REQUEST_CODE = 100 ;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        img.setImageURI(selectedImageUri);
                        Utils.getInstance().uploadImage(getActivity(), selectedImageUri);
                    }
                });
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
        img= getView().findViewById(R.id.imageViewProfile);
        fbs = FirebaseServices.getInstance();
        etNumber = getView().findViewById(R.id.etNumber);
        etType = getView().findViewById(R.id.etType);
        etPrice = getView().findViewById(R.id.etPrice);
        etNameProduct=getView().findViewById(R.id.etnameProduct);
        add2 = getView().findViewById(R.id.btnAdd2);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent gallerIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(gallerIntent,GALLARY_REQUEST_CODE);
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(galleryIntent);

            }
        });
        add2.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                //get data from screen

                String Nameproduct1, number1, type1, price1;
                Nameproduct1 = etNameProduct.getText().toString();
                number1 = etNumber.getText().toString();
                type1 = etType.getText().toString();
                price1 = etPrice.getText().toString();
                Uri selectedImageUri=fbs.getSelectedImageURL();
                String imageUri  = "";
                if(selectedImageUri!=null) {
                    imageUri = selectedImageUri.toString();
                }

                //check data
                if (Nameproduct1.isEmpty() || number1.isEmpty() || type1.isEmpty() || price1.isEmpty()) {
                    Toast.makeText(getActivity(), "something is missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                //add to firbase
                Uri selectImageUri=fbs.getSelectedImageURL();
                String imguri="";
                if (selectImageUri!=null)
                    imguri=selectImageUri.toString();
                Product pro = new Product(number1 ,type1,Nameproduct1,price1,imguri);
                fbs.getFire().collection("products").add(pro).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        gotoِAllData2();
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

    /*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLARY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            img.setImageURI(selectedImageUri);
            Utils.getInstance().uploadImage(getActivity(), selectedImageUri);
        }
   } */

    private void gotoِAllData2() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new AllData2Fragment());
        ft.commit();


    }

}