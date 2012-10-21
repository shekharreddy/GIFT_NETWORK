package myproject.facebook.gplus.twitter.linkedin.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;

/**
 *  @author Shekhar Reddy
 *  Set and Get UserName and PassWord details into/from preferences, and Suggestion for UserName using AccountManager.
 */

public class AccountManagerUserInfo {
	
	/*
	 *   UserName suggestion for Signup, gets Google UserName from available User Accounts logged into the device.
	 */
	public static String getUserInfo(final Context context){
		AccountManager am = AccountManager.get(context); 
		try{
			Account[] accounts = am.getAccountsByType("com.google");
			return accounts[0].name;
		} catch(ArrayIndexOutOfBoundsException e){
			return "";
		} catch(Exception e){
			return "";
		}
	}
	
	/*
	 *  User's Sign In status from preference. 
	 *  Returns true - if Singed in else fasle
	 */
	public static boolean getSignInStatusFromPref(final Context context) {
		SharedPreferences userPrefs = context.getSharedPreferences(AppUtils.USERINFO_PREFERENCE, Context.MODE_PRIVATE);
		boolean prefix = userPrefs.getBoolean(AppUtils.KEY_SIGNINSTATUS_PREF, false);
		return prefix;
	}

	/*
	 *  Get User's Sign In status from preference. 
	 *  true - if signed in
	 */
	public static void setSignInStatusToPref(final Context context) {
		SharedPreferences.Editor userPrefs = context.getSharedPreferences(AppUtils.USERINFO_PREFERENCE, Context.MODE_PRIVATE).edit();
		userPrefs.putBoolean(AppUtils.KEY_SIGNINSTATUS_PREF , true);
		userPrefs.commit();
	}
	
	/*
	 *  Sets Sign In status for the User. 
	 *  false - if sign out
	 */
	public static boolean setSignOutStatusToPref(final Context context) {
		SharedPreferences.Editor userPrefs = context.getSharedPreferences(AppUtils.USERINFO_PREFERENCE, Context.MODE_PRIVATE).edit();
		userPrefs.putBoolean(AppUtils.KEY_SIGNINSTATUS_PREF , false);
		return userPrefs.commit();
	}
	
	/*
	 *  Get User's UserName from the preference  
	 */

	public static String getUserNameFromPref(final Context context) {
		SharedPreferences userPrefs = context.getSharedPreferences(AppUtils.USERINFO_PREFERENCE, Context.MODE_PRIVATE);
		String prefix = userPrefs.getString(AppUtils.KEY_USERNAME_PREF, null);
		if (prefix != null) {
			return prefix;
		} 
		return null;
	}

	/*
	 *  Get User's Password from the preference  
	 */
	public static String getPassWordFromPref(final Context context) {
		SharedPreferences userPrefs = context.getSharedPreferences(AppUtils.USERINFO_PREFERENCE, Context.MODE_PRIVATE);
		String prefix = userPrefs.getString(AppUtils.KEY_PASSWORD_PREF, null);
		if (prefix != null) {
			return prefix;
		} 
		return null;
	}

	/*
	 *  On User SignUp saving User details into preference for later user
	 */
	public static boolean saveUserInfoToPref(final Context context, String userName, String passWord) {
		boolean commitStatus = false;
		SharedPreferences.Editor userPrefs = context.getSharedPreferences(AppUtils.USERINFO_PREFERENCE, Context.MODE_PRIVATE).edit();
		userPrefs.putString(AppUtils.KEY_USERNAME_PREF , userName);
		userPrefs.putString(AppUtils.KEY_PASSWORD_PREF , passWord);
		userPrefs.putBoolean(AppUtils.KEY_SIGNINSTATUS_PREF , false);
		commitStatus = userPrefs.commit();
		return commitStatus;
	}
}
