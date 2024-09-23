package com.example.springboot.cloudrun.jobs.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.IOException;

public class ADCSetup {

    /* Setting up with application default credential
       This is simple approach to test things in locally pointing to
       staging firestore.
     */

        private final String projectId ;
        private Firestore firestore;

        public ADCSetup(final String projectId){
            this.projectId = projectId;
        }
        public Firestore connect(){

            if(firestore == null){
                try {
                    this.firestore =  FirestoreOptions.getDefaultInstance().toBuilder()
                                                      .setProjectId(projectId)
                                                      .setCredentials(GoogleCredentials.getApplicationDefault())
                                                      .build().getService();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return this.firestore;
            }
            return firestore;
        }


}
