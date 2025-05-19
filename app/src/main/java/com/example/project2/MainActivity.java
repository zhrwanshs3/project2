package com.example.project2;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.project2.LoginSignupfile1.LoginFragment;
import com.example.project2.LoginSignupfile1.SignupFragment;
import com.example.project2.productfile1.AllData2Fragment;
import com.example.project2.userfile1.AllData1Fragment;
import com.example.project2.userfile1.addDataFragment;

public class MainActivity extends AppCompatActivity {
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onStart(){
        super .onStart();


            // تسجيل الـ BroadcastReceiver
            networkChangeReceiver = new NetworkChangeReceiver();
            IntentFilter filter = new IntentFilter();
           filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(networkChangeReceiver, filter);


        gotoLogin();
    }
    @Override
   protected void onStop() {
   super.onStop();
      unregisterReceiver(networkChangeReceiver);
    }

    private void gotoSignup(){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain,new SignupFragment());
        ft.commit();
    }

    private void gotoLogin() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain, new LoginFragment());
        ft.commit();
    }
    private void gotoAddFragment() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain,new addDataFragment());
        ft.commit();
    }

    private void gotoAllData2Fragment() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutMain,new AllData2Fragment());
        ft.commit();
    }
}
