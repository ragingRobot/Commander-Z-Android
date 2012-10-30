package com.commanderZ;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Button;

public class Character {
	private Bitmap character;
    private int x =200;
    private int y = 20;
    int tileHeight = 33;
    int tileWidth = 33;
    int gravity = 5;
    int speed = 2;
    private boolean right = false;
    private boolean left = false;
    private GameDisplay display;
    public Character(Bitmap charImage, GameDisplay display){
    	this.character = charImage;
    	this.display = display;
    	
    	
    
    }
    
    public Bitmap draw(){
    	int[][] map = display.getMap();
    	int tileBellow = Math.round((y + (tileHeight * 2) + gravity)/tileHeight);
    	int tileRight = Math.round((x + (tileWidth) + speed)/tileWidth);
    	int tileLeft = Math.round((x  - speed)/tileWidth);
    	
    	//Log.d("test", "tileBellow= " + tileBellow);
    	
    	
    	if(tileBellow < map.length - 1){
	    	if(map[tileBellow][Math.round((x + (tileWidth/2))/tileWidth)] == 0){
	    		this.y += gravity;
	    	}else{
	    		this.y = (tileBellow * tileHeight) - (tileHeight * 2);
	    	}
    	}
    	
    	if(right)
    	{
    		if(map[Math.round((y + (tileHeight))/tileHeight)][tileRight] == 0){
    			this.x += speed;
	    	}else{
	    		this.x = (tileRight*tileWidth) - tileWidth;
	    	}
    		
    	}
    	
    	if(left)
    	{
    		if(map[Math.round((y + (tileHeight))/tileHeight)][tileLeft] == 0){
    			this.x -= speed;
	    	}else{
	    		this.x = (tileLeft*tileWidth) + tileWidth;
	    	}
    	}
    	
    	
    	return character;
    }
    public void left(boolean val){
    	this.left = val;
    }
    public void right(boolean val){
    		this.right = val;
    }
    public int getX()
    {
    	return x;
    }
    public int getY(){
    	return y;
    }
}
