package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    private TextView authStatusTv;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private Button authbutton;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authStatusTv = findViewById(R.id.authStatusTv);
        authbutton = findViewById(R.id.authbutton);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                authStatusTv.setText("Authentication error"+errString);
                Toast.makeText(MainActivity.this, "Authentication error"+errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                openSelmi();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                authStatusTv.setText("Authentication failed...!");
                Toast.makeText(MainActivity.this, "Authentication failed...!", Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("login using fingerprint authentication")
                .setNegativeButtonText("User App Password")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK )
                .build();
        authbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                biometricPrompt.authenticate(promptInfo);
            }
        });



    }

    private void openSelmi() {

        Intent intent = new Intent(this, Selmi.class);
        startActivity(intent);

    }
}