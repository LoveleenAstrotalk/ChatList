package com.astrotalk.sdk.api.adapter;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.activities.AstroUserAstrologerChatWindowActivity;
import com.astrotalk.sdk.api.model.WaitlistModel;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroUtilities;
import com.astrotalk.sdk.api.utils.AstroWaitlistClick;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

public class AstroWaitlistAdapter extends RecyclerView.Adapter<AstroWaitlistAdapter.ViewHolder> {

    Activity mContext;
    ArrayList<WaitlistModel> waitlistModelArrayList;
    SharedPreferences preferences;
    AstroWaitlistClick waitlistClick;
    boolean isCollpase = false;
    String userTimeZone;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String from = "";
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private long user_id = -1;
    private String jwt_token = "";

    public AstroWaitlistAdapter(Activity mContext, ArrayList<WaitlistModel> waitlistModelArrayList,
                                AstroWaitlistClick waitlistClick, boolean isCollpase, String from) {

        this.mContext = mContext;
        this.waitlistModelArrayList = waitlistModelArrayList;
        preferences = mContext.getSharedPreferences(AstroConstants.USER_DETAIL, 0);
        this.waitlistClick = waitlistClick;
        this.isCollpase = isCollpase;
        this.from = from;
        userTimeZone = AstroConstants.TIME_ZONE;
        requestQueue = Volley.newRequestQueue(mContext);
        sharedPreferences = mContext.getSharedPreferences(AstroConstants.USER_DETAIL, 0);
        user_id = sharedPreferences.getLong(AstroConstants.USER_ID, -1);
        jwt_token = sharedPreferences.getString(AstroConstants.JWT_TOKEN, "");
    }

