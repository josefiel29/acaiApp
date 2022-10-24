package com.example.acai;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText nameTxt, passwordTxt;
    private String name, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        nameTxt = findViewById(R.id.editTextTextPassword);
        passwordTxt = findViewById(R.id.editTextTextPassword2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameTxt.getText().toString();
                password = passwordTxt.getText().toString();

                String authToken = createAuthToken(name, password);
                checkLoginDetails(authToken);
            }
        });
    }

    private void checkLoginDetails(String authToken) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        final InterfaceAPI api = retrofit.create(InterfaceAPI.class);

        Call<String> call = api.checkLogin(authToken);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().matches("success")) {
                        Toast.makeText(getApplicationContext(), "Successfully logged in.", Toast.LENGTH_LONG).show();
                        //see shared preferences
                        //next intent
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid credentials.", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("TAG", t.toString());
                t.printStackTrace();
            }
        });
    }

    private String createAuthToken(String name, String password) {
        byte [] data = new byte[0];
        try {
            data = (name + ":" + password).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Basic" + Base64.encodeToString(data, Base64.NO_WRAP);
    }
}