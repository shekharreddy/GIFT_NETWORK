package myproject.facebook.gplus.twitter.linkedin;


import myproject.facebook.gplus.twitter.linkedin.utils.AccountManagerUserInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Shekhar Reddy
 * An Activity to Login into the Application having UserName, Password and SignIn widgets.
 */
public class GIFTSignInActivity extends Activity{
	private static final String TAG = "GIFTSingInActivity";

	private EditText userNameEdit;
	private EditText passWordEdit;
	private Button signInButton;
	private String userName;
	private String passWord;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*
		 *  This(GIFTLoginActivity) will be skipped if user already signin and GIFTMainActivity will be shown.
		 */
		if(AccountManagerUserInfo.getSignInStatusFromPref(getApplicationContext())) {
			Intent intent = new Intent(getApplicationContext(), GIFTMainActivity.class);
			startActivity(intent); 	 
			finish();
		} else {
			setContentView(R.layout.gift_signin);
			userNameEdit = (EditText) findViewById(R.id.username_edit);
			passWordEdit = (EditText) findViewById(R.id.password_edit);
			signInButton = (Button) findViewById(R.id.signin_button);
			userNameEdit.setText(AccountManagerUserInfo.getUserNameFromPref(getApplicationContext()));
			userNameEdit.addTextChangedListener(inputTextWatcher);
			passWordEdit.addTextChangedListener(inputTextWatcher);
		}

	}

	/*   
	 *   TextChangeListener to enable/disable Sign In button.
	 *   Sign In button will be enabled only if user enters data in all fileds 
	 */
	private TextWatcher inputTextWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable arg0) {
			userName = userNameEdit.getText().toString().trim();
			passWord = passWordEdit.getText().toString().trim();
			if(userName.isEmpty() || passWord.isEmpty()) {
				signInButton.setClickable(false);
				signInButton.setBackgroundResource(R.drawable.btn_bg_disabled);
			} else {
				signInButton.setClickable(true);
				signInButton.setBackgroundResource(R.drawable.btn_background);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}
	};

	/*
	 *    Sign In Failed AlertDialog, will be shown when user enter invalid Username or Password
	 *    Dismissed on selecting OK button
	 */
	public void showLoginFailDialog() {

		final AlertDialog loginFailDialog = new AlertDialog.Builder(GIFTSignInActivity.this)
		.setIcon(R.drawable.icon_alert)
		.setTitle(R.string.dialog_title_signin_fail)
		.setMessage(R.string.dialog_fields_invalid_login_message)
		.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		})
		.create();
		loginFailDialog.show();
	}

	/*
	 *  Validating User entered details - username and password
	 */
	public void validateUserLogin(View view) {
		userName = userNameEdit.getText().toString().trim();
		passWord = passWordEdit.getText().toString().trim();
		if(!userName.equals(AccountManagerUserInfo.getUserNameFromPref(getApplicationContext()))
				|| !passWord.equals(AccountManagerUserInfo.getPassWordFromPref(getApplicationContext()))) {
			showLoginFailDialog();
		} else {
			Log.i(TAG,"Login Success, GIFTMainActivity called from GIFTLoginActivity");
			AccountManagerUserInfo.setSignInStatusToPref(getApplicationContext());
			Intent intent = new Intent(getApplicationContext(), GIFTMainActivity.class);
			startActivity(intent); 	 
			finish();
		}
	}	
}
