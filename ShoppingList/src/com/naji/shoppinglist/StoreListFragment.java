package com.naji.shoppinglist;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class StoreListFragment extends Fragment {

	ListView listView;
	static Store currentStore = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("ClickableViewAccessibility")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_store_list_fragment, container,
				false);

		listView = (ListView) v.findViewById(R.id.shopList);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				currentStore = (Store) listView.getAdapter().getItem(position);
				ShoppingListFragment.fillShoppingList(getActivity(),
						currentStore);

				((MainActivity) getActivity()).pager.setCurrentItem(1);

			}

		});
		listView.setOnTouchListener(new AdapterView.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				MainActivity.pager.setPagingEnabled(true);
				currentStore = (Store) listView.getAdapter().getItem(
						listView.pointToPosition((int) event.getX(),
								(int) event.getY()));

				ShoppingListFragment.fillShoppingList(getActivity(),
						currentStore);

				return false;
			}
		});

		listView.setAdapter(MainActivity.adapter);

		return v;
	}

	public static StoreListFragment newInstance() {

		StoreListFragment f = new StoreListFragment();

		return f;
	}

}
