package com.nlh.minishoping;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    public static AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetManager = getAssets();

        // Init data before starting!
        SharedInfo.getInstance().initData();

        // https://developer.android.com/training/id-auth/identify
        AccountManager am = AccountManager.get(this); // "this" references the current Context
        Account[] accounts = am.getAccountsByType("com.google");

        // https://stackoverflow.com/questions/15406535/accountmanager-how-to-let-the-user-select-an-account-using-a-dialog
        Intent intent = AccountManager.newChooseAccountIntent(null, null,
                new String[]{"com.google"}, null, null, null,
                null);

        startActivityForResult(intent, 1);

        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}