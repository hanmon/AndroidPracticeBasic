package chtti.boo.messageapitest;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class SimpleMessageActivity extends Activity implements
        MessageApi.MessageListener,GoogleApiClient.ConnectionCallbacks{

    private TextView mIntroText;
    private static final String WEAR_MESSAGE_PATH="/message";
    private GoogleApiClient mGoogleApiClient;
    private ListView mDataItemList;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataItemList=(ListView)findViewById(R.id.listview);
        mIntroText=(TextView)findViewById(R.id.intro);
        //store data events received by the local broadcaster
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mDataItemList.setAdapter(mAdapter);
        //build a new Google API Client
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .build();
        if(mGoogleApiClient!=null && !(mGoogleApiClient.isConnected()||
                mGoogleApiClient.isConnecting()))
        {
            mGoogleApiClient.connect();
            Log.d("wear", "mGoogleApiClient is Connecting");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if( mGoogleApiClient != null && !( mGoogleApiClient.isConnected() ||
                mGoogleApiClient.isConnecting() ) )
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient!=null){
            Wearable.MessageApi.removeListener(mGoogleApiClient,this);
            if(mGoogleApiClient.isConnected()){
                mGoogleApiClient.disconnect();
            }
        }
    }

    @Override
    protected void onDestroy() {

        if(mGoogleApiClient!=null)
            mGoogleApiClient.unregisterConnectionCallbacks(this);
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.MessageApi.addListener(mGoogleApiClient,this);
        Log.d("wear", "mGoogleApiClient is Connected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onMessageReceived(final MessageEvent messageEvent) {
        Log.d("wear", "onMessageReceived");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (messageEvent.getPath().equalsIgnoreCase(WEAR_MESSAGE_PATH)) {
                    mIntroText.setVisibility(View.INVISIBLE);
                    mAdapter.add(new String(messageEvent.getData()));
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
