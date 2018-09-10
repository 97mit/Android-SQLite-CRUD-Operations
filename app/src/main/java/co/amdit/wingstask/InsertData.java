package co.amdit.wingstask;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertData extends AppCompatActivity {
    EditText employee_name, employee_email,employee_phone,employee_company;
    Button submit_details,btn_update;
    String e_name,e_email,e_phone,e_company;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data);
        employee_name = (EditText)findViewById(R.id.name);
        employee_email = (EditText)findViewById(R.id.email);
        employee_phone = (EditText)findViewById(R.id.phone);
        employee_company = (EditText)findViewById(R.id.company);
        submit_details = (Button)findViewById(R.id.submit);
        btn_update = (Button)findViewById(R.id.bt_update);

        // set button update by default invisible. It is visible when we perform update.
        btn_update.setVisibility(getIntent().getIntExtra("visible",View.INVISIBLE));

        //create DatabaseHelper object to access the methods of that class.
        databaseHelper = new DatabaseHelper(this);


        String id = getIntent().getStringExtra("ID");
        Cursor cur = databaseHelper.getData(id);
        if(cur!=null && cur.getCount()>0){
            cur.moveToFirst(); // move cursor to first position
            /*
            getting the present data with the help of cursor an put them into EditText fields before update.
             */
            employee_name.setText(cur.getString(cur.getColumnIndexOrThrow("name")));
            employee_email.setText(cur.getString(cur.getColumnIndexOrThrow("email")));
            employee_phone.setText(cur.getString(cur.getColumnIndexOrThrow("phone")));
            employee_company.setText(cur.getString(cur.getColumnIndexOrThrow("company")));

        }

        // define the Insert button onClick event
        submit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the input details from the editText fields
                e_name = employee_name.getText().toString();
                e_email = employee_email.getText().toString();
                e_phone = employee_phone.getText().toString();
                e_company = employee_company.getText().toString();
                // validation to check value is inserted or not
                if(e_name.equals(String.valueOf("")))
                    employee_name.setError("Enter your name.");
                else if(e_email.equals(String.valueOf("")))
                    employee_email.setError("Enter your email.");
                else if(e_phone.equals(String.valueOf("")))
                    employee_phone.setError("Enter your phone.");
                else if(e_company.equals(String.valueOf("")))
                    employee_company.setError("Enter your company.");
                else {
                    /*
                    insert the data into the database by calling rhe insertdata method with reference of databaseHelper object
                    and pass the employee details as a parameter.
                     */
                    databaseHelper.insertdata(e_name,e_email,e_phone,e_company);
                    Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_SHORT).show();
                    // go to the main activity after inserting the data
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }


            }
        });

        // define the update button onClick event
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = getIntent().getStringExtra("ID");
                //getting the input details from the editText fields
                e_name = employee_name.getText().toString();
                e_email = employee_email.getText().toString();
                e_phone = employee_phone.getText().toString();
                e_company = employee_company.getText().toString();
                // perform validation to check value is inserted or not
                if(e_name.equals(String.valueOf("")))
                    employee_name.setError("Enter your name.");
                else if(e_email.equals(String.valueOf("")))
                    employee_email.setError("Enter your email.");
                else if(e_phone.equals(String.valueOf("")))
                    employee_phone.setError("Enter your phone.");
                else if(e_company.equals(String.valueOf("")))
                    employee_company.setError("Enter your company.");

                 else {
                      /*
                    update the details of a particular id into the database by calling rhe updateData method with reference of databaseHelper object
                    and pass the new employee details as a parameter.
                     */
                     databaseHelper.updateData(id,e_name,e_email,e_phone,e_company);
                    Toast.makeText(getApplicationContext(),"Updated  "+id,Toast.LENGTH_SHORT).show();
                    //go to the main activity after update
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }


            }
        });

    }


}
