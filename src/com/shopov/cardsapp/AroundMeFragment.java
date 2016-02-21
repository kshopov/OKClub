package com.shopov.cardsapp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shopov.cardsapp.model.Organisation;

public class AroundMeFragment extends Fragment implements LocationListener {
	private static final String KEY_CONTENT = "Around Me";
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	private String mContent = "TEST FRAGMENT";

	private String[] distances = { "0.5км", "1км", "2км", "2км+" };
	private CardsApp app = null;
	private List<Organisation> organisationsList = new ArrayList<Organisation>();
	private HashSet<String> categories = new HashSet<String>();
	private HashSet<String> subcategories = new HashSet<String>();
	private List<String> listCategories = null;
	private List<String> listSubCategories = null;
	
	private MapView mapView;
	private GoogleMap map;
	private Circle myLocationCircle = null;

	private LocationManager locationManager;
	private String provider;
	
	private double gpsLongtitude;
	private double gpsLatitude;
	private double radius = 500;

	private Spinner subCategoriesSpinner = null;
	private Spinner categoriesSpinner = null;	
	private Spinner distanceSpinner = null;
	private Button findClosestOrganisationsButton = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
		
		app = (CardsApp) getActivity().getApplication();
        organisationsList = app.getDataManager().getAllOrganisations();
        
        for (Organisation organisation : organisationsList) {
			categories.add(organisation.getCategory());
			subcategories.add(organisation.getSubCategory());
		}
        
        listCategories = new ArrayList<String>(categories);
        listSubCategories = new ArrayList<String>(subcategories);
        
        listCategories.add(0, getString(R.string.all));
        listSubCategories.add(0, getString(R.string.all));

		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		// Define the criteria how to select the location provider -> use
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
		final View v = inflater
				.inflate(R.layout.aroundme_fragment_layout, null);

		categoriesSpinner = (Spinner) v.findViewById(R.id.branches_spinner);
    	ArrayAdapter<String> branchesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listCategories);
    	categoriesSpinner.setAdapter(branchesAdapter);
    	
    	subCategoriesSpinner = (Spinner) v.findViewById(R.id.sub_categories_spinner);
    	ArrayAdapter<String> subCategoriesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSubCategories);
    	subCategoriesSpinner.setAdapter(subCategoriesAdapter);

		distanceSpinner = (Spinner) v.findViewById(R.id.distance_spinner);
		distanceSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, distances));
		distanceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					radius = 500;
					break;
				case 1:
					radius = 1000;
					break;
				case 2:
					radius = 2000;
					break;
				case 3:
					radius = Double.MAX_VALUE;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});

		findClosestOrganisationsButton = (Button) v
				.findViewById(R.id.search_button);

		final Location organisationLocation = new Location("");
		organisationLocation.setLatitude(42.142781);
		organisationLocation.setLongitude(24.791481);

		final Location currentLocation = new Location("");
		currentLocation.setLongitude(gpsLongtitude);
		currentLocation.setLatitude(gpsLatitude);
		findClosestOrganisationsButton
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						map.clear();
						myLocationCircle = drawCircle(new LatLng(gpsLatitude,
								gpsLongtitude));
						addMarkers();
					}
				});

		if (isGooglePlayServiceAvailable()) {
			mapView = (MapView) v.findViewById(R.id.map_view);
			mapView.onCreate(savedInstanceState);

			// Gets to GoogleMap from the MapView and does initialization stuff
			map = mapView.getMap();
			map.setMyLocationEnabled(true);
			map.getUiSettings().setMyLocationButtonEnabled(true);
			myLocationCircle = drawCircle(new LatLng(gpsLatitude, gpsLongtitude));

			addMarkers();

			MapsInitializer.initialize(this.getActivity());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					new LatLng(gpsLatitude, gpsLongtitude), 14);
			map.animateCamera(cameraUpdate);
		}

		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	@Override
	public void onResume() {
		mapView.onResume();
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	public void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override
	public void onLocationChanged(Location location) {
		gpsLatitude = location.getLatitude();
		gpsLongtitude = location.getLongitude();
		myLocationCircle = drawCircle(new LatLng(gpsLatitude, gpsLongtitude));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	private void addMarkers() {
		Location myLocation = new Location("");
		myLocation.setLatitude(gpsLatitude);
		myLocation.setLongitude(gpsLongtitude);
		for (Organisation org : organisationsList) {
			Location organisationLocation = new Location("");
			organisationLocation.setLatitude(org.getGpsLatitude());
			organisationLocation.setLongitude(org.getGpsLongtitude());
			if (calculateDistance(myLocation, organisationLocation) <= radius) {
				map.addMarker(new MarkerOptions()
						.position(new LatLng(org.getGpsLatitude(), org.getGpsLongtitude())).title(org.getName()));
			}
		}
	}

	private Circle drawCircle(LatLng ll) {
		if (myLocationCircle != null) {
			myLocationCircle.remove();
		}
		CircleOptions options = new CircleOptions()
				.center(ll)
				.radius(radius)
				.fillColor(0x330000FF)
				.strokeColor(Color.BLUE)
				.strokeWidth(3);

		return map.addCircle(options);
	}

	private boolean isGooglePlayServiceAvailable() {
		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity());

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					getActivity(), GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		} else {
			Toast.makeText(getActivity(),
					getText(R.string.google_play_not_available),
					Toast.LENGTH_SHORT).show();
		}

		return false;
	}

	private float calculateDistance(Location currentLocation,
			Location organisationLocation) {
		return currentLocation.distanceTo(organisationLocation);
	}

}
