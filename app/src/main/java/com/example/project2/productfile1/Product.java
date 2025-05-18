package com.example.project2.productfile1;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String type;
    private String Price;
    private String NameProduct;
    private String NumberProduct;
    private String image;


    public Product() {

    }

    public Product(String type, String numberProduct, String nameProduct, String price, String image) {
        this.type = type;
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
        this.Price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                "Type='" + type + '\'' +
                ", Price='" + Price + '\'' +
                ", NameProduct='" + NameProduct + '\'' +
                ", NumberProduct='" + NumberProduct + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
    protected Product(Parcel in){
         type=in.readString();
         Price=in.readString();
         NameProduct=in.readString();
         NumberProduct=in.readString();
         image=in.readString();
}
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(Price);
        dest.writeString(NameProduct);
        dest.writeString(NumberProduct);
        dest.writeString(image);
    }


}





