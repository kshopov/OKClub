package com.shopov.cardsapp;

import com.shopov.cardsapp.model.Card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SavingsFragment extends Fragment {
	private static final String KEY_CONTENT = "Savings fragment";
	private String mContent = "TEST FRAGMENT";
	
	private Button calculateDiscountButton = null;
	private Button saveButton = null;
	private TextView totalSavingsTextView = null;
	private TextView currentSavingsTextView = null;
	private EditText totalSumEditText = null;
	private EditText percentEditText = null;
	private LinearLayout savingsLayout = null;
	
	
	private double savings = 0.0;
	private double totalSum = 0.0;
	private double percent = 0.0;
	private boolean isSaveButtonCreated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.savings_fragment_layout, null);
    	
    	totalSavingsTextView = (TextView) v.findViewById(R.id.savings_total_textview);
    	currentSavingsTextView = (TextView) v.findViewById(R.id.current_savings_textview);
    	totalSumEditText = (EditText) v.findViewById(R.id.total_sum_edit_text);
    	percentEditText = (EditText) v.findViewById(R.id.percent_edit_text);
    	savingsLayout = (LinearLayout) v.findViewById(R.id.savings_layout);
    	
/*    	if (card != null ) {
    		totalSavingsTextView.setText(getText(R.string.total_savings) + 
    				" " + String.valueOf(card.getSavings()) + getText(R.string.leva));
    	} else {
    		totalSavingsTextView.setText("Няма намерена активна карта");
    	}
    	calculateDiscountButton = (Button) v.findViewById(R.id.calculate_discount_button);
    	calculateDiscountButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calculateDiscount();
				if (!isSaveButtonCreated) {
					isSaveButtonCreated = true;
					saveButton = new Button(getActivity());
					saveButton.setText("Запази");
					savingsLayout.addView(saveButton);
					saveButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							double totalSavings = card.getSavings();
							totalSavings += savings;
							card.setSavings(totalSavings);
							if (app.getDataManager().updateCard(card) > 0) {
								Log.i("CARD", "id " + card.getId() + "first_name" + card.getFirstName() + " last name " + card.getLastName() + " savings " + card.getSavings());
								Toast.makeText(getActivity(), "Спестяванията са успешно добавени!", Toast.LENGTH_LONG).show();
								totalSavingsTextView.setText(getText(R.string.total_savings) + 
					    				" " + String.valueOf(card.getSavings()) + getText(R.string.leva));				
							}
						}
					});
				}
			}
		});*/
    	
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
    
    private void calculateDiscount() {
    	totalSum = Double.valueOf(totalSumEditText.getText().toString());
    	percent = Double.valueOf(percentEditText.getText().toString());
    	savings = totalSum * percent / 100;
    	currentSavingsTextView.setText("Текущо: " + savings);
    }
    
}
