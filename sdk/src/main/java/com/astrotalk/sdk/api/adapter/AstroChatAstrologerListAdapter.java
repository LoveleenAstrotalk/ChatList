package com.astrotalk.sdk.api.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.model.UniversalAstrologerListModel;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroUtilities;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AstroChatAstrologerListAdapter extends RecyclerView.Adapter<AstroChatAstrologerListAdapter.ViewHolder> {

    Activity context;
    ArrayList<UniversalAstrologerListModel> astrologerListModelArrayList;
    SharedPreferences sharedPreferences;
    private long offerPrice;
    private int consultantType = 1;
    private boolean isLike = false;
    private Map<Long, ViewHolder> astrologerHolderMap;
    private int serviceId = AstroConstants.CHAT_SERVICE_ID;
    private RequestQueue requestQueue;
    private OnChatButtonListener onChatButtonListener;

    public AstroChatAstrologerListAdapter(Activity context, ArrayList<UniversalAstrologerListModel> astrologerListModelArrayList, OnChatButtonListener onChatButtonListener) {
        this.astrologerListModelArrayList = astrologerListModelArrayList;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(AstroConstants.USER_DETAIL, 0);
        astrologerHolderMap = new HashMap<>();
        requestQueue = Volley.newRequestQueue(context);
        this.onChatButtonListener = onChatButtonListener;
    }


    @Override
    public AstroChatAstrologerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.at_item_chat_astrologer_list, parent, false);
        AstroChatAstrologerListAdapter.ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AstroChatAstrologerListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final UniversalAstrologerListModel userChatListModel = astrologerListModelArrayList.get(position);

        astrologerHolderMap.put(userChatListModel.getId(), holder);
        holder.language.setVisibility(View.VISIBLE);
        holder.nameET.setText(userChatListModel.getFirstname());
        holder.expET.setText(context.getResources().getString(R.string.at_experience_adapter).replaceAll("@EXP", userChatListModel.getExperience() + ""));
        holder.skillET.setText(userChatListModel.getSkill().replace(",", ", "));
        holder.language.setText(userChatListModel.getLanguage());
        holder.average_ratingbar.setRating((float) userChatListModel.getAvgRating());

//        if (userChatListModel.getHasOffer() && userChatListModel.getCashbackOfferValue() != 0) {
//            if (userChatListModel.getOfferDisplayName().equalsIgnoreCase("")) {
//                holder.cashback_tv.setVisibility(View.GONE);
//            } else {
//                holder.cashback_tv.setVisibility(View.VISIBLE);
//                holder.cashback_tv.setText(userChatListModel.getOfferDisplayName());
//            }
//
////            holder.price2.setVisibility(View.VISIBLE);
//            holder.price.setTextColor(context.getResources().getColor(R.color.at_grey));
//            holder.price2.setBackground(context.getResources().getDrawable(R.drawable.at_strike_line));
//            if (AstroConstants.TIME_ZONE.equalsIgnoreCase("Asia/Calcutta") || AstroConstants.TIME_ZONE.equalsIgnoreCase("Asia/Kolkata")) {
//                holder.price2.setText(userChatListModel.getPrice() + context.getResources().getString(R.string.at_per_minute));
//            } else {
//                holder.price2.setText(userChatListModel.getPrice() + context.getResources().getString(R.string.at_per_minute));
//            }
//
////            if (AstroConstants.TIME_ZONE.equalsIgnoreCase("Asia/Calcutta") || AstroConstants.TIME_ZONE.equalsIgnoreCase("Asia/Kolkata")) {
////                if (userChatListModel.isPo()) {
////                    holder.price.setText("FREE");
////                    holder.price2.setVisibility(View.VISIBLE);
////                } else {
////                    holder.price2.setVisibility(View.VISIBLE);
////                    holder.price.setText(AstroUtilities.getConvertedValueFromINR((userChatListModel.getPrice() - userChatListModel.getCashbackOfferValue()), sharedPreferences));
////                }
////            } else {
////                if (userChatListModel.isPo()) {
////                    holder.price.setText("FREE");
////                    holder.price2.setVisibility(View.GONE);
////                } else {
////                    holder.price.setVisibility(View.VISIBLE);
////                    holder.price.setText(AstroUtilities.getConvertedValueFromINR((userChatListModel.getPrice() - userChatListModel.getCashbackOfferValue()), sharedPreferences));
////                }
////            }
//            offerPrice = userChatListModel.getPrice() - userChatListModel.getCashbackOfferValue();
//
//        } else {
//            holder.price.setTextColor(context.getResources().getColor(R.color.at_grey));
//            holder.cashback_tv.setVisibility(View.GONE);
////            holder.price2.setVisibility(View.GONE);
//            holder.price.setText(AstroUtilities.getConvertedValueFromINR(userChatListModel.getSessionPrice(), sharedPreferences) + "");
//        }

        holder.price.setTextColor(context.getResources().getColor(R.color.at_grey));
        holder.cashback_tv.setVisibility(View.GONE);
