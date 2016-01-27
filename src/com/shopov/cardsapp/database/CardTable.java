package com.shopov.cardsapp.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class CardTable {
	
	public static final String TABLE_NAME = "cards";
	
	public static class CardColumns implements BaseColumns {
		public static final String FIRST_NAME = "first_name";
		public static final String LAST_NAME = "last_name";
		public static final String EXPIRY = "expiry";
		public static final String CLUB_NAME = "club_name";
		public static final String CARD_NUMBER = "card_number";
		public static final String SAVINGS = "savings";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		/*StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + CardTable.TABLE_NAME + " (");
		sb.append(CardColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(CardColumns.FIRST_NAME + " TEXT NOT NULL, ");
		sb.append(CardColumns.LAST_NAME + " TEXT NOT NULL, ");
		sb.append(CardColumns.EXPIRY + " TEXT NOT NULL, ");
		sb.append(CardColumns.CLUB_NAME + " TEXT NOT NULL, ");
		sb.append(CardColumns.CARD_NUMBER + " TEXT NOT NULL, ");
		sb.append(CardColumns.SAVINGS + " REAL NOT NULL DEFAULT 0 ");
		sb.append(");");
		
		db.execSQL(sb.toString());*/
	}

}
