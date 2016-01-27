package com.shopov.cardsapp.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.shopov.cardsapp.database.OrganisationTable.OrganisationColumns;
import com.shopov.cardsapp.model.Organisation;

public class OrganisationDao implements Dao<Organisation> {
	
	private SQLiteDatabase db = null;
	private SQLiteStatement insertStatement = null;
	
	private static final String INSERT = "INSERT INTO " + OrganisationTable.TABLE_NAME + "("
			+ OrganisationColumns.NAME + ", "
			+ OrganisationColumns.ADDRESS + ", "
			+ OrganisationColumns.EMAIL + ", "
			+ OrganisationColumns.WEB + ", "
			+ OrganisationColumns.PHONE_NUMBER + ", "
			+ OrganisationColumns.IS_PROMO + ", "
			+ OrganisationColumns.DISCOUNT + ", "
			+ OrganisationColumns.RATING + ", "
			+ OrganisationColumns.CITY + ", "
			+ OrganisationColumns.BRANCH + ", "
			+ OrganisationColumns.GPS_LON + ", "
			+ OrganisationColumns.GPS_LAT + ", "
			+ OrganisationColumns.CATEGORY + ", "
			+ OrganisationColumns.SUB_CATEGOTY + ")"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public OrganisationDao(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(INSERT);
	}