    @Override
    public AstroWaitlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.at_item_waitlist_adapter, parent, false);
        AstroWaitlistAdapter.ViewHolder myViewHolder = new AstroWaitlistAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AstroWaitlistAdapter.ViewHolder holder, int position) {
        WaitlistModel waitlistModel = waitlistModelArrayList.get(position);
        holder.waitTimeInfo.setVisibility(GONE);

        if (waitlistModel.getTokenType().equalsIgnoreCase("chat")) {
            holder.waitlistTypeTV.setText(mContext.getResources().getString(R.string.at_chat_1));
            holder.waitlistTypeTV.setTextColor(mContext.getResources().getColor(R.color.at_red_dark));
        }

        if (waitlistModel.getFixedSessionId() != -1) {
            // Fixed Session
            if (AstroConstants.LANGUAGE_ID == 1) {
                if (userTimeZone.equalsIgnoreCase("Asia/Calcutta") || userTimeZone.equalsIgnoreCase("Asia/Kolkata")) {
                    double amount = Double.parseDouble(new DecimalFormat("##.##").format(waitlistModel.getAmount()));
                    holder.price.setText(AstroUtilities.getConvertedValueFromINR(amount, preferences) + " for " + waitlistModel.getDuration() + mContext.getResources().getString(R.string.at_mins_postfix));
                } else {
                    holder.price.setText(AstroUtilities.getConvertedValueFromINR(waitlistModel.getAmount(), preferences) + " for " + waitlistModel.getDuration() + mContext.getResources().getString(R.string.at_mins_postfix));
                }
            } else {
                if (userTimeZone.equalsIgnoreCase("Asia/Calcutta") || userTimeZone.equalsIgnoreCase("Asia/Kolkata")) {
                    double amount = Double.parseDouble(new DecimalFormat("##.##").format(waitlistModel.getAmount()));
                    holder.price.setText(waitlistModel.getDuration() + " मिनट के लिए " + AstroUtilities.getConvertedValueFromINR(amount, preferences));
                } else {

                    holder.price.setText(waitlistModel.getDuration() + " मिनट के लिए " + AstroUtilities.getConvertedValueFromINR(waitlistModel.getAmount(), preferences));
                }
            }
        } else {
            holder.price.setText(AstroUtilities.getConvertedValueFromINR(waitlistModel.getSessionPrice(), preferences));
        }

        holder.tokenNoTV.setText(waitlistModel.getWt());
        if (waitlistModel.getTokenStatus().equalsIgnoreCase("IN_PROGRESS")) {
            holder.tokenNoTV.setText(waitlistModel.getTokenType() + mContext.getResources().getString(R.string.at_is_in_progress));

            holder.tokenNoTV.setTextColor(mContext.getResources().getColor(R.color.at_green_dark));
            if (waitlistModel.getTokenType().equalsIgnoreCase("chat")) {
                holder.cancel_tv.setVisibility(View.VISIBLE);
                holder.holdImage.setVisibility(View.GONE);
                holder.recharge_continew.setVisibility(View.GONE);
                holder.remaning_time_call.setVisibility(View.GONE);
                holder.richagehint.setVisibility(View.GONE);
                if (waitlistModel.getChatStatus().equalsIgnoreCase("ask")) {
                    holder.cancel_tv.setText(mContext.getResources().getString(R.string.at_waiting));
                    holder.holdImage.setVisibility(View.GONE);

                } else if (waitlistModel.getChatStatus().equalsIgnoreCase("ACCEPTED_BY_CONSULTANT")) {
                    holder.cancel_tv.setText(mContext.getResources().getString(R.string.at_accept_chat_btn));
                    holder.cancel_tv.setBackgroundResource(R.drawable.at_chat_progress_btn);
                    holder.cancel_tv.setTextColor(mContext.getResources().getColor(R.color.at_link));
                    holder.richagehint.setVisibility(View.GONE);
                    holder.holdImage.setVisibility(View.GONE);
                } else if (waitlistModel.getChatStatus().equalsIgnoreCase("INPROGRESS")) {
                    holder.cancel_tv.setText(mContext.getResources().getString(R.string.at_chat_btn));
                    holder.cancel_tv.setBackgroundResource(R.drawable.at_chat_progress_btn);
                    holder.cancel_tv.setTextColor(mContext.getResources().getColor(R.color.at_link));
                    holder.richagehint.setVisibility(View.GONE);
                    holder.holdImage.setVisibility(View.GONE);
                } else {
                    holder.cancel_tv.setVisibility(View.GONE);
                    holder.richagehint.setVisibility(View.GONE);
                    holder.holdImage.setVisibility(View.GONE);
                }
            }
        }
        else {
            if (waitlistModel.isConsultantOffline())
                holder.tokenNoTV.setTextColor(mContext.getResources().getColor(R.color.at_red_dark));
            if (waitlistModel.getTokenStatus().equalsIgnoreCase("WAITING")) {
                holder.tokenNoTV.setTextColor(mContext.getResources().getColor(R.color.at_black));
            }

            if (waitlistModel.isPo()) {
                if (waitlistModel.isConsultantOffline()) {
                    holder.cancel_tv.setVisibility(View.VISIBLE);
                    holder.cancel_tv.setText(mContext.getResources().getString(R.string.at_cancel));
                } else if (waitlistModel.getWtInSecs() > 300) {
                    holder.cancel_tv.setVisibility(View.VISIBLE);
                    holder.cancel_tv.setText(mContext.getResources().getString(R.string.at_cancel));
                } else {
                    holder.cancel_tv.setVisibility(GONE);
                    holder.cancel_tv.setText(mContext.getResources().getString(R.string.at_cancel));
                }
            } else {
                holder.cancel_tv.setVisibility(View.VISIBLE);
                holder.cancel_tv.setText(mContext.getResources().getString(R.string.at_cancel));
            }

            if (waitlistModel.isIsholdUsed()) {
                holder.holdImage.setVisibility(View.GONE);
            } else {
                holder.holdImage.setVisibility(View.GONE);
            }

            holder.cancel_tv.setBackgroundResource(R.drawable.at_background_call_red);
            holder.cancel_tv.setTextColor(mContext.getResources().getColor(R.color.at_red_dark));
            holder.richagehint.setVisibility(View.GONE);
            holder.remaning_time_call.setVisibility(View.GONE);
            holder.recharge_continew.setVisibility(View.GONE);
        }

        if (!waitlistModel.getConsultantPic().trim().isEmpty()) {
            holder.consultantPic.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(waitlistModel.getConsultantPic().trim())
                    .placeholder(R.drawable.at_ic_user)
                    .error(R.drawable.at_ic_user)
                    .into(holder.consultantPic);
        } else {
            holder.consultantPic.setVisibility(View.GONE);
        }

        holder.waitTimeInfo.setVisibility(GONE);
        if (!waitlistModel.getDisplayStatus().equalsIgnoreCase("")) {
            if (!waitlistModel.getTokenStatus().equalsIgnoreCase("IN_PROGRESS")) {
                holder.tokenNoTV.setTextColor(mContext.getResources().getColor(R.color.at_red_dark));
            }
            holder.tokenNoTV.setText(waitlistModel.getDisplayStatus());
        } else {
            holder.waitTimeInfo.setVisibility(View.VISIBLE);
        }

        if (holder.waitTimeInfo.getVisibility() == View.VISIBLE) {
            holder.tokenRL.setOnClickListener(view -> showWaitListInfoDialog());
        }

        holder.astrologer_name_tv.setText(waitlistModel.getConsultantName());

        holder.cancel_tv.setOnClickListener(view ->
        {
            if (waitlistModel.getTokenStatus().equalsIgnoreCase("IN_PROGRESS")) {
                holder.cancel_tv.setVisibility(View.VISIBLE);

                if (waitlistModel.getChatStatus().equalsIgnoreCase("ACCEPTED_BY_CONSULTANT")) {
                    chatAccept(waitlistModel.getChatOrderId(), waitlistModel.getConsultantId(), waitlistModel.getConsultantName());
                } else if (waitlistModel.getChatStatus().equalsIgnoreCase("INPROGRESS")) {
                    mContext.startActivity(new Intent(mContext, AstroUserAstrologerChatWindowActivity.class)
                            .putExtra("chatorder_id", waitlistModel.getChatOrderId())
                            .putExtra("astrologer_id", waitlistModel.getConsultantId())
                            .putExtra("astrologer_name", waitlistModel.getConsultantName()));
                } else {
                    holder.cancel_tv.setVisibility(View.GONE);
                }
            } else {

                android.app.AlertDialog.Builder Builder = new android.app.AlertDialog.Builder(mContext);
                Builder.setTitle(mContext.getResources().getString(R.string.at_leave_waitlist_heading));
                Builder.setMessage(mContext.getResources().getString(R.string.at_leave_waitlist_question) + " " + waitlistModel.getConsultantName() + "?");
                Builder.setCancelable(false)
                        .setPositiveButton(mContext.getResources().getString(R.string.at_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    dialog.cancel();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                waitlistClick.onClick(waitlistModel, false, false, "");
                            }
                        })
                        .setNegativeButton(mContext.getResources().getString(R.string.at_no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    dialog.cancel();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                android.app.AlertDialog alert = Builder.create();
                alert.show();
                alert.getButton(alert.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(alert.BUTTON_POSITIVE).setTextColor(Color.RED);
            }
        });

        holder.waitTimeInfo.setOnClickListener(view -> showWaitListInfoDialog());

        if (waitlistModel.isHold()) {
            holder.wailtlistHoldRL.setVisibility(View.VISIBLE);
            holder.cancel_tv.setClickable(false);
            holder.cancel_tv.setFocusable(false);
        } else {
            holder.wailtlistHoldRL.setVisibility(View.GONE);
            holder.cancel_tv.setClickable(true);
            holder.cancel_tv.setFocusable(true);
        }

        holder.unholdLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitlistClick.onClick(waitlistModel, true, false, "fromHold");
            }
        });

        holder.removeToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitlistClick.onClick(waitlistModel, true, true, "fromHold");
            }
        });
    }

    private void showWaitListInfoDialog() {

        String message = "\n" +
                "<html>\n" +
                "  <body>\n" +
                "<ul style=\"margin-top:0;margin-bottom:0;padding-inline-start:48px;\">\n" +
                "    <li aria-level=\"1\" dir=\"ltr\" style=\"list-style-type:disc;font-size:11pt;font-family:Arial;color:#666666;background-color:transparent;font-style:normal;font-variant:normal;text-decoration:none;vertical-align:baseline;white-space:pre;\">\n" +
                "        <p dir=\"ltr\" style=\"line-height:1.38;margin-top:0pt;margin-bottom:0pt;\"><span style=\"font-size:11pt;font-family:Arial;color:#666666;background-color:transparent;font-style:normal;font-variant:normal;text-decoration:none;vertical-align:baseline;white-space:pre;white-space:pre-wrap;\">&nbsp;If the user ahead of you in the waitlist does a recharge during his session</span></p>\n" +
                "    </li>\n" +
                "    <li aria-level=\"1\" dir=\"ltr\" style=\"list-style-type:disc;font-size:11pt;font-family:Arial;color:#666666;background-color:transparent;font-style:normal;font-variant:normal;text-decoration:none;vertical-align:baseline;white-space:pre;\">\n" +
                "        <p dir=\"ltr\" style=\"line-height:1.38;margin-top:0pt;margin-bottom:0pt;\"><span style=\"font-size:11pt;font-family:Arial;color:#666666;background-color:transparent;font-style:normal;font-variant:normal;text-decoration:none;vertical-align:baseline;white-space:pre;white-space:pre-wrap;\">&nbsp;If the consultant goes offline</span></p>\n" +
                "    </li>\n" +
                "    <li aria-level=\"1\" dir=\"ltr\" style=\"list-style-type:disc;font-size:11pt;font-family:Arial;color:#666666;background-color:transparent;font-style:normal;font-variant:normal;text-decoration:none;vertical-align:baseline;white-space:pre;\">\n" +
                "        <p dir=\"ltr\" style=\"line-height:1.38;margin-top:0pt;margin-bottom:0pt;\"><span style=\"font-size:11pt;font-family:Arial;color:#666666;background-color:transparent;font-style:normal;font-variant:normal;text-decoration:none;vertical-align:baseline;white-space:pre;white-space:pre-wrap;\">&nbsp;If the user ahead of you, misses his session</span></p>\n" +
                "    </li>\n" +
                "    <li aria-level=\"1\" dir=\"ltr\" style=\"list-style-type:disc;font-size:11pt;font-family:Arial;color:#666666;background-color:transparent;font-style:normal;font-variant:normal;text-decoration:none;vertical-align:baseline;white-space:pre;\">\n" +
                "        <p dir=\"ltr\" style=\"line-height:1.2;margin-top:0pt;margin-bottom:0pt;\"><span style=\"font-size:11pt;font-family:Arial;color:#666666;background-color:transparent;font-style:normal;font-variant:normal;text-decoration:none;vertical-align:baseline;white-space:pre;white-space:pre-wrap;\">&nbsp;If any user who had missed a session earlier, marks himself available. In this case, he gets the priority</span></p>\n" +
                "    </li>\n" +
                "</ul>\n" +
                "  </body>\n" +
                "</html>\n";

        Dialog optionDialog = new Dialog(mContext);
        optionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = optionDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        optionDialog.setContentView(R.layout.at_dialog_wait_time_info_popup);

        TextView confrim_btn = (TextView) optionDialog.findViewById(R.id.confrim_btn);
        TextView text_heading = (TextView) optionDialog.findViewById(R.id.text_heading);

        CharSequence trimmedString = AstroUtilities.trim(Html.fromHtml(message));

        text_heading.setText(trimmedString);

        confrim_btn.setOnClickListener(view -> {
            optionDialog.dismiss();
        });

        optionDialog.show();
    }

    private void chatAccept(long chatOrderId, long consultantId, String consultantName) {
        String url = null;
        try {
            url = AstroConstants.ACCEPT_CHAT +
                    "?chatOrderId=" + URLEncoder.encode(chatOrderId + "", "UTF-8") +
                    "&userId=" + URLEncoder.encode(user_id + "", "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AstroUtilities.showLoader(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                AstroUtilities.closeLoader();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equalsIgnoreCase("success")) {
                        AstroUtilities.showToast(mContext, "Success");
                        mContext.startActivity(new Intent(mContext, AstroUserAstrologerChatWindowActivity.class)
                                .putExtra("chatorder_id", chatOrderId)
                                .putExtra("astrologer_id", consultantId)
                                .putExtra("astrologer_name", consultantName));
                    } else {
                        AstroUtilities.showToast(mContext, object.getString("reason"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    AstroUtilities.closeLoader();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AstroUtilities.closeLoader();
            }
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


    @Override
    public int getItemCount() {
        if (waitlistModelArrayList.size() > 1 && preferences.getBoolean(AstroConstants.WAITLIST_SHOW_HIDE, false)) {
            return 1;
        } else if (waitlistModelArrayList.size() == 0) {
            return 0;
        } else {
            return waitlistModelArrayList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView astrologer_name_tv, price, waitlistTypeTV, tokenNoTV, show_token_number, remaning_time_call, rechargeTV, cancel_tv, holdReason;
        RelativeLayout richagehint;
        private LinearLayout recharge_continew;
        private ImageView consultantPic, holdImage, unholdlImg, removeTokenImg;
        private LinearLayout unholdLL, removeToken, pic_LL;
        private RelativeLayout wailtlistHoldRL, waitListLL, tokenRL;
        private ImageView waitTimeInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            astrologer_name_tv = (TextView) itemView.findViewById(R.id.astrologer_name_tv);
            waitlistTypeTV = (TextView) itemView.findViewById(R.id.waitlistTypeTV);
            tokenNoTV = (TextView) itemView.findViewById(R.id.tokenNoTV);
            show_token_number = (TextView) itemView.findViewById(R.id.show_token_number);
            remaning_time_call = (TextView) itemView.findViewById(R.id.remaning_time_call);
            rechargeTV = (TextView) itemView.findViewById(R.id.rechargeTV);
            cancel_tv = (TextView) itemView.findViewById(R.id.cancel_tv);
            waitTimeInfo = itemView.findViewById(R.id.waitTimeInfo);
            richagehint = (RelativeLayout) itemView.findViewById(R.id.richagehint);
            waitListLL = (RelativeLayout) itemView.findViewById(R.id.waitListLL);
            recharge_continew = (LinearLayout) itemView.findViewById(R.id.recharge_continew);
            consultantPic = (ImageView) itemView.findViewById(R.id.consultantPic);
            unholdLL = (LinearLayout) itemView.findViewById(R.id.unholdLL);
            price = (TextView) itemView.findViewById(R.id.price);
            holdImage = (ImageView) itemView.findViewById(R.id.holdImage);
            wailtlistHoldRL = (RelativeLayout) itemView.findViewById(R.id.wailtlistHoldRL);
            removeToken = (LinearLayout) itemView.findViewById(R.id.removeToken);
            unholdlImg = (ImageView) itemView.findViewById(R.id.unholdlImg);
            removeTokenImg = (ImageView) itemView.findViewById(R.id.removeTokenImg);
            removeTokenImg = (ImageView) itemView.findViewById(R.id.removeTokenImg);
            pic_LL = (LinearLayout) itemView.findViewById(R.id.pic_LL);
            tokenRL = (RelativeLayout) itemView.findViewById(R.id.tokenRL);
        }
    }
}