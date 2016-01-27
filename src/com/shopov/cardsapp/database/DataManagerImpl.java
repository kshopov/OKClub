package com.shopov.cardsapp.database;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shopov.cardsapp.model.Organisation;

public class DataManagerImpl implements DataManager {

	private Context context = null;

	private SQLiteDatabase db = null;

	private OrganisationDao organisationDao = null;
	//private CardDao cardDao = null;

	public DataManagerImpl(Context context) {
		this.context = context;

		SQLiteOpenHelper openHelper = new DBOpenHelper(this.context);
		db = openHelper.getWritableDatabase();

		organisationDao = new OrganisationDao(db);
		//cardDao = new CardDao(db);
	}

	@Override
	public Organisation getOrganisation(long organisationId) {
		return organisationDao.get(organisationId);
	}

	@Override
	public List<Organisation> getAllOrganisations() {
		return organisationDao.getAll();
	}

	@Override
	public long saveOrganisation(Organisation organisation) {
		return organisationDao.save(organisation);
	}

	@Override
	public long deleteOrganisation(Organisation organisation) {
		return organisationDao.delete(organisation);
	}

	@Override
	public long updateOrganisation(Organisation organisation) {
		return organisationDao.update(organisation);
	}
	
	public List<Organisation> getOrganisations(String city, String category, String subCategory) {
		return organisationDao.getOrganisations(city, category, subCategory);
	}
	
	public List<Organisation> getPromoOrganisations(String city, String category, String subCategory) {
		return organisationDao.getPromoOrganisations(city, category, subCategory);
	}

	/*@Override
	public Card getCard(Long cardId) {
		return cardDao.get(cardId);
	}

	@Override
	public List<Card> getAllCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long saveCard(Card card) {
		return cardDao.save(card);
	}

	@Override
	public long deleteCard(Card card) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long updateCard(Card card) {
		return cardDao.update(card);
	}*/

}
