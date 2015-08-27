package com.naji.shoppinglist;

import java.util.List;
 

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	public static TouchingViewPager pager;
	static StoreAdapter adapter;
	static ShoppingDataSource datasource;
	private Menu menuActionBar;
	public static Boolean inShopMode = false;
	Store storeItem = null;
	ShoppingItem shopItem = null;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 
		datasource = new ShoppingDataSource(this);
		datasource.open();
		pager = (TouchingViewPager) findViewById(R.id.viewPager);
		setDisplayHomeAsUpEnabled(false);

	pager.addOnPageChangeListener(new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) { 
			setDisplayHomeAsUpEnabled(position > 0);
 			setActionBarButtons();
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) { 
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	 
	});
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


		fillStoresAdapter();
	}

	private void fillStoresAdapter() {
		List<Store> values = datasource.getAllStores();

		adapter = new StoreAdapter(this, android.R.layout.simple_list_item_1,
				values);

	}

	private void setDisplayHomeAsUpEnabled(Boolean Enabled) {
		getActionBar().setDisplayShowTitleEnabled(Enabled);
		getActionBar().setDisplayHomeAsUpEnabled(Enabled);
		
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity, menu);

		menu.findItem(R.id.action_shop_mode).setVisible(true);
		menu.findItem(R.id.action_unstrikeAll).setVisible(false);
		menu.findItem(R.id.action_payment).setVisible(false);
		menuActionBar = menu;
		return super.onCreateOptionsMenu(menu);

	}

 
	private void setActionBarButtons() { 
		
		if (pager.getCurrentItem() == 0) {
			getActionBar().setTitle("");
			menuActionBar.findItem(R.id.action_unstrikeAll).setVisible(false);
			menuActionBar.findItem(R.id.action_shop_mode).setVisible(true);
			menuActionBar.findItem(R.id.action_add_new).setVisible(true);
			menuActionBar.findItem(R.id.action_payment).setVisible(false);
		} else {
			getActionBar().setTitle(ShoppingListFragment.CurrentStore.getStoreName());
			menuActionBar.findItem(R.id.action_shop_mode).setVisible(false);
			menuActionBar.findItem(R.id.action_unstrikeAll).setVisible(
					inShopMode);
			menuActionBar.findItem(R.id.action_payment).setVisible(
					inShopMode);
			menuActionBar.findItem(R.id.action_add_new).setVisible(
					!inShopMode);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			pager.setCurrentItem(0);
			return true;
		case R.id.action_add_new:
			
			if (pager.getCurrentItem() == 0) {
				addNewStore();
			} else {
				addNewItem();
			}

			return true;
		case R.id.action_payment:
			Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_shop_mode:
			setShopMode();
			return true;
		case R.id.action_unstrikeAll:
			if (MainActivity.inShopMode) {
				ShoppingListFragment.unstrikeAll();
				fillStoresAdapter();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void setShopMode() {
		// update shop mode status
		if (inShopMode) {
			inShopMode = false;
			menuActionBar.findItem(R.id.action_shop_mode).setIcon(
					getResources().getDrawable(R.drawable.shop_mode_off));
			Toast.makeText(this, "Shop Mode OFF", Toast.LENGTH_SHORT).show();
		} else {
			inShopMode = true;
			menuActionBar.findItem(R.id.action_shop_mode).setIcon(
					getResources().getDrawable(R.drawable.shop_mode_on));
			Toast.makeText(this, "Shop Mode ON", Toast.LENGTH_SHORT).show();
		}
	}
 
	private void addNewStore() {
		LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
		View dialogView = layoutInflater.inflate(R.layout.item_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(dialogView);
		TextView tv = (TextView) dialogView.findViewById(R.id.textView);
		tv.setText(getResources().getString(R.string.store_name));
		final EditText input = (EditText) dialogView
				.findViewById(R.id.userInput);

		alertDialogBuilder
				.setCancelable(false)
				.setNegativeButton(
						getResources().getString(R.string.action_ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								String itemName = input.getText().toString();
								if (itemName.length() > 0) {
									storeItem = datasource.createStore(input
											.getText().toString());
									adapter.add(storeItem);
									adapter.notifyDataSetChanged();
								}
							}
						})
				.setPositiveButton(
						getResources().getString(R.string.action_cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
		AlertDialog dialog = alertDialogBuilder.create();
		// show soft keyboard
		dialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				AppUtils.showSoftKeboard(getApplicationContext(), input);
			}
		});
		dialog.show();

	}

	private void addNewItem() {
		LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
		View dialogView = layoutInflater.inflate(R.layout.item_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(dialogView);
		TextView tv = (TextView) dialogView.findViewById(R.id.textView);
		tv.setText(getResources().getString(R.string.item_name));
		final EditText input = (EditText) dialogView
				.findViewById(R.id.userInput);

		alertDialogBuilder
				.setCancelable(false)
				.setNegativeButton(
						getResources().getString(R.string.action_ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) { 
								String itemName = input.getText().toString();
								if (itemName.length() > 0) {
									shopItem = datasource.createShoppingItem(
											StoreListFragment.currentStore
													.getStoreId(), input
													.getText().toString());
									ShoppingListFragment
											.updateAdapter(shopItem);
								}
							}
						})
				.setPositiveButton(
						getResources().getString(R.string.action_cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
		AlertDialog dialog = alertDialogBuilder.create();
		// show soft keyboard
		dialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				AppUtils.showSoftKeboard(getApplicationContext(), input);
			}
		});
		dialog.show();

	}

}
