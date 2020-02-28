package com.example.myapplication8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button button,button2;
    String str_email,str_password;
    String url="https://womenssafety8.000webhostapp.com/login.php";
    boolean a=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(getBaseContext(),MyService.class));
        Context context;
        email = (EditText)findViewById(R.id.login_email);
        password = (EditText)findViewById(R.id.login_password);
        button = (Button)findViewById(R.id.button);





        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                final String loginEmail = email.getText().toString();
                final String loginPass = password.getText().toString();

                if (loginEmail.length() == 0)
                    Toast.makeText(MainActivity.this, "E-mail cannot be empty", Toast.LENGTH_LONG).show();

                else if (loginPass.length() == 0)
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_LONG).show();

                else {
                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Please Wait..");

                    progressDialog.show();

                    str_email = email.getText().toString().trim();
                    str_password = password.getText().toString().trim();
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            if (response.contains("Logged in successfully")) {

                                email.setText("");
                                password.setText("");
                                User user = new User(12,str_email);
                                SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
                                SharedPreferences.Editor Ed=sp.edit();
                                Ed.putString("email",loginEmail);
                                Ed.commit();

                                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                                sessionManagement.saveSession(user);

                                //2. step
                                moveToMainActivity(str_email);
                                EditText editTe = findViewById(R.id.login_email);

                                String mess=editTe.getText().toString();
                                Intent intent=new Intent(getApplicationContext(), Main40Activity.class);

                                intent.putExtra("extra_message",loginEmail);
                               // Toast.makeText(MainActivity.this,loginEmail, Toast.LENGTH_SHORT).show();

                                startActivity(intent);
                                // Intent intent = new Intent(MainActivity.this, Main16Activity.class);
                              //  startActivity(intent);

                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }

                        private void moveToMainActivity(String str_email) {
                            Intent intent = new Intent(MainActivity.this, Main40Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("extra_message",str_email);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", str_email);
                            params.put("password", str_password);
                            return params;

                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(request);

                }
            }

        });


        /*indViewById(R.id.button2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //if user already registered, open LoginActivity
                finish();
                startActivity(new Intent(MainActivity.this, Main8Activity.class));
            }
        });*/
        button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,Main8Activity.class);
                startActivity(in);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userID = sessionManagement.getSession();
        String emailID=sessionManagement.getSession2();
        if(userID != -1){
            //user id logged in and so move to mainActivity
            moveToMainActivity(emailID);
        }
        else{
            //do nothing
        }
    }

    private void moveToMainActivity(String emailID) {
        final String loginEmail = email.getText().toString();
        Intent intent = new Intent(MainActivity.this, Main40Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("extra_message",emailID);
        startActivity(intent);
    }
}
