package com.shopov.cardsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Organisation implements Parcelable {

	private long id;
	private String name;
	private String address;
	private String email;
	private String webPage;
	private String phoneNumber;
	private int isPromo;
	private int discount;
	private float rating;
	private String city;
	private String branch;
	private double gpsLongtitude;
	private double gpsLatitude;
	private String category;
	private String subCategory;

	public Organisation() {
		id = 0;
		name = "";
		address = "";
		email = "";
		webPage = "";
		phoneNumber = "";
		isPromo = 0;
		discount = 0;
		rating = 0.0f;
		city = "";
		branch = "";
		gpsLatitude = 0.0;
		gpsLongtitude = 0.0;
	}

	public Organisation(Parcel in) {
		readFromParcel(in);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCityId(String city) {
		this.city = city;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getIsPromo() {
		return isPromo;
	}

	public void setIsPromo(int isPromo) {
		this.isPromo = isPromo;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public double getGpsLongtitude() {
		return gpsLongtitude;
	}

	public void setGpsLongtitude(double gpsLongtitude) {
		this.gpsLongtitude = gpsLongtitude;
	}

	public double getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(id);
		out.writeString(name);
		out.writeString(address);
		out.writeString(email);
		out.writeString(webPage);
		out.writeString(phoneNumber);
		out.writeInt(isPromo);
		out.writeInt(discount);
		out.writeFloat(rating);
		out.writeString(city);
		out.writeString(branch);
		out.writeDouble(gpsLatitude);
		out.writeDouble(gpsLongtitude);
		out.writeString(category);
		out.writeString(subCategory);
	}

	private void readFromParcel(Parcel in) {
		id = in.readLong();
		name = in.readString();
		address = in.readString();
		email = in.readString();
		webPage = in.readString();
		phoneNumber = in.readString();
		isPromo = in.readInt();
		discount = in.readInt();
		rating = in.readFloat();
		city = in.readString();
		branch = in.readString();
		gpsLatitude = in.readDouble();
		gpsLongtitude = in.readDouble();
		category = in.readString();
		subCategory = in.readString();
	}

	public static final Parcelable.Creator<Organisation> CREATOR = new Parcelable.Creator<Organisation>() {
		public Organisation createFromParcel(Parcel in) {
			return new Organisation(in);
		}

		public Organisation[] newArray(int size) {
			return new Organisation[size];
		}
	};

}
