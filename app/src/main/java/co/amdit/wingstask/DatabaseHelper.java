package co.amdit.wingstask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE = "database.db"; //database name
    public static String TABLE ="employee";   //table name
    // columns name
    public static String ID = "id";
    public static String NAME ="name";
    public static String EMAIL = "email";
    public static String PHONE = "phone";
    public static String COMPANY ="company";
    String br;


    public DatabaseHelper(Context context) {
        super(context, DATABASE, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //query to create table if not exist.
        br = "CREATE TABLE IF NOT EXISTS "+TABLE+"("+ID+ " Integer PRIMARY KEY AUTOINCREMENT,"+NAME+ " Text, "+EMAIL+ " Text, "+PHONE+ " Text, "+COMPANY+ " Text);";
        db.execSQL(br); //execute the SQL query.

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE+" ;");
        onCreate(db);

    }
    /*
    insert the employee details (row values) into the table
     */
    public void insertdata(String name,String email ,String phone,String company){
        SQLiteDatabase db = this.getWritableDatabase();  //access the database in writable mode
        // create an object of ContentValues class to contain the values
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME,name);
        contentValues.put(EMAIL,email);
        contentValues.put(PHONE,phone);
        contentValues.put(COMPANY,company);
        //insert contentValues into database table.
        db.insert(TABLE,null,contentValues);

    }

    /*
    method to delete the employee detail with an particular id.
     */
    public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE,"id= ?",new String[]{id});
    }

    /*
    method to update the employee details
     */
    public boolean updateData(String id,String name,String email,String phone,String company){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(EMAIL,email);
        contentValues.put(PHONE,phone);
        contentValues.put(COMPANY,company);
        db.update(TABLE,contentValues,"id= ?",new String[]{id});
        return true;
    }
    /*
    method to get a single row of the employee table
     */
    public Cursor getData(String key){
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT * FROM '"+TABLE+"' where id = '"+key+"';";
        return db.rawQuery(qry,null);
    }

    /*
    method to get the list of all employee details in the table.
     */
    public List<DataModel> getAllData(){
        List<DataModel> data = new ArrayList<>(); //ArrayList to store the data
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //query to fetch all employee details and store the result of query into cursor object.
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLE+" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext())
        {
            //getting the data with the help of cursor and store into string variables
            dataModel = new DataModel();
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String company = cursor.getString(cursor.getColumnIndexOrThrow("company"));

            //set the string variables into the variables of DataModel class by using that object.
            dataModel.setId(id);
            dataModel.setEmpName(name);
            dataModel.setEmpEmail(email);
            dataModel.setEmpPhone(phone);
            dataModel.setEmpCompany(company);
            data.add(dataModel);
        }
        for (DataModel mo:data ) {

            Log.i("Hellomo",""+mo.getEmpName()+" "+ mo.getId());
        }
        //return the List data
        return data;
    }
}
