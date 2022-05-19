package com.astrotalk.sdk.api.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.adapter.AstroChatAstrologerListAdapter;
import com.astrotalk.sdk.api.adapter.AstroWaitlistAdapter;
import com.astrotalk.sdk.api.model.ChatCompleteModel;
import com.astrotalk.sdk.api.model.UniversalAstrologerListModel;
import com.astrotalk.sdk.api.model.WaitlistModel;
import com.astrotalk.sdk.api.network.AstroApiEndPointInterface;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroDividerItemDecoration;
import com.astrotalk.sdk.api.utils.AstroUtilities;
import com.astrotalk.sdk.api.utils.AstroWaitlistClick;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class AstroChatAstrologerListActivity extends AppCompatActivity implements AstroChatAstrologerListAdapter.OnChatButtonListener, AstroWaitlistClick {

    private final Context context = this;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private AstroApiEndPointInterface apiEndPointInterface;

    private long fixedSessionId = -1;
    private ArrayList<UniversalAstrologerListModel> astrologerListModelArrayList = new ArrayList<>();
    private Call<ResponseBody> responseBodyCall;
    private int totalPageNumber = 1;
    private int pageNumber = 0;
    private boolean loading = true;
    private boolean isOffer = false;
    private AstroChatAstrologerListAdapter chatAstrologerListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<WaitlistModel> waitlistModelArrayList = new ArrayList<>();
    private int waitlistSize = 0;
    private boolean isWaitListJoined = false;
    private boolean holdallow = false;
    private boolean isPoWaitlsitJoined = false;
    boolean userHaveWaitlist = false;
    private RecyclerView waitlistRecycler;
    private RelativeLayout waitlistHolder;
    private AstroWaitlistAdapter waitlistAdapter;
    private final boolean isCollpase = false;
    private ImageView collapseActionView;

    private String token = "", auth_token = "", client_id = "", client_secret = "", base64String = "";
    private SharedPreferences sharedPreferences;
    private long user_id = -1;
    private String jwt_token = "";
    private Toolbar toolbar;
    private TextView toolbarTV, tvOrderHistory;
    Handler handler = new Handler();
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_activity_chat_astrologer_list);

        initViews();

        if (getIntent().hasExtra("token")) {
            token = getIntent().getStringExtra("token");
        }

        if (getIntent().hasExtra("client_id")) {
            client_id = getIntent().getStringExtra("client_id");
        }

        if (getIntent().hasExtra("client_secret")) {
            client_secret = getIntent().getStringExtra("client_secret");
        }

        if (getIntent().hasExtra("production")) {
            AstroConstants.LIVE_MODE = getIntent().getBooleanExtra("production", false);
        }

        byte[] data = new byte[0];
        try {
            String text = client_id + ":" + client_secret;
            data = text.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        auth_token = Base64.encodeToString(data, Base64.DEFAULT);

        verifyToken();

        tvOrderHistory.setOnClickListener(v -> {
            Intent intent = new Intent(context, AstroOrderHistoryActivity.class);
            startActivity(intent);
        });


        swipeRefreshLayout.setOnRefreshListener(() -> {
            pageNumber = 0;
            loading = true;
            totalPageNumber = 1;
            getAstrologerList();
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUserWaitList();
    }

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTV = (TextView) findViewById(R.id.toolbarTV);
        toolbarTV.setText(getResources().getString(R.string.at_chat_with_astrologer_heading));

        sharedPreferences = getSharedPreferences(AstroConstants.USER_DETAIL, MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(context);
        apiEndPointInterface = AstroApiEndPointInterface.retrofit.create(AstroApiEndPointInterface.class);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        chatAstrologerListAdapter = new AstroChatAstrologerListAdapter(this, astrologerListModelArrayList, this);
        recyclerView.setAdapter(chatAstrologerListAdapter);

        tvOrderHistory = findViewById(R.id.tvOrderHistory);

        waitlistHolder = findViewById(R.id.waitlistHolder);
        waitlistRecycler = (RecyclerView) findViewById(R.id.waitlistRecycler);

        collapseActionView = findViewById(R.id.collapseActionView);
        collapseActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo loveleen (no issue)
//                if (sharedPreferences.getBoolean(AstroConstants.WAITLIST_SHOW_HIDE, false) == true) {
//                    sharedPreferences.edit().putBoolean(AstroConstants.WAITLIST_SHOW_HIDE, false).apply();
//                    collapseActionView.setImageResource(R.drawable.arrow_down_navigation_new);
//                    Drawable drawable = getResources().getDrawable(R.drawable.at_recycler_div_black);
//                    AstroDividerItemDecoration dividerItemDecoration = new AstroDividerItemDecoration(context, drawable, 0);
//                    waitlistRecycler.addItemDecoration(dividerItemDecoration);
//                    waitlistAdapter = new AstroWaitlistAdapter(AstroChatAstrologerListActivity.this, waitlistModelArrayList, AstroChatAstrologerListActivity.this, isCollpase, "fromChat");
//                    waitlistRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//                    waitlistRecycler.setAdapter(waitlistAdapter);
//                } else {
//                    sharedPreferences.edit().putBoolean(AstroConstants.WAITLIST_SHOW_HIDE, true).apply();
//                    collapseActionView.setImageResource(R.drawable.arrow_up_navigation_new);
//                    Drawable drawable = getResources().getDrawable(R.drawable.at_recycler_div_black);
//                    AstroDividerItemDecoration dividerItemDecoration = new AstroDividerItemDecoration(context, drawable, 0);
//                    waitlistRecycler.addItemDecoration(dividerItemDecoration);
//                    waitlistAdapter = new AstroWaitlistAdapter(AstroChatAstrologerListActivity.this, waitlistModelArrayList, AstroChatAstrologerListActivity.this, isCollpase, "fromChat");
//                    waitlistRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//                    waitlistRecycler.setAdapter(waitlistAdapter);
//                }
            }
        });
    }

    private void verifyToken() {
        String url = "";
        try {
            url = AstroConstants.VERIFY_TOKEN +
                    "?authToken=" + token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        AstroUtilities.showLoader(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getString("status").equalsIgnoreCase("success")) {
                    JSONObject dataObject = new JSONObject(object.getString("data"));
                    user_id = dataObject.getLong("id");
                    jwt_token = "Bearer " + dataObject.getString("authToken");
                    String timeZone = dataObject.getString("timeZone");
                    String winzoDuration = dataObject.getString("winzoDuration");

                    sharedPreferences.edit().putLong(AstroConstants.USER_ID, user_id).apply();
                    sharedPreferences.edit().putString(AstroConstants.JWT_TOKEN, jwt_token).apply();
                    sharedPreferences.edit().putString(AstroConstants.WINZO_DURATION, winzoDuration).apply();

                    getAstrologerList();

                    Drawable drawable = getResources().getDrawable(R.drawable.at_recycler_div_black);
                    AstroDividerItemDecoration dividerItemDecoration = new AstroDividerItemDecoration(context, drawable, 0);
                    waitlistRecycler.addItemDecoration(dividerItemDecoration);
                    waitlistAdapter = new AstroWaitlistAdapter(this, waitlistModelArrayList, this, isCollpase, "fromChat");
                    waitlistRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    waitlistRecycler.setAdapter(waitlistAdapter);

                    getChatOrder(true);
                    handler.postDelayed(runnable = () -> {
                        checkUserWaitList();
                        handler.postDelayed(runnable, 10000);
                    }, 2000);

                } else {
                    AstroUtilities.showToast(context, object.getString("reason"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                AstroUtilities.closeLoader();
            }
        }, error -> AstroUtilities.closeLoader()) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", auth_token);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    public void getAstrologerList() {

        responseBodyCall = apiEndPointInterface.getAstrologerListSorting(
                AstroConstants.APP_ID, AstroConstants.BUSINESS_ID, AstroConstants.CONSULTANT_TYPE_ID, AstroConstants.TIME_ZONE, user_id,
                0, 1000, AstroConstants.SDK_VERSION, AstroConstants.CHAT_SERVICE_ID, AstroConstants.LANGUAGE_ID,
                false, false, false, false);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                AstroUtilities.closeLoader();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            String status;
                            int fo = 0;
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            totalPageNumber = jsonObject.getInt("totalPages");
                            if (totalPageNumber > pageNumber) {
                                loading = true;
                                pageNumber++;
                            } else {
                                loading = false;
                            }
                            JSONArray dataArray = jsonObject.getJSONArray("content");
                            ArrayList<UniversalAstrologerListModel> childChatArray = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                UniversalAstrologerListModel userChatListModel = new UniversalAstrologerListModel();
                                JSONObject jsonObject1 = dataArray.getJSONObject(i);
                                userChatListModel.setId(jsonObject1.getLong("id"));
                                if (jsonObject1.has("name") && !jsonObject1.isNull("name")) {
                                    userChatListModel.setFirstname(jsonObject1.getString("name"));
                                } else {
                                    userChatListModel.setFirstname("");
                                }

                                if (jsonObject1.has("exp") && !jsonObject1.isNull("exp")) {
                                    userChatListModel.setExperience(jsonObject1.getString("exp"));
                                } else {
                                    userChatListModel.setExperience("0 year");
                                }

                                if (jsonObject1.has("isPo") && !jsonObject1.isNull("isPo")) {
                                    userChatListModel.setPo(jsonObject1.getBoolean("isPo"));
                                } else {
                                    userChatListModel.setPo(false);
                                }

                                if (jsonObject1.has("isPoSo") && !jsonObject1.isNull("isPoSo")) {
                                    userChatListModel.setPoSo(jsonObject1.getBoolean("isPoSo"));
                                } else {
                                    userChatListModel.setPoSo(false);
                                }

                                userChatListModel.setOffer(isOffer);

                                if (jsonObject1.has("fo") && !jsonObject1.isNull("fo")) {
                                    fo = jsonObject1.getInt("fo");
                                } else {
                                    fo = 0;
                                }

                                if (jsonObject1.has("isBoostOn") && !jsonObject1.isNull("isBoostOn")) {
                                    userChatListModel.setBoostOn(jsonObject1.getBoolean("isBoostOn"));
                                } else {
                                    userChatListModel.setBoostOn(false);
                                }

                                if (fo > 0) {
                                    userChatListModel.setHasOffer(true);
                                    userChatListModel.setCashbackOfferValue(fo);
                                } else {
                                    userChatListModel.setHasOffer(false);
                                    userChatListModel.setCashbackOfferValue(0);
                                }

                                if (jsonObject1.has("skill") && !jsonObject1.isNull("skill")) {
                                    userChatListModel.setSkill(jsonObject1.getString("skill"));
                                } else {
                                    userChatListModel.setSkill("No skill");
                                }

                                if (jsonObject1.has("isFavourite") && !jsonObject1.isNull("isFavourite")) {
                                    userChatListModel.setFavourite(jsonObject1.getBoolean("isFavourite"));
                                } else {
                                    userChatListModel.setFavourite(false);
                                }
                                if (jsonObject1.has("offerDisplayName") && !jsonObject1.isNull("offerDisplayName")) {
                                    userChatListModel.setOfferDisplayName(jsonObject1.getString("offerDisplayName"));
                                } else {
                                    userChatListModel.setOfferDisplayName("");
                                }

                                if (jsonObject1.has("label") && !jsonObject1.isNull("label")) {
                                    userChatListModel.setLabel(jsonObject1.getString("label"));
                                } else {
                                    userChatListModel.setLabel("");
                                }

                                if (jsonObject1.has("languages") && !jsonObject1.isNull("languages")) {
                                    List<String> list = new ArrayList<>();
                                    JSONArray languageArray = jsonObject1.getJSONArray("languages");
                                    for (int j = 0; j < languageArray.length(); j++) {
                                        JSONObject languageObject = languageArray.getJSONObject(j);
                                        list.add(languageObject.getString("language"));
                                    }
                                    userChatListModel.setLanguage(TextUtils.join(", ", list));
                                } else {
                                    userChatListModel.setLanguage("");
                                }

                                if (jsonObject1.has("picId") && !jsonObject1.isNull("picId")) {
                                    userChatListModel.setProfilePic(jsonObject1.getString("picId"));
                                } else {
                                    userChatListModel.setProfilePic("");
                                }

                                if (jsonObject1.has("price") && !jsonObject1.isNull("price")) {
                                    userChatListModel.setPrice(jsonObject1.getInt("price"));
                                } else {
                                    userChatListModel.setPrice(1100);
                                }

                                if (jsonObject1.has("sessionPrice") && !jsonObject1.isNull("sessionPrice")) {
                                    userChatListModel.setSessionPrice(jsonObject1.getInt("sessionPrice"));
                                } else {
                                    userChatListModel.setSessionPrice(1100);
                                }

                                if (jsonObject1.has("notify") && !jsonObject1.isNull("notify")) {
                                    userChatListModel.setNotify(jsonObject1.getBoolean("notify"));
                                } else {
                                    userChatListModel.setNotify(false);
                                }
                                if (jsonObject1.has("rating") && !jsonObject1.isNull("rating")) {
                                    userChatListModel.setAvgRating(jsonObject1.getDouble("rating"));
                                } else {
                                    userChatListModel.setAvgRating(5);
                                }

                                if (jsonObject1.has("order") && !jsonObject1.isNull("order")) {
                                    userChatListModel.setNoOfRating(jsonObject1.getInt("order"));
                                    userChatListModel.setNew(jsonObject1.getInt("order") == 0);

                                } else {
                                    userChatListModel.setNoOfRating(0);
                                    userChatListModel.setNew(true);
                                }

                                /* MAIN STATUS */
                                if (jsonObject1.has("status") && !jsonObject1.isNull("status")) {
                                    status = jsonObject1.getString("status");
                                } else {
                                    status = "2";
                                }
                                if (status.equalsIgnoreCase("1")) {
                                    userChatListModel.setStatus("BUSY");

                                } else if (status.equalsIgnoreCase("2")) {
                                    userChatListModel.setStatus("CHAT");
                                } else if (status.equalsIgnoreCase("3")) {
                                    userChatListModel.setStatus("OFFLINE");
                                } else if (status.equalsIgnoreCase("4")) {
                                    userChatListModel.setWaitListJoined(true);
                                    userChatListModel.setStatus("BUSY");

                                } else if (status.equalsIgnoreCase("5")) {
                                    userChatListModel.setStatus("ASK");
                                } else if (status.equalsIgnoreCase("6")) {
                                    userChatListModel.setStatus("INPROGRESS");
                                } else if (status.equalsIgnoreCase("7")) {
                                    userChatListModel.setStatus("NOTAVILABLE");
                                } else {
                                    userChatListModel.setStatus("CHAT");
                                }

                                /* STATUS CALL */
                                String callStatus;
                                if (jsonObject1.has("statusCall") && !jsonObject1.isNull("statusCall")) {
                                    callStatus = jsonObject1.getString("statusCall");
                                } else {
                                    callStatus = "2";
                                }
                                userChatListModel.setStatusCallNew(callStatus);

                                if (callStatus.equalsIgnoreCase("1")) {
                                    userChatListModel.setStatusCall("BUSY");
                                } else if (callStatus.equalsIgnoreCase("2")) {
                                    userChatListModel.setStatusCall("CALL");
                                } else if (callStatus.equalsIgnoreCase("3")) {
                                    userChatListModel.setStatusCall("OFFLINE");
                                } else if (callStatus.equalsIgnoreCase("4")) {
                                    userChatListModel.setWaitListJoinedCall(true);
                                    userChatListModel.setStatusCall("BUSY");
                                } else {
                                    userChatListModel.setStatusCall("CALL");
                                }

                                if (jsonObject1.has("visibleForVideoLocal") && !jsonObject1.isNull("visibleForVideoLocal")) {
                                    userChatListModel.setVisibleForVideoLocal(jsonObject1.getBoolean("visibleForVideoLocal"));
                                } else {
                                    userChatListModel.setVisibleForVideoLocal(false);
                                }

                                if (jsonObject1.has("nextCall") && !jsonObject1.isNull("nextCall")) {
                                    userChatListModel.setNextCall(jsonObject1.getString("nextCall"));
                                } else {
                                    userChatListModel.setNextCall("");
                                }

                                /* STATUS VIDEO CALL */
                                String videoCall;
                                if (jsonObject1.has("statusVideoCall") && !jsonObject1.isNull("statusVideoCall")) {
                                    videoCall = jsonObject1.getString("statusVideoCall");
                                } else {
                                    videoCall = "2";
                                }

                                if (videoCall.equalsIgnoreCase("1")) {
                                    userChatListModel.setStatusVideoCall("BUSY");
                                } else if (videoCall.equalsIgnoreCase("2")) {
                                    userChatListModel.setStatusVideoCall("CALL");
                                } else if (videoCall.equalsIgnoreCase("3")) {
                                    userChatListModel.setStatusVideoCall("OFFLINE");
                                } else if (videoCall.equalsIgnoreCase("4")) {
                                    userChatListModel.setWaitListJoinedVideoCall(true);
                                    userChatListModel.setStatusVideoCall("BUSY");
                                } else {
                                    userChatListModel.setStatusVideoCall("CALL");
                                }

                                if (jsonObject1.has("visibleForVideoCallLocal") && !jsonObject1.isNull("visibleForVideoCallLocal")) {
                                    userChatListModel.setVisibleForVideoCallLocal(jsonObject1.getBoolean("visibleForVideoCallLocal"));
                                } else {
                                    userChatListModel.setVisibleForVideoCallLocal(false);
                                }

                                if (jsonObject1.has("nextVideoCall") && !jsonObject1.isNull("nextVideoCall")) {
                                    userChatListModel.setNextVideoCall(jsonObject1.getString("nextVideoCall"));
                                } else {
                                    userChatListModel.setNextVideoCall("");
                                }
                                userChatListModel.setStatusVideoCallNew(videoCall);

                                /* STATUS CHAT */
                                String statusChat;
                                if (jsonObject1.has("statusChat") && !jsonObject1.isNull("statusChat")) {
                                    statusChat = jsonObject1.getString("statusChat");
                                } else {
                                    statusChat = "2";
                                }
                                userChatListModel.setStatusChatNew(statusChat);
                                if (statusChat.equalsIgnoreCase("1")) {
                                    userChatListModel.setStatusChat("BUSY");

                                } else if (statusChat.equalsIgnoreCase("2")) {
                                    userChatListModel.setStatusChat("CHAT");
                                } else if (statusChat.equalsIgnoreCase("3")) {
                                    userChatListModel.setStatusChat("OFFLINE");
                                } else if (statusChat.equalsIgnoreCase("4")) {
                                    userChatListModel.setWaitListJoinedChat(true);
                                    userChatListModel.setStatusChat("BUSY");

                                } else if (statusChat.equalsIgnoreCase("5")) {
                                    userChatListModel.setStatusChat("ASK");
                                } else if (statusChat.equalsIgnoreCase("6")) {
                                    userChatListModel.setStatusChat("INPROGRESS");
                                } else if (statusChat.equalsIgnoreCase("7")) {
                                    userChatListModel.setStatusChat("NOTAVILABLE");
                                } else {
                                    userChatListModel.setStatusChat("CHAT");
                                }

                                if (jsonObject1.has("nextChat") && !jsonObject1.isNull("nextChat")) {
                                    userChatListModel.setNextChat(jsonObject1.getString("nextChat"));
                                } else {
                                    userChatListModel.setNextChat("");
                                }

                                if (jsonObject1.has("visibleForChatLocal") && !jsonObject1.isNull("visibleForChatLocal")) {
                                    userChatListModel.setVisibleForChatLocal(jsonObject1.getBoolean("visibleForChatLocal"));
                                } else {
                                    userChatListModel.setVisibleForChatLocal(false);
                                }

                                if (jsonObject1.has("tick") && !jsonObject1.isNull("tick")) {
                                    userChatListModel.setVerified(jsonObject1.getBoolean("tick"));
                                } else {
                                    userChatListModel.setVerified(false);
                                }

                                if (jsonObject1.has("wt") && !jsonObject1.isNull("wt")) {
                                    userChatListModel.setWaitListWaitTime(jsonObject1.getInt("wt"));
                                } else {
                                    userChatListModel.setWaitListWaitTime(0);
                                }

                                if (jsonObject1.has("next") && !jsonObject1.isNull("next")) {
                                    userChatListModel.setNextOnlineTimeChat(jsonObject1.getString("next"));
                                } else {
                                    userChatListModel.setNextOnlineTimeChat("");
                                }

                                if (jsonObject1.has("isFirstSession") && !jsonObject1.isNull("isFirstSession")) {
                                    userChatListModel.setFirstSession(jsonObject1.getBoolean("isFirstSession"));
                                } else {
                                    userChatListModel.setFirstSession(false);
                                }

                                if (jsonObject1.has("tag") && !jsonObject1.isNull("tag")) {
                                    userChatListModel.setTag(jsonObject1.getString("tag"));
                                } else {
                                    userChatListModel.setTag("");
                                }

                                if (jsonObject1.has("isIntroVideoActive") && !jsonObject1.isNull("isIntroVideoActive")) {
                                    userChatListModel.setIntroVideoActive(jsonObject1.getBoolean("isIntroVideoActive"));
                                } else {
                                    userChatListModel.setIntroVideoActive(false);
                                }
                                if (jsonObject1.has("introVideo") && !jsonObject1.isNull("introVideo")) {
                                    userChatListModel.setIntroVideo(jsonObject1.getString("introVideo"));
                                } else {
                                    userChatListModel.setIntroVideo("");
                                }

                                childChatArray.add(userChatListModel);
                            }

                            if (swipeRefreshLayout.isRefreshing()) {
                                astrologerListModelArrayList.clear();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            // demoArrayList= name of arrayList from which u want to remove duplicates
                            LinkedHashSet hs = new LinkedHashSet(childChatArray);
                            // demoArrayList= name of arrayList from which u want to remove duplicates
                            astrologerListModelArrayList.addAll(hs);

                            chatAstrologerListAdapter.setConsultantType(1);
                            chatAstrologerListAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                AstroUtilities.closeLoader();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onChatButtonClick(UniversalAstrologerListModel universalAstrologerListModel) {
        fixedSessionId = -1;
        if (universalAstrologerListModel.getStatus().equalsIgnoreCase("INPROGRESS")) {
            getLastChatStatus(universalAstrologerListModel);
        } else {
            if (universalAstrologerListModel.getStatus().equalsIgnoreCase("chat") && !universalAstrologerListModel.getWaitListJoined() ||
                    universalAstrologerListModel.getStatus().equalsIgnoreCase("busy") && !universalAstrologerListModel.getWaitListJoined() ||
                    universalAstrologerListModel.getStatus().equalsIgnoreCase("OFFLINE") && !universalAstrologerListModel.getWaitListJoined()) {

                userCanChat(universalAstrologerListModel, true);

            }
        }
    }

    private void chatshowOfflinePopup(UniversalAstrologerListModel userChatListModel) {
        if (userChatListModel.getNextChat() == null || userChatListModel.getNextChat().trim().isEmpty()) {
            chatshowOfflineWithDetailsPopup(userChatListModel);
        } else {
            AstroUtilities.showLoader(context);
            String url;
            url = AstroConstants.GET_NEXT_ONLINE_TIME +
                    "?userId=" + user_id +
                    "&appId=" + AstroConstants.APP_ID +
                    "&consultantId=" + userChatListModel.getId();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        AstroUtilities.closeLoader();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                String nextOnlineTime = "";
                                int waitTime = 0;
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd hh:mm aaa");
                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone(AstroConstants.TIME_ZONE));
                                if (dataObject.has("nextOnlineTimeChat") && !dataObject.isNull("nextOnlineTimeChat")) {
                                    nextOnlineTime = simpleDateFormat.format(new Date(dataObject.getLong("nextOnlineTimeChat")));
                                }

                                if (dataObject.has("waitlistWaitTime") && !dataObject.isNull("waitlistWaitTime")) {
                                    waitTime = dataObject.getInt("waitlistWaitTime");
                                }

                                if (nextOnlineTime.isEmpty()) {
                                    userChatListModel.setNextOnlineTimeChat(null);
                                    userChatListModel.setOfflineNextOnlineTime("");

                                } else {
                                    userChatListModel.setOfflineNextOnlineTime(nextOnlineTime);
                                    userChatListModel.setOfflineNextOnlineTimeLong(dataObject.getLong("nextOnlineTimeChat"));
                                    userChatListModel.setOfflineWaitListWaitTime(waitTime);

                                }
                                chatshowOfflineWithDetailsPopup(userChatListModel);

                            } else {
                                userChatListModel.setNextOnlineTimeChat(null);
                                chatshowOfflineWithDetailsPopup(userChatListModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            userChatListModel.setNextOnlineTimeChat(null);
                            chatshowOfflineWithDetailsPopup(userChatListModel);
                        }
                    }, error -> {
                        AstroUtilities.closeLoader();
                        userChatListModel.setNextOnlineTimeChat(null);
                        chatshowOfflineWithDetailsPopup(userChatListModel);
                    }) {
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
    }

    private void getLastChatStatus(final UniversalAstrologerListModel userChatListModel) {
        String url;
        url = AstroConstants.GET_LAST_CHAT_ID_STATUS +
                "?userId=" + user_id +
                "&appId=" + AstroConstants.APP_ID;
        AstroUtilities.showLoader(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                            JSONObject lastChatDataObject = new JSONObject(jsonObject.getString("data"));

                            if (lastChatDataObject.has("hasOrder") && !lastChatDataObject.isNull("hasOrder")) {
                                String currentStatus = "";
                                if (lastChatDataObject.getBoolean("hasOrder") == true) {
                                    if (lastChatDataObject.has("chatStatus") && !lastChatDataObject.isNull("chatStatus")) {
                                        currentStatus = lastChatDataObject.getString("chatStatus");
                                    } else {
                                        currentStatus = "";
                                    }
                                    if (currentStatus.equalsIgnoreCase("INPROGRESS")) {
                                        Intent intent = new Intent(context, AstroUserAstrologerChatWindowActivity.class);
                                        intent.putExtra("chatorder_id", lastChatDataObject.getLong("orderId"));
                                        intent.putExtra("isWaitList", true);
                                        startActivity(intent);
                                    } else {
                                        userCanChat(userChatListModel, true);
                                    }
                                } else {
                                    userCanChat(userChatListModel, true);
                                }
                            } else {
                                userCanChat(userChatListModel, true);
                            }
                        } else {
                            userCanChat(userChatListModel, true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("Error", error.getMessage())) {
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

    private void userCanChat(final UniversalAstrologerListModel userChatListModel, boolean b) {

        String url = AstroConstants.CHECK_CAN_CHAT_CAN_CALL_V2 +
                "?userId=" + user_id +
                "&consultantId=" + userChatListModel.getId() +
                "&appId=" + AstroConstants.APP_ID +
                "&businessId=" + AstroConstants.BUSINESS_ID +
                "&appVersionUser=" + AstroConstants.SDK_VERSION +
                "&timezone=" + AstroConstants.TIME_ZONE;

        AstroUtilities.showLoader(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    AstroUtilities.closeLoader();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                            if (userChatListModel.getStatus().equalsIgnoreCase("OFFLINE") && !userChatListModel.getWaitListJoined() && b) {
                                chatshowOfflinePopup(userChatListModel);
                                return;
                            }

                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            Boolean isToShowPlaces = false;
                            Boolean atLocationApi = false;

                            if (dataObject.has("googlePlaces") && !dataObject.isNull("googlePlaces"))
                                isToShowPlaces = dataObject.getBoolean("googlePlaces");

                            if (dataObject.has("atLocationApi") && !dataObject.isNull("atLocationApi"))
                                atLocationApi = dataObject.getBoolean("atLocationApi");

                            chatcheckUserWaitList(userChatListModel, isToShowPlaces, atLocationApi);

                        } else {
                            popup(jsonObject.getString("reason"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
            Log.e("Error", "Error");
            AstroUtilities.closeLoader();
        }) {
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

    private void chatshowOfflineWithDetailsPopup(UniversalAstrologerListModel userChatListModelNew) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.at_offline_when_online_popup_new);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView submit_btn = (TextView) dialog.findViewById(R.id.submit_btn);
        TextView cancelBtn = (TextView) dialog.findViewById(R.id.cancel_btn);
        TextView nextOnlineTime = (TextView) dialog.findViewById(R.id.onlineInTv);
        TextView consultantNameTV = (TextView) dialog.findViewById(R.id.consultantNameTV);
        RelativeLayout nextOnlineTimeRL = dialog.findViewById(R.id.nextOnlineTimeRL);
        RelativeLayout waitTimeRL = dialog.findViewById(R.id.waitTimeRL);
        TextView waitTimeTv = (TextView) dialog.findViewById(R.id.waitTimeTV);
        ImageView consultantPic = dialog.findViewById(R.id.consultantPic);
        TextView message = (TextView) dialog.findViewById(R.id.text);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);

        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);

        if (userChatListModelNew.getProfilePic().trim().isEmpty()) {
            consultantPic.setImageResource(R.drawable.at_ic_bg_astrologer);
        } else {
            Glide.with(context)
                    .load(AstroConstants.IMAGE_DOMAIN + userChatListModelNew.getProfilePic().trim())
                    .placeholder(R.drawable.at_ic_bg_astrologer)
                    .error(R.drawable.at_ic_bg_astrologer)
                    .into(consultantPic);
        }

        consultantNameTV.setText(userChatListModelNew.getFirstname());
        waitTimeTv.setTextColor(getResources().getColor(R.color.at_black));

        if (userChatListModelNew.getOfflineNextOnlineTime() == null || userChatListModelNew.getOfflineNextOnlineTime().trim().isEmpty())
            nextOnlineTimeRL.setVisibility(GONE);

        if (userChatListModelNew.getOfflineWaitListWaitTime() == 0)
            waitTimeRL.setVisibility(GONE);

        String messageText = "";
        if (userChatListModelNew.getOfflineNextOnlineTime() != null && !userChatListModelNew.getOfflineNextOnlineTime().trim().isEmpty()) {
            nextOnlineTime.setText(" " + userChatListModelNew.getOfflineNextOnlineTime());
            messageText = getResources().getString(R.string.at_astrologer_is_offline_text_popup_new).replaceAll("@ASTROLOGER", userChatListModelNew.getFirstname());
            message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            message.setMinLines(3);
            message.setVisibility(VISIBLE);
            message.setText(Html.fromHtml(messageText));
        } else {
            messageText = getResources().getString(R.string.at_astrologer_is_offline_text_popup_new).replaceAll("@ASTROLOGER", userChatListModelNew.getFirstname());
            message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            message.setMinLines(3);
            message.setVisibility(VISIBLE);
            message.setText(Html.fromHtml(messageText));
        }

        submit_btn.setOnClickListener(v -> {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            userCanChat(userChatListModelNew, false);
        });

        cancelBtn.setOnClickListener(v -> {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        if (!isFinishing()) {
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void popup(String text) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.at_dialog_general);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView submit_btn = (TextView) dialog.findViewById(R.id.submit_btn);
        TextView message = (TextView) dialog.findViewById(R.id.text);

        message.setText(text);
        submit_btn.setOnClickListener(v -> {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        if (!isFinishing()) {
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void popup2(String text) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.at_dialog_general);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView submit_btn = (TextView) dialog.findViewById(R.id.submit_btn);
        TextView message = (TextView) dialog.findViewById(R.id.text);
        TextView heading = (TextView) dialog.findViewById(R.id.heading);
        heading.setVisibility(VISIBLE);
        heading.setText(getResources().getString(R.string.at_profile_waitlist_limit));
        message.setText(text);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        if (!isFinishing()) {
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void chatcheckUserWaitList(UniversalAstrologerListModel
                                               astrologerListModel, Boolean isToShowPlaces, Boolean atLocationApi) {

        String url = null;
        try {
            url = AstroConstants.WAIT_LIST_CHECK_USER_V2_NEW +
                    "?userId=" + URLEncoder.encode(user_id + "", "UTF-8") +
                    "&appId=" + URLEncoder.encode(AstroConstants.APP_ID + "", "UTF-8") +
                    "&appVersion=" + URLEncoder.encode(AstroConstants.SDK_VERSION, "UTF-8") +
                    "&businessId=" + URLEncoder.encode(AstroConstants.BUSINESS_ID + "", "UTF-8");
        } catch (Exception e) {
            Log.e("Exception", "checkUserWaitList: " + e.getMessage());
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(), response -> {

            AstroUtilities.closeLoader();
            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                    boolean isWaitListJoined;
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    if (dataObject.getBoolean("allowed")) {
                        isWaitListJoined = false;
                    } else {
                        isWaitListJoined = true;
                    }
                    if (isWaitListJoined) {
                        popup2(getResources().getString(R.string.at_profile_you) + dataObject.getInt("maxWaitlistSize") + getResources().getString(R.string.at_profile_token));
                    } else {
                        String language_vals = "astrologerDetails.getLanguage()";
                        List<String> list = Arrays.asList(language_vals.split(","));
                        if (astrologerListModel.isVerified()) {
                            navigatetoChatIntakeform(astrologerListModel, isWaitListJoined, isToShowPlaces, atLocationApi);
                        } else {
                            navigatetoChatIntakeform(astrologerListModel, isWaitListJoined, isToShowPlaces, atLocationApi);
                        }
                    }
                } else {
                    AstroUtilities.showToast(context, jsonObject.getString("reason"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
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

    public void navigatetoChatIntakeform(
            final UniversalAstrologerListModel astrologerListModel,
            final boolean isWaitListJoined, Boolean isToShowPlaces, Boolean atLocationApi) {

        Intent intent = new Intent(context, AstroChatIntakeFormActivity.class);
        intent.putExtra("astrologer_details", astrologerListModel);
        intent.putExtra("isWaitListJoined", isWaitListJoined);
        intent.putExtra("isToShowPlaces", isToShowPlaces);
        // todo loveleen
//        intent.putExtra("atLocationApi", atLocationApi);
        intent.putExtra("atLocationApi", true);

        if (astrologerListModel.getPoSo()) {
            intent.putExtra("isOfferV3", true);
        } else {
            intent.putExtra("isOfferV3", astrologerListModel.isOffer());
        }

        intent.putExtra("status", astrologerListModel.getStatusChat());
        intent.putExtra("consultant", "astrologer");
        startActivityForResult(intent, 100);
    }

    private void checkUserWaitList() {

        waitlistModelArrayList.clear();
        String url = null;
        try {
            url = AstroConstants.WAIT_LIST_CHECK_USER_V2_NEW +
                    "?userId=" + URLEncoder.encode(user_id + "", "UTF-8") +
                    "&appId=" + URLEncoder.encode(AstroConstants.APP_ID + "", "UTF-8") +
                    "&appVersion=" + URLEncoder.encode(AstroConstants.SDK_VERSION, "UTF-8") +
                    "&businessId=" + URLEncoder.encode(AstroConstants.BUSINESS_ID + "", "UTF-8");

        } catch (Exception e) {
            Log.e("Exception", "Exception");
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(), response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject2 = jsonObject.getJSONObject("data");

                isWaitListJoined = !jsonObject2.getBoolean("allowed");
                holdallow = jsonObject2.getBoolean("isUnHoldUsed");

                if (jsonObject2.has("maxWaitlistSize") && !jsonObject2.isNull("maxWaitlistSize")) {
                    waitlistSize = jsonObject2.getInt("maxWaitlistSize");
                } else {
                    waitlistSize = 0;
                }

                if (jsonObject2.has("isPo") && !jsonObject2.isNull("isPo")) {
                    isPoWaitlsitJoined = jsonObject2.getBoolean("isPo");
                } else {
                    isPoWaitlsitJoined = false;
                }

                if (jsonObject2.has("tokens") && !jsonObject2.isNull("tokens")) {
                    JSONArray dataArray = jsonObject2.getJSONArray("tokens");
                    if (dataArray != null) {
                        if (dataArray.length() > 0) {
                            userHaveWaitlist = true;
                        } else {
                            userHaveWaitlist = false;
                        }
                    }
                    ArrayList<WaitlistModel> childOrderArray = new ArrayList<>();

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject1 = dataArray.getJSONObject(i);
                        WaitlistModel waitlistModel = new WaitlistModel();
                        if (jsonObject1.has("consultantName") && !jsonObject1.isNull("consultantName")) {
                            waitlistModel.setConsultantName(jsonObject1.getString("consultantName"));
                        } else {
                            waitlistModel.setConsultantName("");
                        }

                        if (jsonObject1.has("tokenStatus") && !jsonObject1.isNull("tokenStatus")) {
                            waitlistModel.setTokenStatus(jsonObject1.getString("tokenStatus"));
                        } else {
                            waitlistModel.setTokenStatus("");
                        }

                        if (jsonObject1.has("isVideoCall") && !jsonObject1.isNull("isVideoCall")) {
                            waitlistModel.setVideoCall(jsonObject1.getBoolean("isVideoCall"));
                        } else {
                            waitlistModel.setVideoCall(false);
                        }

                        if (jsonObject1.has("isPo") && !jsonObject1.isNull("isPo")) {
                            waitlistModel.setPo(jsonObject1.getBoolean("isPo"));
                        } else {
                            waitlistModel.setPo(false);
                        }

                        if (jsonObject1.has("leStatus") && !jsonObject1.isNull("leStatus")) {
                            waitlistModel.setLeStatus(jsonObject1.getString("leStatus"));
                        } else {
                            waitlistModel.setLeStatus("");
                        }

                        if (jsonObject1.has("liveEventId") && !jsonObject1.isNull("liveEventId")) {
                            waitlistModel.setLiveEventId(jsonObject1.getLong("liveEventId"));
                        } else {
                            waitlistModel.setLiveEventId(-1);
                        }

                        if (jsonObject1.has("liveEventType") && !jsonObject1.isNull("liveEventType")) {
                            waitlistModel.setLiveEventType(jsonObject1.getString("liveEventType"));
                        } else {
                            waitlistModel.setLiveEventType("");
                        }

                        if (jsonObject1.has("wt") && !jsonObject1.isNull("wt")) {
                            waitlistModel.setWt(jsonObject1.getString("wt"));
                        } else {
                            waitlistModel.setWt("");
                        }

                        if (jsonObject1.has("fixedSessionAmount") && !jsonObject1.isNull("fixedSessionAmount")) {
                            waitlistModel.setAmount(jsonObject1.getDouble("fixedSessionAmount"));
                        } else {
                            waitlistModel.setAmount(-1);
                        }

                        if (jsonObject1.has("fixedSessionDuration") && !jsonObject1.isNull("fixedSessionDuration")) {
                            waitlistModel.setDuration(jsonObject1.getInt("fixedSessionDuration"));
                        } else {
                            waitlistModel.setDuration(-1);
                        }

                        if (jsonObject1.has("fixedSessionId") && !jsonObject1.isNull("fixedSessionId")) {
                            waitlistModel.setFixedSessionId(jsonObject1.getLong("fixedSessionId"));
                        } else {
                            waitlistModel.setFixedSessionId(-1);
                        }

                        if (jsonObject1.has("offerV3id") && !jsonObject1.isNull("offerV3id")) {
                            waitlistModel.setOfferV3id(jsonObject1.getLong("offerV3id"));
                        } else {
                            waitlistModel.setOfferV3id(-1);
                        }

                        if (jsonObject1.has("holdReason") && !jsonObject1.isNull("holdReason")) {
                            waitlistModel.setHoldReason(jsonObject1.getString("holdReason"));
                        } else {
                            waitlistModel.setHoldReason("");
                        }

                        waitlistModel.setWaitlistsize(waitlistSize);
                        waitlistModel.setIsholdUsed(holdallow);

                        if (jsonObject1.has("isHold") && !jsonObject1.isNull("isHold")) {
                            waitlistModel.setHold(jsonObject1.getBoolean("isHold"));
                        } else {
                            waitlistModel.setHold(false);
                        }

                        if (jsonObject1.has("reportId") && !jsonObject1.isNull("reportId")) {
                            waitlistModel.setReportOrderId(jsonObject1.getLong("reportId"));
                        } else {
                            waitlistModel.setReportOrderId(-1);
                        }

                        if (jsonObject1.has("consultantId") && !jsonObject1.isNull("consultantId")) {
                            waitlistModel.setConsultantId(jsonObject1.getLong("consultantId"));
                        } else {
                            waitlistModel.setConsultantId(-1);
                        }

                        if (jsonObject1.has("callSubStatus") && !jsonObject1.isNull("callSubStatus")) {
                            waitlistModel.setCallSubStatus(jsonObject1.getString("callSubStatus"));
                        } else {
                            waitlistModel.setCallSubStatus("");
                        }

                        if (jsonObject1.has("isAgoraVoip") && !jsonObject1.isNull("isAgoraVoip")) {
                            waitlistModel.setAgoraVoip(jsonObject1.getBoolean("isAgoraVoip"));
                        } else {
                            waitlistModel.setAgoraVoip(false);
                        }

                        if (jsonObject1.has("callRate") && !jsonObject1.isNull("callRate")) {
                            waitlistModel.setCallRate(jsonObject1.getInt("callRate"));
                        } else {
                            waitlistModel.setCallRate(1100);
                        }

                        if (jsonObject1.has("sessionPrice") && !jsonObject1.isNull("sessionPrice")) {
                            waitlistModel.setSessionPrice(jsonObject1.getInt("sessionPrice"));
                        } else {
                            waitlistModel.setSessionPrice(1100);
                        }

                        if (jsonObject1.has("callTxnId") && !jsonObject1.isNull("callTxnId")) {
                            waitlistModel.setCallingTransactionId(jsonObject1.getLong("callTxnId"));
                        } else {
                            waitlistModel.setCallingTransactionId(-1);
                        }

                        if (jsonObject1.has("rt") && !jsonObject1.isNull("rt")) {
                            waitlistModel.setInprogressRemainingTimeInSec(jsonObject1.getLong("rt"));
                        } else {
                            waitlistModel.setInprogressRemainingTimeInSec(-1);
                        }

                        if (jsonObject1.has("chatOrderId") && !jsonObject1.isNull("chatOrderId")) {
                            waitlistModel.setChatOrderId(jsonObject1.getLong("chatOrderId"));
                        } else {
                            waitlistModel.setChatOrderId(-1);
                        }

                        if (jsonObject1.has("consultantPic") && !jsonObject1.isNull("consultantPic")) {
                            waitlistModel.setConsultantPic(jsonObject1.getString("consultantPic"));
                        } else {
                            waitlistModel.setConsultantPic("");
                        }

                        if (jsonObject1.has("displayStatus") && !jsonObject1.isNull("displayStatus")) {
                            waitlistModel.setDisplayStatus(jsonObject1.getString("displayStatus"));
                        } else {
                            waitlistModel.setDisplayStatus("");
                        }

                        if (jsonObject1.has("isConsultantOffline") && !jsonObject1.isNull("isConsultantOffline")) {
                            waitlistModel.setConsultantOffline(jsonObject1.getBoolean("isConsultantOffline"));
                        } else {
                            waitlistModel.setConsultantOffline(false);
                        }

                        if (jsonObject1.has("tokenType") && !jsonObject1.isNull("tokenType")) {
                            waitlistModel.setTokenType(jsonObject1.getString("tokenType"));
                        } else {
                            waitlistModel.setTokenType("");
                        }

                        if (jsonObject1.has("chatStatus") && !jsonObject1.isNull("chatStatus")) {
                            waitlistModel.setChatStatus(jsonObject1.getString("chatStatus"));
                        } else {
                            waitlistModel.setChatStatus("");
                        }

                        if (jsonObject1.has("isRecharge") && !jsonObject1.isNull("isRecharge")) {
                            waitlistModel.setRecharge(jsonObject1.getBoolean("isRecharge"));
                        } else {
                            waitlistModel.setRecharge(false);
                        }

                        if (jsonObject1.has("id") && !jsonObject1.isNull("id")) {
                            waitlistModel.setId(jsonObject1.getLong("id"));
                        } else {
                            waitlistModel.setId(-1);
                        }

                        if (waitlistModel.getReportOrderId() == -1) {
                            childOrderArray.add(waitlistModel);
                        }
                    }

                    waitlistModelArrayList.addAll(childOrderArray);
                    waitlistAdapter.notifyDataSetChanged();

                    if (waitlistModelArrayList.size() == 0) {
                        waitlistRecycler.setVisibility(View.GONE);
                    } else {
                        waitlistRecycler.setVisibility(View.VISIBLE);
                    }

                    if (waitlistModelArrayList.size() > 1) {
                        collapseActionView.setVisibility(View.VISIBLE);
                    } else {
                        collapseActionView.setVisibility(View.GONE);
                    }

                } else {
                    waitlistRecycler.setVisibility(View.GONE);
                    userHaveWaitlist = false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.e("Error", "Error");
        }) {
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

    private void getChatOrder(boolean isStart) {
        String url = null;
        try {
            url = AstroConstants.COMPLETE_CHAT_ORDER_LIST + "?userId=" + user_id + "" +
                    "&pageno=" + pageNumber +
                    "&pagesize=" + "7" +
                    "&timezone=" + URLEncoder.encode(AstroConstants.TIME_ZONE, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    ArrayList<ChatCompleteModel> childOrderArray = new ArrayList<>();

                    if(dataArray.length() > 0) {
                        tvOrderHistory.setVisibility(VISIBLE);
                    }
                    else {
                        tvOrderHistory.setVisibility(GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            AstroUtilities.closeLoader();
        }) {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Authorization", jwt_token);
                headers.put("id", user_id + "");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(WaitlistModel waitlistModel, boolean selected, boolean unhold, String fromHold) {
        // todo loveleen (pending)
        if (!fromHold.equalsIgnoreCase("")) {
            if (unhold) {
                cancelToken(waitlistModel.getId(), waitlistModel.getConsultantName());
            }
//            else {
//                unholdWaitlist(waitlistModel.getId());
//            }
        } else {
            cancelToken2(waitlistModel.getId());
//            if (selected) {
//                waitlistId = -1;
//                /*Intent intent = new Intent(ChatAstrologerlistActivity.this, AddMoneyActivity.class);
//                intent.putExtra("from", "chatlist");
//                startActivityForResult(intent, 1);*/
//
//                getAddMoneyData("");
//            } else {
//                cancelToken2(waitlistModel.getId());
//            }
        }
    }

    private void cancelToken(Long tokenNo, String consultantName) {
        android.app.AlertDialog.Builder Builder = new android.app.AlertDialog.Builder(AstroChatAstrologerListActivity.this);
        Builder.setTitle(getResources().getString(R.string.at_leave_waitlist_heading));
        Builder.setMessage(getResources().getString(R.string.at_leave_waitlist_heading) + " " + consultantName + "?");

        Builder.setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.at_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            dialog.cancel();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        AstroUtilities.showLoader(context);
                        String url = null;
                        try {
                            url = AstroConstants.CANCEL_WAITLIST +
                                    "?tokenId=" + URLEncoder.encode(tokenNo + "", "UTF-8");

                        } catch (Exception e) {
                            Log.e("Exception", "Exception");
                        }
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.trim(), response -> {
                            AstroUtilities.closeLoader();
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                                    waitlistModelArrayList.clear();
//                                    checkUserWaitList();
                                    refreshList();
                                    AstroUtilities.showToast(context, jsonObject.getString("Waitlist Canceled"));
                                } else {
                                    AstroUtilities.showToast(context, jsonObject.getString("reason"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> AstroUtilities.closeLoader()) {
                            @Override
                            public Map getHeaders() {
                                HashMap headers = new HashMap();
                                headers.put("Authorization", auth_token);
                                headers.put("id", user_id + "");
                                return headers;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        requestQueue.add(stringRequest);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.at_no), (dialog, id) -> {
                    try {
                        dialog.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        android.app.AlertDialog alert = Builder.create();
        alert.show();
        alert.getButton(alert.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alert.getButton(alert.BUTTON_POSITIVE).setTextColor(Color.RED);
    }

    private void cancelToken2(long id) {
        AstroUtilities.showLoader(context);
        String url = null;
        try {
            url = AstroConstants.CANCEL_WAITLIST +
                    "?tokenId=" + URLEncoder.encode(id + "", "UTF-8");
        } catch (Exception e) {
            Log.e("Exception", "Exception");
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url.trim(), response -> {
            AstroUtilities.closeLoader();
            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                    waitlistModelArrayList.clear();
//                    checkUserWaitList();
                    refreshList();
                    AstroUtilities.showToast(context, jsonObject.getString("Waitlist Canceled"));

                } else {
                    AstroUtilities.showToast(context, jsonObject.getString("reason"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> AstroUtilities.closeLoader()) {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Authorization", jwt_token);
                headers.put("id", user_id + "");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void refreshList() {
        pageNumber = 0;
        loading = true;
        totalPageNumber = 1;
        astrologerListModelArrayList.clear();
        getAstrologerList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}