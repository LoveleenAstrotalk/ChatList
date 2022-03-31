package com.astrotalk.sdk.api.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.model.UserAstrogerChatWindowModel;
import com.astrotalk.sdk.api.utils.CalenderUtils;
import com.astrotalk.sdk.api.utils.Constants;
import com.astrotalk.sdk.api.utils.Utilities;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by rajeev on 4/8/18.
 */

public class UserAstrologerChatWindowAdapter extends RecyclerView.Adapter<UserAstrologerChatWindowAdapter.ViewHolder> {

    Context context;
    ArrayList<UserAstrogerChatWindowModel> messages = new ArrayList<>();
    SharedPreferences sharedPreferences;
    public boolean isAnyOneSleted = false;
    public long latestOrderId = 0;

    public UserAstrologerChatWindowAdapter(Context context, ArrayList<UserAstrogerChatWindowModel> messages) {
        this.messages = messages;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constants.USER_DETAIL, 0);
    }

    public void changeData(ArrayList<UserAstrogerChatWindowModel> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public UserAstrologerChatWindowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_astrologer_chat_window, parent, false);
        UserAstrologerChatWindowAdapter.ViewHolder myViewHolder = new UserAstrologerChatWindowAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final UserAstrologerChatWindowAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        UserAstrogerChatWindowModel chatModel = messages.get(position);
        boolean isShowDate = chatModel.isShowDate();
        if (latestOrderId==0){
            latestOrderId = chatModel.getChatOrderId();
        }

        String time = Utilities.longToDateWithoutHyphen(chatModel.getCreation_time());
        Log.d("Details", "Message: "+messages.get(position).getMessage()+" time: "+time);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        if (chatModel.isHighlight()) {
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.at_color_yellow_on));
        } else {
            holder.mainLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            holder.chatLayout.setPadding(10,10,10,10);
        }

        if (chatModel.getType().equalsIgnoreCase("TEXT")) {
            holder.chat_image.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.GONE);
            holder.body.setVisibility(View.VISIBLE);
            if (chatModel.isLowBalanceText() == true) {
                holder.body.setTextColor(ContextCompat.getColor(context,R.color.at_dark_red));
            } else {
                holder.body.setTextColor(Color.parseColor("#FFFFFF"));
            }
            if (chatModel.isConsultant()) {
                holder.timeTV.setGravity(Gravity.RIGHT);
                if (DateUtils.isToday(chatModel.getCreation_time())) {
                    holder.timeTV.setText(CalenderUtils.getTimeFormat(chatModel.getCreation_time()));
                } else {
                    holder.timeTV.setText(CalenderUtils.getDateTimeString(chatModel.getCreation_time()));
                }
                holder.body.setText(Html.fromHtml(chatModel.getMessage()));
                holder.body.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                holder.chatLayout.setBackgroundResource(R.drawable.at_chat_bubble_incoming);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                if (isShowDate){
                    try {
                        String date = Utilities.longToDateWithoutHyphen(chatModel.getCreation_time());
                        holder.tvChatTiming.setText(date);
                        holder.tvChatTiming.setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        holder.tvChatTiming.setVisibility(View.GONE);
                    }
                }else{
                    holder.tvChatTiming.setVisibility(View.GONE);
                }

                if (isShowDate){
                    params.setMargins(20, 100, 100, 10);
                }else{
                    params.setMargins(20, 10, 100, 10);
                }

                holder.body.setGravity(Gravity.LEFT);
                holder.chatLayout.setLayoutParams(params);
                holder.tick_icon.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.GONE);
                params1.gravity = Gravity.RIGHT;
                holder.info_layout.setLayoutParams(params1);

            } else {
                params1.gravity = Gravity.RIGHT;
                holder.info_layout.setLayoutParams(params1);
                holder.timeTV.setGravity(Gravity.LEFT);
                if (DateUtils.isToday(chatModel.getCreation_time())) {
                    holder.timeTV.setText(CalenderUtils.getTimeFormat(chatModel.getCreation_time()));
                } else {
                    holder.timeTV.setText(CalenderUtils.getDateTimeString(chatModel.getCreation_time()));
                }
                holder.body.setText(Html.fromHtml(chatModel.getMessage()));
                holder.body.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                holder.chatLayout.setBackgroundResource(R.drawable.at_chat_bubble_outgoing1);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                if (isShowDate){
                    try {
                        String date = Utilities.longToDateWithoutHyphen(chatModel.getCreation_time());
                        holder.tvChatTiming.setText(date);
                        holder.tvChatTiming.setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        holder.tvChatTiming.setVisibility(View.GONE);
                    }
                }else{
                    holder.tvChatTiming.setVisibility(View.GONE);
                }
                if (isShowDate){
                    params.setMargins(100, 100, 20, 10);
                }else{
                    params.setMargins(100, 10, 20, 10);
                }
                holder.body.setGravity(Gravity.LEFT);
                holder.chatLayout.setLayoutParams(params);
                if (chatModel.isSent()) {
                    if (chatModel.isDelivered()) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tick_icon.setVisibility(View.VISIBLE);
                        holder.tick_icon.setImageResource(R.drawable.at_ic_double_tick);
                        holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_double_click), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tick_icon.setVisibility(View.VISIBLE);
                        holder.tick_icon.setImageResource(R.drawable.at_ic_double_tick);
                        holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_C0C0C0_color), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                } else {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.tick_icon.setVisibility(View.VISIBLE);
                    holder.tick_icon.setImageResource(R.drawable.at_ic_action_error);
                    holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_dark_red), android.graphics.PorterDuff.Mode.SRC_IN);


                }
            }


        } else {

            holder.imageprogressBar.setVisibility(View.VISIBLE);


            if (chatModel.isMessageDelete()) {
                holder.body.setVisibility(View.VISIBLE);
                holder.chat_image.setVisibility(View.GONE);
                holder.frameLayout.setVisibility(View.GONE);
                holder.body.setText(Html.fromHtml(chatModel.getMessage()));
            } else {
                holder.frameLayout.setVisibility(View.VISIBLE);
                holder.chat_image.setVisibility(View.VISIBLE);
                holder.body.setVisibility(View.GONE);
                Glide.with(context).load(chatModel.getMessage()).into(holder.chat_image);
                Glide.with(context)
                        .load(chatModel.getMessage())
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.at_ic_gallery))
                        .into(holder.chat_image);
            }

            holder.chatLayout.setVisibility(View.VISIBLE);
            if (chatModel.isConsultant()) {
                params1.gravity = Gravity.RIGHT;
                holder.info_layout.setLayoutParams(params1);
                if (DateUtils.isToday(chatModel.getCreation_time())) {
                    holder.timeTV.setText(CalenderUtils.getTimeFormat(chatModel.getCreation_time()));
                } else {
                    holder.timeTV.setText(CalenderUtils.getDateTimeString(chatModel.getCreation_time()));
                }

                holder.chatLayout.setBackgroundResource(R.drawable.at_chat_bubble_incoming);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                if (isShowDate){
                    try {
                        String date = Utilities.longToDateWithoutHyphen(chatModel.getCreation_time());
                        holder.tvChatTiming.setText(date);
                        holder.tvChatTiming.setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        holder.tvChatTiming.setVisibility(View.GONE);
                    }
                }else{
                    holder.tvChatTiming.setVisibility(View.GONE);
                }
                if (isShowDate){
                    params.setMargins(20, 100, 150, 10);
                }else{
                    params.setMargins(20, 10, 150, 10);
                }
                holder.body.setGravity(Gravity.LEFT);
                holder.chatLayout.setLayoutParams(params);
                holder.tick_icon.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.GONE);
                holder.timeTV.setGravity(Gravity.RIGHT);
            } else {
                params1.gravity = Gravity.RIGHT;
                holder.info_layout.setLayoutParams(params1);
                holder.timeTV.setGravity(Gravity.LEFT);
                if (DateUtils.isToday(chatModel.getCreation_time())) {
                    holder.timeTV.setText(CalenderUtils.getTimeFormat(chatModel.getCreation_time()));
                } else {
                    holder.timeTV.setText(CalenderUtils.getDateTimeString(chatModel.getCreation_time()));
                }
                holder.chatLayout.setBackgroundResource(R.drawable.at_chat_bubble_outgoing1);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                if (isShowDate){
                    try {
                        String date = Utilities.longToDateWithoutHyphen(chatModel.getCreation_time());
                        holder.tvChatTiming.setText(date);
                        holder.tvChatTiming.setVisibility(View.VISIBLE);
                    }catch (Exception e){
                        holder.tvChatTiming.setVisibility(View.GONE);
                    }
                }else{
                    holder.tvChatTiming.setVisibility(View.GONE);
                }
                if (isShowDate){
                    params.setMargins(150, 100, 20, 10);
                }else{
                    params.setMargins(150, 10, 20, 10);
                }
                holder.body.setGravity(Gravity.LEFT);
                holder.chatLayout.setPadding(10,10,10,0);
                holder.chatLayout.setLayoutParams(params);
                if (chatModel.isSent()) {
                    if (chatModel.isDelivered()) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tick_icon.setVisibility(View.VISIBLE);
                        holder.tick_icon.setImageResource(R.drawable.at_ic_double_tick);
                        holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_double_click), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tick_icon.setVisibility(View.VISIBLE);
                        holder.tick_icon.setImageResource(R.drawable.at_ic_double_tick);
                        holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_C0C0C0_color), android.graphics.PorterDuff.Mode.SRC_IN);
                    }


                } else {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.tick_icon.setVisibility(View.VISIBLE);
                    holder.tick_icon.setImageResource(R.drawable.at_ic_action_error);
                    holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_dark_red), android.graphics.PorterDuff.Mode.SRC_IN);


                }
            }


        }


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messages.get(position).isSelectedForDelete()) {
                    isAnyOneSleted = false;
                    holder.mainLayout.setBackgroundColor(Color.TRANSPARENT);

                    Log.e("status", "unselect");
                } else {
                    if (!chatModel.getType().equalsIgnoreCase("TEXT") && !chatModel.isMessageDelete()) {

//                        Intent intent = new Intent(context, ChatImageViwerActvity.class);
//                        intent.putExtra("url", messages.get(position).getMessage());
//                        context.startActivity(intent);
                    }
                }
            }
        });

        if (chatModel.getParentMessageType() != null) {

            if (chatModel.getParentMessageType().equalsIgnoreCase("IMAGE")) {

                if (chatModel.getParentMessage() != null) {
                    if (chatModel.getParentMessage().equalsIgnoreCase("") ||
                            chatModel.isMessageDelete()) {

                        holder.rl_show_message.setVisibility(View.GONE);
                        holder.tv_sender_message.setText("");
                        holder.sender_imv.setImageResource(0);
                    } else {
                        Glide.with(context).load(chatModel.getParentMessage()).dontAnimate().into(holder.sender_imv);
                        holder.tv_sender_message.setText(context.getResources().getString(R.string.at_image));
                        holder.rl_show_message.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.rl_show_message.setVisibility(View.GONE);
                    holder.tv_sender_message.setText("");
                    holder.sender_imv.setImageResource(0);
                }


            } else if (chatModel.getParentMessageType().equalsIgnoreCase("TEXT")) {

                if (chatModel.getParentMessage() != null) {
                    if (chatModel.getParentMessage().equalsIgnoreCase("") ||
                            chatModel.isMessageDelete()) {

                        holder.rl_show_message.setVisibility(View.GONE);
                        holder.tv_sender_message.setText("");
                        holder.sender_imv.setImageResource(0);
                    } else {
                        holder.rl_show_message.setVisibility(View.VISIBLE);
                        holder.tv_sender_message.setText(Html.fromHtml(chatModel.getParentMessage()));
                        holder.sender_imv.setImageResource(0);
                    }
                } else {
                    holder.rl_show_message.setVisibility(View.GONE);
                    holder.tv_sender_message.setText("");
                    holder.sender_imv.setImageResource(0);
                }
            } else {
                holder.rl_show_message.setVisibility(View.GONE);
                holder.tv_sender_message.setText("");
                holder.sender_imv.setImageResource(0);
            }
        } else {
            holder.rl_show_message.setVisibility(View.GONE);
            holder.tv_sender_message.setText("");
            holder.sender_imv.setImageResource(0);
        }

        if (chatModel.getParentMessageSentByUser() != null) {

            if (!(chatModel.getParentMessageSentByUser())) {
                // todo loveleen
                if(chatModel.getAstrologerName().equalsIgnoreCase("")) {
                    holder.txt_sender_name.setText(context.getResources().getString(R.string.at_astrologer));
                }
                else {
                    holder.txt_sender_name.setText(chatModel.getAstrologerName());
                }
            } else {
                holder.txt_sender_name.setText(context.getResources().getString(R.string.at_you));
            }
        }


        if (chatModel.isParentReply()) {
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.e("dshd", millisUntilFinished + "");
                    holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.at_colorPrimary));
                }

                public void onFinish() {
                    messages.get(position).setParentReply(false);
                    holder.mainLayout.setBackgroundColor(0);
                }
            }.start();

        } else {
            holder.mainLayout.setBackgroundColor(0);
        }

        if (chatModel.isSelectedForDelete()) {
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.at_colorPrimary));
        } else {
            holder.mainLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView body, txt_sender_name;
        TextView timeTV, tv_sender_message;
        LinearLayout chatLayout;
        RelativeLayout mainLayout;
        ImageView tick_icon, chat_image;
        ProgressBar progressBar, imageprogressBar;
        FrameLayout frameLayout;
        LinearLayout info_layout;
        RelativeLayout rl_show_message;
        ImageView sender_imv;
        TextView tvChatTiming;

        public ViewHolder(View convertView) {
            super(convertView);
            rl_show_message = convertView.findViewById(R.id.rl_show_message);
            body = (TextView) convertView.findViewById(R.id.messageTV);
            txt_sender_name = convertView.findViewById(R.id.txt_sender_name);
            timeTV = (TextView) convertView.findViewById(R.id.timeTV);
            chatLayout = (LinearLayout) convertView.findViewById(R.id.chat_layout);
            tv_sender_message = convertView.findViewById(R.id.tv_sender_message);
            mainLayout = (RelativeLayout) convertView.findViewById(R.id.mainLayout);
            tick_icon = (ImageView) convertView.findViewById(R.id.tick_icon);
            chat_image = (ImageView) convertView.findViewById(R.id.chat_image);
            progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            imageprogressBar = (ProgressBar) convertView.findViewById(R.id.imageProgressBar);
            frameLayout = (FrameLayout) convertView.findViewById(R.id.frameLayout);
            info_layout = (LinearLayout) convertView.findViewById(R.id.info_layout);
            sender_imv = (ImageView) convertView.findViewById(R.id.sender_imv);
            tvChatTiming = (TextView) convertView.findViewById(R.id.tvChatTiming);
        }
    }
}
