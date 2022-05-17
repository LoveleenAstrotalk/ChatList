package com.astrotalk.sdk.api.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
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
import com.astrotalk.sdk.api.activities.AstroChatImageViewerActivity;
import com.astrotalk.sdk.api.model.UserAstrogerChatWindowModel;
import com.astrotalk.sdk.api.utils.AstroCalenderUtils;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroDoubleClickListener;
import com.astrotalk.sdk.api.utils.AstroUtilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class AstroUserAstrologerChatWindowAdapter extends RecyclerView.Adapter<AstroUserAstrologerChatWindowAdapter.ViewHolder> {

    Context context;
    ArrayList<UserAstrogerChatWindowModel> messages = new ArrayList<>();
    SharedPreferences sharedPreferences;
    ItemClickListener itemClickListener;
    public boolean isAnyOneSleted = false;
    private AstroUserAstrologerChatWindowAdapter.ParentReplyClick parentReplyClick;
    public long latestOrderId = 0;

    public AstroUserAstrologerChatWindowAdapter(Context context, ArrayList<UserAstrogerChatWindowModel> messages, ItemClickListener itemClickListener, ParentReplyClick parentReplyClick) {
        this.messages = messages;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(AstroConstants.USER_DETAIL, 0);
        this.itemClickListener = itemClickListener;
        this.parentReplyClick = parentReplyClick;
    }

    public void changeData(ArrayList<UserAstrogerChatWindowModel> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public AstroUserAstrologerChatWindowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.at_item_user_astrologer_chat_window, parent, false);
        AstroUserAstrologerChatWindowAdapter.ViewHolder myViewHolder = new AstroUserAstrologerChatWindowAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final AstroUserAstrologerChatWindowAdapter.ViewHolder holder, final int position) {
        UserAstrogerChatWindowModel chatModel = messages.get(position);
        boolean isShowDate = chatModel.isShowDate();
        if (latestOrderId==0){
            latestOrderId = chatModel.getChatOrderId();
        }

        String time = AstroUtilities.longToDateWithoutHyphen(chatModel.getCreation_time());
        Log.d("Details", "Message: "+messages.get(position).getMessage()+" time: "+time);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        if (chatModel.isHighlight()) {
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.at_yellow_on));
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
                holder.body.setTextColor(ContextCompat.getColor(context,R.color.at_red_dark));
            } else {
                holder.body.setTextColor(ContextCompat.getColor(context,R.color.at_black));
            }
            if (chatModel.isConsultant()) {
                holder.timeTV.setGravity(Gravity.RIGHT);
                if (DateUtils.isToday(chatModel.getCreation_time())) {
                    holder.timeTV.setText(AstroCalenderUtils.getTimeFormat(chatModel.getCreation_time()));
                } else {
                    holder.timeTV.setText(AstroCalenderUtils.getDateTimeString(chatModel.getCreation_time()));
                }
                holder.body.setText(Html.fromHtml(chatModel.getMessage()));
                holder.body.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                holder.chatLayout.setBackgroundResource(R.drawable.at_chat_bubble_incoming1);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                if (isShowDate){
                    try {
                        String date = AstroUtilities.longToDateWithoutHyphen(chatModel.getCreation_time());
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
                    holder.timeTV.setText(AstroCalenderUtils.getTimeFormat(chatModel.getCreation_time()));
                } else {
                    holder.timeTV.setText(AstroCalenderUtils.getDateTimeString(chatModel.getCreation_time()));
                }
                holder.body.setText(Html.fromHtml(chatModel.getMessage()));
                holder.body.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                holder.chatLayout.setBackgroundResource(R.drawable.at_chat_bubble_outgoing1);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                if (isShowDate){
                    try {
                        String date = AstroUtilities.longToDateWithoutHyphen(chatModel.getCreation_time());
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
                        holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_sky_blue), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tick_icon.setVisibility(View.VISIBLE);
                        holder.tick_icon.setImageResource(R.drawable.at_ic_double_tick);
                        holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_grey2), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                } else {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.tick_icon.setVisibility(View.VISIBLE);
                    holder.tick_icon.setImageResource(R.drawable.at_ic_action_error);
                    holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_red_dark), android.graphics.PorterDuff.Mode.SRC_IN);


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
                        .placeholder(ContextCompat.getDrawable(context, R.drawable.at_gallery_gray))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                holder.imageprogressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                holder.imageprogressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.chat_image);
            }

            holder.chatLayout.setVisibility(View.VISIBLE);
            if (chatModel.isConsultant()) {
                params1.gravity = Gravity.RIGHT;
                holder.info_layout.setLayoutParams(params1);
                if (DateUtils.isToday(chatModel.getCreation_time())) {
                    holder.timeTV.setText(AstroCalenderUtils.getTimeFormat(chatModel.getCreation_time()));
                } else {
                    holder.timeTV.setText(AstroCalenderUtils.getDateTimeString(chatModel.getCreation_time()));
                }

                holder.chatLayout.setBackgroundResource(R.drawable.at_chat_bubble_incoming1);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

                if (isShowDate){
                    try {
                        String date = AstroUtilities.longToDateWithoutHyphen(chatModel.getCreation_time());
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
                    holder.timeTV.setText(AstroCalenderUtils.getTimeFormat(chatModel.getCreation_time()));
                } else {
                    holder.timeTV.setText(AstroCalenderUtils.getDateTimeString(chatModel.getCreation_time()));
                }
                holder.chatLayout.setBackgroundResource(R.drawable.at_chat_bubble_outgoing1);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                if (isShowDate){
                    try {
                        String date = AstroUtilities.longToDateWithoutHyphen(chatModel.getCreation_time());
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
                        holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_sky_blue), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.tick_icon.setVisibility(View.VISIBLE);
                        holder.tick_icon.setImageResource(R.drawable.at_ic_double_tick);
                        holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_grey2), android.graphics.PorterDuff.Mode.SRC_IN);
                    }


                } else {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.tick_icon.setVisibility(View.VISIBLE);
                    holder.tick_icon.setImageResource(R.drawable.at_ic_double_tick);
                    holder.tick_icon.setColorFilter(ContextCompat.getColor(context, R.color.at_red_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }
        }
        holder.mainLayout.setOnLongClickListener(v -> {
            Log.e("statussssss", chatModel.isMessageDelete() + "");

            if (isAnyOneSleted || chatModel.isMessageDelete()) {
            } else {
                isAnyOneSleted = true;
                holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.at_color_primary));
                Log.e("status", "select");
                if (messages.get(position).getType().equalsIgnoreCase("TEXT")) {
                    if (messages.get(position).isConsultant()) {
                        itemClickListener.onClick(position, true, 0, messages.get(position).getMessage(), messages.get(position).getId(), false);
                    } else {
                        itemClickListener.onClick(position, true, 0, messages.get(position).getMessage(), messages.get(position).getId(), true);
                    }
                } else {
                    if (messages.get(position).isConsultant()) {
                        itemClickListener.onClick(position, true, 1, messages.get(position).getMessage(), messages.get(position).getId(), false);
                    } else {
                        itemClickListener.onClick(position, true, 1, messages.get(position).getMessage(), messages.get(position).getId(), true);
                    }
                }
            }
            return true;
        });

        holder.rl_show_message.setOnClickListener(new AstroDoubleClickListener() {
            @Override
            public void onSingleClick(View v) {
                parentReplyClick.onParentReplyClick(messages, position);
            }

            @Override
            public void onDoubleClick(View v) {
            }
        });

        holder.mainLayout.setOnClickListener(v -> {
            if (messages.get(position).isSelectedForDelete()) {
                isAnyOneSleted = false;
                holder.mainLayout.setBackgroundColor(Color.TRANSPARENT);
                if (messages.get(position).getType().equalsIgnoreCase("TEXT")) {
                    if (messages.get(position).isConsultant()) {
                        itemClickListener.onClick(position, false, 0, messages.get(position).getMessage(), messages.get(position).getId(), false);
                    } else {
                        itemClickListener.onClick(position, false, 0, messages.get(position).getMessage(), messages.get(position).getId(), true);
                    }
                } else {
                    if (messages.get(position).isConsultant()) {
                        itemClickListener.onClick(position, false, 1, messages.get(position).getMessage(), messages.get(position).getId(), false);
                    } else {
                        itemClickListener.onClick(position, false, 1, messages.get(position).getMessage(), messages.get(position).getId(), true);
                    }
                }
                Log.e("status", "unselect");
            } else {
                if (!chatModel.getType().equalsIgnoreCase("TEXT") && !chatModel.isMessageDelete()) {
                    Intent intent = new Intent(context, AstroChatImageViewerActivity.class);
                    intent.putExtra("url", messages.get(position).getMessage());
                    context.startActivity(intent);
                }
            }
        });

        if (chatModel.getParentMessageType() != null) {
            if (chatModel.getParentMessageType().equalsIgnoreCase("IMAGE")) {
                if (chatModel.getParentMessage() != null) {
                    if (chatModel.getParentMessage().equalsIgnoreCase("") || chatModel.isMessageDelete()) {
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
                    holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.at_color_primary));
                }

                public void onFinish() {
                    messages.get(position).setParentReply(false);
                    holder.mainLayout.setBackgroundColor(0);
                }
            }.start();

        } else {
            holder.mainLayout.setBackgroundColor(0);
        }

        holder.rl_show_message.setOnLongClickListener(v -> {
            Log.e("statussssss", chatModel.isMessageDelete() + "");

            if (isAnyOneSleted || chatModel.isMessageDelete()) {
                Log.e("Response", "isAnyOneSleted");
            } else {
                isAnyOneSleted = true;
                holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.at_color_primary));
                Log.e("status", "select");
                if (messages.get(position).getType().equalsIgnoreCase("TEXT")) {
                    if (messages.get(position).isConsultant()) {
                        itemClickListener.onClick(position, true, 0, messages.get(position).getMessage(), messages.get(position).getId(), false);
                    } else {
                        itemClickListener.onClick(position, true, 0, messages.get(position).getMessage(), messages.get(position).getId(), true);

                    }

                } else {
                    if (messages.get(position).isConsultant()) {
                        itemClickListener.onClick(position, true, 1, messages.get(position).getMessage(), messages.get(position).getId(), false);
                    } else {
                        itemClickListener.onClick(position, true, 1, messages.get(position).getMessage(), messages.get(position).getId(), true);
                    }
                }
            }
            return true;
        });

        if (chatModel.isSelectedForDelete()) {
            holder.mainLayout.setBackgroundColor(context.getResources().getColor(R.color.at_color_primary));
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

    public interface ItemClickListener {
        void onClick(int position, boolean status, int messageType, String message, long id, boolean isUserMessage);
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
            imageprogressBar = (ProgressBar) convertView.findViewById(R.id.imageprogressBar);
            frameLayout = (FrameLayout) convertView.findViewById(R.id.frameLayout);
            info_layout = (LinearLayout) convertView.findViewById(R.id.info_layout);
            sender_imv = (ImageView) convertView.findViewById(R.id.sender_imv);
            tvChatTiming = (TextView) convertView.findViewById(R.id.tvChatTiming);
        }
    }

    public interface ParentReplyClick {
        void onParentReplyClick(ArrayList<UserAstrogerChatWindowModel> messages, int position);
    }
}