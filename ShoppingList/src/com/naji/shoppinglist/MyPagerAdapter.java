package com.naji.shoppinglist;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ListView;

public class MyPagerAdapter extends FragmentPagerAdapter {
ListView list;
Context context;
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    
	@Override
	public  Fragment getItem(int pos) {
	       switch(pos) {

	        case 0: return StoreListFragment.newInstance();
	        case 1: return ShoppingListFragment.newInstance();
	        default: return StoreListFragment.newInstance();
	       }
	}

	@Override
	public int getCount() {
		return 2;
	}
	


}
