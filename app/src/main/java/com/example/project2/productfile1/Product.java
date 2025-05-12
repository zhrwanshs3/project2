package com.example.project2.productfile1;

public class Product {

    private String Type;
    private String Price;
    private String NameProduct;
    private String NumberProduct;
    private String image;


    public Product() {

    }

    public Product(String type, String numberProduct, String nameProduct, String price, String image) {
        this.Type = type;
        this.NumberProduct = numberProduct;
        this.NameProduct = nameProduct;
        this.Price = price;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

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

    public void setNumberProduct(String numberProduct) {
        NumberProduct = numberProduct;
    }

    @Override
    public String toString() {
        return "product{" +
                "Type='" + Type + '\'' +
                ", Price='" + Price + '\'' +
                ", NameProduct='" + NameProduct + '\'' +
                ", NumberProduct='" + NumberProduct + '\'' +
                ", image='" + image + '\'' +
                '}';
    }


}





