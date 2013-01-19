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
	// ///////////////////////////////////////////////////////////////////////////////////
	// PRIVATE VARIABLES
	// ///////////////////////////////////////////////////////////////////////////////////
	private Bitmap _character;
	private int _x = 200;
	private int _y = 400;
	private int _gravity = 22;
	private float _speedX = 0;
	private float _speedY = 0;
	private float _jumpSpeed = 0;
	private Canvas _puck;
	private Bitmap _puckImage;
	private Rect _tileScreenLocation;
	private Rect _tileLocation;
	private int _frame = 0;
	private int _frameY = 0;
	private int _frameTick = 0;
	private int _maxJump = 60;
	private int _maxSpeed = 10;
	private int _maxRun = 20;
	private boolean _jumpStarted = false;
	
	private int _tileUp=0;
	private int _tileUpRight=0;
	private int _tileRight=0;
	private int _tileDownRight=0;
	private int _tileDown=0;
	private int _tileDownLeft=0;
	private int _tileLeft=0;
	private int _tileUpLeft=0;
	

	// ///////////////////////////////////////////////////////////////////////////////////
	// SETUP STUFF
	// ///////////////////////////////////////////////////////////////////////////////////
	public Character(Bitmap charImage, int startx, int starty) {
		this._x = startx;
		this._y = starty;
		this._character = charImage;
		int w = GameDataManager.getInstance().getTileWidth();
		int h = GameDataManager.getInstance().getOriginalCharHeight();

		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		_puckImage = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE
														// bitmap
		_puck = new Canvas(_puckImage);
		_tileScreenLocation = new Rect();
		_tileLocation = new Rect();
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	// EVENT HANDELING
	// ///////////////////////////////////////////////////////////////////////////////////
	@SuppressLint("FloatMath")
	public void updatePhysics(int fps) {
		int cleartiles = 26;
	
		int tileBellow = Math.round((_y + (GameDataManager.getInstance().getTileHeight() * 2) + _gravity) / GameDataManager.getInstance().getTileHeight());
	
		
		int tileHeight = GameDataManager.getInstance().getTileHeight();
		int tileWidth = GameDataManager.getInstance().getTileWidth();
		int[][] map = GameDataManager.getInstance().getCurrentMap();
	
		int tileCurrentX = Math.round((_x + ( GameDataManager.getInstance().getCharWidth() / 3))/tileWidth);
		int tileCurrentY = Math.round((_y + (GameDataManager.getInstance().getCharHeight() - GameDataManager.getInstance().getCharHeight() / 3))/ tileHeight);


		int tileCurrentLeftX = Math.round((_x + 5 )/tileWidth);
		int tileCurrentRightX = Math.round((_x + 20 + ( GameDataManager.getInstance().getCharWidth() / 3))/tileWidth);
		/***************************************************************************
		 * This gets the values of the surrounding tiles
		 ***************************************************************************/
		_tileUp = map[tileCurrentY - 2][tileCurrentX];
		_tileUpRight = map[tileCurrentY - 1][tileCurrentRightX ];
		_tileRight = map[tileCurrentY][tileCurrentRightX ];
		_tileDownRight = map[tileCurrentY + 1][tileCurrentRightX ];
		_tileDown = map[tileCurrentY + 1][tileCurrentX];
		_tileDownLeft = map[tileCurrentY + 1][tileCurrentLeftX];
		_tileLeft = map[tileCurrentY][tileCurrentLeftX];
		_tileUpLeft = map[tileCurrentY - 1][tileCurrentLeftX];
		

		TriggerTile trigger = TriggerTile.getTriggerTile(tileCurrentX,tileCurrentY);

		
		/***************************************************************************
		 * Animation
		 ***************************************************************************/
		
		if (_frameTick < fps / 7) {
			_frameTick++;
		} else {
			_frameTick = 0;
			if (GameDataManager.getInstance().getMovingRight()
					|| GameDataManager.getInstance().getMovingLeft()) {
				if (_frame < 3) {
					_frame++;
				} else {
					_frame = 1;
				}

				if (GameDataManager.getInstance().getMovingRight()) {
					_frameY = 0;
				} else {
					_frameY = 1;
				}
			} else {
				_frame = 0;
			}
		}

		
		
		
		
		
		
		
		
		
		
		/***************************************************************************
		 * Gravity
		 ***************************************************************************/
		
		if (_tileDown < cleartiles) {
			this._y += _gravity;

		} else {
			this._y = (tileBellow * tileHeight) - (tileHeight * 2);
		}
		
		
		
		/***************************************************************************
		 * Jumping
		 ***************************************************************************/

		if (Math.floor(_jumpSpeed) > 0) {

			GameDataManager.getInstance().setJumping(false);
			_jumpSpeed -= _jumpSpeed * .15;
			
			
			if (Math.floor(_jumpSpeed) > 10) {
				_frame = 5;
			} else {
				_frame = 7;

			}
		}
	
		
		if (GameDataManager.getInstance().getJumping() && ( _tileDown > cleartiles)) {

			
				
				_jumpSpeed = _maxJump;
				GameDataManager.getInstance().setJumping(false);
			

		}

		if (Math.floor(_jumpSpeed) < 10) {

			_jumpSpeed = 0;
		}

		
	

		if ( _tileUp < cleartiles) {

			this._y -= _jumpSpeed;
		} else {

			_jumpSpeed = 0;
		}
		
/*
		_speedY += _gravity;
		_speedY -= _jumpSpeed ;
	*/	
		
		/***************************************************************************
		 * Set moving speed
		 ***************************************************************************/
		
		int maxMoveSpeed = _maxSpeed;
		
		if(GameDataManager.getInstance().getRunning()){
			maxMoveSpeed = _maxRun;
			
		}
		
		
		if (GameDataManager.getInstance().getMovingRight()) {
			if (_speedX < maxMoveSpeed) {
				_speedX++;
			}

		} else if (GameDataManager.getInstance().getMovingLeft()) {
			if (_speedX > -maxMoveSpeed) {
				_speedX--;
			}

		} else {
			if (_speedX > 0) {
				_speedX -= Math.abs(_speedX) * .2;
			}

			if (_speedX < 0) {
				_speedX += Math.abs(_speedX) * .2;
			}
		}

		if (_speedX > 0) {


			if (_tileRight > cleartiles) {

			
				_speedX = 0;

			}

		} else if (_speedX < 0) {

			if (_tileLeft > cleartiles) {

				
				_speedX = 0;
			}
			

		}

		this._x += _speedX;
		this._y += _speedY;

		_puck.drawColor(0, PorterDuff.Mode.CLEAR);
		_tileScreenLocation.set(0, 0, GameDataManager.getInstance().getCharWidth(), GameDataManager.getInstance().getCharHeight());

		_tileLocation.set(_frame * GameDataManager.getInstance().getOriginalCharWidth(), _frameY * GameDataManager.getInstance().getOriginalCharHeight(), _frame * GameDataManager.getInstance().getOriginalCharWidth() + GameDataManager.getInstance().getOriginalCharWidth(), _frameY * GameDataManager.getInstance().getOriginalCharHeight() + GameDataManager.getInstance().getOriginalCharHeight());

	
		// this draws the tile to the screen
		_puck.drawBitmap(_character, _tileLocation, _tileScreenLocation, null);

	}

	public Bitmap draw() {

		return _puckImage;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	// GETTERS
	// ///////////////////////////////////////////////////////////////////////////////////
	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}
	// ///////////////////////////////////////////////////////////////////////////////////
	// END :D
	// ///////////////////////////////////////////////////////////////////////////////////
}
