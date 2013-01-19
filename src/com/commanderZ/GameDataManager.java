package com.commanderZ;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameDataManager {
		/////////////////////////////////////////////////////////////////////////////////////
		//STATIC VARIABLES
		/////////////////////////////////////////////////////////////////////////////////////
		private static GameDataManager _instance;
		
		/////////////////////////////////////////////////////////////////////////////////////
		//PRIVATE VARIABLES
		/////////////////////////////////////////////////////////////////////////////////////
		private boolean _movingLeft = false;
		private boolean _movingRight = false;
		private boolean _jumping = false;
		private boolean _running = false;
		private int _tileHeight = 67;
        private int _tileWidth = 67;
        private int _charHeight = 70;
        private int _charWidth = 50;
        private int _dpi = 240;
        private int _originalTileWidth = 67;
        private int _originalTileHeight = 67;
        private int _originalCharWidth = 100;
        private int _originalCharHeight = 140;
        public boolean _paused = false;
        private int[][] _map;
        
        
        
        
        private Bitmap _tiles;
        private Character _character;
        private Bitmap _background;
        
		/////////////////////////////////////////////////////////////////////////////////////
		//STATIC FUNCTIONS
		/////////////////////////////////////////////////////////////////////////////////////
		public static GameDataManager getInstance(){
			
			if(_instance == null){
				
				_instance = new GameDataManager();
			}
			return _instance;
		}
		/////////////////////////////////////////////////////////////////////////////////////
		//PUBLIC GET FUNCTIONS
		/////////////////////////////////////////////////////////////////////////////////////

		public boolean getMovingLeft(){
			return _movingLeft;
		}
		public boolean getMovingRight(){
			return _movingRight;
		}
		public boolean getJumping(){
			return _jumping;
		}
		public boolean getRunning(){
			
			return _running;
		}
		public int getTileHeight(){
			return _tileHeight;
		}
		public int getTileWidth(){
			return _tileWidth;
		}
		public int getCharHeight(){
			return _charHeight;
		}
		public int getCharWidth(){
			return _charWidth;
		}
		public int getOriginalTileHeight(){
			return _originalTileHeight;
		}
		public int getOriginalTileWidth(){
			return _originalTileWidth;
		}
		public int getOriginalCharHeight(){
			return _originalCharHeight;
		}
		public int getOriginalCharWidth(){
			return _originalCharWidth;
		}
		public int getDpi(){
			return _dpi;
		}
		public int[][] getCurrentMap(){
			return _map;
		}
		public void reset(){
				_instance = null;
				_instance = new GameDataManager();
		
		}
		public Bitmap getTiles() {
			// TODO Auto-generated method stub
			return _tiles;
	       
		}
		public Character getCharacter() {
			// TODO Auto-generated method stub
			
			return _character;
	    
			
		}
		public Bitmap getBackground() {
			// TODO Auto-generated method stub
			
			return _background;
			
		}

		/////////////////////////////////////////////////////////////////////////////////////
		//PUBLIC SET FUNCTIONS
		/////////////////////////////////////////////////////////////////////////////////////
		public void setMovingLeft(boolean val){
			_movingLeft = val;
		}
		public void setMovingRight(boolean val){
			_movingRight = val;
		}
		public void setJumping(boolean val){
			_jumping = val;
		}
		
		public void setRunning(boolean val){
			
			_running = val;
		}
		public void setDpi(int val){
			_dpi = val;
			 if(_dpi != 120){
				 _tileWidth = _originalTileWidth / (_dpi/120);
				 _tileHeight = _originalTileHeight  / (_dpi/120);
				 
				 _charWidth = _originalCharWidth / (_dpi/120);
				 _charHeight = _originalCharHeight  / (_dpi/120);
				 
			 }else{
				 _tileWidth =  _originalTileWidth;
				 _tileHeight = _originalTileHeight;
				 
				 _charWidth =  _originalCharWidth;
				 _charHeight = _originalCharHeight;
				 
			 }
		}
		public void setCurrentTiles(Bitmap decodeResource) {
			// TODO Auto-generated method stub
			_tiles = decodeResource;
	       
		}
		public void setCurrentCharacter(Character character) {
			// TODO Auto-generated method stub
			
	        _character = character;
	    
			
		}
		public void setCurrentBackground(Bitmap decodeResource) {
			// TODO Auto-generated method stub
			
	        _background = decodeResource;
			
		}
		public void setCurrentMap(int[][] val){
			_map = val;
		}
		/////////////////////////////////////////////////////////////////////////////////////
		//END :D
		/////////////////////////////////////////////////////////////////////////////////////
		
}
