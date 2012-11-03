package com.commanderZ;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Button;

@SuppressLint("FloatMath")
public class Character {
	/////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE VARIABLES
	/////////////////////////////////////////////////////////////////////////////////////
	private Bitmap _character;
    private int _x =200;
    private int _y = 200;
    private int _gravity = 6;
    private float _speed = 0;
    private float _jumpSpeed = 0;
    private Canvas _puck;
    private Bitmap _puckImage;
    private Rect _tileScreenLocation;
    private Rect _tileLocation;
    private int _frame = 0;
    private int _frameY = 0;
    private int _frameTick = 0;
    private int _maxJump = 30;
	/////////////////////////////////////////////////////////////////////////////////////
	//SETUP STUFF
	/////////////////////////////////////////////////////////////////////////////////////
    public Character(Bitmap charImage){
    	this._character = charImage;
    	int w =  GameDataManager.getInstance().getTileWidth(), h =  GameDataManager.getInstance().getOriginalTileHeight() * 2;

		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		_puckImage = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
		_puck = new Canvas(_puckImage);
		_tileScreenLocation = new Rect();
		_tileLocation = new Rect();
    }
    
	/////////////////////////////////////////////////////////////////////////////////////
	//EVENT HANDELING
	/////////////////////////////////////////////////////////////////////////////////////
    @SuppressLint("FloatMath")
	public void updatePhysics(int fps){
    	
    	int tileBellow = Math.round((_y + ( GameDataManager.getInstance().getTileHeight() * 2) + _gravity)/ GameDataManager.getInstance().getTileHeight());
    	int tileRight = Math.round((_x + (GameDataManager.getInstance().getTileWidth()) + _speed)/ GameDataManager.getInstance().getTileWidth());
    	int tileLeft = Math.round((_x  + _speed - 15)/ GameDataManager.getInstance().getTileWidth());
    	
    	
    	if(_frameTick < fps/6){
    		_frameTick++;
    	}else{
    		_frameTick = 0;
    		if(GameDataManager.getInstance().getMovingRight() || GameDataManager.getInstance().getMovingLeft()){
	    		if(_frame < 3){
	        		_frame ++;
	        	}else{
	        		_frame = 1;
	        	}
	    		
	    		if(GameDataManager.getInstance().getMovingRight()){
	    			_frameY = 0;
	    		}else{
	    			_frameY = 1;
	    		}
    		}else{
    			_frame = 0;
    		}
    	}
    	
    	
    	if(Math.floor(_jumpSpeed) > 0){
    		GameDataManager.getInstance().setJumping(false);
    		_jumpSpeed -= _jumpSpeed * .2;
    		
    		if(Math.floor(_jumpSpeed) > 0){
    			_frame = 5;
    		}else{
    			_frame = 7;
    		}
    	}
    	
    	if(GameDataManager.getInstance().getJumping() && (GameDataManager.getInstance().getCurrentMap()[tileBellow][Math.round((_x + ( GameDataManager.getInstance().getTileWidth()/2))/ GameDataManager.getInstance().getTileWidth())] > 0)){
    		_jumpSpeed = _maxJump;
    		GameDataManager.getInstance().setJumping(false);
    	
    	}
    	
    	if(Math.floor(_jumpSpeed) > 0 ){
    		
    		
    	}else{
    		_jumpSpeed = 0;
    	}
    	
    	
    	

    	if(tileBellow < GameDataManager.getInstance().getCurrentMap().length - 1){
	    	if(GameDataManager.getInstance().getCurrentMap()[tileBellow][Math.round((_x + ( GameDataManager.getInstance().getTileWidth()/2))/ GameDataManager.getInstance().getTileWidth())] == 0){
	    		this._y += _gravity;
	    	
	    	}else{
	    		this._y = (tileBellow *  GameDataManager.getInstance().getTileHeight()) - ( GameDataManager.getInstance().getTileHeight() * 2);
	    	}
    	}
    	
    	if(GameDataManager.getInstance().getCurrentMap()[Math.round((_y - 10 )/ GameDataManager.getInstance().getTileHeight())][Math.round((_x + ( GameDataManager.getInstance().getTileWidth()/2))/ GameDataManager.getInstance().getTileWidth())] == 0){
	    	
    		this._y -= _jumpSpeed;
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
    			_speed -= Math.abs(_speed) * .3;
    		}
    		
    		if(_speed < 0){
    			_speed += Math.abs(_speed) * .6;
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
    	
    	
    	
    	_puck.drawColor( 0,PorterDuff.Mode.CLEAR );
    	_tileScreenLocation.set( 0 , 0, GameDataManager.getInstance().getCharWidth(), GameDataManager.getInstance().getCharHeight());
        
        _tileLocation.set(_frame * GameDataManager.getInstance().getOriginalCharWidth(), _frameY * GameDataManager.getInstance().getOriginalCharHeight() , _frame * GameDataManager.getInstance().getOriginalCharWidth() + GameDataManager.getInstance().getOriginalCharWidth() , _frameY * GameDataManager.getInstance().getOriginalCharHeight() + GameDataManager.getInstance().getOriginalCharHeight());
     
        //Log.d("test", "dpi= " + dpi);
        //this draws the tile to the screen
        _puck.drawBitmap(_character, _tileLocation, _tileScreenLocation, null);
    	
    	
    }
    public Bitmap draw(){
    	
    	return _puckImage;
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
