package myproject.facebook.gplus.twitter.linkedin;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * @author Shekhar Reddy
 * Fragment that shows Social Network site, reused for all Social Networks
 */

public class SocialNetworkFragment extends Fragment {
	private static final String TAG = "SocialNetworkFragment";

	private WebView socialNetworkWebView;
	private View mLoadingSpinner;
	private String socailNetworkURL;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	/*
	 *  Social Network URL Set
	 */
	void setURL(String URL){
		socailNetworkURL = URL;
	}

	/*
	 *  Social Network URL Get
	 */
	private String getURL(){
		return socailNetworkURL;
	}

	/*
	 *  Initializing Views - Spinner and WebView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.webview_common, container, false);
		mLoadingSpinner = root.findViewById(R.id.loading_spinner);
		socialNetworkWebView = (WebView) root.findViewById(R.id.webview);
		socialNetworkWebView.setWebViewClient(socailNetworkWebViewClient);
		return root;
	}

	@Override
	public void onStop() {
		super.onStop();
		socialNetworkWebView.stopLoading();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		socialNetworkWebView.getSettings().setJavaScriptEnabled(true);
		socialNetworkWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
		socialNetworkWebView.loadUrl(getURL());
	}

	/*
	 *  Creating Refresh menu from menu_refersh xml
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_refresh, menu);
	}

	/*
	 *  Handling Refresh menu, Current Socail Network Webview will be refreshed 
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_refresh) {
			socialNetworkWebView.reload();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 *  WebViewClient to handle loading lifecycle, showing spinner while loading and reporting errors if any.
	 */
	private final WebViewClient socailNetworkWebViewClient = new WebViewClient() {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mLoadingSpinner.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			mLoadingSpinner.setVisibility(View.GONE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description,
				String failingUrl) {
			Log.e(TAG, "Error " + errorCode + ": " + description + "failingUrl :: " + failingUrl);
			Toast.makeText(view.getContext(), "Error " + errorCode + ": " + description, Toast.LENGTH_LONG).show();
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
		
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed() ;
		}
		
	};

}

