package com.commanderZ;

import java.util.ArrayList;

import android.util.Log;

public class TriggerTile {

	
	public int x = 0;
	public int y = 0;
	public  TriggerData _data;
	private Trigger _trigger;
	private String _name ="";

	public static ArrayList<TriggerTile> _tileList = new ArrayList<TriggerTile>();
	
	public TriggerTile( int x, int y, Trigger trig, String name,  TriggerData data){
		this._data = data;
		this._name = name;
		this.x = x;
		this.y = y;
		this._trigger = trig;
		
		TriggerTile.addTrigger(this);
		
	}
	public void trigger(){
		Log.d("test", "trigger called - x:"+this.x+" y:"+this.y + " type:" + _name);
		this._trigger.trigger(_name, _data);
	}
	public static void addTrigger(TriggerTile tile){
		
		 
		_tileList.add(tile);
		//_tileList[_tileList.length] = tile;
		
	}
	public static void clearAll()
	{
		
		_tileList.clear();
	}
	public String getName(){
		return _name;
	}
	public static  TriggerTile getTriggerTile(int testx, int testy){
		
		Object[] list = _tileList.toArray();
		TriggerTile item = null;; 
		for( int i=0 ; i< list .length ; i++){
			
			item = (TriggerTile) list[i];
			if( item.y == testy  && item.x == testx ){
				item.trigger();
				
			}
		}
		
		return item;
	
	}
}

