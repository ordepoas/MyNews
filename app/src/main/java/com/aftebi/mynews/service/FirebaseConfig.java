package com.aftebi.mynews.service;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConfig {

    private static FirebaseAuth auth;

    public static FirebaseAuth getAuth(){

        return auth = FirebaseAuth.getInstance();
    }

}
