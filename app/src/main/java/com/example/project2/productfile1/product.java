package com.example.project2.productfile1;

public class product {
    @Override
    public String toString() {
        return "product{" +
                "Price=" + Price +
                ", Type='" + Type + '\'' +
                ", NameProduct='" + NameProduct + '\'' +
                ", NumberProduct=" + NumberProduct +
                '}';
    }

    private String Type;
    private String  Price;
    private String NameProduct;
    private String NumberProduct;

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }
    public String getNumberProduct() {
        return NumberProduct;
    }
    public void setNumberProduct(String numberProduct){
        NumberProduct = numberProduct;
    }




    public product(String price, String type, String nameProduct,String numberProduct) {
        Price = price;
        Type = type;
        NameProduct = nameProduct;
        NumberProduct=numberProduct;
    }



}




