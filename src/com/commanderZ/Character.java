package com.commanderZ;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Button;

public class Character {
	/////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE VARIABLES
	/////////////////////////////////////////////////////////////////////////////////////
	private Bitmap _character;
    private int _x =200;
    private int _y = 20;
    private int _gravity = 5;
    private float _speed = 0;

    
	/////////////////////////////////////////////////////////////////////////////////////
	//SETUP STUFF
	/////////////////////////////////////////////////////////////////////////////////////
    public Character(Bitmap charImage){
    	this._character = charImage;
    }
    
	/////////////////////////////////////////////////////////////////////////////////////
	//EVENT HANDELING
	/////////////////////////////////////////////////////////////////////////////////////
    public Bitmap draw(){
    	int tileBellow = Math.round((_y + ( GameDataManager.getInstance().getTileHeight() * 2) + _gravity)/ GameDataManager.getInstance().getTileHeight());
    	int tileRight = Math.round((_x + (GameDataManager.getInstance().getTileWidth()) + _speed)/ GameDataManager.getInstance().getTileWidth());
    	int tileLeft = Math.round((_x  + _speed - 15)/ GameDataManager.getInstance().getTileWidth());
    	
    	if(tileBellow < GameDataManager.getInstance().getCurrentMap().length - 1){
	    	if(GameDataManager.getInstance().getCurrentMap()[tileBellow][Math.round((_x + ( GameDataManager.getInstance().getTileWidth()/2))/ GameDataManager.getInstance().getTileWidth())] == 0){
	    		this._y += _gravity;
	    	}else{
	    		this._y = (tileBellow *  GameDataManager.getInstance().getTileHeight()) - ( GameDataManager.getInstance().getTileHeight() * 2);
	    	}
    	}
    	
    	if(GameDataManager.getInstance().getMovingRight())
    	{
    		if(_speed < 5){
    			_speed ++;
    		}
    		
    	}
    	else if(GameDataManager.getInstance().getMovingLeft())
    	{
    		if(_speed > -5){
    			_speed --;
    		}
    		
    		
    	} else{
    		if(_speed >0){
    			_speed -= Math.abs(_speed) * .12;
    		}
    		
    		if(_speed < 0){
    			_speed += Math.abs(_speed) * .12;
    		}
    	}
    	
    	
    	if(_speed > 0){
    		if(GameDataManager.getInstance().getCurrentMap()[Math.round((_y + ( GameDataManager.getInstance().getTileHeight()))/ GameDataManager.getInstance().getTileHeight())][tileRight] == 0){
    			this._x += _speed;
	    	}else{
	    		this._x = (tileRight* GameDataManager.getInstance().getTileWidth()) -  GameDataManager.getInstance().getTileWidth();
	    		
	    	}
    	}
    	
    	if(_speed < 0){
    		
    		if(GameDataManager.getInstance().getCurrentMap()[Math.round((_y + ( GameDataManager.getInstance().getTileHeight()))/ GameDataManager.getInstance().getTileHeight())][tileLeft] == 0){
    			this._x += _speed;
	    	}else{
	    		this._x = (tileLeft* GameDataManager.getInstance().getTileWidth()) +  GameDataManager.getInstance().getTileWidth();
	    		
	    	}
    	}
    	
    	
    	return _character;
    }
    
	/////////////////////////////////////////////////////////////////////////////////////
	//GETTERS
	/////////////////////////////////////////////////////////////////////////////////////
    public int getX()
    {
    	return _x;
    }
    public int getY(){
    	return _y;
    }
	/////////////////////////////////////////////////////////////////////////////////////
	//END :D
	/////////////////////////////////////////////////////////////////////////////////////
}
