package com.astrotalk.sdk.api.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.activities.AstroUserAstrologerChatWindowActivity;
import com.astrotalk.sdk.api.model.ChatCompleteModel;
import com.astrotalk.sdk.api.utils.AstroCalenderUtils;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroUtilities;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ChatCompleteOrderAdapter extends RecyclerView.Adapter<ChatCompleteOrderAdapter.ViewHolder> {

    Context context;
    ArrayList<ChatCompleteModel> questionHistoryModelArrayList;
    String tz;
    SharedPreferences sharedPreferences;
    private long mLastClickTime = 0;
    private String version;
    private ClickListener clickListener = null;

    public interface ClickListener {
        public void onDeleteClicked(int position);
    }

    public ChatCompleteOrderAdapter(Context context, ArrayList<ChatCompleteModel> questionHistoryModelArrayList) {
        this.questionHistoryModelArrayList = questionHistoryModelArrayList;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(AstroConstants.USER_DETAIL, 0);
        tz = AstroConstants.TIME_ZONE;
    }

    @Override
    public ChatCompleteOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.at_item_order_history, parent, false);
        ChatCompleteOrderAdapter.ViewHolder myViewHolder = new ChatCompleteOrderAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatCompleteOrderAdapter.ViewHolder holder, final int position) {

        ChatCompleteModel questionHistoryModel = questionHistoryModelArrayList.get(position);
        holder.tvChatType.setVisibility(View.GONE);
        holder.order_id.setText("#" + questionHistoryModel.getOrderId());
        holder.date.setText(AstroCalenderUtils.getDateTimeString(questionHistoryModel.getCreationTime()));
        holder.astrologer_name.setText(Html.fromHtml(questionHistoryModel.getConsultantName()));


        if (tz.equalsIgnoreCase("Asia/Calcutta") || tz.equalsIgnoreCase("Asia/Kolkata")) {
            if (questionHistoryModel.getFixedId() == -1) {
                holder.price_per_minute.setVisibility(View.GONE);
                holder.price_per_minute.setText(context.getResources().getString(R.string.at_rate_order_history) + AstroUtilities.getConvertedValueFromINR(questionHistoryModel.getPricePerMinute(), sharedPreferences));
            } else {
                holder.price_per_minute.setVisibility(View.GONE);
            }

            if (questionHistoryModel.getFixedId()!=-1){
                holder.total_deduction.setText(Html.fromHtml("Fixed Session of "+questionHistoryModel.getDuration() +" minutes :"
                        + " " + AstroUtilities.getConvertedValueFromINR(questionHistoryModel.getTotalPriceSpent(), sharedPreferences)));
            } else {
                holder.total_deduction.setText(Html.fromHtml(context.getResources().getString(R.string.at_deduction_order_history)
                        + " " + AstroUtilities.getConvertedValueFromINR(questionHistoryModel.getTotalPriceSpent(), sharedPreferences)));
            }
        } else {
            if (questionHistoryModel.getFixedId() == -1) {
                holder.price_per_minute.setVisibility(View.GONE);
                holder.price_per_minute.setText(context.getResources().getString(R.string.at_rate_order_history) + AstroUtilities.getConvertedValueFromINR(questionHistoryModel.getPricePerMinute(), sharedPreferences));
            } else {
                holder.price_per_minute.setVisibility(View.GONE);
            }
            if (questionHistoryModel.getFixedId()!=-1){
                holder.total_deduction.setText(Html.fromHtml("Fixed Session of "+questionHistoryModel.getDuration() +" minutes :"
                        + " " + AstroUtilities.getConvertedValueFromINR(questionHistoryModel.getTotalPriceSpent(), sharedPreferences)));
            } else {
                holder.total_deduction.setText(Html.fromHtml(context.getResources().getString(R.string.at_deduction_order_history)
                        + " " + AstroUtilities.getConvertedValueFromINR(questionHistoryModel.getTotalPriceSpent(), sharedPreferences)));
            }
        }

        if (!questionHistoryModel.getChatTypeToken().equalsIgnoreCase("")){
            holder.tvChatType.setVisibility(View.VISIBLE);
            if (questionHistoryModel.getChatTypeToken().equalsIgnoreCase("normal")){
                holder.tvChatType.setVisibility(View.GONE);
                holder.tvChatType.setText(context.getResources().getString(R.string.at_chat_type).replaceAll("@TYPE","Normal"));
            }else if(questionHistoryModel.getChatTypeToken().equalsIgnoreCase("fixed")) {
                String first = context.getResources().getString(R.string.at_chat_type);
                String next = "<font color='#EE0000'> Fixed Session</font>";
                holder.tvChatType.setText(Html.fromHtml(first + next));
            }else if(questionHistoryModel.getChatTypeToken().equalsIgnoreCase("emergency")) {
                String first = context.getResources().getString(R.string.at_chat_type);
                String next = "<font color='#EE0000'> Emergency</font>";
                holder.tvChatType.setText(Html.fromHtml(first + next));
            }
            else{
                String first = context.getResources().getString(R.string.at_chat_type);
                String next = "<font color='#EE0000'> "+questionHistoryModel.getChatTypeToken()+"</font>";
                holder.tvChatType.setText(Html.fromHtml(first + next));
            }
        }

        if (questionHistoryModel.isPromotional()) {
            holder.tvChatType.setVisibility(View.VISIBLE);
            String first = context.getResources().getString(R.string.at_chat_type);
            String next = "<font color='#EE0000'> Free session</font>";
            holder.tvChatType.setText(Html.fromHtml(first + next));
        } else {
            if ((questionHistoryModel.getOfferV3id() != -1) && (questionHistoryModel.getPricePerMinuteNew() > 0)) {
                holder.tvChatType.setVisibility(View.VISIBLE);
                String first = context.getResources().getString(R.string.at_chat_type);
                String next = "<font color='#EE0000'> "+AstroUtilities.getConvertedValueFromINR(questionHistoryModel.getPricePerMinuteNew(), sharedPreferences)+"/min session"+"</font>";
                holder.tvChatType.setText(Html.fromHtml(first + next));
            } else{
                if (questionHistoryModel.getChatTypeToken().equalsIgnoreCase("")){
                    holder.tvChatType.setVisibility(View.GONE);
                }
            }
        }

        holder.duration.setText(context.getResources().getString(R.string.at_duration_chat_history1).
                replaceAll("@DURATION", questionHistoryModel.getDuration() + ""));

        if (questionHistoryModel.getStatus().equalsIgnoreCase("CANCELLED")) {
            holder.addImage.setVisibility(View.GONE);
            String upperString = questionHistoryModel.getStatus().substring(0, 1).toUpperCase() + questionHistoryModel.getStatus().substring(1).toLowerCase();
            holder.status.setText(Html.fromHtml(context.getResources().getString(R.string.at_status_with_colon) + "<font color='#c10101'>" + upperString + "</font>"));
        } else {
            holder.addImage.setVisibility(View.GONE);
            String upperString = questionHistoryModel.getStatus().substring(0, 1).toUpperCase() + questionHistoryModel.getStatus().substring(1).toLowerCase();
            holder.status.setText(Html.fromHtml(context.getResources().getString(R.string.at_status_with_colon) + "<font color='#008000'>" + upperString + "</font>"));
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderHistory = new Intent(context, AstroUserAstrologerChatWindowActivity.class);
                orderHistory.putExtra("chatorder_id", questionHistoryModelArrayList.get(position).getId());
                orderHistory.putExtra("astrologer_id", questionHistoryModelArrayList.get(position).getConsultantId());
                orderHistory.putExtra("astrologer_name", questionHistoryModelArrayList.get(position).getConsultantName());
                orderHistory.putExtra("iden", "chat_history");
                context.startActivity(orderHistory);

//                Intent orderHistory = new Intent(context, AstroCompleteChatDetails.class);
//                orderHistory.putExtra("chatorder_id", questionHistoryModelArrayList.get(position).getId());
//                orderHistory.putExtra("astrologer_id", questionHistoryModelArrayList.get(position).getConsultantId());
//                orderHistory.putExtra("astrologer_name", questionHistoryModelArrayList.get(position).getConsultantName());
//                orderHistory.putExtra("iden", "chat_history");
//                context.startActivity(orderHistory);

            }
        });

        if (questionHistoryModel.getConsultantPic().trim().isEmpty()) {
            holder.consult_pic.setImageResource(R.drawable.at_ic_bg_astrologer);
        } else {
            holder.consult_pic.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(questionHistoryModel.getConsultantPic().trim())
                    .placeholder(R.drawable.at_ic_bg_astrologer)
                    .error(R.drawable.at_ic_bg_astrologer)
                    .into(holder.consult_pic);
        }

        if (questionHistoryModel.getReview().equalsIgnoreCase("")) {
            holder.review.setVisibility(View.GONE);
        } else {
            holder.review.setVisibility(View.VISIBLE);
            holder.review.setText(context.getResources().getString(R.string.at_review_order_history) + questionHistoryModel.getReview());
        }

        if (questionHistoryModel.getRating() == 0) {
            holder.average_ratingbar.setVisibility(View.GONE);
        } else {
            holder.average_ratingbar.setVisibility(View.VISIBLE);
            holder.average_ratingbar.setRating((float) questionHistoryModel.getRating());
        }

        holder.parent.setOnLongClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AtDialogTheme);
            alertDialogBuilder.setMessage(context.getResources().getString(R.string.at_sure_about_deleting_order));
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton(context.getResources().getString(R.string.at_ok), (dialog, id) -> {
                        dialog.cancel();
                        if (clickListener != null) {
                            clickListener.onDeleteClicked(position);
                        }
                    })
                    .setNegativeButton(context.getResources().getString(R.string.at_cancel), (dialog, id) -> dialog.cancel());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            return false;
        });

        holder.share_rl.setOnClickListener(v -> {
//            AstroUtilities.preventMultiClick(v);
//            shareChat(position);
//            byte[] bytesEncoded = Base64.encodeBase64(Base64.encodeBase64(String.valueOf(questionHistoryModelArrayList.get(position).getOrderId()).getBytes()));
//            String shareBody = "Check my chat with Astrologer on Astrotalk app \uD83D\uDE0A You should also try and see your future \uD83D\uDE2C First chat is FREE! " + " " + Constants.CHAT_SHARE + new String(bytesEncoded);
//            shareLinkByWhatsapp(shareBody);
        });

        if (questionHistoryModel.getReportOrderId() != -1) {
            holder.review_feedback.setVisibility(View.VISIBLE);
        } else {
            holder.review_feedback.setVisibility(View.GONE);
        }

