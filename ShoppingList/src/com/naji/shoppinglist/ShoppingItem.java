package com.naji.shoppinglist;

public class ShoppingItem {
	Store store;
	int itemId;
	String itemName;
	Boolean isStiked;

	public ShoppingItem() {
		super();
	}
	public ShoppingItem(int storeID, int itemId, String itemName) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.isStiked= false;
	}
	
	public ShoppingItem(Store store, int itemId, String itemName) {
		super();
		this.store = store;
		this.itemId = itemId;
		this.itemName = itemName;
		this.isStiked=false;
	}
	
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public int getItemId() {
		return itemId;
	}
	public void setId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public Boolean isStiked() {
		return isStiked;
	}
	public void setStiked(Boolean stiked) {
		this.isStiked = stiked;
	}
	
	@Override
	public String toString() {
		return "ShoppingItem [store=" + store + ", itemId=" + itemId
				+ ", itemName=" + itemName + ", isStiked=" + isStiked + "]";
	}
	
}
