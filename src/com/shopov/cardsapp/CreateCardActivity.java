package com.shopov.cardsapp;

import org.json.JSONException;
import org.json.JSONObject;

import com.shopov.cardsapp.model.Card;
import com.shopov.cardsapp.utils.Validator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateCardActivity extends Activity {
	
	private static final String URL = "http://www.google.bg";
	private EditText emailEditText = null;
	private EditText passwordEditText = null;
	private TextView userDetailsTextView = null;
	private ProgressDialog pd = null;
	private SharedPreferences settings;
	
	private Card card = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings = getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
		
		if(settings.getString("email", null) == null) {
			setContentView(R.layout.create_card_activity);
			emailEditText = (EditText) findViewById(R.id.email_editText);
			passwordEditText = (EditText) findViewById(R.id.password_editText);
		} else {
			setContentView(R.layout.user_details_layout);
			userDetailsTextView = (TextView) findViewById(R.id.user_details_textView);
			userDetailsTextView.setText(
					  "Име: " + settings.getString("name", "") + "\n"
					+ "Емаил: " + settings.getString("email", "") + "\n"
					+ "Номер на карта: " + settings.getString("card_number", "") + "\n"
					+ "Валидност: " + settings.getString("expiry", ""));
		}
		
		card = new Card();
	}
	
	public void activateCard(View v) {
		if (isValidData()) {
			pd = new ProgressDialog(this);
			pd.setMessage(getText(R.string.activating));
			pd.show();
			ActivateCardTask activateCardTask = new ActivateCardTask();
			activateCardTask.execute(URL);
		}
	}
	
	private boolean isValidData() {
		if (!Validator.isValidEmail(emailEditText)) {
			emailEditText.setError(getText(R.string.wrong_email));
			emailEditText.requestFocus();
			return false;
		} else if (Validator.isEmpty(passwordEditText)) {
			passwordEditText.setError(getText(R.string.empty_field_error));
			passwordEditText.requestFocus();
			return false;
		}
		
		return true;
	}
	
	private void createAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getText(R.string.successful_activation));
		builder.setNeutralButton(R.string.main, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent mainIntent = new Intent(CreateCardActivity.this, MainActivity.class);
				startActivity(mainIntent);
			}
		});
		builder.show();
	}
	
	private class ActivateCardTask extends AsyncTask<String, Void, JSONObject>{

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject jsonObject = new JSONObject();
			JSONObject results = null;
			String url = params[0];
			try {
				jsonObject.put("email", emailEditText.getText().toString());
				jsonObject.put("password", passwordEditText.getText().toString());
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
			//results = HttpClient.SendHttpPost(url, jsonObject);
			results = new JSONObject();
			try {
				results.put("email", "krasimir.shopov@gmail.com");
				results.put("isActive", 1);
				results.put("serverCode", 1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return results;
		}
		
		//server codes
		//0 wrong user data (email/password)
		
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(CreateCardActivity.this, getText(R.string.server_error), Toast.LENGTH_LONG).show();
			} else {
				try {
					int serverCode = result.getInt("serverCode");
					switch (serverCode) {
					case 0:
						Toast.makeText(CreateCardActivity.this, getText(R.string.wrong_user_data_error), Toast.LENGTH_LONG).show();
						break;
					case 1:
						if (result.getInt("isActive") == 0) {
							Toast.makeText(CreateCardActivity.this, getText(R.string.card_not_active), Toast.LENGTH_LONG).show();
						} else {
							SharedPreferences.Editor editor = settings.edit();
							editor.putString("email", result.getString("email"));
							editor.commit();
						}
						break;
					default:
						break;
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			pd.dismiss();
		}
	}
}