//        holder.addImage.setOnClickListener(v -> {
//            Intent orderHistory = new Intent(context, AddImageActvity.class);
//            orderHistory.putExtra("orderId", questionHistoryModelArrayList.get(position).getId());
//            orderHistory.putExtra("service_id", Constants.CHAT_SERVICEID);
//            context.startActivity(orderHistory);
//        });

        if (questionHistoryModel.isShowChatbtn) {
            holder.ll_holder.setVisibility(View.GONE);
            holder.price.setVisibility(View.GONE);
            if (questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("BUSY")) {
                holder.call_rl.setBackgroundResource(R.drawable.at_background_call_red);
                holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_red));
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));

                if (questionHistoryModel.getWaitListWaitTime() > 0) {
                    holder.online_time.setVisibility(View.VISIBLE);
                    holder.online_time.setTextColor(context.getResources().getColor(R.color.at_red));
                    String waitTimeText = "";
                    long timeSec = questionHistoryModel.getWaitListWaitTime();
                    long min = timeSec / 60;
                    long hours = 0;
                    long days = 0;
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
                    holder.online_time.setText(context.getResources().getString(R.string.at_available_in_txt).replaceAll("/@TIME", waitTimeText));
                } else {
                    holder.online_time.setVisibility(View.GONE);
                }

            } else if (questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("OFFLINE")) {
                holder.call_rl.setBackgroundResource(R.drawable.at_bg_call_gray);
                holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_grey3));
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));

                if (questionHistoryModel.getNextOnlineTimeChat().equalsIgnoreCase("")) {
                    holder.online_time.setVisibility(View.VISIBLE);
                    holder.online_time.setTextColor(context.getResources().getColor(R.color.at_red));
                    holder.online_time.setText(context.getResources().getString(R.string.at_profile_currently_offline));
                } else {
                    holder.online_time.setVisibility(View.VISIBLE);
                    holder.online_time.setTextColor(context.getResources().getColor(R.color.at_green_dark));
                    holder.online_time.setText(context.getResources().getString(R.string.at_online_in).replaceAll("/@TIME", questionHistoryModel.getNextOnlineTimeChat()));
                }
            } else if (questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("INPROGRESS")) {
                holder.call_rl.setBackgroundResource(R.drawable.at_btn_blue);
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));
                holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_link));
                holder.online_time.setVisibility(View.GONE);
            } else if (questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("ASK")) {
                holder.call_rl.setBackgroundResource(R.drawable.at_btn_blue);
                holder.call_tv.setText(context.getResources().getString(R.string.at_waiting_list));
                holder.online_time.setVisibility(View.GONE);
                holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_link));
            } else {
                holder.call_rl.setBackgroundResource(R.drawable.at_bg_call_green);
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));
                holder.online_time.setVisibility(View.GONE);
                holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_green_dark));
            }

            if (questionHistoryModel.getWaitListJoined()) {
                holder.call_rl.setBackgroundResource(R.drawable.at_btn_blue);
                holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_link));
                holder.call_tv.setText(context.getResources().getString(R.string.at_waiting_list));
                holder.online_time.setVisibility(View.GONE);
            }

            if (questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("INPROGRESS")) {
                holder.call_rl.setBackgroundResource(R.drawable.at_btn_blue);
                holder.call_tv.setText(context.getResources().getString(R.string.at_chat));
                holder.online_time.setVisibility(View.GONE);
                holder.call_tv.setTextColor(context.getResources().getColor(R.color.at_link));

            }
        } else {
            holder.ll_holder.setVisibility(View.GONE);
            holder.price.setVisibility(View.GONE);
        }

        holder.price.setText(AstroUtilities.getConvertedValueFromINR(questionHistoryModel.getPrice(), sharedPreferences));

