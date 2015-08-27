package com.naji.shoppinglist;

import android.content.Context;
 
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class AppUtils {

	public static void showSoftKeboard(Context context, EditText input) {

		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
	}


}
