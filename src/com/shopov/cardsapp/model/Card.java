package com.shopov.cardsapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {

	private long id;
	private String firstName;
	private String lastName;
	private String expiry;
	private String clubName;
	private String cardNumber;
	private double savings;

	public Card() {
		firstName = "";
		lastName = "";
		expiry = "";
		clubName = "";
		cardNumber = "";
		savings = 0.0;
	}

	public Card(Parcel in) {
		readFromParce(in);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public double getSavings() {
		return savings;
	}

	public void setSavings(double savings) {
		this.savings = savings;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(id);
		out.writeString(firstName);
		out.writeString(lastName);
		out.writeString(expiry);
		out.writeString(clubName);
		out.writeString(cardNumber);
		out.writeDouble(savings);
	}
	
	private void readFromParce(Parcel in){
		id = in.readLong();
		firstName = in.readString();
		lastName = in.readString();
		expiry = in.readString();
		clubName = in.readString();
		cardNumber = in.readString();
		savings = in.readDouble();
	}
	
	public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
		public Card createFromParcel(Parcel in) {
			return new Card(in);
		}

		public Card[] newArray(int size) {
			return new Card[size];
		}
	};

}
