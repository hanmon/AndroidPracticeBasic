package chtti.boo.messageapitest;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks {
    private static final String WEAR_MESSAGE_PATH="/message";
    private GoogleApiClient mGoogleApiClient;
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;
    private EditText mEditText;
    private Button mBtnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mEditText = (EditText) findViewById(R.id.txtInput);
        mBtnSend = (Button) findViewById(R.id.btnSend);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    mAdapter.add(text);
                    mAdapter.notifyDataSetChanged();
                    sendMessage(WEAR_MESSAGE_PATH, text);
                }


            }
        });
        //---Build a new Google API client---
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();

    }

    private void sendMessage(final String path, final String text){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        NodeApi.GetConnectedNodesResult nodes =
                                Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                        for (Node node : nodes.getNodes()) {
                            MessageApi.SendMessageResult result =
                                    Wearable.MessageApi.sendMessage(mGoogleApiClient, node.getId(), path, text.getBytes()).await();
                            Log.d("chtti","Messgae Sent!");
                            if (result.getRequestId() == MessageApi.UNKNOWN_REQUEST_ID) {
                                Log.v("sendMessage", "Failed to send message: " + text);
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mEditText.setText("");
                            }
                        });
                    }
                }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("chtti","GoogleApiClientConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
