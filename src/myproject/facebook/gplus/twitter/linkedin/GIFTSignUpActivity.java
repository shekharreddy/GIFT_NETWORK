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
import android.widget.Toast;

/**
 * @author Shekhar Reddy
 * An Activity to SignUp having UserName, Password, Confirm Password and SignUp widgets.
 */
public class GIFTSignUpActivity extends Activity{

	private static final String TAG = "GIFTSignUpActivity";

	private EditText userNameEdit;
	private EditText passWordEdit;
	private EditText confirmPassWordEdit;
	private Button signUpButton;
	private String userName;
	private String passWord;
	private String confirmPassWord;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*
		 *  This(GIFTSignupActivity) will be skipped if user already signedup and GIFTLoginActivity will be shown.
		 */
		if(AccountManagerUserInfo.getUserNameFromPref(getApplicationContext()) != null){
			Intent intent = new Intent(getApplicationContext(), GIFTSignInActivity.class);
			startActivity(intent); 	 
			finish();
		} else {		
			setContentView(R.layout.gift_signup);
			setTitle(R.string.title_activity_signup_gift);
			userNameEdit = (EditText) findViewById(R.id.username_edit);
			passWordEdit = (EditText) findViewById(R.id.password_edit);
			confirmPassWordEdit = (EditText) findViewById(R.id.confirm_password_edit);
			signUpButton = (Button) findViewById(R.id.signup_button);

			setUserNamefromDevice();
			userNameEdit.addTextChangedListener(inputTextWatcher);
			passWordEdit.addTextChangedListener(inputTextWatcher);
			confirmPassWordEdit.addTextChangedListener(inputTextWatcher);


		}
	}

	/*   
	 *   TextChangeListener to enable/disable SignUp button.
	 *   Signup button will be enabled only if user enters data in all fileds	 *   
	 */

	private TextWatcher inputTextWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable arg0) {
			userName = userNameEdit.getText().toString().trim();
			passWord = passWordEdit.getText().toString().trim();
			confirmPassWord = confirmPassWordEdit.getText().toString().trim();

			if(userName.isEmpty() || passWord.isEmpty() || confirmPassWord.isEmpty()) {
				signUpButton.setClickable(false);
				signUpButton.setBackgroundResource(R.drawable.btn_bg_disabled);
			} else {
				signUpButton.setClickable(true);
				signUpButton.setBackgroundResource(R.drawable.btn_background);
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
	 *  Validating User entered details 
	 */
	public void validateSignUp(View view) {
		userName = userNameEdit.getText().toString().trim();
		passWord = passWordEdit.getText().toString().trim();
		confirmPassWord = confirmPassWordEdit.getText().toString().trim();
		if(!passWord.equals(confirmPassWord)) {			
			showLoginFailDialog();
		} else {		
			boolean commitStatus = AccountManagerUserInfo.saveUserInfoToPref(getApplicationContext(), userName, passWord);
			if(commitStatus){
				Toast.makeText(this, R.string.signup_result_msg, Toast.LENGTH_LONG).show();		        
				Intent intent = new Intent(getApplicationContext(), GIFTSignInActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	/*
	 *    Show Password and Confirm Password mismatch AlertDialog
	 *    Dismissed on selecting OK button
	 */
	public void showLoginFailDialog() {
		final AlertDialog passwordMismatchDialog = new AlertDialog.Builder(GIFTSignUpActivity.this)
		.setIcon(R.drawable.icon_alert)
		.setTitle(R.string.dialog_title_signup_fail)
		.setMessage(R.string.dialog_fields_password_mismatch_message)
		.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		})
		.create();
		passwordMismatchDialog.show();
	}

	/*
	 *   Username will be suggested from device's AccountManager for Signup
	 */
	private void setUserNamefromDevice(){
		Log.i(TAG, "UserName set from AccountManager");
		userNameEdit.setText(AccountManagerUserInfo.getUserInfo(getApplicationContext()));
	}

}