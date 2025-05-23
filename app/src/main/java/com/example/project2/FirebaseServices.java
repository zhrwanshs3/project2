package com.example.project2;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.project2.cartfile1.CartWrapper;
import com.example.project2.productfile1.Product;
import com.example.project2.userfile1.User;
import com.example.project2.userfile1.UserCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
//ملاحطات للامتحان
public class FirebaseServices {
    private static FirebaseServices instance; //نسخة وحدة
    private FirebaseAuth auth; //لتسجيل دخول المستخدمين
    private FirebaseFirestore fire; //قاعدة بيانات سحابية لحفظ بيانات
    private FirebaseStorage storage ; //لتخزين الصور
    private Uri selectedImageURL;
    private User currentUser;
    private StorageReference storageReference;
    private boolean userChangeFlag;
    private ArrayList<Product> cartProducts;
    private FirebaseDatabase database; //لحفظ بيانات مباشرة

    public ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }

    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    public  FirebaseServices ()
    {
        auth=FirebaseAuth.getInstance();
        fire=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        cartProducts = new ArrayList<>();
        database=FirebaseDatabase.getInstance();
        getCurrentObjectUser(new UserCallback() {
            @Override
            public void onUserLoaded(User user) {
                // Access the currentUser here
                if (user != null) {
                    setCurrentUser(user);
                }
            }
        });

        userChangeFlag = false;
        selectedImageURL = null;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseFirestore getFire() {
        return fire;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }



    public  static FirebaseServices getInstance(){
        if (instance==null){
            instance=new FirebaseServices();

        }
        return instance;
    }

    public static FirebaseServices reloadInstance(){
        instance=new FirebaseServices();
        return instance;
    }

    public boolean isUserChangeFlag() {
        return userChangeFlag;
    }

    public void setUserChangeFlag(boolean userChangeFlag) {
        this.userChangeFlag = userChangeFlag;
    }
//للتعامل مع الايميل
    public void getCurrentObjectUser(UserCallback callback) {        ArrayList<User> usersInternal = new ArrayList<>();
        fire.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    User user = dataSnapshot.toObject(User.class);
                    if (auth.getCurrentUser() != null && auth.getCurrentUser().getEmail().equals(user.getName())) {
                        usersInternal.add(user);

                    }
                }
                if (usersInternal.size() > 0)
                    currentUser = usersInternal.get(0);

                callback.onUserLoaded(currentUser);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public User getCurrentUser()
    {
        return this.currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean updateUser(User user) {
        return false;
    }

    public void getUserDataByPhone(String phone, OnSuccessListener<QueryDocumentSnapshot> age, OnFailureListener onFailureListener) {

    }
    public void saveCartToFirebase(ArrayList<Product> cartItems) {
        if (auth.getCurrentUser() == null) return;
        String userId = auth.getCurrentUser().getUid();

        // كل مستخدم إله document باسمه، وفي داخله مجموعة cart
        fire.collection("carts").document(userId)
                .set(new CartWrapper(cartItems)) // نغلف القائمة داخل كائن ليسهل حفظها
                .addOnSuccessListener(unused -> {
                    // نجاح الحفظ - ممكن تعرضي رسالة
                })
                .addOnFailureListener(e -> {
                    // فشل الحفظ - طباعة أو إشعار
 });
    }

    public DatabaseReference getRefernce(String path){
        return database.getReference(path);
    }
}
