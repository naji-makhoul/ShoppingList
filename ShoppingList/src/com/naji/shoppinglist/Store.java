package com.naji.shoppinglist;

import java.util.List;

public class Store{
	
	private int StoreId;
	private String storeName;
	List<ShoppingItem> shoppingItems;
//	private  List<ShoppingItem> shoppingItems;
//	
//	public List<ShoppingItem> getShoppingItems() {
//		return shoppingItems;
//	}
//	public void setShoppingItems(List<ShoppingItem> shoppingItems) {
//		this.shoppingItems = shoppingItems;
//	}
	
	public int getStoreId() {
		return StoreId;
	}
	public List<ShoppingItem> getShoppingItems() {
		return shoppingItems;
	}
	public void setShoppingItems(List<ShoppingItem> shoppingItems) {
		this.shoppingItems = shoppingItems;
	}
	
	public void setStoreId(int id) {
		this.StoreId = id;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String name) {
		this.storeName = name;
	}

	
	
	
}
