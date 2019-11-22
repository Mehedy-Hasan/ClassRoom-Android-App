package com.example.class_room;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {
    SQLiteDatabase db;
    EditText editsearchname,editempname,editempmail,editempsalary,editempphone;
    Button Add, Delete, Modify, View,search ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create database,EmployeeDB database name
        db=openOrCreateDatabase("EmployeeDBs", Context.MODE_PRIVATE, null);
        //create table Employee
        db.execSQL("CREATE TABLE IF NOT EXISTS Employee(EmpId INTEGER PRIMARY KEY AUTOINCREMENT,EmpName VARCHAR,EmpMail VARCHAR,EmpSalary VARCHAR, EmpPhone VARCHAR);");
        editsearchname = (EditText) findViewById(R.id.et);
        editempname = (EditText) findViewById(R.id.etName);
        editempmail = (EditText) findViewById(R.id.etmailid);
        editempsalary = (EditText) findViewById(R.id.etRoll);
        editempphone = (EditText) findViewById(R.id.etPhone);
        Add = (Button) findViewById(R.id.btnsave);
        Delete= (Button) findViewById(R.id.btndel);
        Modify= (Button) findViewById(R.id.btnupdate);
        View= (Button) findViewById(R.id. btnselect);
        search=(Button) findViewById(R.id. btnselectperticular);
        Add.setOnClickListener(this);
        Delete.setOnClickListener(this);
        Modify.setOnClickListener(this);
        View.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    public void msg(Context context,String str)
    {
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnsave)
        {
            // code for save data
            if(editempname.getText().toString().trim().length()==0||
                    editempmail.getText().toString().trim().length()==0||
                    editempsalary.getText().toString().trim().length()==0||
                    editempphone.getText().toString().trim().length()==0
            )
            {
                msg(this, "Please enter all values");
                return;
            }
            db.execSQL("INSERT INTO Employee(EmpName,EmpMail,EmpSalary,EmpPhone)VALUES('"+ editempname.getText()+"','"+ editempmail.getText()+ "','"+    editempsalary.getText()+"','"+ editempphone.getText()+ "');");
            msg(this, "Record added");
        }

        else if(v.getId()==R.id.btnupdate)
        {
            //code for update data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, "Enter Student  Name");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName='"+ editsearchname.getText()+"'", null);
            if(c.moveToFirst()) {
                db.execSQL("UPDATE Employee  SET EmpName ='"+ editempname.getText()+"', EmpMail='"+ editempmail.getText()+"',EmpSalary='"+      editempsalary.getText()+"', EmpPhone='"+ editempphone.getText()+"' WHERE EmpName ='"+editsearchname.getText()+"'");
                msg(this, "Record Modified");
            }
            else
            {
                msg(this, "Invalid Student Name");
            }
        }
        else if(v.getId()==R.id.btndel)
        {
            //code for delete data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, " Please enter Employee  Name ");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName ='"+ editsearchname.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM Employee WHERE EmpName ='"+ editsearchname.getText()+"'");
                msg(this, "Record Deleted");
            }
            else
            {
                msg(this, "Invalid Student Name ");
            }
        }
        else if (v.getId() == R.id.btnselect)
        {
            //code for select all data
            Cursor c=db.rawQuery("SELECT * FROM Employee", null);
            if(c.getCount()==0)
            {
                msg(this, "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("Student Name: "+c.getString(1)+"\n");
                buffer.append("Student E-Mail: "+c.getString(2)+"\n");
                buffer.append("Student Roll: "+c.getString(3)+"\n");
                buffer.append("Student Phone: "+c.getString(4)+"\n\n");
            }
            msg(this, buffer.toString());
        }
        else if(v.getId()==R.id.btnselectperticular)
        {
            //code for select particular data
            if(editsearchname.getText().toString().trim().length()==0)
            {
                msg(this, "Enter Student Name");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM Employee WHERE EmpName='"+editsearchname.getText()+"'", null);
            if(c.moveToFirst())
            {
                editempname.setText(c.getString(1));
                editempmail.setText(c.getString(2));
                editempsalary.setText(c.getString(3));
                editempphone.setText(c.getString(4));
            }
            else
            {
                msg(this, "Invalid Student Name");
            }
        }
    }
}
