package com.commanderZ;

import java.util.ArrayList;

import android.util.Log;

public class TriggerTile {

	
	public int x = 0;
	public int y = 0;
	private Trigger _trigger;

	public static ArrayList<TriggerTile> _tileList = new ArrayList<TriggerTile>();
	
	public TriggerTile( int x, int y, Trigger trig){
		this.x = x;
		this.y = y;
		this._trigger = trig;
		
		TriggerTile.addTrigger(this);
		
	}
	public void trigger(){
		Log.d("test", "trigger called - x:"+this.x+" y:"+this.y);
		this._trigger.trigger();
	}
	public static void addTrigger(TriggerTile tile){
		
		 
		_tileList.add(tile);
		//_tileList[_tileList.length] = tile;
		
	}
	public static  TriggerTile getTriggerTile(int testx, int testy){
		
		Object[] list = _tileList.toArray();
		
		for( int i=0 ; i< list .length ; i++){
			
			TriggerTile item = (TriggerTile) list[i];
			if( item.y == testy  && item.x == testx ){
				item.trigger();
				return item;
			}
		}
		return null;
	}
}