//        if (questionHistoryModel.isPrescribed()) {
//            holder.remedies_rl.setVisibility(View.VISIBLE);
//        } else {
//            holder.remedies_rl.setVisibility(View.GONE);
//        }

//        holder.call_rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (onChatButtonListener != null) {
//                    onChatButtonListener.onChatButtonClick(questionHistoryModel);
//                    return;
//                }
//                if (questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("INPROGRESS")) {
//                    getLastChatStatus(questionHistoryModelArrayList.get(position));
//
//                } else if (questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("ASK")) {
//                } else {
//                    if (questionHistoryModelArrayList.get(position).getConsultantChatBtnstatus().equalsIgnoreCase("chat") && !questionHistoryModelArrayList.get(position).getWaitListJoined() ||
//                            questionHistoryModelArrayList.get(position).getConsultantChatBtnstatus().equalsIgnoreCase("busy") && !questionHistoryModelArrayList.get(position).getWaitListJoined() ||
//                            questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("OFFLINE") && !questionHistoryModelArrayList.get(position).getWaitListJoined()) {
//
//                        if (questionHistoryModel.getConsultantChatBtnstatus().equalsIgnoreCase("OFFLINE") && !questionHistoryModelArrayList.get(position).getWaitListJoined()) {
//
//                            showOfflinePopup(questionHistoryModel);
//
//                        } else {
//                            userCanChat(questionHistoryModelArrayList.get(position));
//                        }
//                    }
//                }
//            }
//        });

