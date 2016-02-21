package com.shopov.cardsapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

import com.shopov.cardsapp.adapters.OrganisationsAdapter;
import com.shopov.cardsapp.model.Organisation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class PromoFragment extends Fragment implements LocationListener {
	private static final String KEY_CONTENT = "TestFragment:Content";
	private String mContent = "TEST FRAGMENT";

	private LocationManager locationManager;
	private String provider;

	private double gpsLongtitude;
	private double gpsLatitude;

	private CardsApp app = null;
	private List<Organisation> organisationsList = new ArrayList<Organisation>();
	private HashSet<String> cities = new HashSet<String>();
	private HashSet<String> categories = new HashSet<String>();
	private HashSet<String> subcategories = new HashSet<String>();
	private List<String> listCities = null;
	private List<String> listCategories = null;
	private List<String> listSubCategories = null;

	private Spinner subCategoriesSpinner = null;
	private Spinner citiesSpinner = null;
	private Spinner categoriesSpinner = null;
	private OrganisationsAdapter adapter = null;

	ListView listView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}

		app = (CardsApp) getActivity().getApplication();
		organisationsList = app.getDataManager().getPromoOrganisations(null, null, null);

		for (Organisation organisation : organisationsList) {
			cities.add(organisation.getCity());
			categories.add(organisation.getCategory());
			subcategories.add(organisation.getSubCategory());
		}

		listCities = new ArrayList<String>(cities);
		listCategories = new ArrayList<String>(categories);
		listSubCategories = new ArrayList<String>(subcategories);

		listCities.add(0, getString(R.string.all));
		listCategories.add(0, getString(R.string.all));
		listSubCategories.add(0, getString(R.string.all));

		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			gpsLatitude = location.getLatitude();
			gpsLongtitude = location.getLongitude();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.organisations_fragment_layout,
				container, false);
		listView = (ListView) v.findViewById(R.id.list);

		citiesSpinner = (Spinner) v.findViewById(R.id.cities_spinner);
		ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, listCities);
		citiesSpinner.setAdapter(citiesAdapter);

		categoriesSpinner = (Spinner) v.findViewById(R.id.branches_spinner);
		ArrayAdapter<String> branchesAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				listCategories);
		categoriesSpinner.setAdapter(branchesAdapter);

		subCategoriesSpinner = (Spinner) v
				.findViewById(R.id.sub_categories_spinner);
		ArrayAdapter<String> subCategoriesAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				listSubCategories);
		subCategoriesSpinner.setAdapter(subCategoriesAdapter);

		Location myLocation = new Location("");
		myLocation.setLatitude(gpsLatitude);
		myLocation.setLongitude(gpsLongtitude);
		adapter = new OrganisationsAdapter(getActivity(), organisationsList,
				myLocation);

		listView.setAdapter(adapter);

		final Button searchButton = (Button) v.findViewById(R.id.search_button);
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String city = citiesSpinner.getSelectedItem().toString();
				String category = categoriesSpinner.getSelectedItem().toString();
				String subCategory = subCategoriesSpinner.getSelectedItem().toString();
				
				if (city.equals("������")) city = null;
				if (category.equals("������")) category = null;
				if (subCategory.equals("������")) subCategory = null;
				
				organisationsList.removeAll(organisationsList);
				organisationsList.addAll(app.getDataManager().getPromoOrganisations(city, category, subCategory));
				
				adapter.notifyDataSetChanged();
			}
		});

		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	@SuppressWarnings("unused")
	private class SendData extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
		}

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}
}
