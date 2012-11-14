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
	private float _speed = 0;
	private float _jumpSpeed = 0;
	private Canvas _puck;
	private Bitmap _puckImage;
	private Rect _tileScreenLocation;
	private Rect _tileLocation;
	private int _frame = 0;
	private int _frameY = 0;
	private int _frameTick = 0;
	private int _maxJump = 60;
	private int _maxSpeed = 8;
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
		int w = GameDataManager.getInstance().getTileWidth(), h = GameDataManager
				.getInstance().getOriginalTileHeight() * 2;

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
		int tileAbove = Math.round((_y + (GameDataManager.getInstance().getTileHeight() * 2))/ GameDataManager.getInstance().getTileHeight()) - 1;
		int tileRight = Math.round((_x + (GameDataManager.getInstance().getTileWidth()) + _speed)/ GameDataManager.getInstance().getTileWidth());
		int tileLeft = Math.round((_x + _speed - 15)/ GameDataManager.getInstance().getTileWidth());	
		
		int tileHeight = GameDataManager.getInstance().getTileHeight();
		int tileWidth = GameDataManager.getInstance().getTileWidth();
		int[][] map = GameDataManager.getInstance().getCurrentMap();
	
		int tileCurrentX = Math.round((_x + ( GameDataManager.getInstance().getCharWidth() / 3))/tileWidth);
		int tileCurrentY = Math.round((_y + (GameDataManager.getInstance().getCharHeight() - GameDataManager.getInstance().getCharHeight() / 3))/ tileHeight);
		
		
		/***************************************************************************
		 * This gets the values of the surrounding tiles
		 ***************************************************************************/
		_tileUp = map[tileCurrentY - 2][tileCurrentX];
		_tileUpRight = map[tileCurrentY - 2][tileCurrentX + 1];
		_tileRight = map[tileCurrentY][tileCurrentX + 1];
		_tileDownRight = map[tileCurrentY + 1][tileCurrentX + 1];
		_tileDown = map[tileCurrentY + 1][tileCurrentX];
		_tileDownLeft = map[tileCurrentY + 1][tileCurrentX - 1];
		_tileLeft = map[tileCurrentY][tileCurrentX - 1];
		_tileUpLeft = map[tileCurrentY - 2][tileCurrentX - 1];
		

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

			if (trigger == null || trigger.getName() != "death") {
				
				_jumpSpeed = _maxJump;
				GameDataManager.getInstance().setJumping(false);
			} else {
				_jumpSpeed = 0;
				GameDataManager.getInstance().setJumping(false);
			}

		}

		if (Math.floor(_jumpSpeed) < 10) {

			_jumpSpeed = 0;
		}

		
			if (_tileDown < cleartiles) {
				this._y += _gravity;

			} else {
				this._y = (tileBellow * tileHeight) - (tileHeight * 2);
			}
	

		if ( _tileUp < cleartiles) {

			this._y -= _jumpSpeed;
		} else {

			_jumpSpeed = 0;
		}
		

		
		
		/***************************************************************************
		 * Set moving speed
		 ***************************************************************************/

		if (GameDataManager.getInstance().getMovingRight()) {
			if (_speed < _maxSpeed) {
				_speed++;
			}

		} else if (GameDataManager.getInstance().getMovingLeft()) {
			if (_speed > -_maxSpeed) {
				_speed--;
			}

		} else {
			if (_speed > 0) {
				_speed -= Math.abs(_speed) * .3;
			}

			if (_speed < 0) {
				_speed += Math.abs(_speed) * .6;
			}
		}

		if (_speed > 0) {


			if (_tileRight > cleartiles) {

			
				_speed = 0;

			}

		} else if (_speed < 0) {

			if (_tileLeft > cleartiles) {

				
				_speed = 0;
			}
			

		}

		this._x += _speed;

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
