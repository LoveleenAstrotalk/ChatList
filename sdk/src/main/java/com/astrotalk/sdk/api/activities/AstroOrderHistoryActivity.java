package com.astrotalk.sdk.api.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.adapter.ChatCompleteOrderAdapter;
import com.astrotalk.sdk.api.model.ChatCompleteModel;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroUtilities;
import com.astrotalk.sdk.api.utils.AstroWrapContentLinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AstroOrderHistoryActivity extends AppCompatActivity {

    private Context context = this;

    private Toolbar toolbar;
    private TextView toolbarTV;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private long user_id = -1;
    private String jwt_token = "";

    private TextView no_data_text;
    private SwipeRefreshLayout swipe_refresh;
    ChatCompleteOrderAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ChatCompleteModel> chatCompleteModelArrayList = new ArrayList<>();
    AstroWrapContentLinearLayoutManager linearLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int pageNumber = 0;
    int totalpageNumber = 0;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_activity_order_history);

        initViews();

        getChatOrder(true);

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 0;
                loading = true;
                totalpageNumber = 0;
                getChatOrder(true);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTV = (TextView) findViewById(R.id.toolbarTV);
        toolbarTV.setText(getResources().getString(R.string.at_chat_order_history));

        requestQueue = Volley.newRequestQueue(context);
        sharedPreferences = getSharedPreferences(AstroConstants.USER_DETAIL, MODE_PRIVATE);
        user_id = sharedPreferences.getLong(AstroConstants.USER_ID, -1);
        jwt_token = sharedPreferences.getString(AstroConstants.JWT_TOKEN, "");

        no_data_text = findViewById(R.id.no_data_text);
        swipe_refresh = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_view);

        linearLayoutManager = new AstroWrapContentLinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ChatCompleteOrderAdapter(context, chatCompleteModelArrayList);

        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            getChatOrder(false);
                        }
                    }
                }
            }
        });
    }

    private void getChatOrder(boolean isStart) {
        AstroUtilities.showLoader(context);
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
                AstroUtilities.closeLoader();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    totalpageNumber = jsonObject.getInt("totalPages");
                    if (totalpageNumber > pageNumber) {
                        loading = true;
                        pageNumber++;
                    } else {
                        loading = false;
                    }

                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    ArrayList<ChatCompleteModel> childOrderArray = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject1 = dataArray.getJSONObject(i);
                        Log.d("chat history", jsonObject1.toString());
                        ChatCompleteModel questionHistoryModel = new ChatCompleteModel();

                        if (jsonObject1.getString("status").equalsIgnoreCase("COMPLETED") || jsonObject1.getString("status").equalsIgnoreCase("CANCELLED")) {

                            String status = "2";

                            if (jsonObject1.has("pricePerMinute") && !jsonObject1.isNull("pricePerMinute")) {
                                questionHistoryModel.setPricePerMinute(jsonObject1.getDouble("pricePerMinute"));
                            } else {
                                questionHistoryModel.setPricePerMinute(0);
                            }
                            if (jsonObject1.has("isPrescribed") && !jsonObject1.isNull("isPrescribed")) {
                                questionHistoryModel.setPrescribed(jsonObject1.getBoolean("isPrescribed"));

                            } else {
                                questionHistoryModel.setPrescribed(false);

                            }
                            if (jsonObject1.has("totalPriceSpent") && !jsonObject1.isNull("totalPriceSpent")) {
                                questionHistoryModel.setTotalPriceSpent(jsonObject1.getDouble("totalPriceSpent"));
                            } else {
                                questionHistoryModel.setTotalPriceSpent(0);
                            }
                            questionHistoryModel.setId(jsonObject1.getLong("id"));
                            if (jsonObject1.has("consultantId") && !jsonObject1.isNull("consultantId")) {
                                questionHistoryModel.setConsultantId(jsonObject1.getLong("consultantId"));
                            } else {
                                questionHistoryModel.setConsultantId(-1);
                            }
                            if (jsonObject1.has("consultantName") && !jsonObject1.isNull("consultantName")) {
                                questionHistoryModel.setConsultantName(jsonObject1.getString("consultantName"));
                            } else {
                                questionHistoryModel.setConsultantName(" ");
                            }

                            if (jsonObject1.has("durationInMin") && !jsonObject1.isNull("durationInMin")) {
                                questionHistoryModel.setDuration(jsonObject1.getInt("durationInMin"));
                            } else {
                                questionHistoryModel.setDuration(0);
                            }


                            if (jsonObject1.has("fixedId") && !jsonObject1.isNull("fixedId")) {
                                questionHistoryModel.setFixedId(jsonObject1.getLong("fixedId"));
                            } else {
                                questionHistoryModel.setFixedId(-1);
                            }
                            if (jsonObject1.has("rating") && !jsonObject1.isNull("rating")) {
                                questionHistoryModel.setRating(jsonObject1.getInt("rating"));
                            } else {
                                questionHistoryModel.setRating(0);
                            }
                            if (jsonObject1.has("reviewPublic") && !jsonObject1.isNull("reviewPublic")) {
                                questionHistoryModel.setReview(jsonObject1.getString("reviewPublic"));
                            } else {
                                questionHistoryModel.setReview("");
                            }

                            if (jsonObject1.has("chatTypeToken") && !jsonObject1.isNull("chatTypeToken")) {
                                questionHistoryModel.setChatTypeToken(jsonObject1.getString("chatTypeToken"));
                            } else {
                                questionHistoryModel.setChatTypeToken("");
                            }

                            if (jsonObject1.has("startTime") && !jsonObject1.isNull("startTime")) {
                                questionHistoryModel.setStartTime(jsonObject1.getLong("startTime"));
                            } else {
                                questionHistoryModel.setStartTime(System.currentTimeMillis());
                            }
                            if (jsonObject1.has("endTime") && !jsonObject1.isNull("endTime")) {
                                questionHistoryModel.setEndTime(jsonObject1.getLong("endTime"));
                            } else {
                                questionHistoryModel.setEndTime(System.currentTimeMillis());
                            }

                            if (jsonObject1.has("orderId") && !jsonObject1.isNull("orderId")) {
                                questionHistoryModel.setOrderId(jsonObject1.getString("orderId"));

                            } else {
                                questionHistoryModel.setOrderId("");
                            }
                            if (jsonObject1.has("consultantPic") && !jsonObject1.isNull("consultantPic")) {
                                questionHistoryModel.setConsultantPic(jsonObject1.getString("consultantPic"));

                            } else {
                                questionHistoryModel.setConsultantPic("");
                            }

                            if (jsonObject1.has("isPromotional") && !jsonObject1.isNull("isPromotional")) {
                                questionHistoryModel.setPromotional(jsonObject1.getBoolean("isPromotional"));
                            } else {
                                questionHistoryModel.setPromotional(false);
                            }

                            if (jsonObject1.has("offerV3id") && !jsonObject1.isNull("offerV3id")) {
                                questionHistoryModel.setOfferV3id(jsonObject1.getLong("offerV3id"));
                            } else {
                                questionHistoryModel.setOfferV3id(-1);
                            }

                            if (jsonObject1.has("pricePerMinute") && !jsonObject1.isNull("pricePerMinute")) {
                                Log.d("PriceDetails", "onResponse: " + jsonObject1.getLong("pricePerMinute"));
                                questionHistoryModel.setPricePerMinuteNew(jsonObject1.getLong("pricePerMinute"));
                            } else {
                                questionHistoryModel.setPricePerMinuteNew(0);
                            }

                            if (jsonObject1.has("reportOrderId") && !jsonObject1.isNull("reportOrderId")) {
                                questionHistoryModel.setReportOrderId(jsonObject1.getLong("reportOrderId"));
                            } else {
                                questionHistoryModel.setReportOrderId(-1);
                            }

                            questionHistoryModel.setCreationTime(jsonObject1.getLong("creationTime"));

                            questionHistoryModel.setStatus(jsonObject1.getString("status"));

                            if (jsonObject1.has("consultantStatus") && !jsonObject1.isNull("consultantStatus")) {
                                JSONObject consultantObject = new JSONObject(jsonObject1.getString("consultantStatus"));
                                if (consultantObject.has("isActive") && !consultantObject.isNull("isActive")) {
                                    if (consultantObject.getBoolean("isActive")) {
                                        questionHistoryModel.setShowChatbtn(true);
                                    } else {
                                        questionHistoryModel.setShowChatbtn(false);
                                    }


                                } else {
                                    questionHistoryModel.setShowChatbtn(false);
                                }
                                if (consultantObject.has("status") && !consultantObject.isNull("status")) {
                                    status = consultantObject.getString("status");
                                } else {
                                    status = "2";
                                }
                                if (status.equalsIgnoreCase("1")) {
                                    questionHistoryModel.setConsultantChatBtnstatus("BUSY");

                                } else if (status.equalsIgnoreCase("2")) {
                                    questionHistoryModel.setConsultantChatBtnstatus("CHAT");
                                } else if (status.equalsIgnoreCase("3")) {
                                    questionHistoryModel.setConsultantChatBtnstatus("OFFLINE");
                                } else if (status.equalsIgnoreCase("4")) {

                                    questionHistoryModel.setWaitListJoined(true);
                                    questionHistoryModel.setConsultantChatBtnstatus("BUSY");

                                } else if (status.equalsIgnoreCase("5")) {
                                    questionHistoryModel.setConsultantChatBtnstatus("ASK");
                                } else if (status.equalsIgnoreCase("6")) {
                                    questionHistoryModel.setConsultantChatBtnstatus("INPROGRESS");
                                } else {
                                    questionHistoryModel.setConsultantChatBtnstatus("CHAT");
                                }

                                if (consultantObject.has("wt") && !consultantObject.isNull("wt")) {
                                    questionHistoryModel.setWaitListWaitTime(consultantObject.getInt("wt"));
                                } else {
                                    questionHistoryModel.setWaitListWaitTime(0);
                                }

                                if (consultantObject.has("next") && !consultantObject.isNull("next")) {
                                    questionHistoryModel.setNextOnlineTimeChat(consultantObject.getString("next"));
                                } else {
                                    questionHistoryModel.setNextOnlineTimeChat("");
                                }

                                /*STATUS CALL*/
                                String callStatus = "";
                                if (consultantObject.has("statusCall") && !consultantObject.isNull("statusCall")) {
                                    callStatus = consultantObject.getString("statusCall");
                                } else {
                                    callStatus = "2";
                                }
                                questionHistoryModel.setStatusCallNew(callStatus);

                                if (callStatus.equalsIgnoreCase("1")) {
                                    questionHistoryModel.setStatusCall("BUSY");
                                } else if (callStatus.equalsIgnoreCase("2")) {
                                    questionHistoryModel.setStatusCall("CALL");
                                } else if (callStatus.equalsIgnoreCase("3")) {
                                    questionHistoryModel.setStatusCall("OFFLINE");
                                } else if (callStatus.equalsIgnoreCase("4")) {
                                    questionHistoryModel.setWaitListJoinedCall(true);
                                    questionHistoryModel.setStatusCall("BUSY");
                                } else {
                                    questionHistoryModel.setStatusCall("CALL");
                                }

                                if (consultantObject.has("visibleForVideoLocal") && !consultantObject.isNull("visibleForVideoLocal")) {
                                    questionHistoryModel.setVisibleForVideoLocal(consultantObject.getBoolean("visibleForVideoLocal"));
                                } else {
                                    questionHistoryModel.setVisibleForVideoLocal(false);
                                }

                                if (consultantObject.has("nextCall") && !consultantObject.isNull("nextCall")) {
                                    questionHistoryModel.setNextCall(consultantObject.getString("nextCall"));
                                } else {
                                    questionHistoryModel.setNextCall("");
                                }

                                /* STATUS VIDEOCALL */
                                String videoCall = "";
                                if (consultantObject.has("statusVideoCall") && !consultantObject.isNull("statusVideoCall")) {
                                    videoCall = consultantObject.getString("statusVideoCall");
                                } else {
                                    videoCall = "2";
                                }

                                if (videoCall.equalsIgnoreCase("1")) {
                                    questionHistoryModel.setStatusVideoCall("BUSY");
                                } else if (videoCall.equalsIgnoreCase("2")) {
                                    questionHistoryModel.setStatusVideoCall("CALL");
                                } else if (videoCall.equalsIgnoreCase("3")) {
                                    questionHistoryModel.setStatusVideoCall("OFFLINE");
                                } else if (videoCall.equalsIgnoreCase("4")) {
                                    questionHistoryModel.setWaitListJoinedVideoCall(true);
                                    questionHistoryModel.setStatusVideoCall("BUSY");
                                } else {
                                    questionHistoryModel.setStatusVideoCall("CALL");
                                }

                                if (consultantObject.has("visibleForVideoCallLocal") && !consultantObject.isNull("visibleForVideoCallLocal")) {
                                    questionHistoryModel.setVisibleForVideoCallLocal(consultantObject.getBoolean("visibleForVideoCallLocal"));
                                } else {
                                    questionHistoryModel.setVisibleForVideoCallLocal(false);
                                }

                                if (consultantObject.has("nextVideoCall") && !consultantObject.isNull("nextVideoCall")) {
                                    questionHistoryModel.setNextVideoCall(consultantObject.getString("nextVideoCall"));
                                } else {
                                    questionHistoryModel.setNextVideoCall("");
                                }
                                questionHistoryModel.setStatusVideoCallNew(videoCall);


                                /*STATUS CHAT*/
                                String statusChat = "";
                                if (consultantObject.has("statusChat") && !consultantObject.isNull("statusChat")) {
                                    statusChat = consultantObject.getString("statusChat");
                                } else {
                                    statusChat = "2";
                                }
                                questionHistoryModel.setStatusChatNew(statusChat);
                                if (statusChat.equalsIgnoreCase("1")) {
                                    questionHistoryModel.setStatusChat("BUSY");

                                } else if (statusChat.equalsIgnoreCase("2")) {
                                    questionHistoryModel.setStatusChat("CHAT");
                                } else if (statusChat.equalsIgnoreCase("3")) {
                                    questionHistoryModel.setStatusChat("OFFLINE");
                                } else if (statusChat.equalsIgnoreCase("4")) {
                                    questionHistoryModel.setWaitListJoinedChat(true);
                                    questionHistoryModel.setStatusChat("BUSY");

                                } else if (statusChat.equalsIgnoreCase("5")) {
                                    questionHistoryModel.setStatusChat("ASK");
                                } else if (statusChat.equalsIgnoreCase("6")) {
                                    questionHistoryModel.setStatusChat("INPROGRESS");
                                } else if (statusChat.equalsIgnoreCase("7")) {
                                    questionHistoryModel.setStatusChat("NOTAVILABLE");
                                } else {
                                    questionHistoryModel.setStatusChat("CHAT");
                                }


                                if (consultantObject.has("nextChat") && !consultantObject.isNull("nextChat")) {
                                    questionHistoryModel.setNextChat(consultantObject.getString("nextChat"));
                                } else {
                                    questionHistoryModel.setNextChat("");
                                }

                                if (consultantObject.has("visibleForChatLocal") && !consultantObject.isNull("visibleForChatLocal")) {
                                    questionHistoryModel.setVisibleForChatLocal(consultantObject.getBoolean("visibleForChatLocal"));
                                } else {
                                    questionHistoryModel.setVisibleForChatLocal(false);
                                }

                                if (consultantObject.has("wt") && !consultantObject.isNull("wt")) {
                                    questionHistoryModel.setWaitListWaitTime(consultantObject.getInt("wt"));
                                } else {
                                    questionHistoryModel.setWaitListWaitTime(0);
                                }

                                if (consultantObject.has("next") && !consultantObject.isNull("next")) {
                                    questionHistoryModel.setNextOnlineTimeChat(consultantObject.getString("next"));
                                } else {
                                    questionHistoryModel.setNextOnlineTimeChat("");
                                }

                                if (consultantObject.has("price") && !consultantObject.isNull("price")) {
                                    questionHistoryModel.setPrice(consultantObject.getInt("price"));
                                } else {
                                    questionHistoryModel.setPrice(1100);
                                }
                            } else {
                                questionHistoryModel.setShowChatbtn(false);
                            }

                            childOrderArray.add(questionHistoryModel);
                        }
                    }
                    if (swipe_refresh.isRefreshing()) {
                        chatCompleteModelArrayList.clear();
                        swipe_refresh.setRefreshing(false);
                    }
                    chatCompleteModelArrayList.addAll(childOrderArray);
                    adapter.notifyDataSetChanged();
                    if (chatCompleteModelArrayList.size() == 0) {
                        no_data_text.setVisibility(VISIBLE);
                    } else {
                        no_data_text.setVisibility(GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipe_refresh.setRefreshing(false);
                }
            }
        }, error -> {
            AstroUtilities.closeLoader();
            swipe_refresh.setRefreshing(false);
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
}