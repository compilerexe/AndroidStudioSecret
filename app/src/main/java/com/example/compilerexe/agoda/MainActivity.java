package com.example.compilerexe.agoda;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compilerexe.agoda.model.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Intent intent_register;
    EditText txt_email, txt_password;
    Button btn_submit, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DBHelper db = new DBHelper(this, null, null, 0);
//        db.getWritableDatabase();

        intent_register = new Intent(this, RegisterActivity.class);

        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_password = (EditText)findViewById(R.id.txt_password);

        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_submit.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit) {
            String getInputEmail = txt_email.getText().toString();
            String getInputPassword = txt_password.getText().toString();
            DBHelper myDbHelper = new DBHelper(this, null, null, 0);
            SQLiteDatabase db = myDbHelper.getReadableDatabase();

            if (!TextUtils.isEmpty(getInputEmail) && !TextUtils.isEmpty(getInputPassword)) {

                //String sql = "SELECT * FROM Customers";
                String sql = "SELECT * FROM Customers WHERE Email = ? AND Password = ?";
                String[] whereArgs = {getInputEmail, getInputPassword};
                Cursor cur = db.rawQuery(sql, whereArgs);
                //Cursor cur = db.rawQuery(sql, null);
                if (cur != null) {
                    if (cur.moveToFirst()) {
                        do {
                            Log.d(TAG, "" + cur.getString(0) + cur.getString(1) + cur.getString(2));
                        } while (cur.moveToNext());
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Not found member.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }

        } else if (v == btn_register) {
            startActivity(intent_register);
        }
    }
}
