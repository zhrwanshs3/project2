package com.example.project2.productfile1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.FirebaseServices;
import com.example.project2.R;
import com.example.project2.cartfile1.CartManager;
import com.example.project2.cartfile1.CartProductFragment;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link productDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class productDetailsFragment extends Fragment {
    private FirebaseServices fbs;
    private Product product;
    private ImageView imageView3;
    private ImageView imgb;
    private ImageButton addToCart;
    private TextView Datialsnameproduct,DatiailsMethod, DetilsPrice;
    private ImageView btncart;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public productDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment productDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static productDetailsFragment newInstance(String param1, String param2) {
        productDetailsFragment fragment = new productDetailsFragment();
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
          //  product=getArguments().getParcelable("product");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        connect();

    }
    public void connect() {
        fbs = new FirebaseServices();
        imgb=getView().findViewById(R.id.imageButtonDetiails);
        imageView3 = getView().findViewById(R.id.imageView3);
        Datialsnameproduct = getView().findViewById(R.id.Datialsnameproduct);
        DatiailsMethod = getView().findViewById(R.id.DatiailsMethod);
        DetilsPrice = getView().findViewById(R.id.DetilsPrice);
        btncart=getView().findViewById(R.id.gocartdet);
        btncart.setOnClickListener(v -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.FrameLayoutMain, new CartProductFragment());
            ft.commit();
        });

        addToCart = getView().findViewById(R.id.btnadDatial);

        product=getArguments().getParcelable("product");
        /*
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbs.addRecipeToFavorites(recipe.getIdRecipe(),
                        unused -> Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show(),
                        e -> Toast.makeText(getContext(), "failure " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );

                Picasso.get().load(R.drawable.__2025_04_16_150949).into(fav);
            }
        });
        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction= getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main ,new ProductAdapter());
                transaction.commit();
            }
        });*/
        if (product != null) {
            Datialsnameproduct.setText(product.getNameProduct());
            DetilsPrice.setText(product.getPrice() + " ₪");
            DatiailsMethod.setText(product.getType());
            if (product.getImage() == null || product.getImage().isEmpty()) {
                Picasso.get().load(R.drawable.screenshot_2025_04_19_153315).into(imageView3);
            } else {
                Picasso.get().load(product.getImage()).into(imageView3);
            }
        }
        addToCart.setOnClickListener(v -> {

            if (product == null) {
                Toast.makeText(getContext(), "المنتج غير متوفر", Toast.LENGTH_SHORT).show();
                return;
            }

            CartManager.getInstance().addToCart(product);
            Toast.makeText(getContext(), "تمت إضافة المنتج إلى السلة", Toast.LENGTH_SHORT).show();
        });
        imgb.setOnClickListener(v -> {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.replace(R.id.FrameLayoutMain, new AllData2Fragment());
            ft.commit();
        });
    }

}



