package com.shopov.cardsapp.adapters;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.shopov.cardsapp.model.Organisation;

public class OrganisationsAdapter extends BaseAdapter {
	
	private List<Organisation> data = null;
	private Organisation tempValues = null;
	private LayoutInflater inflater = null;
	private Location myLocation = null;
	private Context context = null;

	public OrganisationsAdapter(Context context, List<Organisation> data, Location myLocation) {
		this.data = data;
		this.context = context;
		this.myLocation = myLocation;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private static class ViewHolder {
		public ImageView organisationImage;
		public TextView organisationName;
		public TextView organisationAddress;
		public TextView organisationDistance;
		public Button discountButton;
		//public RatingBar organisationRating;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder viewHolder;
		
		if (convertView == null) {
			vi = inflater.inflate(com.shopov.cardsapp.R.layout.list_row_test, null);
			viewHolder = new ViewHolder();
			viewHolder.organisationImage = (ImageView) vi.findViewById(com.shopov.cardsapp.R.id.list_image);
			viewHolder.organisationName = (TextView) vi.findViewById(com.shopov.cardsapp.R.id.organisation_name);
			viewHolder.organisationAddress = (TextView) vi.findViewById(com.shopov.cardsapp.R.id.organisation_address);
			viewHolder.organisationDistance = (TextView) vi.findViewById(com.shopov.cardsapp.R.id.distance_textView);
			viewHolder.discountButton = (Button) vi.findViewById(com.shopov.cardsapp.R.id.discount_Button);
			//viewHolder.organisationRating = (RatingBar) vi.findViewById(com.shopov.cardsapp.R.id.ratingBar);
			
			vi.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) vi.getTag();
		}
		
		if (data.size() <= 0) {
			
		} else {
			tempValues = (Organisation) data.get(position);
			Location organisationLocation = new Location("");
			organisationLocation.setLatitude(tempValues.getGpsLatitude());
			organisationLocation.setLongitude(tempValues.getGpsLongtitude());
			viewHolder.organisationImage.setImageResource(com.shopov.cardsapp.R.drawable.coffee_img);
			viewHolder.organisationName.setText(tempValues.getName());
			viewHolder.organisationAddress.setText(tempValues.getAddress());
			viewHolder.discountButton.setText("" + tempValues.getDiscount() + "%");
			viewHolder.organisationDistance.setText(calculateDistance(organisationLocation) + "\nêì");
			//viewHolder.organisationRating.setRating(tempValues.getRating());
		}
		
		return vi;
	}
	
	private double calculateDistance(Location organisationLocation) {
		return  round(myLocation.distanceTo(organisationLocation) * 0.001, 2);
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}
