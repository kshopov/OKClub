package com.shopov.cardsapp.database;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.shopov.cardsapp.database.CardTable.CardColumns;
import com.shopov.cardsapp.model.Card;


public class CardDao implements Dao<Card> {
	
	private static final String INSERT = 
			"INSERT INTO " + CardTable.TABLE_NAME + "("
				+ CardColumns.FIRST_NAME + ", "
				+ CardColumns.LAST_NAME + ", "
				+ CardColumns.EXPIRY + ", "
				+ CardColumns.CLUB_NAME + ", "
				+ CardColumns.CARD_NUMBER + ", "
				+ CardColumns.SAVINGS + ")"
					+ "values (?, ?, ?, ?, ?, ?)";
	
	private SQLiteDatabase db = null;
	private SQLiteStatement insertStatement = null;
	
	public CardDao(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(INSERT);
	}

	@Override
	public long save(Card card) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, card.getFirstName());
		insertStatement.bindString(2, card.getLastName());
		insertStatement.bindString(3, card.getExpiry());
		insertStatement.bindString(4, card.getClubName());
		insertStatement.bindString(5, card.getCardNumber());
		insertStatement.bindDouble(6, card.getSavings());
		
		return insertStatement.executeInsert();
	}

	@Override
	public long update(Card card) {
		long result = 0;
		ContentValues contentValues = new ContentValues();
		contentValues.put(CardColumns.FIRST_NAME, card.getFirstName());
		contentValues.put(CardColumns.LAST_NAME, card.getLastName());
		contentValues.put(CardColumns.CLUB_NAME, card.getClubName());
		contentValues.put(CardColumns.EXPIRY, card.getExpiry());
		contentValues.put(CardColumns.CARD_NUMBER, card.getCardNumber());
		contentValues.put(CardColumns.SAVINGS, card.getSavings());
		
		result = db.update(CardTable.TABLE_NAME,
				contentValues, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(card.getId()) });
		
		return result;
	}

	@Override
	public long delete(Card card) {
		long result = 0;
		if (card.getId() > 0) {
			result = db.delete(CardTable.TABLE_NAME,
					BaseColumns._ID + " = ?",
					new String[] {String.valueOf(card.getId())});
		}
		return result;
	}

	@Override
	public Card get(long id) {
		Card card = null;
		Cursor c = db.query(CardTable.TABLE_NAME,
				null,
				BaseColumns._ID + " = ?",
				new String[] {String.valueOf(id)},
				null, null, null);
		
		if(c.moveToFirst()) {
			card = buildCardFromCursor(c);
		}
		return card;
	}

	@Override
	public List<Card> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Card buildCardFromCursor(Cursor c) {
		Card card = null;
		if (c != null) {
			card = new Card();
			card.setId(c.getLong(0));
			card.setFirstName(c.getString(1));
			card.setLastName(c.getString(2));
			card.setExpiry(c.getString(3));
			card.setClubName(c.getString(4));
			card.setCardNumber(c.getString(5));
			card.setSavings(c.getDouble(6));
		}
		return card;
	}

}
