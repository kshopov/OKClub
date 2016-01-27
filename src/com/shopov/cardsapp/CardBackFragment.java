package com.shopov.cardsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class CardBackFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.card_back_custom_view, null, false);
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CardFragment cardBackFragment = new CardFragment();
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();
				transaction.replace(R.id.card_back_relativeLayout, cardBackFragment);
				transaction.commit();
			}
		});

		return v;
	}

}