//            holder.price2.setVisibility(View.GONE);
        String winzoDuration = sharedPreferences.getString(AstroConstants.WINZO_DURATION, "");
        holder.price.setText(AstroUtilities.getConvertedValueFromINR(userChatListModel.getSessionPrice(), sharedPreferences) + " (for " + winzoDuration + " mins)");


        if (userChatListModel.getNoOfRating() == 0) {
            holder.user_rating_icon.setVisibility(View.GONE);
            holder.totalcount.setText(context.getResources().getString(R.string.at_new_));
            holder.totalcount.setTextSize(12);
            holder.average_ratingbar.setVisibility(View.VISIBLE);
            holder.totalcount.setTextColor(context.getResources().getColor(R.color.at_red_dark));
        } else {
            if (userChatListModel.isNew()) {
                holder.user_rating_icon.setVisibility(View.GONE);
                holder.totalcount.setText(context.getResources().getString(R.string.at_new_));
                holder.totalcount.setTextSize(12);
                holder.average_ratingbar.setVisibility(View.VISIBLE);
                holder.totalcount.setTextColor(context.getResources().getColor(R.color.at_red_dark));
            } else {
                holder.user_rating_icon.setVisibility(View.VISIBLE);
                holder.totalcount.setText(userChatListModel.getNoOfRating() + context.getResources().getString(R.string.at_ratings_list_adapter_total));
                holder.totalcount.setTextSize(10);
                holder.average_ratingbar.setVisibility(View.VISIBLE);
                holder.totalcount.setTextColor(context.getResources().getColor(R.color.at_black));
            }
        }

        if (userChatListModel.getLabel().equalsIgnoreCase("")) {
            holder.label.setVisibility(View.GONE);
            holder.label.setText("");
        } else {
            holder.label.setVisibility(View.VISIBLE);
            holder.label.setText(userChatListModel.getLabel());
        }
        if (userChatListModel.isBoostOn()) {
            holder.boost.setVisibility(View.VISIBLE);
        } else {
            holder.boost.setVisibility(View.GONE);
        }

        holder.background_user_pic_circle.setVisibility(View.GONE);

        if (userChatListModel.getTag().equalsIgnoreCase("")) {
            holder.celebrity_astrologer.setVisibility(View.GONE);
        } else {
            holder.celebrity_astrologer.setVisibility(View.VISIBLE);
            holder.celebrity_astrologer.setText(userChatListModel.getTag());
        }

        if (userChatListModel.isVerified()) {
            holder.verified.setVisibility(View.VISIBLE);
        } else {
            holder.verified.setVisibility(View.INVISIBLE);
        }

        holder.verified.setOnClickListener(v -> {
            AstroUtilities.showToast(context, context.getResources().getString(R.string.at_recommended_astrologer));
        });

        if (userChatListModel.getProfilePic().trim().isEmpty()) {
            holder.userPicIV.setImageResource(R.drawable.at_ic_bg_astrologer);
        } else {
            Glide.with(context).load(AstroConstants.IMAGE_DOMAIN + userChatListModel.getProfilePic().trim()).error(R.drawable.at_ic_bg_astrologer).placeholder(R.drawable.at_ic_bg_astrologer).into(holder.userPicIV);
        }

        if (userChatListModel.getStatus().equalsIgnoreCase("BUSY")) {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.at_background_call_red);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_red));
            if (serviceId == AstroConstants.CHAT_SERVICE_ID) {
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));
            } else {
                holder.call_tv.setText(context.getResources().getString(R.string.at_call));

            }
            if (userChatListModel.getWaitListWaitTime() > 0) {
                holder.online_time.setVisibility(View.VISIBLE);
                holder.online_time.setTextColor(context.getResources().getColor(R.color.at_red));
                String waitTimeText = "";
                long timeSec = userChatListModel.getWaitListWaitTime();
                long min = timeSec / 60;
                long hours = 0;
                long days;
                if (min < 1)
                    min = 1;
                if (min > 60) {
                    hours = min / 60;
                    min = min % 60;
                    waitTimeText = hours + "h " + min + "m";
                } else {
                    waitTimeText = min + "m";
                }
                if (hours > 24) {
                    days = hours / 24;
                    hours = hours % 24;
                    waitTimeText = days + "d " + hours + "h ";
                }
                holder.online_time.setText(context.getResources().getString(R.string.at_wait_time_in_txt).replaceAll("/@TIME", waitTimeText));
            } else {
                holder.online_time.setVisibility(View.GONE);
            }

        } else if (userChatListModel.getStatus().equalsIgnoreCase("OFFLINE")) {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.at_bg_call_gray);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_grey3));

            if (serviceId == AstroConstants.CHAT_SERVICE_ID) {
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));
            } else {
                holder.call_tv.setText(context.getResources().getString(R.string.at_call));

            }

            if (userChatListModel.getNextOnlineTimeChat().equalsIgnoreCase("")) {
                holder.online_time.setVisibility(View.VISIBLE);
                holder.online_time.setTextColor(context.getResources().getColor(R.color.at_red));
                holder.online_time.setText(context.getResources().getString(R.string.at_profile_currently_offline));
            } else {
                holder.online_time.setVisibility(View.VISIBLE);
                holder.online_time.setTextColor(context.getResources().getColor(R.color.at_green_dark));
                holder.online_time.setText(context.getResources().getString(R.string.at_online_in).replaceAll("/@TIME", userChatListModel.getNextOnlineTimeChat()));

            }
        } else if (userChatListModel.getStatus().equalsIgnoreCase("INPROGRESS")) {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.at_btn_blue);

            if (serviceId == AstroConstants.CHAT_SERVICE_ID) {
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));
            } else {
                holder.call_tv.setText(context.getResources().getString(R.string.at_call));
            }

            holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_link));
            holder.waitlist_size_tv.setVisibility(View.GONE);
            holder.online_time.setVisibility(View.GONE);
        } else if (userChatListModel.getStatus().equalsIgnoreCase("ASK")) {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.at_btn_blue);
            holder.call_tv.setText(context.getResources().getString(R.string.at_waiting_list));
            holder.online_time.setVisibility(View.GONE);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_link));
            holder.waitlist_size_tv.setVisibility(View.GONE);

        } else if (userChatListModel.getStatus().equalsIgnoreCase("NOTAVILABLE")) {
            holder.call_rl.setClickable(false);
            holder.call_rl.setBackgroundResource(R.drawable.at_bg_call_gray);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_grey3));
            holder.call_tv.setText(context.getResources().getString(R.string.at_offline));
        } else {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.at_bg_call_green);

            if (serviceId == AstroConstants.CHAT_SERVICE_ID) {
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));
            } else {
                holder.call_tv.setText(context.getResources().getString(R.string.at_call));
            }

            holder.online_time.setVisibility(View.GONE);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_green_dark));
            holder.waitlist_size_tv.setVisibility(View.GONE);
        }

        if (userChatListModel.getWaitListJoined()) {
            holder.call_rl.setBackgroundResource(R.drawable.at_btn_blue);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_link));
            holder.call_tv.setText(context.getResources().getString(R.string.at_waiting_list));
            holder.online_time.setVisibility(View.GONE);
            holder.waitlist_size_tv.setVisibility(View.GONE);
            if (userChatListModel.getNextOnlineTimeChat().equalsIgnoreCase("")) {
                holder.online_time.setVisibility(View.GONE);
            } else {
                holder.online_time.setVisibility(View.VISIBLE);
                holder.online_time.setTextColor(context.getResources().getColor(R.color.at_green_dark));
                holder.online_time.setText(context.getResources().getString(R.string.at_online_in).replaceAll("/@TIME", userChatListModel.getNextOnlineTimeChat()));
            }
        }

        if (userChatListModel.getStatus().equalsIgnoreCase("INPROGRESS")) {
            holder.call_rl.setBackgroundResource(R.drawable.at_btn_blue);

            if (serviceId == AstroConstants.CHAT_SERVICE_ID) {
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));
            } else {
                holder.call_tv.setText(context.getResources().getString(R.string.at_call));
            }

            holder.online_time.setVisibility(View.GONE);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_link));
            holder.waitlist_size_tv.setVisibility(View.GONE);
        }

        holder.call_rl.setOnClickListener(v -> {

//            if (userChatListModel.isPo() && serviceId == Constants.CHAT_SERVICE_ID) {
//                if (userChatListModel.getStatus().equalsIgnoreCase("INPROGRESS")) {
//                    getLastChatStatus(astrologerListModelArrayList.get(position));
//                } else {
//                    if (astrologerListModelArrayList.get(position).getStatus().equalsIgnoreCase("chat") && !astrologerListModelArrayList.get(position).getWaitListJoined() ||
//                            astrologerListModelArrayList.get(position).getStatus().equalsIgnoreCase("busy") && !astrologerListModelArrayList.get(position).getWaitListJoined() ||
//                            userChatListModel.getStatus().equalsIgnoreCase("OFFLINE") && !astrologerListModelArrayList.get(position).getWaitListJoined()) {
//
//                        if (userChatListModel.getStatus().equalsIgnoreCase("OFFLINE") && !astrologerListModelArrayList.get(position).getWaitListJoined()) {
//                            showOfflinePopup(userChatListModel);
//                        } else {
//                            userCanChat(astrologerListModelArrayList.get(position));
//                        }
//                    }
//                }
//            }

            if (onChatButtonListener != null) {
                onChatButtonListener.onChatButtonClick(userChatListModel);
            }

        });

        holder.main.setOnClickListener(view -> {
            // todo loveleen (no issues)
//            holder.main.setClickable(false);
//            holder.main.setEnabled(false);

            if (onChatButtonListener != null) {
                onChatButtonListener.onChatButtonClick(userChatListModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return astrologerListModelArrayList.size();
    }

    public void setConsultantType(int consultantType) {
        this.consultantType = consultantType;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameET, expET, poSoText, label, skillET, price, language, totalcount, call_tv, waitlist_size_tv, online_time, cashback_tv, celebrity_astrologer, price2;
        ImageView userPicIV, verified, user_rating_icon, background_user_pic_circle, background_user_pic;
        LinearLayout parentLayout, main;
        RelativeLayout call_rl, boost;
        RatingBar average_ratingbar;

        public ViewHolder(View convertView) {
            super(convertView);
            label = (TextView) convertView.findViewById(R.id.label);
            nameET = (TextView) convertView.findViewById(R.id.nameET);
            poSoText = (TextView) convertView.findViewById(R.id.poSoText);
            online_time = (TextView) convertView.findViewById(R.id.online_time);
            background_user_pic = convertView.findViewById(R.id.background_user_pic);
            expET = (TextView) convertView.findViewById(R.id.expET);
            call_tv = (TextView) convertView.findViewById(R.id.call_tv);
            skillET = (TextView) convertView.findViewById(R.id.skillET);
            cashback_tv = (TextView) convertView.findViewById(R.id.cashback_tv);
            language = (TextView) convertView.findViewById(R.id.language);
            totalcount = (TextView) convertView.findViewById(R.id.totalcount);
            price = (TextView) convertView.findViewById(R.id.price);
            price2 = (TextView) convertView.findViewById(R.id.price2);
            waitlist_size_tv = (TextView) convertView.findViewById(R.id.waitlist_size_tv);
            celebrity_astrologer = (TextView) convertView.findViewById(R.id.celebrity_astrologer);

            verified = (ImageView) convertView.findViewById(R.id.verified);
            userPicIV = (ImageView) convertView.findViewById(R.id.user_pic);
            background_user_pic_circle = (ImageView) convertView.findViewById(R.id.background_user_pic_circle);
            user_rating_icon = (ImageView) convertView.findViewById(R.id.user_rating_icon);

            parentLayout = (LinearLayout) convertView.findViewById(R.id.parent_layout);
            main = (LinearLayout) convertView.findViewById(R.id.main);
            call_rl = (RelativeLayout) convertView.findViewById(R.id.call_rl);
            boost = (RelativeLayout) convertView.findViewById(R.id.boost);
            average_ratingbar = (RatingBar) convertView.findViewById(R.id.average_ratingbar);
        }
    }

    public interface OnChatButtonListener {
        void onChatButtonClick(UniversalAstrologerListModel universalAstrologerListModel);
    }

//    private void getLastChatStatus(final UniversalAstrologerListModel astrologerListModel) {
//        String url = Constants.GET_LAST_CHAT_ID_STATUS +
//                "?userId=" + Constants.ID;
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                response -> {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                            JSONObject lastChatDataObject = new JSONObject(jsonObject.getString("data"));
//
//                            if (lastChatDataObject.has("hasOrder") && !lastChatDataObject.isNull("hasOrder")) {
//                                String currentStatus;
//                                if (lastChatDataObject.getBoolean("hasOrder")) {
//                                    if (lastChatDataObject.has("chatStatus") && !lastChatDataObject.isNull("chatStatus")) {
//                                        currentStatus = lastChatDataObject.getString("chatStatus");
//                                    } else {
//                                        currentStatus = "";
//                                    }
//
//                                    if (currentStatus.equalsIgnoreCase("INPROGRESS") || currentStatus.equalsIgnoreCase("ASK") || currentStatus.equalsIgnoreCase("ACCEPTED_BY_CONSULTANT")) {
//                                        Intent intent = new Intent(context, UserAstrologerChatWindowActivity.class);
//                                        intent.putExtra("chatorder_id", lastChatDataObject.getLong("orderId"));
//                                        intent.putExtra("astrologer_id", astrologerListModel.getId());
//                                        intent.putExtra("astrologer_name", astrologerListModel.getFirstname());
//                                        context.startActivity(intent);
//                                    } else {
//                                        userCanChat(astrologerListModel);
//                                    }
//                                } else {
//                                    userCanChat(astrologerListModel);
//                                }
//                            } else {
//                                userCanChat(astrologerListModel);
//                            }
//                        } else {
//                            userCanChat(astrologerListModel);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//                }, error -> Log.e("Exception", error.getMessage())) {
//            @Override
//            public Map getHeaders() {
//                HashMap headers = new HashMap();
//                headers.put("Authorization", Constants.AUTHORIZATION);
//                headers.put("id", Constants.ID);
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(stringRequest);
//    }
//
//    private void showOfflinePopup(UniversalAstrologerListModel userChatListModel) {
//        if (userChatListModel.getNextOnlineTimeChat() == null || userChatListModel.getNextOnlineTimeChat().trim().isEmpty()) {
//            showOfflineWithDetailsPopup(userChatListModel);
//        } else {
//            Utilities.showLoader(context);
//            String url;
//            url = Constants.GET_NEXT_ONLINE_TIME +
//                    "?userId=" + Constants.ID +
//                    "&consultantId=" + userChatListModel.getId();
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    response -> {
//                        Utilities.closeLoader();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                                JSONObject dataObject = jsonObject.getJSONObject("data");
//                                String nextOnlineTime = "";
//                                int waitTime = 0;
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd | hh:mm aaa");
//                                Calendar c = Calendar.getInstance();
//                                String timeZone = c.getTimeZone().getID();
//                                simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
//                                if (dataObject.has("nextOnlineTimeChat") && !dataObject.isNull("nextOnlineTimeChat")) {
//                                    nextOnlineTime = simpleDateFormat.format(new Date(dataObject.getLong("nextOnlineTimeChat")));
//                                }
//
//                                if (dataObject.has("waitlistWaitTime") && !dataObject.isNull("waitlistWaitTime")) {
//                                    waitTime = dataObject.getInt("waitlistWaitTime");
//                                }
//
//                                if (nextOnlineTime.isEmpty()) {
//                                    userChatListModel.setNextOnlineTimeChat(null);
//                                } else {
//                                    userChatListModel.setOfflineNextOnlineTime(nextOnlineTime);
//                                    userChatListModel.setOfflineNextOnlineTimeLong(dataObject.getLong("nextOnlineTimeChat"));
//                                    userChatListModel.setOfflineWaitListWaitTime(waitTime);
//                                }
//                            } else {
//                                userChatListModel.setNextOnlineTimeChat(null);
//                            }
//                            showOfflineWithDetailsPopup(userChatListModel);
//                        } catch (Exception e) {
//                            Utilities.closeLoader();
//                            e.printStackTrace();
//                            userChatListModel.setNextOnlineTimeChat(null);
//                            showOfflineWithDetailsPopup(userChatListModel);
//                        }
//                    }, error -> {
//                        Utilities.closeLoader();
//                        userChatListModel.setNextOnlineTimeChat(null);
//                        showOfflineWithDetailsPopup(userChatListModel);
//                    }) {
//                @Override
//                public Map getHeaders() {
//                    HashMap headers = new HashMap();
//                    headers.put("Authorization", Constants.AUTHORIZATION);
//                    headers.put("id", Constants.ID);
//                    return headers;
//                }
//            };
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            requestQueue.add(stringRequest);
//        }
//
//    }
//
//    private void userCanChat(final UniversalAstrologerListModel astrologerListModel) {
//
//        String url = Constants.CHECK_CAN_CHAT_CAN_CALL_V2 +
//                "?userId=" + Constants.ID +
//                "&consultantId=" + astrologerListModel.getId() +
//                "&appId=" + Constants.APP_ID +
//                "&businessId=" + Constants.BUSINESS_ID +
//                "&appVersionUser=" + Constants.SDK_VERSION +
//                "&timezone=" + Constants.TIME_ZONE;
//
//        Utilities.showLoader(context);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                response -> {
//                    Utilities.closeLoader();
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                            JSONObject dataObject = jsonObject.getJSONObject("data");
//                            Boolean isToShowPlaces = false;
//                            Boolean atLocationApi = false;
//
//                            if (dataObject.has("googlePlaces") && !dataObject.isNull("googlePlaces")) {
//                                isToShowPlaces = dataObject.getBoolean("googlePlaces");
//                            }
//
//                            if (dataObject.has("atLocationApi") && !dataObject.isNull("atLocationApi")) {
//                                atLocationApi = dataObject.getBoolean("atLocationApi");
//                            }
//
//                            checkUserWaitList(astrologerListModel, isToShowPlaces, atLocationApi);
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }, error -> Utilities.closeLoader()) {
//            @Override
//            public Map getHeaders() {
//                HashMap headers = new HashMap();
//                headers.put("Authorization", Constants.AUTHORIZATION);
//                headers.put("id", Constants.ID);
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(stringRequest);
//    }
//
//    private void checkUserWaitList(UniversalAstrologerListModel astrologerListModel, Boolean isToShowPlaces, Boolean atLocationApi) {
//
//        Utilities.showLoader(context);
//        String url = null;
//        try {
//            url = Constants.WAIT_LIST_CHECK_USER_V2_NEW +
//                    "?userId=" + Constants.ID +
//                    "&appId=" + Constants.APP_ID +
//                    "&appVersion=" + Constants.SDK_VERSION +
//                    "&businessId=" + Constants.BUSINESS_ID;
//        } catch (Exception e) {
//            Log.e("Exception", "checkUserWaitList: " + e.getMessage());
//        }
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(), response -> {
//            Utilities.closeLoader();
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                    boolean isWaitListJoined;
//                    JSONObject dataObject = jsonObject.getJSONObject("data");
//
//                    if (dataObject.getBoolean("allowed")) {
//                        isWaitListJoined = false;
//                    } else {
//                        isWaitListJoined = true;
//                    }
//
//                    if (isWaitListJoined) {
//                        popup2(context.getResources().getString(R.string.at_maxWaitlistSize).replaceAll("@MAXWAITLIST", dataObject.getInt("maxWaitlistSize") + ""));
//                    } else {
//                        navigate(astrologerListModel, isWaitListJoined, isToShowPlaces, atLocationApi);
//                    }
//                } else {
//                    Utilities.showToast(context, jsonObject.getString("reason"));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Utilities.closeLoader();
//            }
//        }, error -> Utilities.closeLoader()) {
//            @Override
//            public Map getHeaders() {
//                HashMap headers = new HashMap();
//                headers.put("Authorization", Constants.AUTHORIZATION);
//                headers.put("id", Constants.ID);
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(stringRequest);
//    }
//
//    private void showOfflineWithDetailsPopup(UniversalAstrologerListModel userChatListModel) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.at_offline_when_online_popup_new);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        TextView submit_btn = (TextView) dialog.findViewById(R.id.submit_btn);
//        TextView cancelBtn = (TextView) dialog.findViewById(R.id.cancel_btn);
//        TextView nextOnlineTime = (TextView) dialog.findViewById(R.id.onlineInTv);
//        TextView consultantNameTV = (TextView) dialog.findViewById(R.id.consultantNameTV);
//        RelativeLayout nextOnlineTimeRL = dialog.findViewById(R.id.nextOnlineTimeRL);
//        RelativeLayout waitTimeRL = dialog.findViewById(R.id.waitTimeRL);
//        TextView waitTimeTv = (TextView) dialog.findViewById(R.id.waitTimeTV);
//        ImageView consultantPic = dialog.findViewById(R.id.consultantPic);
//        TextView message = (TextView) dialog.findViewById(R.id.text);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog.getWindow().getAttributes());
//        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);
//
//        if (userChatListModel.getProfilePic().trim().isEmpty()) {
//            consultantPic.setImageResource(R.drawable.at_ic_bg_astrologer);
//        } else {
//            Glide.with(context).load(Constants.IMAGE_DOMAIN + userChatListModel.getProfilePic().trim())
//                    .placeholder(R.drawable.at_ic_bg_astrologer)
//                    .error(R.drawable.at_ic_bg_astrologer)
//                    .into(consultantPic);
//        }
//
//        consultantNameTV.setText(userChatListModel.getFirstname());
//        waitTimeTv.setTextColor(context.getResources().getColor(R.color.at_black));
//
//        if (userChatListModel.getOfflineNextOnlineTime() == null || userChatListModel.getOfflineNextOnlineTime().trim().isEmpty())
//            nextOnlineTimeRL.setVisibility(View.GONE);
//
//        if (userChatListModel.getOfflineWaitListWaitTime() == 0)
//            waitTimeRL.setVisibility(View.GONE);
//
//        String messageText = "";
//        if (userChatListModel.getOfflineNextOnlineTime() != null && !userChatListModel.getOfflineNextOnlineTime().trim().isEmpty()) {
//            nextOnlineTime.setText(" " + userChatListModel.getOfflineNextOnlineTime());
//            message.setVisibility(View.VISIBLE);
//            messageText = context.getResources().getString(R.string.at_astrologer_is_offline_text_popup_new)
//                    .replaceAll("@ASTROLOGER", userChatListModel.getFirstname());
//            message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            message.setMinLines(3);
//            message.setText(Html.fromHtml(messageText));
//
//        } else
//            message.setVisibility(View.VISIBLE);
//        messageText = context.getResources().getString(R.string.at_astrologer_is_offline_text_popup_new)
//                .replaceAll("@ASTROLOGER", userChatListModel.getFirstname());
//        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        message.setMinLines(3);
//        message.setText(Html.fromHtml(messageText));
//        submit_btn.setOnClickListener(v -> {
//            try {
//                dialog.dismiss();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            userCanChat(userChatListModel);
//        });
//
//        cancelBtn.setOnClickListener(v -> {
//            try {
//                dialog.dismiss();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        if (!context.isFinishing()) {
//            try {
//                dialog.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void popup2(String text) {
//
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.at_general_dialog);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//        window.setAttributes(wlp);
//        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        TextView submit_btn = (TextView) dialog.findViewById(R.id.submit_btn);
//        TextView message = (TextView) dialog.findViewById(R.id.text);
//        TextView heading = (TextView) dialog.findViewById(R.id.heading);
//        heading.setVisibility(View.VISIBLE);
//        heading.setText(context.getResources().getString(R.string.at_profile_waitlist_limit));
//        message.setText(text);
//        submit_btn.setOnClickListener(v -> {
//            try {
//                dialog.dismiss();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        dialog.show();
//    }
//
//    public void navigate(final UniversalAstrologerListModel astrologerListModel, final boolean isWaitListJoined, Boolean isToShowPlaces, Boolean atLocationApi) {
//
//        if (astrologerListModel.getPoSo() && !astrologerListModel.isPo()) {
//            Dialog offer_dialog = new Dialog(context);
//            offer_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            offer_dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, Utilities.getScreenHeight(context));
//            offer_dialog.setContentView(R.layout.at_popup_live_event_offer);
//            offer_dialog.setCanceledOnTouchOutside(true);
//            offer_dialog.setCancelable(true);
//
//
//            Button submit_btn = (Button) offer_dialog.findViewById(R.id.submit_btn);
//            submit_btn.setText("Chat now");
//            ImageView background_user_pic_circle = (ImageView) offer_dialog.findViewById(R.id.background_user_pic);
//            ImageView imv_close = (ImageView) offer_dialog.findViewById(R.id.imv_close);
//            TextView tv_live_event_offer = (TextView) offer_dialog.findViewById(R.id.tv_live_event_offer);
//
//            String minBalance;
//            if (Constants.TIME_ZONE.equalsIgnoreCase("Asia/Calcutta") || Constants.TIME_ZONE.equalsIgnoreCase("Asia/Kolkata")) {
//                minBalance = Utilities.getConvertedValueFromINR(astrologerListModel.getPrice() - astrologerListModel.getCashbackOfferValue(), sharedPreferences) + context.getResources().getString(R.string.at_min);
//            } else {
//                minBalance = Utilities.getConvertedValueFromINR(astrologerListModel.getPrice() - astrologerListModel.getCashbackOfferValue(), sharedPreferences) + context.getResources().getString(R.string.at_min);
//            }
//            tv_live_event_offer.setText(context.getResources().getString(R.string.at_live_event_offer_txt, minBalance));
//
//            imv_close.setOnClickListener(v -> offer_dialog.dismiss());
//
//            try {
//                if (astrologerListModel.getProfilePic().trim().isEmpty()) {
//                    background_user_pic_circle.setImageResource(R.drawable.at_ic_bg_astrologer);
//                } else {
//                    Glide.with(context)
//                            .load(Constants.IMAGE_DOMAIN + astrologerListModel.getProfilePic().trim())
//                            .placeholder(R.drawable.at_ic_bg_astrologer)
//                            .error(R.drawable.at_ic_bg_astrologer)
//                            .into(background_user_pic_circle);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            submit_btn.setOnClickListener(v -> {
//                offer_dialog.dismiss();
//                Intent intent = new Intent(context, ChatIntakeFormActivity.class);
//                intent.putExtra("astrologer_details", astrologerListModel);
//                intent.putExtra("isWaitListJoined", isWaitListJoined);
//                intent.putExtra("isToShowPlaces", isToShowPlaces);
//                intent.putExtra("atLocationApi", atLocationApi);
//                if (astrologerListModel.getPoSo()) {
//                    intent.putExtra("isOfferV3", true);
//                } else {
//                    intent.putExtra("isOfferV3", astrologerListModel.isOffer());
//                }
//                intent.putExtra("status", astrologerListModel.getStatus());
//                if (consultantType == 1) {
//                    intent.putExtra("consultant", "astrologer");
//                } else {
//                    intent.putExtra("consultant", "counsellor");
//                }
//                context.startActivity(intent);
//
//            });
//
//            if (!context.isFinishing()) {
//                try {
//                    offer_dialog.show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            Intent intent = new Intent(context, ChatIntakeFormActivity.class);
//            intent.putExtra("astrologer_details", astrologerListModel);
//            intent.putExtra("isWaitListJoined", isWaitListJoined);
//            intent.putExtra("isToShowPlaces", isToShowPlaces);
//            intent.putExtra("atLocationApi", atLocationApi);
//            if (astrologerListModel.getPoSo()) {
//                intent.putExtra("isOfferV3", true);
//            } else {
//                intent.putExtra("isOfferV3", astrologerListModel.isOffer());
//            }
//            intent.putExtra("status", astrologerListModel.getStatus());
//            if (consultantType == 1) {
//                intent.putExtra("consultant", "astrologer");
//            } else {
//                intent.putExtra("consultant", "counsellor");
//            }
//            context.startActivity(intent);
//        }
//    }
}