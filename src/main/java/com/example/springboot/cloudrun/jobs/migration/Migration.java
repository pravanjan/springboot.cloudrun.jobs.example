package com.example.springboot.cloudrun.jobs.migration;

import com.example.springboot.cloudrun.jobs.model.NotificationSettings;
import com.example.springboot.cloudrun.jobs.service.MigrationService;
import com.example.springboot.cloudrun.jobs.utils.ADCSetup;
import com.example.springboot.cloudrun.jobs.utils.WriteDataTocsv;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.AllArgsConstructor;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* This class is written only for temporary basis of migration
   After We successfully migrate the Tuna review this would be
   removed

 */
@AllArgsConstructor
public class Migration {
    private ADCSetup adcSetup;



    public Firestore getdb(){
      return  adcSetup.connect();

    }

    public List<QueryDocumentSnapshot> queryWithPagination(QueryDocumentSnapshot lastDoc , int limit) throws ExecutionException, InterruptedException, TimeoutException {

        CollectionReference cities = this.getdb().collection("DEMO");
        Query myQuery ;
        if( lastDoc == null)
             myQuery = cities.limit(limit);
        else
            myQuery = cities.startAfter(lastDoc).limit(limit);


        ApiFuture<QuerySnapshot> future = myQuery.get();
        List<QueryDocumentSnapshot> docs = future.get(90, TimeUnit.SECONDS).getDocuments();
        return docs;
    }

    public String getandUpdate(CollectionReference targetCollection, int limit, QueryDocumentSnapshot lastDoc ){

            try {

                List<QueryDocumentSnapshot> docs = queryWithPagination(lastDoc ,limit);
                updatedocs(targetCollection, docs);
                int count = limit;
               while(docs.size() >= limit){
                   lastDoc = docs.get(docs.size() -1);
                   docs =  queryWithPagination(lastDoc, limit);
                    updatedocs(targetCollection,docs);
                   count =  count+docs.size();
                   System.out.println("The total count of record "+count);


             }
                System.out.println("The total count of record "+count);


            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
            return  "success";


    }





    /* Two change on cloud run job

        Added batch update to firestore data

     */

    public List<QueryDocumentSnapshot> updatedocs(CollectionReference targetCollection, List<QueryDocumentSnapshot> docs) throws InterruptedException, ExecutionException {
         WriteBatch batch = this.getdb().batch();

        for (QueryDocumentSnapshot document : docs) {
            // Update the document data if needed
            // For example, adding a new field
            Map<String, Object> updatedData = document.getData();
            NotificationSettings settings = new MigrationService().convertToNotificationSettings(document.getId(), updatedData);
            DocumentReference documentReference = targetCollection.document(document.getId());
            batch.set(documentReference, settings);

        }
        batch.commit();
        return docs;

    }

    public void process(Migration migration , CollectionReference targetCollection , int limit)  {

        migration.getandUpdate( targetCollection, limit, null);
        try{
            WriteDataTocsv.uploadIntoCloudStorage(WriteDataTocsv.getCsvDataList());

        }
        catch (Exception e){
            System.out.println("Error in uploading to cloud storage");
        }
    }





    /** THIS MAIN METHOD WOULD BE USED FOR REVIEW SETTINGS  MIGRATION
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */

    public static  void run() throws ExecutionException, InterruptedException {

        Migration migration = new Migration(
                new ADCSetup("your projectId"));
        CollectionReference targetCollection = migration.getdb().collection("RESULT");

        migration.process(migration, targetCollection,  2);
    }






}