//        holder.remedies_rl.setOnClickListener(v -> {
//            Intent intent = new Intent(context, AstrologerPrescriptionActivity.class);
//            intent.putExtra("id", questionHistoryModelArrayList.get(position).getId());
//            intent.putExtra("type", "CHAT");
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return questionHistoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete, consult_pic;
        RelativeLayout share_rl;
        RelativeLayout call_rl, remedies_rl;
        LinearLayout parent, ll_holder;
        RatingBar average_ratingbar;
        CardView card_view;
        TextView addImage;
        private TextView date,tvChatType, price_per_minute, total_deduction, astrologer_name, status, duration, review, order_id, review_feedback, call_tv, online_time, price, help;

        public ViewHolder(View convertView) {
            super(convertView);

            price_per_minute = (TextView) convertView.findViewById(R.id.price_per_minute);
            help = (TextView) convertView.findViewById(R.id.help);
            date = (TextView) convertView.findViewById(R.id.date);
            status = (TextView) convertView.findViewById(R.id.status);
            total_deduction = (TextView) convertView.findViewById(R.id.total_deduction);
            astrologer_name = (TextView) convertView.findViewById(R.id.astrologer_name);
            duration = (TextView) convertView.findViewById(R.id.duration);
            parent = (LinearLayout) convertView.findViewById(R.id.parent);
            review = (TextView) convertView.findViewById(R.id.review);
            order_id = (TextView) convertView.findViewById(R.id.order_id);
            review_feedback = (TextView) convertView.findViewById(R.id.review_feedback);
            consult_pic = (ImageView) convertView.findViewById(R.id.consult_pic);
            share_rl = (RelativeLayout) convertView.findViewById(R.id.share_rl);
            average_ratingbar = (RatingBar) convertView.findViewById(R.id.average_ratingbar);
            card_view = (CardView) convertView.findViewById(R.id.card_view);
            ll_holder = (LinearLayout) convertView.findViewById(R.id.ll_holder);
            call_rl = (RelativeLayout) convertView.findViewById(R.id.call_rl);
            remedies_rl = (RelativeLayout) convertView.findViewById(R.id.remedies_rl);
            call_tv = (TextView) convertView.findViewById(R.id.call_tv);
            online_time = (TextView) convertView.findViewById(R.id.online_time);
            price = (TextView) convertView.findViewById(R.id.price);
            tvChatType = (TextView) convertView.findViewById(R.id.tvChatType);
            addImage = (TextView) convertView.findViewById(R.id.addImage);
        }
    }

