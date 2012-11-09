package com.commanderZ;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

public class CommanderZ extends Activity implements Trigger {
	/////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE VARIABLES
	/////////////////////////////////////////////////////////////////////////////////////
	private GameDisplay view;
	private int[][] _nextMap;
	private int _resetX = 200;
	private int _resetY = 600;
	Trigger trigerlistener;
	private String nextURL = "http://www.jmilstead.com/commanderz/levels.json";
	/////////////////////////////////////////////////////////////////////////////////////
	//SETUP STUFF
	/////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	trigerlistener = this;
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
		
		view = (GameDisplay) findViewById(R.id.surfaceView1);
		
		
		
    	createLevel();
    	
    }
    public void createLevel(){
    	
    	
    
    	GameDataManager.getInstance().setDpi(240);
    	
    	
    	GameDataManager.getInstance().setCurrentMap(new int[][] {{13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
												    			 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
												    			 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
												    			 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
												    			 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,2,3,4,5,6,7,8,9,10,11,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,15,16,17,18,19,20,21,22,23,24,25,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,28,29,30,31,32,33,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,41,42,43,44,45,46,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,59,60,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
													    		 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,52,53,54,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,55,0,55,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,55,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,55,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,52,53,54,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13},
																 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,56,56,56,1,57,57,57,57,57,57,57,1,1},
																 {14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14},
																 {14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14},
																 {14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14},
																 {14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14}});



    		TriggerTile.clearAll();
			
			TriggerTile myTrigger  = new TriggerTile(20,22, trigerlistener, "death");
			TriggerTile myTrigger1 = new TriggerTile(21,22, trigerlistener, "death");
			TriggerTile myTrigger2 = new TriggerTile(22,22, trigerlistener, "death");
			
			TriggerTile myTrigger3 = new TriggerTile(24,22, trigerlistener, "death");
			TriggerTile myTrigger4 = new TriggerTile(25,22, trigerlistener, "death");
			TriggerTile myTrigger5 = new TriggerTile(26,22, trigerlistener, "death");
			TriggerTile myTrigger6 = new TriggerTile(27,22, trigerlistener, "death");
			TriggerTile myTrigger7 = new TriggerTile(28,22, trigerlistener, "death");
			TriggerTile myTrigger8 = new TriggerTile(29,22, trigerlistener, "death");
			TriggerTile myTrigger9 = new TriggerTile(30,22, trigerlistener, "death");
			
			
			
			TriggerTile myTrigger10 = new TriggerTile(31,22, trigerlistener, "nextLevel");
			
		
	    	GameDataManager.getInstance().setCurrentTiles(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.tilestitle));
	    	GameDataManager.getInstance().setCurrentBackground(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.background1));
	    	GameDataManager.getInstance().setCurrentCharacter( new Character(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.commanderz),_resetX ,_resetY));
	    	
			
		
			
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

	public void trigger(String name) {
		
		if(name.contains("death")){
			
			GameDataManager.getInstance().setCurrentCharacter( new Character(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.commanderz),_resetX ,_resetY));
		}
		
		
		if(name.contains("nextLevel")){
			loadNextLevel();
		}
		
	}
	
	private void loadNextLevel(){
		 
		examineJSONFile();
	}
	
	
	
	void examineJSONFile()
    {
		
        try
        {
            String x = "";
            
            
            URL url = new URL(nextURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
                                // gets the server json data
                  /*  BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(
                                urlConnection.getInputStream()));
                    */
                    
            InputStream is =  urlConnection.getInputStream();//this.getResources().openRawResource(R.raw.levels);
            byte [] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String jsontext = new String(buffer);
        

            JSONObject mainObject = new JSONObject(jsontext);
            JSONArray contentObject = mainObject.getJSONArray("map");
            JSONObject charpos = mainObject.getJSONObject("character");
            JSONArray triggers = mainObject.getJSONArray("triggers");
            
            
       
            
            
            
            try
	        {
						GameDataManager.getInstance().setCurrentTiles(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.tilestitle));
				    	GameDataManager.getInstance().setCurrentCharacter( new Character(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.commanderz), charpos.getInt("x"),charpos.getInt("y")));
				    	
				    	_resetX = charpos.getInt("x");
				    	_resetY = charpos.getInt("y");
				    	GameDataManager.getInstance().setCurrentMap(convertJSONArray(contentObject));
			
			    		TriggerTile.clearAll();
						
			    	
			    		for( int i = 0 ; i < triggers.length(); i++)
			    		{
			    			TriggerTile newTrigger = new TriggerTile( triggers.getJSONObject(i).getInt("x"),triggers.getJSONObject(i).getInt("y"), trigerlistener, triggers.getJSONObject(i).getString("type"));
			    			
			    			if(triggers.getJSONObject(i).getString("type").contains("nextLevel")){
			    				nextURL = triggers.getJSONObject(i).getString("url");
			    			}
			    		}
						
						
					
				    	GameDataManager.getInstance().setCurrentBackground(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.background1));
					
				    	view.updateLevel();
	        }
		    catch (Exception je)
	        {
	        			Log.d("level load","Error: " + je.getMessage());
	        }
            
      
        }
        catch (Exception je)
        {
        	Log.d("json","Error w/file: " + je.getMessage());
        }
    }
	
	
	int[][] convertJSONArray(JSONArray json){
		
				int[][] newArray = null;
				JSONArray currentRow = null;
			
			 
			   for (int i=0;i<json.length();i++){
				  
					
						try {
							currentRow = json.getJSONArray(i);
							//Log.d("json row", currentRow.toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							Log.d("json", "cant get json array");
							e.printStackTrace();
						}
					
				   
				   if( i == 0 ){
					   newArray = new int[json.length()][currentRow.length()];
					   
				   }
				   
				   if(currentRow != null && newArray != null){
					   for( int j=0; j < currentRow.length() ; j++ ){
						   try {
							newArray[i][j] = currentRow.getInt(j);
							//Log.d("json value to insert", "" + currentRow.getInt(j));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.d("json", "cant get json array from current row");
						}
					   }
				   }
			   } 
			   
			   return newArray;
		
	}
	
	
  
}
