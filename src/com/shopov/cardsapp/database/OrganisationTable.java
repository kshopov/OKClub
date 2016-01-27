package com.shopov.cardsapp.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class OrganisationTable {
	
	public static final String TABLE_NAME = "organisations";
	
	public static class OrganisationColumns implements BaseColumns {
		public static final String NAME = "name";
		public static final String ADDRESS = "address";
		public static final String EMAIL = "email";
		public static final String WEB = "webPage";
		public static final String PHONE_NUMBER = "phoneNumber";
		public static final String IS_PROMO = "isPromo";
		public static final String DISCOUNT = "discount";
		public static final String RATING = "rating";
		public static final String CITY = "city";
		public static final String BRANCH = "branch";
		public static final String GPS_LON = "longtitude";
		public static final String GPS_LAT = "latitude";
		public static final String CATEGORY = "category";
		public static final String SUB_CATEGOTY = "sub_category";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + OrganisationTable.TABLE_NAME + " (");
		sb.append(OrganisationColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(OrganisationColumns.NAME + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ADDRESS + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.EMAIL + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.WEB + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.PHONE_NUMBER + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.IS_PROMO + " INTEGER NOT NULL DEFAULT 0, ");
		sb.append(OrganisationColumns.DISCOUNT + " INTEGER NOT NULL DEFAULT 0, ");
		sb.append(OrganisationColumns.RATING + " REAL NOT NULL DEFAULT 0.0, ");
		sb.append(OrganisationColumns.CITY + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.BRANCH + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.GPS_LON + " REAL NOT NULL DEFAULT 0.0, ");
		sb.append(OrganisationColumns.GPS_LAT + " REAL NOT NULL DEFAULT 0.0, ");
		sb.append(OrganisationColumns.CATEGORY + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.SUB_CATEGOTY + " TEXT NOT NULL ");
		sb.append(");");
		
		db.execSQL(sb.toString());
	}

}