//    public void shareChat(int position) {
//        String url = null;
//        url = Constants.SHARE_CHAT_GET_URL +
//                "?userId=" + sharedPreferences.getLong(Constants.USER_ID, -1) +
//                "&appId=" + Constants.APP_ID +
//                "&businessId=" + Constants.BUSINESS_ID +
//                "&serviceId=" + Constants.CHAT_SERVICEID +
//                "&orderId=" + questionHistoryModelArrayList.get(position).getId();
//        AstroUtilities.showErrorLog("assign", url);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject1 = new JSONObject(response);
//                    if (jsonObject1.getString("status").equalsIgnoreCase("success")) {
//
//                        Log.e("shareee", "hahahahahahaha");
//
//                    } else {
//
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Authorization", sharedPreferences.getString(Constants.USER_AUTH_TOKEN, ""));
//                headers.put("id", sharedPreferences.getLong(Constants.USER_ID, -1) + "");
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);
//    }

    //Share link by Whatsapp
//    private void shareLinkByWhatsapp(String shareableLink) {
//        // Creating new intent
//        Intent intent
//                = new Intent(Intent.ACTION_SEND);
//
//        intent.setType("text/plain");
//        intent.setPackage("com.whatsapp");
//
//        // Give your message here
//        intent.putExtra(Intent.EXTRA_TEXT, shareableLink);
//
//        // Checking whether Whatsapp
//        // is installed or not
//        if (intent.resolveActivity(context.getPackageManager()) == null) {
//            AstroUtilities.showToast(
//                    context,
//                    "Please install whatsapp first.");
//            return;
//        }
//
//        // Starting Whatsapp
//        context.startActivity(intent);
//    }

//    public void popup2(String text) {
//
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.general_dialog);
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
//        heading.setText(context.getResources().getString(R.string.profile_waitlist_limit));
//        message.setText(text);
//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        dialog.show();
//
//    }

//    private void showOfflinePopup(ChatCompleteModel userChatListModel) {
//        if (userChatListModel.getNextOnlineTimeChat() == null || userChatListModel.getNextOnlineTimeChat().trim().isEmpty()) {
//            showOfflineWithDetailsPopup(userChatListModel);
//        } else {
//            ShowDialog.showDialog(context, context.getResources().getString(R.string.loading_dialogue));
//            String url;
//            url = Constants.GET_NEXT_ONLINE_TIME +
//                    "?userId=" + sharedPreferences.getLong(Constants.USER_ID, -1) +
//                    "&consultantId=" + userChatListModel.getConsultantId();
//            AstroUtilities.showErrorLog("url", url);
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            ShowDialog.hideDialog();
//                            if (!Constants.LIVE_MODE)
//                                Log.e("has order", response.toString());
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//
//                                    JSONObject dataObject = jsonObject.getJSONObject("data");
//                                    String nextOnlineTime = "";
//                                    int waitTime = 0;
//                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd | hh:mm aaa");
//                                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone(tz));
//                                    if (dataObject.has("nextOnlineTimeChat") && !dataObject.isNull("nextOnlineTimeChat")) {
//                                        nextOnlineTime = simpleDateFormat.format(new Date(dataObject.getLong("nextOnlineTimeChat")));
//                                    }
//
//                                    if (dataObject.has("waitlistWaitTime") && !dataObject.isNull("waitlistWaitTime")) {
//                                        waitTime = dataObject.getInt("waitlistWaitTime");
//                                    }
//
//                                    if (nextOnlineTime.isEmpty()) {
//                                        userChatListModel.setNextOnlineTimeChat(null);
//
//                                    } else {
//                                        userChatListModel.setOfflineNextOnlineTime(nextOnlineTime);
//                                        userChatListModel.setOfflineNextOnlineTimeLong(dataObject.getLong("nextOnlineTimeChat"));
//                                        userChatListModel.setOfflineWaitListWaitTime(waitTime);
//
//                                    }
//                                    showOfflineWithDetailsPopup(userChatListModel);
//
//                                } else {
//                                    userChatListModel.setNextOnlineTimeChat(null);
//                                    showOfflineWithDetailsPopup(userChatListModel);
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                userChatListModel.setNextOnlineTimeChat(null);
//                                showOfflineWithDetailsPopup(userChatListModel);
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    ShowDialog.hideDialog();
//                    userChatListModel.setNextOnlineTimeChat(null);
//                    showOfflineWithDetailsPopup(userChatListModel);
//                }
//            }) {
//                @Override
//                public Map getHeaders() throws AuthFailureError {
//                    HashMap headers = new HashMap();
//                    headers.put("Authorization", sharedPreferences.getString(Constants.USER_AUTH_TOKEN, ""));
//                    headers.put("id", sharedPreferences.getLong(Constants.USER_ID, -1) + "");
//                    return headers;
//                }
//            };
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            AppController.getInstance().addToRequestQueue(stringRequest);
//        }
//
//    }

