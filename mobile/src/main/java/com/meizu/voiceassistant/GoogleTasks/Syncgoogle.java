package com.meizu.voiceassistant.GoogleTasks;

import android.app.IntentService;
import android.content.Intent;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.google.api.services.tasks.TasksScopes;
import com.meizu.voiceassistant.activity.TODOAsync;
import com.meizu.voiceassistant.activity.googleacount;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class Syncgoogle extends IntentService {
    private static String TAG = googleacount.class.getSimpleName();
    //    private static final String TAG = "gpm-login-activity";
    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    private SignInButton sign_in_button;
    private TextView mOutputText;

    private static final String PREF_ACCOUNT_NAME = "accountName";


    private static final String scope = "oauth2:" + TasksScopes.TASKS;
    private TODOAsync user;
    String mEmail;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;

    public Syncgoogle() {
        super("Syncgoogle");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method

    @Override
    protected void onHandleIntent(Intent intent) {

    }


}
