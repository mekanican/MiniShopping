package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;

import java.util.ArrayList;
import java.util.Arrays;

public class WelcomeActivity extends AppCompatActivity {
    public static AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetManager = getAssets();

        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                false, null, null, null, null);
        startActivityForResult(intent, 23);

    }

    // https://stackoverflow.com/questions/22174259/pick-an-email-using-accountpicker-newchooseaccountintent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 23) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("Email", email);

                startActivity(intent);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, ":(", Toast.LENGTH_LONG).show();
            }
        }
    }
}