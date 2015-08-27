package com.naji.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "shoppingListDB.db";
	
	public static final String TABLE_SHOPS = "storeList";
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "storeName";
	
	public static final String TABLE_SHOPPING_LIST = "shoppingList";
	public static final String KEY_ITEM_NAME = "itemName";
	public static final String KEY_STORE_ID = "storeID";
	public static final String KEY_STIKED = "striked";
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CreateShopListTable = "CREATE TABLE "
				+ TABLE_SHOPS 
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KEY_NAME+ " TEXT)";
		
		String CreateShopingListTable = "CREATE TABLE "
				+ TABLE_SHOPPING_LIST 
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KEY_ITEM_NAME+ " TEXT,"
				+ KEY_STIKED + " INTEGER,"
				+ KEY_STORE_ID + " INTEGER)";
		
		db.execSQL(CreateShopListTable);
		db.execSQL(CreateShopingListTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPS);
		
		// Create tables again
		onCreate(db);
	}

	
}