	@Override
	public long save(Organisation type) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, type.getName());
		insertStatement.bindString(2, type.getAddress());
		insertStatement.bindString(3, type.getEmail());
		insertStatement.bindString(4, type.getWebPage());
		insertStatement.bindString(5, type.getPhoneNumber());
		insertStatement.bindLong(6, type.getIsPromo());
		insertStatement.bindLong(7, type.getDiscount());
		insertStatement.bindDouble(8, type.getRating());
		insertStatement.bindString(9, type.getCity());
		insertStatement.bindString(10, type.getBranch());
		insertStatement.bindDouble(11, type.getGpsLongtitude());
		insertStatement.bindDouble(12, type.getGpsLatitude());
		insertStatement.bindString(13, type.getCategory());
		insertStatement.bindString(14, type.getSubCategory());
		
		return insertStatement.executeInsert();
	}

	@Override
	public long update(Organisation organisation) {
		long result = 0;
		ContentValues contentValues = new ContentValues();
		contentValues.put(OrganisationColumns.NAME, organisation.getName());
		contentValues.put(OrganisationColumns.ADDRESS, organisation.getAddress());
		contentValues.put(OrganisationColumns.EMAIL, organisation.getEmail());
		contentValues.put(OrganisationColumns.WEB, organisation.getWebPage());
		contentValues.put(OrganisationColumns.PHONE_NUMBER, organisation.getPhoneNumber());
		contentValues.put(OrganisationColumns.IS_PROMO, organisation.getIsPromo());
		contentValues.put(OrganisationColumns.DISCOUNT, organisation.getDiscount());
		contentValues.put(OrganisationColumns.RATING, organisation.getRating());
		contentValues.put(OrganisationColumns.CITY, organisation.getCity());
		contentValues.put(OrganisationColumns.BRANCH, organisation.getBranch());
		contentValues.put(OrganisationColumns.GPS_LON, organisation.getGpsLongtitude());
		contentValues.put(OrganisationColumns.GPS_LAT, organisation.getGpsLatitude());
		contentValues.put(OrganisationColumns.CATEGORY, organisation.getCategory());
		contentValues.put(OrganisationColumns.GPS_LAT, organisation.getSubCategory());
		
		result = db.update(OrganisationTable.TABLE_NAME, contentValues, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(organisation.getId()) });
		
		return result;
	}

	@Override
	public long delete(Organisation organisation) {
		long result = 0;
		if (organisation.getId() > 0) {
			result = db.delete(OrganisationTable.TABLE_NAME,
					BaseColumns._ID + " = ?",
					new String[] {String.valueOf(organisation.getId())});
		}
		return result;
	}

	@Override
	public Organisation get(long id) {
		Organisation organisation = null;
		Cursor c = db.query(OrganisationTable.TABLE_NAME,
				null,
				BaseColumns._ID + " = ?",
				new String[] {String.valueOf(id)},
				null, null, null);
		
		if(c.moveToFirst()) {
			organisation = buildOrganisationFromCursor(c);
		}
		
		return organisation;
	}

	@Override
	public List<Organisation> getAll() {
		List<Organisation> organisations = new ArrayList<Organisation>();
		Cursor c = db.query(OrganisationTable.TABLE_NAME, null, null, null, null, null, null);
		
		if (c.moveToFirst()) {
			do {
				Organisation organisation = this.buildOrganisationFromCursor(c);
				if (organisation != null) {
					organisations.add(organisation);
				}
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		
		return organisations;
	}
	
	public List<Organisation> getOrganisations(String city, String category, String subCategory) {
		List<Organisation> organisations = new ArrayList<Organisation>();
		List<String> selectionArgsList = new ArrayList<String>();
		StringBuilder stringBuilder = new StringBuilder();
		
		if (city != null) {
			stringBuilder.append("city=?");
			selectionArgsList.add(city);
		} 
		
		if (category != null) {
			if (stringBuilder.length() == 0) {
				stringBuilder.append("category=?");
			} else {
				stringBuilder.append(" AND category=?");
			}
			selectionArgsList.add(category);
		} 
		
		if (subCategory != null) {
			if (stringBuilder.length() == 0) {
				stringBuilder.append("sub_category=?");
			} else {
				stringBuilder.append(" AND sub_category=?");
			}
			selectionArgsList.add(subCategory);
		}
		
		Cursor c = db.query(OrganisationTable.TABLE_NAME, null, stringBuilder.toString(), buildselectionArgs(selectionArgsList), null, null, null);
		
		if (c.moveToFirst()) {
			do {
				Organisation organisation = this.buildOrganisationFromCursor(c);
				if (organisation != null) {
					organisations.add(organisation);
				}
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		
		return organisations;
	}
	
	public List<Organisation> getPromoOrganisations(String city, String category, String subCategory) {
		List<Organisation> organisations = new ArrayList<Organisation>();
		List<String> selectionArgsList = new ArrayList<String>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("isPromo=?");
		selectionArgsList.add("1");
		if (city != null) {
			stringBuilder.append(" AND city=?");
			selectionArgsList.add(city);
		} 
		
		if (category != null) {
			stringBuilder.append(" AND category=?");
			selectionArgsList.add(category);
		} 
		
		if (subCategory != null) {
			stringBuilder.append(" AND sub_category=?");
			selectionArgsList.add(subCategory);
		}
		
		Cursor c = db.query(OrganisationTable.TABLE_NAME, null, stringBuilder.toString(), buildselectionArgs(selectionArgsList), null, null, null);
		
		if (c.moveToFirst()) {
			do {
				Organisation organisation = this.buildOrganisationFromCursor(c);
				if (organisation != null) {
					organisations.add(organisation);
				}
			} while (c.moveToNext());
		}
		if (!c.isClosed()) {
			c.close();
		}
		
		return organisations;
	}
	
	private Organisation buildOrganisationFromCursor(Cursor cursor) {
		Organisation organisation = null;
		if (cursor != null) {
			organisation = new Organisation();
			organisation.setId(cursor.getLong(0));
			organisation.setName(cursor.getString(1));
			organisation.setAddress(cursor.getString(2));
			organisation.setEmail(cursor.getString(3));
			organisation.setWebPage(cursor.getString(4));
			organisation.setPhoneNumber(cursor.getString(5));
			organisation.setIsPromo(cursor.getInt(6));
			organisation.setDiscount(cursor.getInt(7));
			organisation.setCityId(cursor.getString(9));
			organisation.setGpsLongtitude(cursor.getDouble(11));
			organisation.setGpsLatitude(cursor.getDouble(12));
			organisation.setCategory(cursor.getString(13));
			organisation.setSubCategory(cursor.getString(14));
		}
		
		return organisation;
	}
	
	private String[] buildselectionArgs(List<String> selectionArgsList) {
		String selectionArgasArray[] =  new String[selectionArgsList.size()];
		for (int i = 0; i < selectionArgasArray.length; i++) {
			selectionArgasArray[i] = selectionArgsList.get(i);
		}
		
		return selectionArgasArray;
	}

}