//    private void showOfflineWithDetailsPopup(ChatCompleteModel userChatListModel) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.offline_when_online_popup_new);
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
//        layoutParams.width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.80);;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);
//        if (userChatListModel.getConsultantPic().trim().isEmpty()) {
//            consultantPic.setImageResource(R.drawable.astrologer_bg_new);
//        } else {
//
//            if (!Constants.LIVE_MODE)
//                Picasso.get()
//                        .load(userChatListModel.getConsultantPic())
//                        .placeholder(R.drawable.astrologer_bg_new)
//                        .error(R.drawable.astrologer_bg_new)
//                        .into(consultantPic);
//
//        }
//
//        consultantNameTV.setText(userChatListModel.getConsultantName());
////        message.setTextColor(context.getResources().getColor(R.color.black));
////        nextOnlineTime.setTextColor(context.getResources().getColor(R.color.black));
//        waitTimeTv.setTextColor(context.getResources().getColor(R.color.black));
//
//
//        if (userChatListModel.getOfflineNextOnlineTime() == null || userChatListModel.getOfflineNextOnlineTime().trim().isEmpty())
//            nextOnlineTimeRL.setVisibility(View.GONE);
//
//        if (userChatListModel.getOfflineWaitListWaitTime() == 0)
//            waitTimeRL.setVisibility(View.GONE);
//
//
//        String messageText = "";
//        if (userChatListModel.getOfflineNextOnlineTime() != null && !userChatListModel.getOfflineNextOnlineTime().trim().isEmpty()) {
//            nextOnlineTime.setText(" " + userChatListModel.getOfflineNextOnlineTime());
//           /* waitTimeTv.setText(" " + (userChatListModel.getOfflineWaitListWaitTime() / 60) + " minutes");
//            Long timeToShow = userChatListModel.getOfflineNextOnlineTimeLong() + (userChatListModel.getOfflineWaitListWaitTime() * 1000);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd hh:mm aaa");
//            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(tz));
//            String timeToShowString = "<font color='#7ea015'>" + simpleDateFormat.format(new Date(timeToShow)) + "</font>";
//            messageText = context.getResources().getString(R.string.your_call_might_start_around_time)
//                    .replaceAll("@TYPE", context.getResources().getString(R.string.at_chat)).replaceAll("@TIME", timeToShowString);*/
//            message.setVisibility(View.VISIBLE);
//            messageText  = context.getResources().getString(R.string.astrologer_is_offline_text_popup_new)
//                    .replaceAll("@ASTROLOGER", userChatListModel.getConsultantName());
//            message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            message.setMinLines(3);
//            message.setText(Html.fromHtml(messageText));
//        } else
//            message.setVisibility(View.VISIBLE);
//        messageText  = context.getResources().getString(R.string.astrologer_is_offline_text_popup_new)
//                .replaceAll("@ASTROLOGER", userChatListModel.getConsultantName());
//        message.setMinLines(3);
//        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//
//          /*  messageText = context.getResources().getString(R.string.astrologer_is_offline_text_popup)
//                    .replaceAll("@TYPE", context.getResources().getString(R.string.at_chat_small)).replaceAll("/@ASTROLOGER", userChatListModel.getConsultantName());*/
//
//        message.setText(Html.fromHtml(messageText));
//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                userCanChat(userChatListModel);
//
//            }
//        });
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        dialog.show();
//    }

