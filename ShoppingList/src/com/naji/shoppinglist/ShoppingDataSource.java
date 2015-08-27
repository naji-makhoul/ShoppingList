package com.naji.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class ShoppingDataSource {
	private SQLiteDatabase database;
	private DBHelper dbHelper;
	private String[] StoreListTableColumns={DBHelper.KEY_ID,DBHelper.KEY_NAME};
	private String[] ShoppingItemTableColumns={DBHelper.KEY_ID,DBHelper.KEY_ITEM_NAME,
			DBHelper.KEY_STIKED,DBHelper.KEY_STORE_ID};
	
	public ShoppingDataSource(Context context){
		dbHelper = new DBHelper(context);
	}
	public void open() throws SQLException{
		if(database==null || !database.isOpen()){//open database if is null or closed
			database = dbHelper.getWritableDatabase();
		}
	}
	public void close(){
		dbHelper.close();
	}
	
	public Store createStore(String name){
		ContentValues values = new ContentValues();
		values.put(DBHelper.KEY_NAME, name);
		long insertID = database.insert(DBHelper.TABLE_SHOPS, null, values);
		Cursor cursos = database.query(DBHelper.TABLE_SHOPS, StoreListTableColumns,
					DBHelper.KEY_ID + " = " + insertID, null, null, null, null);
		cursos.moveToFirst();
		Store newStore = cursorToShopItem(cursos); 
		newStore.shoppingItems = new ArrayList<ShoppingItem>();
		cursos.close();
		return newStore;
	}
	
	public Store getStore(int storeID){
		Cursor cursos = database.query(DBHelper.TABLE_SHOPS, StoreListTableColumns,
					DBHelper.KEY_ID + " = " + storeID, null, null, null, null);
		cursos.moveToFirst();
		Store newStore = cursorToShopItem(cursos);
		cursos.close();
		return newStore;
	}
	
	private Store cursorToShopItem(Cursor cursor) {
		Store store = new Store();
		store.setStoreId(Integer.valueOf(cursor.getInt(0)));
		store.setStoreName(cursor.getString(1));
		return store;
	  }
	
	public List<Store> getAllStores() {
		    List<Store> shopList = new ArrayList<Store>();

		    Cursor cursor = database.query(DBHelper.TABLE_SHOPS,
		    		StoreListTableColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	Store item = cursorToShopItem(cursor);
		    	item.shoppingItems = getAllShoppingListByShopID(item.getStoreId());
		    	shopList.add(item);
		    	cursor.moveToNext();
		    }
		    // close the cursor
		    cursor.close();
		    return shopList;
	 }
	
	public void deleteStore(Store store){
		int id = store.getStoreId();
		database.delete(DBHelper.TABLE_SHOPS, DBHelper.KEY_ID + " = " + id, null);
	}
	
	// ******* shopping list **********
	
	public List<ShoppingItem> getAllShoppingListByShopID(int storeID) {
	    List<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();

	    Cursor cursor = database.query(DBHelper.TABLE_SHOPPING_LIST,
	    		ShoppingItemTableColumns, DBHelper.KEY_STORE_ID + " = " + storeID, null, null, null, null);
	
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	ShoppingItem item = cursorToShopingItem(cursor);
	    	shoppingList.add(item);
	    	cursor.moveToNext();
	    }
	    // close the cursor
	    cursor.close();
	    return shoppingList;
 }
//	private ShoppingItem cursorToShopingItem(Cursor cursor,Store store) {
//		ShoppingItem item = new ShoppingItem();
//		item.setId(Integer.valueOf(cursor.getInt(0)));
//		item.setItemName(cursor.getString(1));
//		item.setStiked(Integer.valueOf(cursor.getInt(3)));
//		item.setStore(store);
//		return item;
//	  }
	private ShoppingItem cursorToShopingItem(Cursor cursor) {
		ShoppingItem item = new ShoppingItem();
		item.setId(Integer.valueOf(cursor.getInt(0)));
		item.setItemName(cursor.getString(1));
		item.setStiked(cursor.getInt(2) != 0);
		return item;
	  }
	
	public void deleteItem(ShoppingItem shop){
		int id = shop.getItemId();
		database.delete(DBHelper.TABLE_SHOPPING_LIST, DBHelper.KEY_ID + " = " + id, null);
	}
	
	public ShoppingItem createShoppingItem(int storeID,String name){
		ContentValues values = new ContentValues();
		values.put(DBHelper.KEY_STORE_ID, storeID);
		values.put(DBHelper.KEY_ITEM_NAME, name);
		values.put(DBHelper.KEY_STIKED, 0);
		
		long insertID = database.insert(DBHelper.TABLE_SHOPPING_LIST, null, values);
		Cursor cursos = database.query(DBHelper.TABLE_SHOPPING_LIST, ShoppingItemTableColumns,
					DBHelper.KEY_ID + " = " + insertID, null, null, null, null);
		cursos.moveToFirst();
		ShoppingItem newItem = cursorToShopingItem(cursos);
		cursos.close();
		return newItem;
	}
	public ShoppingItem updateShoppingItem(ShoppingItem item){
		ContentValues values = new ContentValues();
		values.put(DBHelper.KEY_STIKED, item.isStiked);
		values.put(DBHelper.KEY_ITEM_NAME, item.getItemName());
		
		database.update(DBHelper.TABLE_SHOPPING_LIST,
				values,DBHelper.KEY_ID + " = " + item.getItemId(), null);
		
//		Cursor cursos = database.query(DBHelper.TABLE_SHOPPING_LIST, ShoppingItemTableColumns,
//					DBHelper.KEY_ID + " = " + insertID, null, null, null, null);
//		cursos.moveToFirst();
//		ShoppingItem newItem = cursorToShopingItem(cursos);
//		cursos.close();
		return item;
	}
	
	public List<ShoppingItem> unstrikAllitems(int storeID) {
		List<ShoppingItem> shoppingList = new ArrayList<ShoppingItem>();
	    Cursor cursor = database.query(DBHelper.TABLE_SHOPPING_LIST,
	    		ShoppingItemTableColumns, DBHelper.KEY_STORE_ID + " = " + storeID, null, null, null, null);
	
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	ShoppingItem item = cursorToShopingItem(cursor);
	    	item.setStiked(false);
	    	updateShoppingItem(item);
	    	shoppingList.add(item);
	    	cursor.moveToNext();
	    }
	    // close the cursor
	    cursor.close(); 
	    return shoppingList;
 }
	
	//return shopping items count by store id
	public int getItemsCount(int storeID) { 
	    int count=0;
		Cursor cursor = database.rawQuery("select count(*) from " + 
					DBHelper.TABLE_SHOPPING_LIST +" where "+
					DBHelper.KEY_STORE_ID + " = " + storeID,null);
	    cursor.moveToFirst();
	    count = cursor.getInt(0);
	    // close the cursor
	    cursor.close(); 
	    return count;
 }
}
