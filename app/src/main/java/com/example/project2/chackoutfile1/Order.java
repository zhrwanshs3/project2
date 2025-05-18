package com.example.project2.chackoutfile1;



import com.example.project2.productfile1.Product;

import java.util.List;

public class Order {
    //private String userId;
   // private long timestamp;
    private String status;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private List<Product> products;

    public Order() {} // Required for Firestore

    public Order(String userId, long timestamp, String status, String customerName, String customerAddress, String customerPhone, List<Product> products) {
        //this.userId = userId;
        //this.timestamp = timestamp;
        this.status = status;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.products = products;
    }

    // Getters & Setters
  //  public String getUserId() { return userId; }
   // public void setUserId(String userId) { this.userId = userId; }

   // public long getTimestamp() { return timestamp; }
  //  public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products=products;}
}