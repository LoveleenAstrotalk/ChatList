package com.astrotalk.sdk.api.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroUtilities;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class AstroAcceptChatInterfaceActivity extends AppCompatActivity {

    private Context context = this;
    private static final String TAG = AstroAcceptChatInterfaceActivity.class.getSimpleName();
    Button hang_up_button, pick_up_button;
    TextView tvReceiverName;
    SharedPreferences sharedPreferences;
    ImageView consultantIV;
    private Ringtone mRingtone;
    private Uri uri;
    private long chatOrderId = -1;
    private long astrologerId = -1;
    private String astrologerName = "";
    private String pic = "";
    private RequestQueue requestQueue;
    private long user_id = -1;
    private String jwt_token = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setLayoutFlags();
            }
        });

        requestQueue = Volley.newRequestQueue(context);
        setContentView(R.layout.at_activity_accept_chat_interface_actvity);
        sharedPreferences = getSharedPreferences(AstroConstants.USER_DETAIL, MODE_PRIVATE);
        user_id = sharedPreferences.getLong(AstroConstants.USER_ID, -1);
        jwt_token = sharedPreferences.getString(AstroConstants.JWT_TOKEN, "");

        init();
        playRingtoneSound();

        new CountDownTimer(1000 * 60 * 2, 1000 * 60 * 2) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("onTick", "onTick");
            }

            @Override
            public void onFinish() {
                try {
                    if (mRingtone.isPlaying())
                        mRingtone.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        }.start();
    }

    private void playRingtoneSound() {
        try {
            uri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE);
            if (uri == null)
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
            if (uri == null)
                uri = RingtoneManager.getValidRingtoneUri(this);

            mRingtone = RingtoneManager.getRingtone(this, uri);
            mRingtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLayoutFlags() {
        PowerManager powerManager = ((PowerManager) getSystemService(Context.POWER_SERVICE));
        PowerManager.WakeLock wake = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
        wake.acquire();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void init() {
        tvReceiverName = findViewById(R.id.tvReceiverName);
        consultantIV = findViewById(R.id.consultantIV);

        try {
            if (getIntent().hasExtra("data")) {
                String data = getIntent().getStringExtra("data");
                JSONObject jsonObject = new JSONObject(data);

                chatOrderId = jsonObject.getLong("chatorder_id");
                astrologerId = jsonObject.getLong("astrologer_id");
                astrologerName = jsonObject.getString("astrologer_name");
                pic = getIntent().getStringExtra("pic");
            }
        }
        catch(Exception e) {
            Log.e("Exception", e.getMessage());
        }

//        chatOrderId = getIntent().getLongExtra("chatorder_id", -1);
//        astrologerId = getIntent().getLongExtra("astrologer_id", -1);
//        astrologerName = getIntent().getStringExtra("astrologer_name");
//        pic = getIntent().getStringExtra("pic");

        if (pic != null && !pic.isEmpty()) {
            Glide.with(context)
                    .load(pic)
                    .placeholder(R.drawable.at_ic_user)
                    .error(R.drawable.at_ic_user)
                    .centerCrop()
                    .into(consultantIV);
        }
        tvReceiverName.setText(astrologerName);
        hang_up_button = findViewById(R.id.hang_up_button);
        pick_up_button = findViewById(R.id.pick_up_button);
        hang_up_button.setOnClickListener(v -> {
            try {
                if (mRingtone.isPlaying())
                    mRingtone.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            reject();
        });

        pick_up_button.setOnClickListener(v -> {
            try {
                if (mRingtone.isPlaying())
                    mRingtone.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            chatAccept();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mRingtone.isPlaying())
                mRingtone.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void chatAccept() {
        String url = null;
        try {
            url = AstroConstants.ACCEPT_CHAT +
                    "?chatOrderId=" + URLEncoder.encode(chatOrderId + "", "UTF-8") +
                    "&userId=" + URLEncoder.encode(user_id + "", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AstroUtilities.showLoader(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AstroUtilities.closeLoader();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equalsIgnoreCase("success")) {
                        startActivity(new Intent(context, AstroUserAstrologerChatWindowActivity.class)
                                .putExtra("chatorder_id", chatOrderId)
                                .putExtra("astrologer_id", astrologerId)
                                .putExtra("astrologer_name", astrologerName)
                                .putExtra("from", "notif")
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else {
                        AstroUtilities.showToast(context, object.getString("reason"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AstroUtilities.closeLoader();
                }
            }
        }, error -> AstroUtilities.closeLoader()) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", jwt_token);
                headers.put("id", user_id + "");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void reject() {
        String url = "";
        try {
            url = AstroConstants.REJECT_CHAT +
                    "?chatOrderId=" + URLEncoder.encode(chatOrderId + "", "UTF-8") +
                    "&userId=" + URLEncoder.encode(user_id + "", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AstroUtilities.showLoader(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AstroUtilities.closeLoader();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equalsIgnoreCase("success")) {
                        AstroUtilities.showToast(context, "You’ve cancelled your chat with " + astrologerName);
                        Intent intent = new Intent(context, AstroChatAstrologerListActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        AstroUtilities.showToast(context, object.getString("reason"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AstroUtilities.closeLoader();
                }
            }
        }, error -> AstroUtilities.closeLoader()) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", jwt_token);
                headers.put("id", user_id + "");
                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (mRingtone.isPlaying())
                mRingtone.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, AstroChatAstrologerListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mRingtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
