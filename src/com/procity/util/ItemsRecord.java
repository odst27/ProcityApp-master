package com.procity.util;

import java.util.Set;
import java.util.TreeMap;

public class ItemsRecord {

	
	public static TreeMap<String,Object> myRecords;
	
	public ItemsRecord() {
		
		myRecords = new TreeMap<String,Object>();

		
	}
    
    public void addNewCategory(String cat) {
    	
    	TreeMap<String,Object> subcatitem = new TreeMap<String,Object>();
    	myRecords.put(cat, subcatitem);
    }

    @SuppressWarnings("unchecked")
	public void addNewSubCat(String cat, String sub){
    	
    	TreeMap<String,Object> subcatitem = (TreeMap<String, Object>) myRecords.get(cat);
    	subcatitem.put(sub, new TreeMap<String,String>());
    	
    }
    
    @SuppressWarnings("unchecked")
	public void addNewItem(String cat, String sub, String item, String[] valsarr){
    	
    	TreeMap<String,Object> subcatitem = (TreeMap<String, Object>) myRecords.get(cat);
    	TreeMap<String,String[]> itemmap = (TreeMap<String, String[]>) subcatitem.get(sub);
    	
    	itemmap.put(item, valsarr);
    	
    	
    }
	public String[] getCategories() {
		
		Set<String> keys = myRecords.keySet();
		String[] fields =keys.toArray( new String[ keys.size()]) ;
		return fields;
	}

	@SuppressWarnings("unchecked")
	public String[] getSubcategories(String cat) {
		
		TreeMap<String,Object> mysub = (TreeMap<String, Object>) myRecords.get(cat);
		
		if(mysub== null)
			return null;
		
		Set<String> keys = mysub.keySet();
		String[] fields =keys.toArray( new String[ keys.size()]) ;
		return fields;
		
	}

	@SuppressWarnings("unchecked")
	public String[] getItems(String cat, String subcat) {
		
		TreeMap<String,Object> mysub = (TreeMap<String, Object>) myRecords.get(cat);
		TreeMap<String,String[]> items = (TreeMap<String, String[]>) mysub.get(subcat);
		
		if(items == null)
			return null;
		
		Set<String> keys = items.keySet();
		
		String[] fields =keys.toArray( new String[ keys.size()]) ;
		return fields;
		
	}
	
	@SuppressWarnings("unchecked")
	public String getEmin(String cat, String subcat, String item) {
		
		TreeMap<String,Object> mysub = (TreeMap<String, Object>) myRecords.get(cat);
		TreeMap<String,String[]> items = (TreeMap<String, String[]>) mysub.get(subcat);
		
		return (String) items.get(item)[0];
		
	}
	
	@SuppressWarnings("unchecked")
	public String getEmed(String cat, String subcat, String item) {
		
		TreeMap<String,Object> mysub = (TreeMap<String, Object>) myRecords.get(cat);
		TreeMap<String,String[]> items = (TreeMap<String, String[]>) mysub.get(subcat);
		
		return (String) items.get(item)[1];
		
	}
	
	@SuppressWarnings("unchecked")
	public String getEmax(String cat, String subcat, String item) {
	
		TreeMap<String,Object> mysub = (TreeMap<String, Object>) myRecords.get(cat);
		TreeMap<String,String[]> items = (TreeMap<String, String[]>) mysub.get(subcat);
		
		return (String) items.get(item)[2];
	
	}

	public boolean containsCategory(String cat) {
		
		return myRecords.containsKey(cat);
	}

	@SuppressWarnings("unchecked")
	public boolean containsSubcat(String cat, String subcat) {
		// TODO Auto-generated method stub
		TreeMap<String, Object> sbcat = (TreeMap<String, Object>) myRecords.get(cat);
		
		if(sbcat == null){
			return false;
		}
		
		return sbcat.containsKey(subcat);
	}
   
}
