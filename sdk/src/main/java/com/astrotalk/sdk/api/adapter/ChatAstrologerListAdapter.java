package com.astrotalk.sdk.api.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.astrotalk.sdk.api.activities.ChatIntakeFormActivity;
import com.astrotalk.sdk.api.model.UniversalAstrologerListModel;
import com.astrotalk.sdk.api.utils.Constants;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

/**
 * Created by rajeev on 4/8/18.
 */

public class ChatAstrologerListAdapter extends RecyclerView.Adapter<ChatAstrologerListAdapter.ViewHolder> {

    private final long userId = -1;
    Activity context;
    ArrayList<UniversalAstrologerListModel> astrologerListModelArrayList;

    public ChatAstrologerListAdapter(Activity context, ArrayList<UniversalAstrologerListModel>
            astrologerListModelArrayList) {
        this.astrologerListModelArrayList = astrologerListModelArrayList;
        this.context = context;
    }


    @Override
    public ChatAstrologerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_chat_astrologer_list, parent, false);
        ChatAstrologerListAdapter.ViewHolder myViewHolder = new ChatAstrologerListAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ChatAstrologerListAdapter.ViewHolder holder, final int position) {
        final UniversalAstrologerListModel userChatListModel = astrologerListModelArrayList.get(position);

        holder.language.setVisibility(View.VISIBLE);
        holder.nameET.setText(userChatListModel.getFirstname());
        holder.expET.setText(context.getResources().getString(R.string.experience_adapter).replaceAll("@EXP", userChatListModel.getExperience() + ""));
        holder.skillET.setText(userChatListModel.getSkill().replace(",", ", "));
        holder.language.setText(userChatListModel.getLanguage());
        holder.average_ratingbar.setRating((float) userChatListModel.getAvgRating());

        if (userChatListModel.getHasOffer() && userChatListModel.getCashbackOfferValue() != 0) {

            if (userChatListModel.getOfferDisplayName().equalsIgnoreCase("")) {
                holder.cashback_tv.setVisibility(View.GONE);
            } else {
                holder.cashback_tv.setVisibility(View.VISIBLE);
                holder.cashback_tv.setText(userChatListModel.getOfferDisplayName());
            }

            holder.price2.setVisibility(View.VISIBLE);
            holder.price.setTextColor(context.getResources().getColor(R.color.white));
            holder.price2.setBackground(context.getResources().getDrawable(R.drawable.strike_line));
            holder.price2.setText(userChatListModel.getPrice() + context.getResources().getString(R.string.per_minute));

            if (userChatListModel.isPo()) {
                holder.price.setText("FREE");
                holder.price2.setVisibility(View.VISIBLE);
            } else {
                holder.price2.setVisibility(View.VISIBLE);
                holder.price.setText((userChatListModel.getPrice() - userChatListModel.getCashbackOfferValue()));

            }

        } else {
            holder.price.setTextColor(context.getResources().getColor(R.color.editTextcolor));
            holder.cashback_tv.setVisibility(View.GONE);
            holder.price2.setVisibility(View.GONE);
            holder.price.setText(userChatListModel.getPrice() + context.getResources().getString(R.string.per_minute));
        }

        if (userChatListModel.getNoOfRating() == 0) {
            holder.user_rating_icon.setVisibility(View.GONE);
            holder.totalcount.setText(context.getResources().getString(R.string.new_));
            holder.totalcount.setTextSize(12);
            holder.average_ratingbar.setVisibility(View.VISIBLE);
            holder.totalcount.setTextColor(context.getResources().getColor(R.color.dark_red));
        } else {
            if (userChatListModel.isNew()) {
                holder.user_rating_icon.setVisibility(View.GONE);
                holder.totalcount.setText(context.getResources().getString(R.string.new_));
                holder.totalcount.setTextSize(12);
                holder.average_ratingbar.setVisibility(View.VISIBLE);
                holder.totalcount.setTextColor(context.getResources().getColor(R.color.dark_red));
            } else {
                holder.user_rating_icon.setVisibility(View.VISIBLE);
                holder.totalcount.setText(userChatListModel.getNoOfRating() + context.getResources().getString(R.string.ratings_list_adapter_total));
                holder.totalcount.setTextSize(10);
                holder.average_ratingbar.setVisibility(View.VISIBLE);
                holder.totalcount.setTextColor(context.getResources().getColor(R.color.color_black_454545));

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

        if (userChatListModel.isIntroVideoActive()) {
            holder.background_user_pic_circle.setVisibility(View.GONE);

        } else {
            holder.background_user_pic_circle.setVisibility(View.GONE);
        }

        if (userChatListModel.getTag().equalsIgnoreCase("")) {
            holder.celebrity_astrologer.setVisibility(View.GONE);
        } else {
            holder.celebrity_astrologer.setVisibility(View.VISIBLE);
            holder.celebrity_astrologer.setText(userChatListModel.getTag());

        }

        if (userChatListModel.isVerified()) {
            holder.verified.setVisibility(View.VISIBLE);
        } else {
            holder.verified.setVisibility(View.GONE);
        }

        if (userChatListModel.getProfilePic().trim().isEmpty()) {
            holder.userPicIV.setImageResource(R.drawable.astrologer_bg_new);
        } else {

//            Glide.with(context).load("https://astrotalk.s3.amazonaws.com/consultant_pic/" + userChatListModel.getProfilePic().trim()).into(holder.userPicIV);
        }

        if (userChatListModel.getStatus().equalsIgnoreCase("BUSY")) {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.backgronud_call_red);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.waitlistcolor));
            holder.call_tv.setText(context.getResources().getString(R.string.chat));
            holder.info.setVisibility(View.VISIBLE);
            if (userChatListModel.getWaitListWaitTime() > 0) {
                holder.online_time.setVisibility(View.VISIBLE);
                holder.online_time.setTextColor(context.getResources().getColor(R.color.waitlistcolor));
                String waitTimeText = "";
                long timeSec = userChatListModel.getWaitListWaitTime();
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
                holder.online_time.setText(context.getResources().getString(R.string.waittime_in_txt).replaceAll("/@TIME", waitTimeText));
            } else {
                holder.online_time.setVisibility(View.GONE);
            }

        } else if (userChatListModel.getStatus().equalsIgnoreCase("OFFLINE")) {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.background_call_gray);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.button_gray));

            holder.call_tv.setText(context.getResources().getString(R.string.chat));

            holder.info.setVisibility(View.VISIBLE);

            if (userChatListModel.getNextOnlineTimeChat().equalsIgnoreCase("")) {
                holder.online_time.setVisibility(View.VISIBLE);
                holder.online_time.setTextColor(context.getResources().getColor(R.color.waitlistcolor));
                holder.online_time.setText(context.getResources().getString(R.string.profile_currently_offline));
            } else {
                holder.online_time.setVisibility(View.VISIBLE);
                holder.online_time.setTextColor(context.getResources().getColor(R.color.green_dark));
                holder.online_time.setText(context.getResources().getString(R.string.online_in).replaceAll("/@TIME", userChatListModel.getNextOnlineTimeChat()));

            }
        } else if (userChatListModel.getStatus().equalsIgnoreCase("INPROGRESS")) {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.blue_btn);

            holder.call_tv.setText(context.getResources().getString(R.string.chat));

            holder.call_tv.setTextColor(context.getResources().getColor(R.color.link));
            holder.info.setVisibility(View.GONE);
            holder.waitlist_size_tv.setVisibility(View.GONE);
            holder.online_time.setVisibility(View.GONE);
        } else if (userChatListModel.getStatus().equalsIgnoreCase("ASK")) {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.blue_btn);
            holder.call_tv.setText(context.getResources().getString(R.string.waiting_list));
            holder.online_time.setVisibility(View.GONE);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.link));
            holder.info.setVisibility(View.GONE);
            holder.waitlist_size_tv.setVisibility(View.GONE);

        } else if (userChatListModel.getStatus().equalsIgnoreCase("NOTAVILABLE")) {
            holder.call_rl.setClickable(false);
            holder.call_rl.setBackgroundResource(R.drawable.background_call_gray);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.button_gray));
            holder.call_tv.setText(context.getResources().getString(R.string.offline));
            holder.info.setVisibility(View.VISIBLE);

        } else {
            holder.call_rl.setClickable(true);
            holder.call_rl.setBackgroundResource(R.drawable.background_call_btn_green);

            holder.call_tv.setText(context.getResources().getString(R.string.chat));

            holder.online_time.setVisibility(View.GONE);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.color_1aa260));
            holder.info.setVisibility(View.GONE);
            holder.waitlist_size_tv.setVisibility(View.GONE);
        }

        if (userChatListModel.getWaitListJoined()) {
            holder.call_rl.setBackgroundResource(R.drawable.blue_btn);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.link));
            holder.call_tv.setText(context.getResources().getString(R.string.waiting_list));
            holder.online_time.setVisibility(View.GONE);
            holder.waitlist_size_tv.setVisibility(View.GONE);
            holder.info.setVisibility(View.GONE);
            if (userChatListModel.getNextOnlineTimeChat().equalsIgnoreCase("")) {
                holder.online_time.setVisibility(View.GONE);
            } else {

                holder.online_time.setVisibility(View.VISIBLE);
                holder.online_time.setTextColor(context.getResources().getColor(R.color.green_dark));
                holder.online_time.setText(context.getResources().getString(R.string.online_in).replaceAll("/@TIME", userChatListModel.getNextOnlineTimeChat()));
            }
        }

        if (userChatListModel.getStatus().equalsIgnoreCase("INPROGRESS")) {
            holder.call_rl.setBackgroundResource(R.drawable.blue_btn);

            holder.call_tv.setText(context.getResources().getString(R.string.chat));

            holder.online_time.setVisibility(View.GONE);
            holder.call_tv.setTextColor(context.getResources().getColor(R.color.link));
            holder.info.setVisibility(View.GONE);
            holder.waitlist_size_tv.setVisibility(View.GONE);
        }

        holder.call_rl.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChatIntakeFormActivity.class);
            intent.putExtra("astrologer_details", astrologerListModelArrayList.get(position));
            intent.putExtra("status", astrologerListModelArrayList.get(position).getStatus());
            intent.putExtra("consultant", "astrologer");

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return astrologerListModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView nameET, expET, poSoText, label, skillET, price, language, totalcount, call_tv, waitlist_size_tv, online_time, cashback_tv, celebrity_astrologer, price2;
        ImageView userPicIV, info, verified, user_rating_icon, background_user_pic_circle, like_unlike;

        ImageView background_user_pic;

        LinearLayout parentLayout;
        LinearLayout main, ll_holder;
        RatingBar average_ratingbar;
        RelativeLayout call_rl, boost;
        CardView card_view;


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
            info = (ImageView) convertView.findViewById(R.id.info);
            language = (TextView) convertView.findViewById(R.id.language);
            verified = (ImageView) convertView.findViewById(R.id.verified);
            totalcount = (TextView) convertView.findViewById(R.id.totalcount);
            price = (TextView) convertView.findViewById(R.id.price);
            price2 = (TextView) convertView.findViewById(R.id.price2);
            call_rl = (RelativeLayout) convertView.findViewById(R.id.call_rl);
            boost = (RelativeLayout) convertView.findViewById(R.id.boost);
            userPicIV = (ImageView) convertView.findViewById(R.id.user_pic);
            background_user_pic_circle = (ImageView) convertView.findViewById(R.id.background_user_pic_circle);
            user_rating_icon = (ImageView) convertView.findViewById(R.id.user_rating_icon);
            parentLayout = (LinearLayout) convertView.findViewById(R.id.parent_layout);
            main = (LinearLayout) convertView.findViewById(R.id.main);
            average_ratingbar = (RatingBar) convertView.findViewById(R.id.average_ratingbar);
            waitlist_size_tv = (TextView) convertView.findViewById(R.id.waitlist_size_tv);
            celebrity_astrologer = (TextView) convertView.findViewById(R.id.celebrity_astrologer);
            like_unlike = (ImageView) convertView.findViewById(R.id.like_unlike);
            ll_holder = convertView.findViewById(R.id.ll_holder);
            card_view = convertView.findViewById(R.id.card_view);

        }
    }
}