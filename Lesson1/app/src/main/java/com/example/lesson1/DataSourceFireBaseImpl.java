package com.example.lesson1;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.Objects;

public class DataSourceFireBaseImpl extends BaseDataSource {

    private final static String COLLECTION_REMARKS = "CollectionRemarks";
    private final static String TAG = "DataSourceFireBaseImpl";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DATE = "date";

    private final FirebaseFirestore mStore = FirebaseFirestore.getInstance ();
    private final CollectionReference mCollection = mStore.collection ( COLLECTION_REMARKS );

    //singleTon
    private volatile static DataSourceFireBaseImpl sInstance;

    public static DataSourceFireBaseImpl getInstance() {
        DataSourceFireBaseImpl instance = sInstance;
        if (instance == null) {
            synchronized (DataSourceFireBaseImpl.class) {
                if (sInstance == null) {

                    instance = new DataSourceFireBaseImpl ();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    private DataSourceFireBaseImpl() {
        mCollection.orderBy ( "name", Query.Direction.ASCENDING ).get ()
                .addOnCompleteListener ( this::onFetchComplete )
                .addOnFailureListener ( this::onFetchFailed );
    }

    private void onFetchComplete(Task<QuerySnapshot> task) {
        LinkedList<Remark> data = new LinkedList<> ();
        if (task.isSuccessful ()) {

            for (QueryDocumentSnapshot document : Objects.requireNonNull ( task.getResult () )) {
                data.add ( new DataFromFirestore ( document.getId (), document.getData () ) );
                document.getId ();
                document.getData ();
            }
            mData.clear ();
            mData.addAll ( data );
            data.clear ();
            notifyDataSetChanged ();
        }
    }

    private void onFetchFailed(Exception e) {
        Log.e ( TAG, "Fetch failed", e );
    }

    @Override
    public void add(@NonNull Remark data) {
        final DataFromFirestore remark;
        if (data instanceof DataFromFirestore) {
            remark = (DataFromFirestore) data;
        } else {
            remark = new DataFromFirestore ( data );
        }
        mData.add ( remark );
        mCollection.add ( remark.getFields () ).addOnSuccessListener ( documentReference ->
                remark.setId ( documentReference.getId () ) );
    }

    @Override
    public void remove(int position) {
        String id = mData.get ( position ).getId ();
        assert id != null;
        mCollection.document ( id ).delete ();
        super.remove ( position );
    }

    @Override
    public void update(@NonNull Remark data) {
        String id = data.getId ();

        if (id != null) {
            int idx = 0;
            for (Remark remark : mData) {
                if (id.equals ( remark.getId () )) {

                    remark.setName ( data.getName () );
                    remark.setDescription ( data.getDescription () );
                    remark.setDate ( data.getDate () );
                    notifyUpdated ( idx );

                    mCollection.document ( id ).update ( FIELD_NAME, remark.getName (),
                            FIELD_DESCRIPTION, remark.getDescription (),
                            FIELD_DATE, remark.getDate () );
                    super.update ( remark );
                    return;
                }
                idx++;
            }
        }
    }
}
