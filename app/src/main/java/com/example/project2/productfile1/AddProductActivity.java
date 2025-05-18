package com.example.project2.productfile1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.example.project2.Utils;

public class AddProductActivity extends AppCompatActivity {
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
    private View view;
    private Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.FrameLayoutAdd), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == this.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        img.setImageURI(selectedImageUri);
                        Utils.getInstance().uploadImage(this, selectedImageUri);
                    }
                });
    }



    public static AddData2Fragment newInstance(String param1, String param2) {
        AddData2Fragment fragment = new AddData2Fragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onStart() {
        super.onStart();
        connect();
    }

    private void connect() {

        fbs = FirebaseServices.getInstance();
        utils = Utils.getInstance();
        etNameProduct = findViewById(R.id.etnameProduct);
        etType = findViewById(R.id.etType);
        etNumber = findViewById(R.id.etNumber);
        etPrice = findViewById(R.id.etPrice);
        add2 = findViewById(R.id.btnAdd2);
        img = findViewById(R.id.imageViewProfile);

        img.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(galleryIntent);
        });

        add2.setOnClickListener(v -> {
            String nameProduct = etNameProduct.getText().toString();
            String number = etNumber.getText().toString();
            String type = etType.getText().toString();
            String price = etPrice.getText().toString();

            if (nameProduct.isEmpty() || number.isEmpty() || type.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "something is missing", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri selectedImageUri = fbs.getSelectedImageURL();
            String imageUri = selectedImageUri != null ? selectedImageUri.toString() : "";

            Product product = new Product(number, type, nameProduct, price, imageUri);
            fbs.getFire().collection("products").add(product)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                        gotoAllData2();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show());
        });
    }

    private void gotoAllData2() {
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutAdd, new AllData2Fragment());
        ft.commit();
    }

}