//    private void userCanChat(final ChatCompleteModel astrologerListModel) {
//
//        String url = Constants.CHECK_CANCHAT_CANCALL_V2 +
//                "?userId=" + sharedPreferences.getLong(Constants.USER_ID, -1) +
//                "&consultantId=" + astrologerListModel.getConsultantId() +
//                "&isCall=" + false +
//                "&isPo=" + false +
//                "&appId=" + Constants.APP_ID +
//                "&businessId=" + Constants.BUSINESS_ID +
//                "&appVersionUser=" + AstroUtilities.getAppVersion(context) +
//                "&timezone=" + tz +
//                "&apiVersion=" + 2;
//        AstroUtilities.showErrorLog("url", url);
//        ShowDialog.showDialog(context, context.getResources().getString(R.string.loading_dialogue));
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        ShowDialog.hideDialog();
//                        if (!Constants.LIVE_MODE)
//                            Log.e("response can call", response);
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                                JSONObject dataObject = jsonObject.getJSONObject("data");
//                                Boolean isToShowPlaces = false;
//                                Boolean atLocationApi = false;
//                                if (dataObject.has("googlePlaces") && !dataObject.isNull("googlePlaces"))
//                                    isToShowPlaces = dataObject.getBoolean("googlePlaces");
//
//                                if (dataObject.has("atLocationApi") && !dataObject.isNull("atLocationApi"))
//                                    atLocationApi = dataObject.getBoolean("atLocationApi");
//
//
//                                if (dataObject.has("isAutoDebit") && !dataObject.isNull("isAutoDebit")) {
//                                    if (dataObject.getBoolean("isAutoDebit")) {
//                                        autodebitPouup(astrologerListModel, isToShowPlaces, atLocationApi);
//
//                                    } else {
//                                        checkUserWaitList(astrologerListModel, isToShowPlaces, atLocationApi);
//                                    }
//
//                                } else {
//                                    checkUserWaitList(astrologerListModel, isToShowPlaces, atLocationApi);
//                                }
//                            } else {
//
//                                if (jsonObject.has("flag") && !jsonObject.isNull("flag")) {
//                                    if (jsonObject.getString("flag").equalsIgnoreCase("LOW_BALANCE")) {
//
//                                        int requiredBalance = 10;
//                                        requiredBalance = jsonObject.getInt("minimumRechargeBalance");
//                                        showPopupLowBalance(jsonObject.getString("reason"), requiredBalance, astrologerListModel.getConsultantName());
//                                    } else {
//                                        popup(jsonObject.getString("reason"));
//
//                                    }
//                                } else {
//                                    popup(jsonObject.getString("reason"));
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                ShowDialog.hideDialog();
//
//            }
//        }) {
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Authorization", sharedPreferences.getString(Constants.USER_AUTH_TOKEN, ""));
//                headers.put("id", sharedPreferences.getLong(Constants.USER_ID, -1) + "");
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);
//    }

//    private void getLastChatStatus(final ChatCompleteModel astrologerListModel) {
//        String url;
//        url = Constants.GET_LAST_CHAT_ID_STATUS +
//                "?userId=" + sharedPreferences.getLong(Constants.USER_ID, -1);
//        AstroUtilities.showErrorLog("url", url);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (!Constants.LIVE_MODE)
//                            Log.e("has order", response.toString());
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                                JSONObject lastChatDataObject = new JSONObject(jsonObject.getString("data"));
//
//                                if (lastChatDataObject.has("hasOrder") && !lastChatDataObject.isNull("hasOrder")) {
//                                    String currentStatus = "";
//                                    if (lastChatDataObject.getBoolean("hasOrder") == true) {
//                                        if (lastChatDataObject.has("chatStatus") && !lastChatDataObject.isNull("chatStatus")) {
//                                            currentStatus = lastChatDataObject.getString("chatStatus");
//                                        } else {
//                                            currentStatus = "";
//                                        }
//
//                                        if (currentStatus.equalsIgnoreCase("INPROGRESS") || currentStatus.equalsIgnoreCase("ASK") || currentStatus.equalsIgnoreCase("ACCEPTED_BY_CONSULTANT")) {
//                                            Intent intent = new Intent(context, UserAstrologerChatWindowActivity.class);
//                                            intent.putExtra("chatorder_id", lastChatDataObject.getLong("orderId"));
//                                            intent.putExtra("astrologer_id", astrologerListModel.getConsultantId());
//                                            intent.putExtra("astrologer_name", astrologerListModel.getConsultantName());
//                                            intent.putExtra("isWaitList", true);
//                                            context.startActivity(intent);
//                                        } else {
//                                            userCanChat(astrologerListModel);
//                                        }
//
//                                    } else {
//                                        userCanChat(astrologerListModel);
//
//                                    }
//                                } else {
//                                    userCanChat(astrologerListModel);
//
//                                }
//                            } else {
//                                userCanChat(astrologerListModel);
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Authorization", sharedPreferences.getString(Constants.USER_AUTH_TOKEN, ""));
//                headers.put("id", sharedPreferences.getLong(Constants.USER_ID, -1) + "");
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);
//    }

