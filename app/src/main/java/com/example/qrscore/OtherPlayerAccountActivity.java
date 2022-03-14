package com.example.qrscore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Purpose: This class shows a list of QRCodes that another player owns. Also shows total scanned,
 * total score, and rank. Can click on a QR code to go to a different screen.
 *
 * Outstanding issues:
 * TODO: go to this activity when view profile is clicked on for player
 * TODO: Rank needs to be implemented
 * TODO: Show total score, scanned, username, and rank
 * TODO: Go to QRCodeActivity when QRCode is clicked on
 * TODO: UI testing
 */

public class OtherPlayerAccountActivity extends AppCompatActivity {

    private ListView qrCodesList;
    final String TAG = "Sample";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_player_account);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String userUID = intent.getStringExtra("userID");

        ArrayList<QRCode> qrCodesDataList = new ArrayList<QRCode>();
        QRCodeAdapter qrCodesAdapter = new QRCodeAdapter(this, com.example.qrscore.R.layout.qr_codes_list_content, qrCodesDataList);
        qrCodesList = findViewById(R.id.qr_codes_list_view);
        qrCodesList.setAdapter(qrCodesAdapter);

        // Create textviews
        TextView scannedTextView = findViewById(R.id.scanned_text_view);
        TextView scoreTextView = findViewById(R.id.score_text_view);
        TextView usernameTextView = findViewById(R.id.username_text_view);
        TextView qrCodeTitleTextView = findViewById(R.id.qr_code_title_text_view);
        TextView rankTextView = findViewById(R.id.rank_text_view);

        // Set username textviews
        usernameTextView.setText(userUID);
        qrCodeTitleTextView.setText(userUID + "'s QR Codes");


        DocumentReference profileRef = db.collection("Profile").document(userUID);
        Query account = db.collection("Account").whereEqualTo("Profile", profileRef);

        // get account from user UID
        account.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot accountQuery = task.getResult();
                            if (!accountQuery.isEmpty()) {
                                Log.d(TAG, "DocumentQuery data: " + accountQuery.getDocuments());
                                DocumentReference qrDataList = accountQuery.getDocuments().get(0).getDocumentReference("qrDataList");

                                // get qrDataList from account
                                qrDataList.get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot qrDataListDocument = task.getResult();
                                                    if (qrDataListDocument.exists()) {
                                                        Log.d(TAG, "DocumentSnapshot data: " + qrDataListDocument.getData());
                                                        //qrList = qrDataListDocument.toObject(QRDataList.class);
                                                        scoreTextView.setText(qrDataListDocument.getData().get("sumOfScoresScanned").toString());
                                                        scannedTextView.setText(qrDataListDocument.getData().get("totalQRCodesScanned").toString());
                                                        rankTextView.setText(qrDataListDocument.getData().get("rank").toString());

                                                        ArrayList<DocumentReference> qrCodesArray = (ArrayList<DocumentReference>) qrDataListDocument.getData().get("qrCodes");

                                                        // get each QRCode from array
                                                        for (DocumentReference codeRef : qrCodesArray) {
                                                            codeRef.get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                DocumentSnapshot document = task.getResult();
                                                                                if (document.exists()) {
                                                                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                                                    QRCode code = document.toObject(QRCode.class);

                                                                                    // Add score to list
                                                                                    //qrCodesDataList.add(code.getQRScore().toString());
                                                                                    qrCodesDataList.add(code);
                                                                                    qrCodesAdapter.notifyDataSetChanged();
                                                                                } else {
                                                                                    Log.d(TAG, "No such qr code document");
                                                                                }
                                                                            } else {
                                                                                Log.d(TAG, "get failed with ", task.getException());
                                                                            }
                                                                        }});

                                                        }
                                                    }
                                                }
                                            }
                                        });
                            } else {
                                Log.d(TAG, "No such account document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

        // GOTO QRCodeActivity when code is clicked on
        qrCodesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // Go to new activity on item click
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OtherPlayerAccountActivity.this, QRCodeActivity.class);
                intent.putExtra("QR_ID", qrCodesAdapter.getItem(i).getHash());
                startActivity(intent);

            }
        });

    }

}