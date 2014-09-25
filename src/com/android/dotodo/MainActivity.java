package com.android.dotodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    Button b1,b2,b3,b4;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
       
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        
        b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {				
				
				Intent i=new Intent(MainActivity.this,ReminderEditActivity.class);
				
				startActivityForResult(i, 0);				
			}
		});
        
        
 b3.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {				
				
				Intent i=new Intent(MainActivity.this,ReminderListActivity.class);
				
				startActivityForResult(i, 0);				
			}
		});
        
        
 b2.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View arg0) {				
			
			Intent i=new Intent(MainActivity.this,AddNote.class);
			
			startActivityForResult(i, 0);				
		}
	});
        
 
 b4.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View arg0) {				
			
			Intent i=new Intent(MainActivity.this,ViewNote.class);
			
			startActivity(i);				
		}
	});
 
 
        
		
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if(requestCode==0&&resultCode==2)		
			Toast.makeText(this, "acknowledgement from first activity", Toast.LENGTH_LONG).show();
    	else if(requestCode==1&&resultCode==3)		
			Toast.makeText(this, "acknowledgement from second activity", Toast.LENGTH_LONG).show();			
			
    	super.onActivityResult(requestCode, resultCode, data);
    }
}