//    private void checkUserWaitList(ChatCompleteModel astrologerListModel, Boolean isToShowPlaces, Boolean atLocationApi) {
//        try {
//            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//            version = pInfo.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String url = null;
//        try {
//            url = Constants.WAITLIST_CHECK_USER_V2_NEW +
//                    "?userId=" + URLEncoder.encode(sharedPreferences.getLong(Constants.USER_ID, -1) + "", "UTF-8") +
//                    "&appId=" + URLEncoder.encode(Constants.APP_ID + "", "UTF-8") +
//                    "&appVersion=" + URLEncoder.encode(version, "UTF-8") +
//                    "&businessId=" + URLEncoder.encode(Constants.BUSINESS_ID + "", "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//
//        }
//        AstroUtilities.showErrorLog("url", url);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                ShowDialog.hideDialog();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {
//                        boolean isWaitListJoined = false;
//                        JSONObject dataObject = jsonObject.getJSONObject("data");
//                        if (dataObject.getBoolean("allowed")) {
//                            isWaitListJoined = false;
//                        } else {
//                            isWaitListJoined = true;
//                        }
//
//                        if (isWaitListJoined) {
//                            popup2("You already have " + dataObject.getInt("maxWaitlistSize") + " tokens in waiting. Kindly cancel one to join another wait-list");
//
//                        } else {
//
//                            Intent intent = new Intent(context, SelectKundliActivity.class);
//                            UniversalAstrologerListModel universalAstrologerListModel = new UniversalAstrologerListModel();
//                            universalAstrologerListModel.setFirstname(astrologerListModel.getConsultantName());
//                            universalAstrologerListModel.setId(astrologerListModel.getConsultantId());
//                            intent.putExtra("isChatIntake", true);
//                            intent.putExtra("astrologer_details", universalAstrologerListModel);
//                            intent.putExtra("isWaitListJoined", isWaitListJoined);
//                            intent.putExtra("isToShowPlaces", isToShowPlaces);
//                            intent.putExtra("atLocationApi", atLocationApi);
//                            intent.putExtra("status", astrologerListModel.getConsultantChatBtnstatus());
//                            intent.putExtra("from", 2);
//                            context.startActivity(intent);
//                        }
//
//                    } else {
//                        AstroUtilities.showToast(context, jsonObject.getString("reason"));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                ShowDialog.hideDialog();
//
//
//            }
//        }) {
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Authorization", sharedPreferences.getString(Constants.USER_AUTH_TOKEN, ""));
//                headers.put("id", sharedPreferences.getLong(Constants.USER_ID, -1) + "");
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        AppController.getInstance().addToRequestQueue(stringRequest);
//    }

//    public void popup(String text) {
//
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.general_dialog);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//        window.setAttributes(wlp);
//        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        TextView submit_btn = (TextView) dialog.findViewById(R.id.submit_btn);
//        TextView message = (TextView) dialog.findViewById(R.id.text);
//        message.setText(text);
//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        dialog.show();
//
//    }

//    public void showPopupLowBalance(String message, int requiredBalance, String consultantName) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_popop);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//        window.setAttributes(wlp);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//        TextView cancel_btn = dialog.findViewById(R.id.cancel_btn);
//        TextView submit_btn = dialog.findViewById(R.id.submit_btn);
//        final TextView text = dialog.findViewById(R.id.text);
//        text.setText(message);
//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (openAddMoneyListener != null){
//                    openAddMoneyListener.openAddMoneyPopup();
//                }
//               /* Intent intent = new Intent(context, AddMoneyActivity.class);
//                intent.putExtra("minimumRechargeBalance", requiredBalance);
//                intent.putExtra("from", "ChatCompleteOrder");
//                intent.putExtra("firstname", consultantName);
//                context.startActivity(intent);*/
//
//            }
//        });
//        cancel_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    dialog.dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        dialog.show();
//
//    }

//    public void autodebitPouup(ChatCompleteModel astrologerListModel, Boolean isToShowPlaces, Boolean atLocationApi) {
//
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.prepaid_popup);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.CENTER;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//        window.setAttributes(wlp);
//        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        TextView submit_btn = (TextView) dialog.findViewById(R.id.submit_btn);
//        TextView message = (TextView) dialog.findViewById(R.id.text);
//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    dialog.dismiss();
//                    checkUserWaitList(astrologerListModel, isToShowPlaces, atLocationApi);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        dialog.show();
//    }

}