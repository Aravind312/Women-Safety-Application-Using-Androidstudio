package com.example.myapplication8;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Main8Activity extends AppCompatActivity {
    EditText name,email,phone,password;
    TextView textView2;
    Button signups,button4;
    String url="https://womenssafety8.000webhostapp.com/register.php";
//    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        name=findViewById(R.id.user_name);
        email=findViewById(R.id.user_email);
        phone=findViewById(R.id.user_phone);
        password=findViewById(R.id.editText6);

        signups=findViewById(R.id.button3);

        name = (EditText)findViewById(R.id.user_name);
        email = (EditText)findViewById(R.id.user_email);
        phone = (EditText)findViewById(R.id.user_phone);
        password = (EditText)findViewById(R.id.editText6);

        signups = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);





        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(Main8Activity.this,Mylocationfinding.class);
              //  startActivity(intent);

            }
        });
        signups.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                final String loginname = name.getText().toString();
                final String loginemail = email.getText().toString();
                final String loginphone = phone.getText().toString();
                final String loginpassword = password.getText().toString();



                if(loginemail.length() == 0)
                    Toast.makeText(Main8Activity.this, "E-mail cannot be empty", Toast.LENGTH_LONG).show();

                else if(loginname.length() == 0)
                    Toast.makeText(Main8Activity.this, "Name cannot be empty", Toast.LENGTH_LONG).show();

                else if(loginphone.length() == 0)
                    Toast.makeText(Main8Activity.this, "Phone Number cannot be empty", Toast.LENGTH_LONG).show();

                else if(loginpassword.length() == 0)
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_LONG).show();

                else
                {
                    RequestQueue queue = Volley.newRequestQueue(Main8Activity.this);
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Toast.makeText(Main8Activity.this, response, Toast.LENGTH_SHORT).show();
                            Log.i("My success", "" + response);
                            startActivity(new Intent(Main8Activity.this, MainActivity.class));
                        }


                    },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Main8Activity.this, "Error : " + error, Toast.LENGTH_LONG).show();
                                    Log.i("My error", "" + error);
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError
                        {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("name", loginname);
                            map.put("email", loginemail);
                            map.put("phonenumber", loginphone);
                            map.put("password", loginpassword);
                            return map;
                        }
                    };
                    queue.add(request);
                }
            }
        });






//        mauth=FirebaseAuth.getInstance();

  /*      try {
            if (mauth.getCurrentUser() != null) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            signups.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = editText2.getText().toString().trim();
                    String email = editText4.getText().toString().trim();
                    String phone = editText5.getText().toString().trim();
                    String password = editText6.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        editText2.setError("Name is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(email)) {
                        editText4.setError("Email is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(phone)) {
                        editText5.setError("Phone Number is Required");
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        editText6.setError("Password is Required");
                        return;
                    }
                    if (password.length() < 6) {
                        editText6.setError("Password Must be atleast 6 characters");
                        return;
                    }

                    mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Main8Activity.this, "user created", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Main8Activity.this, "Error!" + task.getException(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            });
        }
        catch (Exception e)
        {
            Log.e("Error",e.getMessage());
        }*/

    }
}
