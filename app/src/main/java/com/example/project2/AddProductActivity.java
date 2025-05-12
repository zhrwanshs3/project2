package com.example.project2;

import static com.example.project2.R.id.*;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project2.productfile1.AddData2Fragment;
import com.example.project2.productfile1.AllData2Fragment;
import com.example.project2.productfile1.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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
        View view = getView();
        if (view == null) return;

        fbs = FirebaseServices.getInstance();
        etNameProduct = view.findViewById(R.id.etnameProduct);
        etType = view.findViewById(R.id.etType);
        etNumber = view.findViewById(R.id.etNumber);
        etPrice = view.findViewById(R.id.etPrice);
        add2 = view.findViewById(R.id.btnAdd2);
        img = view.findViewById(R.id.imageViewProfile);

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
        ft.replace(R.id.FrameLayoutMain, new AllData2Fragment());
        ft.commit();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
