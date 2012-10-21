package myproject.facebook.gplus.twitter.linkedin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
/**
 * @author Shekhar Reddy
 * About Activity that shows the application info, version and logo.
 */
public class AboutActivity extends Activity {

	private static final String TAG = "AboutActivity";

	private TextView versionNameView;
	private String versionName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		versionNameView = (TextView) findViewById(R.id.about_version);
		setVersionName();
	}

	/*
	 * Reads VersionName from PackageManager and sets to Textview
	 */
	private void setVersionName(){
		try {
			versionName = getPackageManager().getPackageInfo(getPackageName(), 0 ).versionName;
		}
		catch(Exception e){
			Log.e(TAG, "Error in reading VersionName" + e);
			versionName = "0.0";
		}		
		versionName = getString(R.string.about_version, versionName);
		versionNameView.setText(versionName);
	}
}

