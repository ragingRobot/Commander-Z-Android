package com.commanderZ;

import android.graphics.Bitmap;


public class Character {
	private Bitmap character;
    private int x =200;
    private int y = 200;
    private GameDisplay display;
    public Character(Bitmap charImage, GameDisplay display){
    	this.character = charImage;
    	this.display = display;
    
    }
    
    public Bitmap draw(){
    	int[][] map = display.getMap();
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
