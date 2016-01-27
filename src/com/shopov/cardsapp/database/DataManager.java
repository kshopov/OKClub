package com.shopov.cardsapp.database;

import java.util.List;

import com.shopov.cardsapp.model.Card;
import com.shopov.cardsapp.model.Organisation;

public interface DataManager {
	
	//organisations
	public Organisation getOrganisation(long organisationId);
	public List<Organisation> getAllOrganisations();
	public long saveOrganisation(Organisation organisation);
	public long deleteOrganisation(Organisation organisation);
	public long updateOrganisation(Organisation organisation);
	
	//cards
	/*public Card getCard(Long cardId);
	public List<Card> getAllCards();
	public long saveCard(Card card);
	public long deleteCard(Card card);
	public long updateCard(Card card);*/
}
