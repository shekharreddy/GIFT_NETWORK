package myproject.facebook.gplus.twitter.linkedin;


import myproject.facebook.gplus.twitter.linkedin.utils.AccountManagerUserInfo;
import myproject.facebook.gplus.twitter.linkedin.utils.AppUtils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

/**  @author Shekhar Reddy
 *   FragmentActivity that displays multiple social network sites.
 *   @see SocialNetworkFragment.java
 *   
 */

public class GIFTMainActivity extends FragmentActivity {

	private static final String TAG = "GIFTMainActivity";

	/*
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * social networks.
	 */
	private SocialSitesPagerAdapter mSocailNetworkPagerAdapter;

	/*
	 * The {@link ViewPager} that will host the section contents for different social networks.
	 */
	private ViewPager mSocialViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gift_main);

		// Create the adapter that will return a fragment for each social network.
		mSocailNetworkPagerAdapter = new SocialSitesPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mSocialViewPager = (ViewPager) findViewById(R.id.pager);
		mSocialViewPager.setAdapter(mSocailNetworkPagerAdapter);
	}

	/*
	 *  check Internet Conncetion status on onResume
	 */
	@Override
	protected void onResume() {
		super.onResume();
		checkNetworkStatus();
	}

	/*
	 *   Display toast message if there is no internet connection available
	 */
	private void checkNetworkStatus(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (!(networkInfo != null && networkInfo.isConnected())) {
			Log.i(TAG, "Network not available");
			Toast.makeText(this, R.string.dialog_no_network_message, Toast.LENGTH_LONG).show();		        
		}
	}

	/*
	 * Create Menu options - about and signout
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	/*
	 *  Handling Menu options about and signout
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle menu item selection
		switch (item.getItemId()) {
		case R.id.menu_about:
			// calling About Activity
			Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
			startActivity(intent); 	 
			return true;
		case R.id.menu_signout:
			// User Singout state change in Preference
			AccountManagerUserInfo.setSignOutStatusToPref(getApplicationContext());
			Toast.makeText(this, R.string.singout_success_message, Toast.LENGTH_SHORT).show();		        
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * A {@link SocialSitesPagerAdapter} that returns a fragment for social network - Facebook, GooglePlus, Twitter, Linkedin
	 */
	public class SocialSitesPagerAdapter extends FragmentPagerAdapter {

		public SocialSitesPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				final SocialNetworkFragment facebookFragment = new SocialNetworkFragment();
				facebookFragment.setURL(AppUtils.FACEBOOK_URL);
				return facebookFragment;

			case 1:
				final SocialNetworkFragment googlePlusFragment = new SocialNetworkFragment();
				googlePlusFragment.setURL(AppUtils.GOOGLEPLUS_URL);
				return googlePlusFragment;
			case 2:
				final SocialNetworkFragment titterFragment = new SocialNetworkFragment();
				titterFragment.setURL(AppUtils.TWITTER_URL);
				return titterFragment;

			case 3:
				final SocialNetworkFragment linkedInFragment = new SocialNetworkFragment();
				linkedInFragment.setURL(AppUtils.LINKEDIN_URL);
				return linkedInFragment;
			}
			return null;		
		}

		@Override
		public int getCount() {
			// No of Social Network Fragments
			return 4;
		}

		/*
		 * Social Network Page Titles
		 */

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0: return getString(R.string.title_facebook);
			case 1: return getString(R.string.title_googleplus);
			case 2: return getString(R.string.title_twitter);
			case 3: return getString(R.string.title_linkedin);
			}
			return null;
		}
	}
}
