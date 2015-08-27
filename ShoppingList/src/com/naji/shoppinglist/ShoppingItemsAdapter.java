package com.naji.shoppinglist;

import java.util.List;

import com.naji.shoppinglist.R;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShoppingItemsAdapter extends ArrayAdapter<ShoppingItem> {
	protected Context mContext;
	protected List<ShoppingItem> mItems;
	
	public ShoppingItemsAdapter(Context context, int resource, List<ShoppingItem> items) {
		super(context, resource, items);
		mContext = context;
		mItems = items;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public ShoppingItem getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public int getPosition(ShoppingItem item) {
		// TODO Auto-generated method stub
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
		
		ShoppingItem item = mItems.get(position);
		
		holder.shopName.setText(item.getItemName());
		if(item.isStiked==true){
			holder.shopName.setPaintFlags(holder.shopName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);//strike text
		}else{
			holder.shopName.setPaintFlags(holder.shopName.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));//unstrike text
		}
		return convertView;
	}

	
	private static class ViewHolder {
		TextView shopName;
	}
}
