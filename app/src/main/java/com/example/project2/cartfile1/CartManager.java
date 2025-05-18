package com.example.project2.cartfile1;

import com.example.project2.productfile1.Product;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    // Singleton
    private static CartManager instance;
    private  List<Product> cartItems ;

    private CartManager() {
        cartItems=new ArrayList<>();
        // private constructor to prevent instantiation
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // إضافة منتج
    public void addToCart(Product item) {
        cartItems.add(item);
    }

    // جلب كل المنتجات
    public List<Product> getCartItems() {
        return cartItems;
    }

    // حساب المجموع
    public int getTotalPrice() {
        int total = 0;
        for (Product p : cartItems) {
            total += Integer.parseInt(p.getPrice());
        }
        return total;
    }

    // إزالة عنصر بموقع معيّن
    public void removeItem(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
        }
    }

    // تفريغ السلة
    public void clearCart() {
        cartItems.clear();
    }
    public List<Product> getCartProduct(){
        return cartItems;
    }
}
