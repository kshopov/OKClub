package com.shopov.cardsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.shopov.cardsapp.model.Organisation;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity {
	
	public static final String PREFERENCES_NAME = "ok_club_user_prefs";
	private String[] titles;
	Organisation organisation = new Organisation();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.simple_tabs);
		
		titles = getResources().getStringArray(R.array.titles);

		FragmentPagerAdapter adapter = new GoogleMusicAdapter(
				getSupportFragmentManager());

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		
		/*SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
		
		if(settings.getString("email", null) == null) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle(getText(R.string.card_not_active));
			alertDialog.setPositiveButton(R.string.activate, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent createCardIntent = new Intent(MainActivity.this, CreateCardActivity.class);
					startActivity(createCardIntent);
				}
			});
			alertDialog.setNegativeButton(getText(R.string.later), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			alertDialog.show();
		}*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//menu.add("Create");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent creatCardIntent = new Intent(MainActivity.this, CreateCardActivity.class);
			startActivity(creatCardIntent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("DefaultLocale")
	class GoogleMusicAdapter extends FragmentPagerAdapter implements
			IconPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new CardFragment();
			switch (position) {
			case 0:
				fragment = new CardFragment();
				break;
			case 1:
				fragment = new OrganisationsFragment();
				break;
			case 2:
				fragment = new PromoFragment();
				break;
			case 3:
				fragment = new AroundMeFragment();
				break;
			case 4:
				fragment = new SavingsFragment();
			default:
				break;
			}

			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position % titles.length].toUpperCase();
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public int getIconResId(int index) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
    }
}

