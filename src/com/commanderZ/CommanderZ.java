package com.commanderZ;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

public class CommanderZ extends Activity {
	/////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE VARIABLES
	/////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////////////////////////////////////////////////////////////
	//SETUP STUFF
	/////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
    	
    	GameDataManager.getInstance().setCurrentMap(new int[][] {{1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,1,0,1,1,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,1,1,1,1,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,1,1,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,1,1,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,1,1,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,1,1,1,0,0,0,0,0,1,1,1,1,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,1,1,1,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,1,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,1,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,1,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,1,0,0,1,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,1,1,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,1,0,0,1,1,1,1,1},
				 {1,0,0,1,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,1,1,1,1,1,1,1,1,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,1,1,1,1,1,1,1,1,1,1},
				 {1,0,0,1,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,1,0,0,0,0,0,0,0,0,0,0,1,1},
				 {1,1,1,0,0,0,0,0,1,0,0,0,0,1},
				 {1,1,1,1,1,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,1,1,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,1,1,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				 {1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				 {1,1,1,1,1,1,1,1,1,1,1,1,1,1}});
    	
    	
    	
    	
    	DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	       
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gamescreen); 
        
        Button leftButton = (Button) findViewById(R.id.button1);
        leftButton.setOnTouchListener(leftListener);
        
        
        Button rightButton = (Button) findViewById(R.id.button2);
        rightButton.setOnTouchListener(rightListener);
        
        
        Button shootButton = (Button) findViewById(R.id.button3);
        shootButton.setOnTouchListener(shootListener);
        
        
        Button jumpButton = (Button) findViewById(R.id.button4);
        jumpButton.setOnTouchListener(jumpListener);
        
    }
    
	/////////////////////////////////////////////////////////////////////////////////////
	//EVENT HANDELING
	/////////////////////////////////////////////////////////////////////////////////////
    public OnTouchListener rightListener = new OnTouchListener() {
    	        public boolean onTouch(View v, MotionEvent event) {
    	            if (event.getAction() == MotionEvent.ACTION_DOWN) {
    	                GameDataManager.getInstance().setMovingRight(true);
    	            }
    	            if (event.getAction() == MotionEvent.ACTION_UP) {
    	                GameDataManager.getInstance().setMovingRight(false);
    	            }
    	            return false;
    	        }
    };
    public OnTouchListener leftListener = new OnTouchListener() {  
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                GameDataManager.getInstance().setMovingLeft(true);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                GameDataManager.getInstance().setMovingLeft(false);
            }
            return false;
        }
    };
    
    public OnTouchListener shootListener = new OnTouchListener() {  
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //GameDataManager.getInstance().setMovingLeft(true);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                //GameDataManager.getInstance().setMovingLeft(false);
            }
            return false;
        }
    };
    
    public OnTouchListener jumpListener = new OnTouchListener() {  
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                GameDataManager.getInstance().setJumping(true);
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
            	 GameDataManager.getInstance().setJumping(false);
            }
            return false;
        }
    };
	/////////////////////////////////////////////////////////////////////////////////////
	//END :D
	/////////////////////////////////////////////////////////////////////////////////////
  
}
