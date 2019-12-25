package com.internousdev.django.dto;

import java.util.Date;

public class CartInfoDTO {

//フィールド
	private int productId;
	private String productName;
	private String productNameKana;
	private String productDescription;
	private int categoryId;
	private int price;
	private String imageFilePath;
	private String imageFileName;
	private Date releaseDate;
	private String releaseCompany;
	private int totalPrice;
	private String userId;
	private int productCount;

//getterとsetter
	public int getProductId() {
	    return productId;
	}

	public void setProductId(int productId) {
	    this.productId = productId;
	}

	public String getProductName() {
	    return productName;
	}

	public void setProductName(String productName) {
	    this.productName = productName;
	}
	public String getProductNameKana() {
	    return productNameKana;
	}

	public void setProductNameKana(String productNameKana) {
	    this.productNameKana = productNameKana;
	}

	public int getCategoryId() {
	    return categoryId;
	}

	public void setCategoryId(int categoryId) {
	    this.categoryId = categoryId;
	}

	public int getPrice() {
	    return price;
	}

	public void setPrice(int price) {
	    this.price = price;
	}

	public String getImageFilePath() {
	    return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
	    this.imageFilePath = imageFilePath;
	}

	public String getImageFileName() {
	    return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
	    this.imageFileName = imageFileName;
	}

	public Date getReleaseDate() {
	    return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
	    this.releaseDate = releaseDate;
	}

	public String getReleaseCompany() {
	    return releaseCompany;
	}

	public void setReleaseCompany(String releaseCompany) {
	    this.releaseCompany = releaseCompany;
	}

	public String getUserId() {
	    return userId;
	}

	public void setUserId(String userId) {
	    this.userId = userId;
	}

	public int getProductCount() {
	    return productCount;
	}

	public void setProductCount(int productCount) {
	    this.productCount = productCount;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

}
