package com.astrotalk.sdk.api.activities;


import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.adapter.ChatAstrologerListAdapter;
import com.astrotalk.sdk.api.model.UniversalAstrologerListModel;
import com.astrotalk.sdk.api.network.ApiEndPointInterface;
import com.astrotalk.sdk.api.utils.Constants;
import com.astrotalk.sdk.api.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class ChatAstrologerListActivity extends AppCompatActivity {

    private final Context context = this;
    private RecyclerView recyclerView;
    private ApiEndPointInterface apiEndPointInterface;

    private final ArrayList<UniversalAstrologerListModel> astrologerListModelArrayList = new ArrayList<>();
    private Call<ResponseBody> responseBodyCall;
    private int totalPageNumber = 1;
    private int pageNumber = 0;
    private boolean loading = true;
    private final boolean isOffer = false;
    private ChatAstrologerListAdapter chatAstrologerListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_astrologer_list);

        initViews();

        apiEndPointInterface = ApiEndPointInterface.retrofit.create(ApiEndPointInterface.class);

        getAstrologerList();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            pageNumber = 0;
            loading = true;
            totalPageNumber = 1;
            getAstrologerList();
        });

    }

    public void initViews() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        chatAstrologerListAdapter = new ChatAstrologerListAdapter(ChatAstrologerListActivity.this, astrologerListModelArrayList);
        recyclerView.setAdapter(chatAstrologerListAdapter);
    }

    public void getAstrologerList() {

        Utilities.showLoader(context);
        responseBodyCall = apiEndPointInterface.getAstrologerListSorting(
                Constants.APP_ID, Constants.BUSINESS_ID, Constants.CONSULTANT_TYPE_ID, Constants.TIME_ZONE, 476914L,
                0, 1000, Constants.SDK_VERSION, Constants.CHAT_SERVICE_ID, Constants.LANGUAGE_ID,
                false, false, false, false);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                Utilities.closeLoader();
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
                Utilities.closeLoader();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}