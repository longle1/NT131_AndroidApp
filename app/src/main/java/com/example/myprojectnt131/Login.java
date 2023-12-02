package com.example.myprojectnt131;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private ApiService.ApiInterface apiInterface;
    Button btn;
    TextInputEditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn = findViewById(R.id.btnLogin);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);


        apiInterface = ApiService.getInstance().getApiInterface();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the login API
                Call<LoginRequest> loginCall = apiInterface.login(username.getText().toString(), password.getText().toString());
                loginCall.enqueue(new Callback<LoginRequest>() {
                    @Override
                    public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                        LoginRequest loginRequest = response.body();
                        if (response.isSuccessful()) {
                            LoginRequest.Data userData = loginRequest.getData();
                            if (userData != null) {
                                Toast.makeText(Login.this, "login success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("username", userData.getUsername());
                                startActivity(i);
                            }else {
                                Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRequest> call, Throwable t) {
                        Log.i("haha", t.toString());
                        Toast.makeText(Login.this, "error " + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}