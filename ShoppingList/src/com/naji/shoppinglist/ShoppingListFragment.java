package com.naji.shoppinglist;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ShoppingListFragment extends  Fragment {
	static ListView listView;
	static ShoppingItemsAdapter adapter;
	static Store CurrentStore;

	static Activity mActivity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_shopping_list_fragment, container, false);
	
			 
        listView = (ListView) v.findViewById(R.id.shopList);
 
    	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				TextView item =(TextView) view.findViewById(R.id.itemName);
				if(MainActivity.inShopMode){
					if(adapter.getItem(position).isStiked==false){
						item.setPaintFlags(item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						adapter.getItem(position).setStiked(true);
					}
					else{
						item.setPaintFlags(item.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
						adapter.getItem(position).setStiked(false);
					
					}
					MainActivity.datasource.updateShoppingItem(adapter.getItem(position));
				}
							 	
			}

		});
		
        return v;
	}
	
	 public static void updateAdapter(ShoppingItem shopItem){
		 adapter.add(shopItem);
	 }
	 
	public static  void unstrikeAll(){
		 List<ShoppingItem> values = MainActivity.datasource.unstrikAllitems(CurrentStore.getStoreId()); 
		 fillShoppingList(values);
		}
	
	 public static void fillShoppingList(Context context, Store store ){

		 CurrentStore= store;
		 //mActivity.getActionBar().setTitle(store.getStoreName());
		fillShoppingList(CurrentStore.getShoppingItems());

	}
	 static int gg=1;
	 private static void fillShoppingList(  List<ShoppingItem>items   ){
	 
		 adapter = new ShoppingItemsAdapter(mActivity,
							android.R.layout.simple_list_item_1, items);
	 
			listView.setAdapter(adapter);

	}
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        mActivity = activity;
	    }
    public static ShoppingListFragment newInstance(){//String text) {

    	ShoppingListFragment f = new ShoppingListFragment();
        return f;
    }
 
 
}
