package com.example.project2.cartfile1;

import com.example.project2.productfile1.Product;

import java.util.ArrayList;

public class CartWrapper {

        private ArrayList<Product> items;

        public CartWrapper() {} // ضروري لفirebase

        public CartWrapper(ArrayList<Product> items) {
            this.items = items;
        }

        public ArrayList<Product> getItems() {
            return items;
        }

        public void setItems(ArrayList<Product> items) {
            this.items =items;

    }
}
