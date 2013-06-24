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
        private int _charHeight = 140;
        private int _charWidth = 100;
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
