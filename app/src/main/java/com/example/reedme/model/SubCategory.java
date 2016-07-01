package com.example.reedme.model;

import java.util.List;

public class SubCategory {
    String CategoryName;
    List<Product> Products;
    String SubCategoryId;
    String SubCategoryName;

    public void setSubCategoryId(String subCategoryId) {
        this.SubCategoryId = subCategoryId;
    }

    public void setCategoryName(String categoryName) {
        this.CategoryName = categoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.SubCategoryName = subCategoryName;
    }

    public void setProducts(List<Product> products) {
        this.Products = products;
    }

    public String getCategoryName() {
        return this.CategoryName;
    }

    public String getSubCategoryId() {
        return this.SubCategoryId;
    }

    public String getSubCategoryName() {
        return this.SubCategoryName;
    }

    public List<Product> getProducts() {
        return this.Products;
    }
}
