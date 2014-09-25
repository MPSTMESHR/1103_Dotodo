package com.android.dotodo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



public class AddNote extends Activity {
	
	
	private EditText NoteText;
    private TextView Note;
    private Button Save;
    private Long mRowId1;
    private RemindersDbAdapter mDbHelper;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_note);
		mDbHelper = new RemindersDbAdapter(this);
        
		
		
		
	NoteText =(EditText)findViewById(R.id.editText1);
		Note =(TextView)findViewById(R.id.textView1);
		Save = (Button)findViewById(R.id.button5);
		Save.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		save(); 
        		setResult(RESULT_OK);
        	    Toast.makeText(AddNote.this, getString(R.string.note), Toast.LENGTH_SHORT).show();
        	    finish(); 
        	}
          
        });
		
		 mRowId1 = savedInstanceState != null ? savedInstanceState.getLong(RemindersDbAdapter.KEY_ROWID1) 
					: null;
    
		    
		    }

			private void setRowIdFromIntent() {
				if (mRowId1 == null) {
					Bundle extras = getIntent().getExtras();            
					mRowId1 = extras != null ? extras.getLong(RemindersDbAdapter.KEY_ROWID1) 
											: null;
					
				}
			}
		    
		    @Override
		    protected void onPause() {
		        super.onPause();
		        mDbHelper.close(); 
		    }
		    
		    @Override
		    protected void onResume() {
		        super.onResume();
		        mDbHelper.open(); 
		    	setRowIdFromIntent();
				
		    }
		    
		    
				
				
			
		   
		   
		    
		    @Override
		    protected void onSaveInstanceState(Bundle outState) {
		        super.onSaveInstanceState(outState);
		        outState.putLong(RemindersDbAdapter.KEY_ROWID1, mRowId1);
		    }
		    

		    
		    private void save() {
		      
		        String body1 = NoteText.getText().toString();

		        if (mRowId1 == null) {
		        	
		        	long id = mDbHelper.createNote(body1);
		            if (id > 0) {
		                mRowId1 = id;
		            }
		        } else {
		            mDbHelper.updateNote(mRowId1, body1);
		        }
		       
		       
		    
		    }
}