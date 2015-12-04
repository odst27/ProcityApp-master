package com.procity.util;

public class UserRecord {

	public String itemname;
	public String description;
	public String picpath;
	public String time;
	public String donatedby;
    public String ebase;
    public String condition;
    public String location;
    
    
    public UserRecord(String item, String descrp, String pp, String tme,
    		String donteby, String eb, String cond, String loc) {
    	
        this.itemname = item;
        this.description = descrp;
        this.picpath = pp;
        this.time = tme;
        this.donatedby = donteby;
        this.ebase = eb;
        this.condition = cond;
        this.location = loc;
        
    }
    
}
