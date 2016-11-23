package org.alljoyn.bus.sample.chat;

import org.alljoyn.bus.sample.chat.ChatApplication;
import org.alljoyn.bus.sample.chat.Observable;
import org.alljoyn.bus.sample.chat.Observer;
import org.alljoyn.bus.sample.chat.DialogBuilder;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by SDG on 11/21/2016.
 */

public class ServerActivity extends Activity implements Observer {

    private static final String TAG = "chat.ServerActivity";
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);


        mWaitList = new ArrayAdapter<String>(this, android.R.layout.test_list_item);
        ListView hlv = (ListView) findViewById(R.id.waitlist);
        hlv.setAdapter(mWaitList);

        Button updateButton = (Button)findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
;               updateHistory();
            }
        });

        Button removeButton = (Button)findViewById(R.id.remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mWaitList.getCount()!=0){
                    mChatApplication.removeHistory();
                    updateHistory();
                    Log.d(TAG, "get is not null");
                }
                else{
                    Log.d(TAG, "is null");
                }

            }
        });

        mChatApplication = (ChatApplication)getApplication();

        mChatApplication.checkin();

        updateHistory();

        mChatApplication.addObserver(this);


    }


    private void updateHistory() {
        Log.i(TAG, "updateHistory()");
        mWaitList.clear();
        List<String> messages = mChatApplication.getHistory();
        for (String message : messages) {
            mWaitList.add(message);
        }
        mWaitList.notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    private ChatApplication mChatApplication = null;
    private ArrayAdapter<String> mWaitList;
}
