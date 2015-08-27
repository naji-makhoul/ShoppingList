package com.naji.shoppinglist;

import java.util.List;

import com.naji.shoppinglist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StoreAdapter extends ArrayAdapter<Store> {
	protected Context mContext;
	protected List<Store> mStore;
	
	public StoreAdapter(Context context, int resource, List<Store> stores) {
		super(context, resource, stores);
		mContext = context;
		mStore = stores;
	}
	

	@Override
	public int getCount() {
		return super.getCount();
	}

	@Override
	public Store getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public int getPosition(Store item) {
		return super.getPosition(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder; 
		if (convertView == null) {
 		convertView = LayoutInflater.from(mContext).inflate(R.layout.shop_item, null);
			holder = new ViewHolder();
			holder.shopName = (TextView)convertView.findViewById(R.id.itemName);
			convertView.setTag(holder); 
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		Store store = mStore.get(position);
		
		holder.shopName.setText(store.getStoreName());
		
		return convertView;
	}
 
	private static class ViewHolder {
		TextView shopName;
		
	}
}
