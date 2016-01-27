package com.shopov.cardsapp.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.shopov.cardsapp.model.Organisation;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

private static final int DATABASE_VERSION = 2;
	
	private Context context;
	
	public DBOpenHelper(Context context) {
		super(context, DataConstants.DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		OrganisationTable.onCreate(db);
		
		OrganisationDao organisationDao = new OrganisationDao(db);
		
		AssetManager am = (AssetManager) context.getAssets();
		try {
			InputStream inputStream = am.open("obekti.txt");
			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader buffReader = new BufferedReader(inputStreamReader);
				String line;
				while ((line = buffReader.readLine()) != null) {
					String separated[] = line.split(";");
					
					
					Organisation organisation = new Organisation();
					organisation.setCategory(separated[0].trim());
					organisation.setSubCategory(separated[1].trim());
					organisation.setName((separated[2]).trim());
					organisation.setCityId(separated[3].trim());
					organisation.setAddress(separated[4].trim());
					organisation.setGpsLatitude(Double.parseDouble(separated[5].trim()));
					organisation.setGpsLongtitude(Double.parseDouble(separated[6].trim()));
					organisation.setEmail(separated[7].trim());
					organisation.setWebPage(separated[8].trim());
					organisation.setPhoneNumber(separated[9].trim());
					organisation.setDiscount(Integer.parseInt(separated[10].trim()));
					organisation.setIsPromo(Integer.parseInt(separated[11].trim()));
					
					organisationDao.save(organisation);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + OrganisationTable.TABLE_NAME);
		this.onCreate(db);
	}
}
