package com.commanderZ;

import android.graphics.Bitmap;
import android.util.Log;

public class Character {
	private Bitmap character;
    private int x =200;
    private int y = 200;
    int tileHeight = 67;
    int tileWidth = 67;
    int gravity = 20;
    private GameDisplay display;
    public Character(Bitmap charImage, GameDisplay display){
    	this.character = charImage;
    	this.display = display;
    
    }
    
    public Bitmap draw(){
    	int[][] map = display.getMap();
    	int tileBellow = Math.round((y + 140)/tileHeight);
    	
    	//Log.d("test", "tileBellow= " + tileBellow);
    	
    	
    	if(tileBellow < map.length - 1){
	    	if(map[tileBellow][Math.round(x/tileWidth)] == 0){
	    		this.y += gravity;
	    	}
    	}
    	
    	
    	return character;
    }
    public int getX()
    {
    	return x;
    }
    public int getY(){
    	return y;
    }
}
