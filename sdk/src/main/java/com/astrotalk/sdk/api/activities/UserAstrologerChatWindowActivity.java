package com.astrotalk.sdk.api.activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.adapter.UserAstrologerChatWindowAdapter;
import com.astrotalk.sdk.api.model.ChatFlagModel;
import com.astrotalk.sdk.api.model.NewPaymentGatewayModel;
import com.astrotalk.sdk.api.model.UserAstrogerChatWindowModel;
import com.astrotalk.sdk.api.network.ApiEndPointInterface;
import com.astrotalk.sdk.api.utils.Constants;
import com.astrotalk.sdk.api.utils.Utilities;
import com.astrotalk.sdk.api.utils.WrapContentLinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.disposables.CompositeDisposable;

public class UserAstrologerChatWindowActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private static final int REQUEST_CODE_PAYMENT = 12;
    public static boolean IS_CHAT_WINDOW_OPEN_USER_ASTROLOGER = false;
    private static final int WALLET_RECHARGE_REQUEST_CODE = 24;
    private final String STATUS_PENDING = "Pending";
    private final String STATUS_SUCCESS = "Success";
    private final String STATUS_CREATED = "Created";
    public long previousOrderId = 0;
    ImageView enable_btn;
    long remainingTimeInsec = 0;
    long remainingTimeInsecForOffer = 0;
    int messageTypes = 1;
    int selectedPosition = -1;
    String copymessages = "";
    ItemTouchHelper itemTouchHelper;
    boolean isPo = false;
    boolean isOfferV3 = false;
    boolean isFixSession = false;
    boolean isEmeregencySession = false;
    ImageView astrologer_pic;
    RecyclerView recyclerView_payment;
    String gatewayTpe = "";
    TextView pay_amount, payment_gst, total_amount_pay;
    LinearLayout paymnet_ll;
    long paymentTypeId = 0;
    boolean isAutodebitOn = false;
    ArrayList<ChatFlagModel> chatFlagModelArrayList = new ArrayList<>();
    ArrayList<ChatFlagModel> chatFlagModelArrayList2 = new ArrayList<>();
    CardView po_suggestion;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1 * 1000;
    TextView typing_tv;
    boolean isPersecondApiCall = true;
    String orderStatus = "";
    boolean isChatCompleted = false;
    boolean ishaveastrologerMessage = false;
    long messageId = 0;
    String mobileNUmber = "";
    String countryCode = "+1";
    int currentScrollPosition = 0;
    long smallMessageCount = 0;
    long fromIdCount = 0;
    long fromIdOldCount = 0;
    int z = 0;
    RelativeLayout rl_count_view;
    TextView tv_small_message_count;
    TextView sender_name, message_;
    RecyclerView rv_add_money;
    private ProgressBar progressBar;
    private SharedPreferences preferences;
    private ImageView chatsendIV;
    private EditText sendMesage;
    private final ArrayList<UserAstrogerChatWindowModel> chatArrayList = new ArrayList<>();
    private UserAstrologerChatWindowAdapter chatAdapter;
    private RecyclerView recyclerView;
    private int pageNumber = 0;
    private final Bitmap bitmap = null;
    private ImageView attachment_iv, refresh_iv, delete_iv, copy_iv;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private WrapContentLinearLayoutManager linearLayoutManager;
    private int totalpageNumber = 1;
    private boolean loading = true;
    private final String imageUrl = "";
    private long chatOrderId = -1;
    private long astrologerId = -1;
    private String astologerName = "";
    private boolean isStatusget = false;
    private RelativeLayout chat_disable_view;
    private TextView headingTV, timer_tv;
    private final String currentStatus = "";
    private String imageFilePath = "";
    private long chatStartTime;
    private LinearLayout below_ll;
    private String from = "", tz = "";
    private TextView btn_200, btn_500, btn_1000;
    private double amount;
    private final double discount = 0.0;
    private final double mrp = 0;
    private final double gstAmount = 0;
    private final long razorPayIdCodeyeti = 0;
    private final long paypalPayId = 0;
    private final long paytmPaymentgetwayId = 0;
    private final long payuPaymentGatewayId = 0;
    private final String transactionID = "";
    private final String transactionHash = "";
    private final String payUSuccessUrl = "";
    private final String payUFailUrl = "";
    private final String payURedirectionUrl = "";
    private final long paymentId = 0;
    private final double amountIndiaOne = 200;
    private final double amountIndiaTwo = 500;
    private final double amountIndiaThree = 1000;
    private final double amountForeignOne = 700;
    private final double amountForeignTwo = 1400;
    private final double amountForeignThree = 3500;
    private boolean isQuickRechargeVisible = false;
    private boolean isPaymentTypesShow = false;
    private boolean isDialogShowInCaseOfPO = false;
    private boolean isChatIntiateDialogShow = false;
    private final String CONTINUE_CHAT_STATUS = "";
    private long fisSessionId = -1;
    private final long emergencySessionId = -1;
    private String parentMessageId = "";
    private final boolean isAstrologerMessageType = false;
    private final boolean isUserMessageType = false;
    private CountDownTimer timer;
    private CountDownTimer timer2;
    private final ArrayList<NewPaymentGatewayModel> newPaymentGatewayModelArrayList = new ArrayList<>();
    private final int position = 0;
    private boolean checkforFlag = false;
    private long flagId;
    private boolean isFroud = false;
    private long currnettime = 0;
    private boolean isMessageSent = true;
    private boolean isLatestApiResponsePending = false;
    private final boolean isForeignPaypal = false;
    private RelativeLayout rl_down;
    private ImageView reply_iv;
    private String parentMessgae = "";
    private String parentMessageType = "";
    private int replyPosition;
    private Boolean isConsultant = false;
    private String parentId = "";
    private LinearLayout ll_edt_curve;
    private ImageView imv_reply;
    private LinearLayout ll_show_message;
    private ImageView reply_box_close;
    private String finalPaytmOrderid;
    private final String paypalTransactionId = "";
    private CardView cvContinueChat;
    private TextView tvContinueChat, tvContinueYes, tvContinueNo;
    private LinearLayout ll_yes_button;
    private String continueChatText = "";
    private boolean isChildRequestCreated = false;
    private CardView cvNewChatStatus;
    private TextView tvNewChatStatus;
    private String astrologerOrignalprice = "0";
    private boolean isChatContinueEnable = false;
    private boolean isAstrologerVersionCompatiable = false;
    private final boolean isRechargePopupOpen = false;
    private final int addmoney_amount = 0;
    private final int addmoney_discount = 0;
    private final int addmoney_id = 0;
    private PopupWindow mPopupWindow;
    private LinearLayout ll_bottom_sheet;
    private ApiEndPointInterface apiEndPointInterface;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_astrologer_chat_window);

        requestQueue = Volley.newRequestQueue(this);

        apiEndPointInterface = ApiEndPointInterface.retrofit.create(ApiEndPointInterface.class);
        cvContinueChat = findViewById(R.id.cvContinueChat);
        cvNewChatStatus = findViewById(R.id.cvNewChatStatus);
        tvNewChatStatus = findViewById(R.id.tvNewChatStatus);
        tvContinueChat = findViewById(R.id.tvContinueChat);
        tvContinueYes = findViewById(R.id.tvContinueYes);
        tvContinueNo = findViewById(R.id.tvContinueNo);
        ll_yes_button = findViewById(R.id.ll_yes_button);
        tvContinueYes.setOnClickListener(this);
        tvContinueNo.setOnClickListener(this);
        recyclerView_payment = findViewById(R.id.payment_recycler_view);
        typing_tv = findViewById(R.id.typing_tv);
        pay_amount = findViewById(R.id.pay_amount);
        astrologer_pic = findViewById(R.id.astrologer_pic);
        astrologer_pic.setOnClickListener(this);
        rl_down = findViewById(R.id.rl_down);
        payment_gst = findViewById(R.id.payment_gst);
        paymnet_ll = findViewById(R.id.paymnet_ll);
        total_amount_pay = findViewById(R.id.total_amount_pay);
        btn_200 = findViewById(R.id.btn_200);
        btn_500 = findViewById(R.id.btn_500);
        btn_1000 = findViewById(R.id.btn_1000);
        below_ll = findViewById(R.id.below_ll);
        po_suggestion = findViewById(R.id.po_suggestion);
        rl_count_view = findViewById(R.id.rl_count_view);
        tv_small_message_count = findViewById(R.id.tv_small_message_count);
        chatOrderId = getIntent().getLongExtra("chatorder_id", -1);
        astrologerId = getIntent().getLongExtra("astrologer_id", -1);
        astologerName = getIntent().getStringExtra("astrologer_name");
        if (getIntent().hasExtra("from")) {
            from = getIntent().getStringExtra("from");
        }

        ll_edt_curve = findViewById(R.id.ll_edt_curve);
        sender_name = findViewById(R.id.sender_name);
        message_ = findViewById(R.id.message);
        imv_reply = findViewById(R.id.imv_reply);
        reply_iv = findViewById(R.id.reply_iv);
        ll_show_message = findViewById(R.id.ll_show_message);
        ll_bottom_sheet = findViewById(R.id.ll_bottom_sheet);

        refresh_iv = findViewById(R.id.refresh_iv);
        delete_iv = findViewById(R.id.delete_iv);
        copy_iv = findViewById(R.id.copy_iv);
        attachment_iv = findViewById(R.id.attachment_iv);
        progressBar = findViewById(R.id.progressBar);
        IS_CHAT_WINDOW_OPEN_USER_ASTROLOGER = true;
        preferences = getSharedPreferences(Constants.USER_DETAIL, MODE_PRIVATE);

        tz = preferences.getString(Constants.USER_TIME_ZONE, "Asia/Kolkata");
        isChatContinueEnable = preferences.getBoolean(Constants.IS_CHAT_CONTINUE_ENABLE, false);
        Log.d("isChatContinueEnable", " Value => " + isChatContinueEnable);

        if (isIndian()) {
            btn_200.setText(Utilities.getConvertedValueFromINR(amountIndiaOne, preferences));
            btn_500.setText(Utilities.getConvertedValueFromINR(amountIndiaTwo, preferences));
            // btn_1000.setText(Utilities.getConvertedValueFromINR(amountIndiaThree, preferences));
        } else {
            btn_200.setText(Utilities.getConvertedValueFromINR(amountForeignOne, preferences));
            btn_500.setText(Utilities.getConvertedValueFromINR(amountForeignTwo, preferences));
            // btn_1000.setText(Utilities.getConvertedValueFromINR(amountForeignThree, preferences));
        }

        Toolbar toolbar = findViewById(R.id.chat_button);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbarTV = findViewById(R.id.toolbarTV);
        chatsendIV = findViewById(R.id.chatsendIV);
        sendMesage = findViewById(R.id.send_message);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView = findViewById(R.id.recycler_view);
        chat_disable_view = findViewById(R.id.chat_disable_view);
        enable_btn = findViewById(R.id.enable_btn);
        headingTV = findViewById(R.id.headingTV);
        timer_tv = findViewById(R.id.timer_tv);
        enable_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserAstrologerChatWindowActivity.this, R.style.AtDialogTheme);
                alertDialogBuilder.setTitle(getString(R.string.at_chat_alert_heading));
                alertDialogBuilder
                        .setMessage(getString(R.string.at_chat_end_alert))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.at_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                orderComplete();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.at_no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        refresh_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStatusget = false;
                chatArrayList.clear();
                pageNumber = 0;
                totalpageNumber = 1;
                cvNewChatStatus.setVisibility(View.GONE);
                getHistory();

            }
        });

        reply_box_close = findViewById(R.id.reply_box_close);
        reply_box_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_edt_curve.setBackground(ContextCompat.getDrawable(UserAstrologerChatWindowActivity.this, R.drawable.at_send_message_view_bg));
                ll_show_message.setVisibility(View.GONE);
                refresh_iv.setVisibility(View.VISIBLE);
                enable_btn.setVisibility(View.VISIBLE);
//                chatArrayList.get(position).setSelectedForDelete(false);
//                isAstrologerMessageType = false;
//                isUserMessageType = false;
                parentId = "";
                parentMessgae = "";
                parentMessageId = "";
                parentMessageType = "";

            }
        });

        linearLayoutManager = new WrapContentLinearLayoutManager(UserAstrologerChatWindowActivity.this);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        chatAdapter = new UserAstrologerChatWindowAdapter(UserAstrologerChatWindowActivity.this, chatArrayList);
        recyclerView.setAdapter(chatAdapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {

                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= (totalItemCount)) {
                            loading = false;
                            getHistory();
                        }
                    }
                    if (linearLayoutManager.findFirstVisibleItemPosition() > 0) {
                        rl_down.setVisibility(View.VISIBLE);
                    } else {
                    }
                }
                if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                    rl_down.setVisibility(View.GONE);
                    fromIdOldCount = 0;
                    z = 0;
                    smallMessageCount = 0;
                    fromIdCount = 0;
                    rl_count_view.setVisibility(View.GONE);
                    tv_small_message_count.setVisibility(View.GONE);
                }

            }
        });

        rl_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
        toolbarTV.setText(astologerName);
        toolbarTV.setOnClickListener(this);
        chatsendIV.setClickable(false);
        chatsendIV.setEnabled(false);

        reply_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_edt_curve.setBackground(ContextCompat.getDrawable(UserAstrologerChatWindowActivity.this, R.drawable.at_ll_curve_2));
                showQuotedMessage(chatArrayList.get(replyPosition).getMessage(),
                        chatArrayList.get(replyPosition).isConsultant(),
                        chatArrayList.get(replyPosition).getType(),
                        String.valueOf(chatArrayList.get(replyPosition).getId()), replyPosition);


                parentMessgae = chatArrayList.get(replyPosition).getMessage();

                isConsultant = chatArrayList.get(replyPosition).isConsultant();
                parentMessageType = chatArrayList.get(replyPosition).getType();

                parentId = String.valueOf(chatArrayList.get(replyPosition).getId());

                chatArrayList.get(selectedPosition).setSelectedForDelete(false);
                chatAdapter.notifyDataSetChanged();
                chatAdapter.isAnyOneSleted = false;
                copy_iv.setVisibility(View.GONE);
                delete_iv.setVisibility(View.GONE);
                reply_iv.setVisibility(View.GONE);
                enable_btn.setVisibility(View.VISIBLE);
                refresh_iv.setVisibility(View.VISIBLE);
                selectedPosition = -1;


            }
        });


        sendMesage.addTextChangedListener(this);

        chatsendIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendMesage.getText().toString().trim().length() > 0) {
                    UserAstrogerChatWindowModel userChatWindowModel = new UserAstrogerChatWindowModel();
                    userChatWindowModel.setMessage(sendMesage.getText().toString().replaceAll("\n", "<br>"));
                    userChatWindowModel.setFrom_user_id(preferences.getLong(Constants.USER_ID, -1));
                    userChatWindowModel.setType("TEXT");
                    userChatWindowModel.setTo_user_id(astrologerId);
                    userChatWindowModel.setCreation_time(System.currentTimeMillis());
                    userChatWindowModel.setConsultant(false);
                    userChatWindowModel.setLowBalanceText(false);
                    userChatWindowModel.setDelivered(false);
                    userChatWindowModel.setAstrologerName(astologerName);
                    userChatWindowModel.setShowDate(false);

                    if (!(parentMessgae.equalsIgnoreCase("")) && !(parentId.equalsIgnoreCase(""))) {
                        if (parentMessageType.equalsIgnoreCase("IMAGE")) {
                            userChatWindowModel.setParentMessageType("IMAGE");
                            userChatWindowModel.setParentMessage(parentMessgae);
                            userChatWindowModel.setParentMessageSentByUser(!isConsultant);
                        } else {
                            userChatWindowModel.setParentMessageType("TEXT");
                            userChatWindowModel.setParentMessage(parentMessgae);
                            userChatWindowModel.setParentMessageSentByUser(!isConsultant);


                        }

                    }

                    chatArrayList.add(0, userChatWindowModel);
                    chatAdapter.changeData(chatArrayList);
                    recyclerView.scrollToPosition(0);
                    checkMatchesantiflag(sendMesage.getText().toString().trim());
                    isMessageSent = false;

                    if (parentMessageId.equalsIgnoreCase("")) {
                        sendChatMessage(chatArrayList.get(0), parentMessageId);

                    } else {
                        sendChatMessage(chatArrayList.get(0), parentMessageId);
                        parentMessageId = "";
                    }
                    currnettime = 0;
                    sendMesage.getText().clear();
                    checkforFlag = false;
                    flagId = 0;
                    isFroud = false;

                    ll_show_message.setVisibility(View.GONE);
                    ll_edt_curve.setBackground(ContextCompat.getDrawable(UserAstrologerChatWindowActivity.this, R.drawable.at_send_message_view_bg));
                    parentId = "";
                    parentMessgae = "";
                    parentMessageType = "";

                } else {

                    Utilities.showToast(getApplicationContext(), getResources().getString(R.string.at_please_enter_some_message));
                }

            }
        });
        getAstrologerAppVersion();

        copy_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatArrayList.get(selectedPosition).setSelectedForDelete(false);
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(copymessages);
                chatAdapter.notifyDataSetChanged();
                chatAdapter.isAnyOneSleted = false;
                copy_iv.setVisibility(View.GONE);
                delete_iv.setVisibility(View.GONE);
                reply_iv.setVisibility(View.GONE);

                refresh_iv.setVisibility(View.VISIBLE);
                enable_btn.setVisibility(View.VISIBLE);
                selectedPosition = -1;
            }
        });
    }


    private void showQuotedMessage(String message, Boolean isConsultant,
                                   String type, String pid, int position) {
        sendMesage.requestFocus();
        if (isConsultant) {
            // Astrologer Name
            if (astologerName.equalsIgnoreCase("")) {
                sender_name.setText(getResources().getString(R.string.at_astrologer));
            } else {
                sender_name.setText(astologerName);
            }


        } else {
            sender_name.setText(getResources().getString(R.string.at_you));
        }

        if (type.equalsIgnoreCase("IMAGE")) {
//            Glide.with(this).load(message).dontAnimate().into(imv_reply);
            message_.setText("Image");
        } else {
            message_.setText(message);
            imv_reply.setImageResource(0);
        }
        parentMessageId = pid;

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(sendMesage, InputMethodManager.SHOW_IMPLICIT);
        ll_show_message.setVisibility(View.VISIBLE);


    }


    private boolean isIndian() {
        return tz.equalsIgnoreCase("Asia/Kolkata") || tz.equalsIgnoreCase("Asia/Calcutta");
    }

    @Override
    protected void onResume() {
        super.onResume();

        IS_CHAT_WINDOW_OPEN_USER_ASTROLOGER = true;
        isStatusget = false;
        chatArrayList.clear();
        pageNumber = 0;
        totalpageNumber = 1;
        previousOrderId = 0;
        getHistory();
        getAstrologerActualPrice();

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                if (isPersecondApiCall) {
                    if (!isLatestApiResponsePending) {
                        getLatestMessage();
                    }
                    handler.postDelayed(runnable, delay);
                }
            }
        }, delay);
    }

    protected void onPause() {
        super.onPause();
        IS_CHAT_WINDOW_OPEN_USER_ASTROLOGER = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        chatsendIV.setClickable(false);
        chatsendIV.setEnabled(false);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0) {
            chatsendIV.setClickable(true);
            chatsendIV.setEnabled(true);
            attachment_iv.setVisibility(View.GONE);

            chatsendIV.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_ic_send));
        } else {
            chatsendIV.setEnabled(false);
            chatsendIV.setClickable(false);
            if (isPo) {
                attachment_iv.setVisibility(View.GONE);
            } else {
                attachment_iv.setVisibility(View.VISIBLE);
            }
            chatsendIV.setBackgroundDrawable(getResources().getDrawable(R.drawable.at_ic_send_deselect));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
            currnettime = System.currentTimeMillis();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkAndRequestPermissions()) {
                    openCameraIntent();
                }

            } else {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(UserAstrologerChatWindowActivity.this, R.style.AtDialogTheme);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(getResources().getString(R.string.at_permission_necessary));
                alertBuilder.setMessage(getResources().getString(R.string.at_external_storage_permission));
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:")));
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }

        } else if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Utilities.showToast(this, getResources().getString(R.string.at_permission_granted));
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), 2);
            }
        } else {
            Utilities.showToast(this, getString(R.string.at_please_allow_permission));

        }

    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int locationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (listPermissionsNeeded.size() > 0) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        } else {
            if (!Constants.LIVE_MODE)
                Log.e("true", "abc");
            return true;

        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(UserAstrologerChatWindowActivity.this, "com.astrotalk.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    public void onClick(View v) {

    }

    private void checkMatchesantiflag(String antimessage) {

        for (int i = 0; i < chatFlagModelArrayList2.size(); i++) {
            Log.e("matches1", chatFlagModelArrayList2.get(i).getFlagValue());
            if (antimessage.toLowerCase().contains(chatFlagModelArrayList2.get(i).getFlagValue().toLowerCase())) {
                Log.e("matches2", chatFlagModelArrayList2.get(i).getFlagValue());
                antimessage = antimessage.toLowerCase().replaceAll(chatFlagModelArrayList2.get(i).getFlagValue(), "");
                Log.e("matches3", antimessage);
            }

        }
        Log.e("matches4", antimessage);
        checkFlag(antimessage);

    }

    private void checkFlag(String antimessage) {
        for (int j = 0; j < chatFlagModelArrayList.size(); j++) {
            if (antimessage.contains(chatFlagModelArrayList.get(j).getFlagValue())) {
                Log.e("matches3", chatFlagModelArrayList.get(j).getFlagValue());
                flagId = chatFlagModelArrayList.get(j).getId();
                checkforFlag = true;
            } else {
                Matcher m2 = Pattern.compile("(0/91)?[7-9][0-9]{9}").matcher(sendMesage.getText().toString());
                if (m2.find()) {
                    flagId = -1;
                    Log.e("maches", "phone matches");
                    isFroud = true;
                } else {
                    isFroud = false;
                }
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    private void sendChatMessage(final Object ref, String parentMessageId) {

        String derIdValue = "";
        if (flagId == 0) {
            derIdValue = "&flagId=" + "";
        } else {
            derIdValue = "&flagId=" + flagId;
        }
        String url = null;
        try {
            url = Constants.SEND_CHAT_MESSAGE +
                    "?fromId=" + URLEncoder.encode(Constants.ID + "", "UTF-8") +
                    "&userId=" + URLEncoder.encode(Constants.ID + "", "UTF-8") +
                    "&toId=" + URLEncoder.encode(astrologerId + "", "UTF-8") +
                    "&message=" + URLEncoder.encode(sendMesage.getText().toString().replaceAll("\n", "<br>"), "UTF-8") +
                    "&isConsultant=" + URLEncoder.encode(false + "", "UTF-8") +
                    "&type=" + URLEncoder.encode("text", "UTF-8") +
                    "&chatOrderId=" + URLEncoder.encode(chatOrderId + "", "UTF-8") +
                    "&parentMessageId=" + URLEncoder.encode(parentMessageId, "UTF-8") +
                    "&isNumber=" + isFroud +
                    "&isCheckedForFlags=" + URLEncoder.encode(checkforFlag + "", "UTF-8") +
                    derIdValue;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!Constants.LIVE_MODE)
            Log.e("send", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    Log.e("send ", response);

                    if (object.getString("status").equalsIgnoreCase("success")) {

                        JSONObject dataObject = new JSONObject(object.getString("data"));

                        if (object.has("remainingTime") && !object.isNull("remainingTime")) {
                            int remainingTime = object.getInt("remainingTime");
                            Log.e("remainingTime", remainingTime + "");
                            Log.e("isquick", isQuickRechargeVisible + "");
                            Log.e("ispooooo", isPo + "");
                            if (isOfferV3 || isFixSession) {
//
                            } else {
                                Log.d("CheckSTATUS", " NOT fixed session not offer ");
                                if (remainingTime != 0 && remainingTime <= 300 && !isQuickRechargeVisible) {
                                    if (isPo == false) {
                                        chat_disable_view.setVisibility(View.VISIBLE);
                                        paymnet_ll.setVisibility(View.GONE);
                                        isPaymentTypesShow = false;
                                    }
                                    isQuickRechargeVisible = true;
                                }
                            }
                        }

                        ((UserAstrogerChatWindowModel) ref).setDelivered(false);
                        ((UserAstrogerChatWindowModel) ref).setSent(true);
                        ((UserAstrogerChatWindowModel) ref).setSentByAdmin(false);
                        if (dataObject.has("id") && !dataObject.isNull("id")) {
                            ((UserAstrogerChatWindowModel) ref).setId(dataObject.getLong("id"));
                        } else {
                            ((UserAstrogerChatWindowModel) ref).setId(0);
                        }


                        if (dataObject.has("parentMessageId") && !dataObject.isNull("parentMessageId")) {
                            ((UserAstrogerChatWindowModel) ref).setParentMessageId(dataObject.getLong("parentMessageId"));
                        } else {
                            ((UserAstrogerChatWindowModel) ref).setParentMessageId(0);
                        }
                        isMessageSent = true;

                        chatAdapter.changeData(chatArrayList);

                    } else {
                        ((UserAstrogerChatWindowModel) ref).setSent(false);
                        chatAdapter.changeData(chatArrayList);
//                        if (object.getString("reason").equalsIgnoreCase("chat order completed")) {
//                            Log.e("order_status", "++++ chat order completed ++++");
//                            Utilities.showToast(UserAstrologerChatWindowActivity.this, "your chat session has ended due to low balance");
//                            Intent orderHistory = new Intent(UserAstrologerChatWindowActivity.this, CompleteChatDetails.class);
//                            orderHistory.putExtra("chatorder_id", chatOrderId);
//                            orderHistory.putExtra("astrologer_id", astrologerId);
//                            orderHistory.putExtra("astrologer_name", astologerName);
//                            orderHistory.putExtra("iden", "chat_end");
//                            orderHistory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(orderHistory);
//                            finish();
//                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ((UserAstrogerChatWindowModel) ref).setSent(false);
                    chatAdapter.changeData(chatArrayList);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((UserAstrogerChatWindowModel) ref).setSent(false);
                chatAdapter.changeData(chatArrayList);
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", Constants.AUTHORIZATION);
                headers.put("id", Constants.ID);
                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getHistory() {
        progressBar.setVisibility(View.VISIBLE);
        String url;
        url = Constants.CHAT_HISTORY +
                "?userId=" + Constants.ID +
                "&chatOrderId=" + chatOrderId +
                "&pageno=" + pageNumber +
                "&pagesize=" + "20";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!Constants.LIVE_MODE)
                                Log.e("order history", response);

                            if (isStatusget == false) {

                                if (jsonObject.has("isPo") && !jsonObject.isNull("isPo")) {
                                    isPo = jsonObject.getBoolean("isPo");
                                } else {
                                    isPo = false;
                                }
                                if (isPo) {
                                    attachment_iv.setVisibility(View.GONE);
                                    // po_suggestion.setVisibility(View.VISIBLE);
                                } else {
                                    attachment_iv.setVisibility(View.VISIBLE);
                                    //  po_suggestion.setVisibility(View.GONE);
                                }
                                Log.e("ispooooo", isPo + "");

                                if (jsonObject.has("isOfferV3") && !jsonObject.isNull("isPo")) {
                                    isOfferV3 = jsonObject.getBoolean("isOfferV3");
                                } else {
                                    isOfferV3 = false;
                                }

                                if (jsonObject.has("fixedSessionId") && !jsonObject.isNull("fixedSessionId")) {
                                    fisSessionId = jsonObject.getLong("fixedSessionId");
                                } else {
                                    fisSessionId = -1;
                                }

                                isFixSession = fisSessionId != -1;

                                if (jsonObject.has("isEmergency") && !jsonObject.isNull("isEmergency")) {
                                    isEmeregencySession = jsonObject.getBoolean("isEmergency");
                                }

                                if (isOfferV3 || isFixSession || isEmeregencySession) {
                                    po_suggestion.setVisibility(View.GONE);
                                } else {
                                    po_suggestion.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(() -> po_suggestion.setVisibility(View.GONE), 5000);
                                }

                                if (jsonObject.has("consultantPic") && !jsonObject.isNull("consultantPic")) {
                                    if (jsonObject.getString("consultantPic").trim().isEmpty()) {
                                        astrologer_pic.setImageResource(R.drawable.at_ic_user);
                                    } else {
//                                        Picasso.get()
//                                                .load(jsonObject.getString("consultantPic"))
//                                                .placeholder(R.drawable.user_icon)
//                                                .error(R.drawable.user_icon)
//                                                .into(astrologer_pic);

                                    }
                                } else {
                                    astrologer_pic.setImageResource(R.drawable.at_ic_user);
                                }


                                if (jsonObject.has("isAutoDebit") && !jsonObject.isNull("isAutoDebit")) {
                                    isAutodebitOn = jsonObject.getBoolean("isAutoDebit");
                                } else {
                                    isAutodebitOn = false;
                                }

                                Log.e("isAutodebitOn", isAutodebitOn + "");
                                timer_tv.setTextColor(getResources().getColor(R.color.at_dark_red));
                                if (jsonObject.has("remainingTimeInSec") && !jsonObject.isNull("remainingTimeInSec")) {
                                    remainingTimeInsec = jsonObject.getLong("remainingTimeInSec");
                                } else {
                                    remainingTimeInsec = 0;
                                }
                                if (timer != null) {
                                    timer.cancel();
                                    timer = null;
                                }

                                timer = new CountDownTimer(remainingTimeInsec * 1000, 1000) {
                                    public void onTick(long millisUntilFinished) {
                                        long Hours = millisUntilFinished / (60 * 60 * 1000) % 24;
                                        long Minutes = (millisUntilFinished / (60 * 1000) % 60) + (Hours * 60);
                                        long Seconds = millisUntilFinished / 1000 % 60;
                                        timer_tv.setVisibility(View.VISIBLE);
                                        timer_tv.setText(" (" + String.format("%02d", Minutes) + ":" + String.format("%02d", Seconds) + " mins)");
                                        //here you can have your logic to set text to edittext
                                        if (isChildRequestCreated) {
                                            String timeString = "" + String.format("%02d", Minutes) + ":" + String.format("%02d", Seconds) + " mins";
                                            if (isIndian()) {
                                                tvContinueChat.setText(String.format("We'll continue the chat after %s at normal price i.e. ₹ " + astrologerOrignalprice + "/min.", timeString));
                                            } else {
                                                tvContinueChat.setText(String.format("We'll continue the chat after %s at normal price i.e. " + astrologerOrignalprice + "/min.", timeString));
                                            }
                                            cvContinueChat.setVisibility(View.VISIBLE);
                                            ll_yes_button.setVisibility(View.GONE);
                                        }
                                    }

                                    public void onFinish() {
                                        timer_tv.setVisibility(View.GONE);
                                        cvContinueChat.setVisibility(View.GONE);
                                    }

                                }.start();


                                if (jsonObject.getString("orderStatus").equalsIgnoreCase("ask")) {

                                } else if (jsonObject.getString("orderStatus").equalsIgnoreCase("CANCELLED")) {

                                } else if (jsonObject.getString("orderStatus").equalsIgnoreCase("INPROGRESS")) {
                                    below_ll.setVisibility(View.VISIBLE);
                                    headingTV.setText(getString(R.string.at_chat_status));
                                } else {
                                    chat_disable_view.setVisibility(View.GONE);
                                    enable_btn.setVisibility(View.INVISIBLE);
                                    below_ll.setVisibility(View.GONE);
                                    headingTV.setVisibility(View.GONE);
                                }

                                JSONArray dataArraycheck = jsonObject.getJSONArray("data");
                                for (int i = 0; i < dataArraycheck.length(); i++) {
                                    JSONObject jsonObject3 = dataArraycheck.getJSONObject(i);
                                    // if (jsonObject3.getBoolean("isConsultant")) {
                                    messageId = jsonObject3.getLong("id");
                                    Log.e("MESSAGEID IN LOOP", messageId + "");
                                    ishaveastrologerMessage = true;
                                    break;
                                    // }
                                }

                                isStatusget = true;
                            }

                            totalpageNumber = jsonObject.getInt("totalPages");
                            if (totalpageNumber > pageNumber) {

                                loading = true;
                                if (!Constants.LIVE_MODE)
                                    Log.e("loading true", loading + "");
                                pageNumber++;
                            } else {
                                loading = false;
                            }

                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            ArrayList<UserAstrogerChatWindowModel> childchatarray = new ArrayList<>();
                            for (int i = 0; i < dataArray.length(); i++) {
                                boolean deletedForConsultant = false;
                                boolean deletedForUser = false;
                                isPersecondApiCall = true;
                                UserAstrogerChatWindowModel userChatWindowModel = new UserAstrogerChatWindowModel();
                                JSONObject jsonObject1 = dataArray.getJSONObject(i);
                                userChatWindowModel.setId(jsonObject1.getLong("id"));
                                userChatWindowModel.setMessage(jsonObject1.getString("message"));
                                userChatWindowModel.setFrom_user_id(jsonObject1.getLong("fromId"));
                                userChatWindowModel.setTo_user_id(jsonObject1.getLong("toId"));
                                userChatWindowModel.setCreation_time(jsonObject1.getLong("creationtime"));
                                userChatWindowModel.setConsultant(jsonObject1.getBoolean("isConsultant"));
                                userChatWindowModel.setType(jsonObject1.getString("type"));
                                userChatWindowModel.setParentReply(false);
                                userChatWindowModel.setAstrologerName(astologerName);

                                if (jsonObject1.has("parentMessageType") && !jsonObject1.isNull("parentMessageType")) {
                                    userChatWindowModel.setParentMessageType(jsonObject1.getString("parentMessageType"));
                                } else {
                                    userChatWindowModel.setParentMessageType("");
                                }

                                if (jsonObject1.has("parentMessage") && !jsonObject1.isNull("parentMessage")) {
                                    userChatWindowModel.setParentMessage(jsonObject1.getString("parentMessage"));
                                } else {
                                    userChatWindowModel.setParentMessage("");
                                }


                                if (jsonObject1.has("parentMessageSentByUser") && !jsonObject1.isNull("parentMessageSentByUser")) {
                                    userChatWindowModel.setParentMessageSentByUser(jsonObject1.getBoolean("parentMessageSentByUser"));
                                } else {
                                    userChatWindowModel.setParentMessageSentByUser(null);
                                }
                                if (jsonObject1.has("parentMessageId") && !jsonObject1.isNull("parentMessageId")) {
                                    userChatWindowModel.setParentMessageId(jsonObject1.getLong("parentMessageId"));
                                } else {

                                }

                                if (jsonObject1.has("isDelivered") && !jsonObject1.isNull("isDelivered")) {

                                    userChatWindowModel.setDelivered(jsonObject1.getBoolean("isDelivered"));
                                } else {
                                    userChatWindowModel.setDelivered(false);
                                }
                                if (jsonObject1.has("lowBalanceText") && !jsonObject1.isNull("lowBalanceText")) {
                                    userChatWindowModel.setLowBalanceText(jsonObject1.getBoolean("lowBalanceText"));
                                } else {
                                    userChatWindowModel.setLowBalanceText(false);
                                }
                                if (jsonObject1.has("deletedForConsultant") && !jsonObject1.isNull("deletedForConsultant")) {
                                    deletedForConsultant = jsonObject1.getBoolean("deletedForConsultant");
                                } else {
                                    deletedForConsultant = false;
                                }
                                if (jsonObject1.has("deletedForUser") && !jsonObject1.isNull("deletedForUser")) {
                                    deletedForUser = jsonObject1.getBoolean("deletedForUser");
                                } else {
                                    deletedForUser = false;
                                }
                                Log.e("1", jsonObject1.getString("message"));
                                if (deletedForConsultant && deletedForUser) {
                                    userChatWindowModel.setMessageDelete(true);
                                    Log.e("1", "1");
                                } else if (deletedForUser && jsonObject1.getBoolean("isConsultant")) {
                                    userChatWindowModel.setMessageDelete(true);
                                    Log.e("1", "2");
                                } else {
                                    userChatWindowModel.setMessageDelete(false);
                                    Log.e("1", "3");
                                }

                                Log.e("Messages", jsonObject1.getString("message"));

                                if (jsonObject1.has("chatOrderId") && !jsonObject1.isNull("chatOrderId")) {
                                    userChatWindowModel.setChatOrderId(jsonObject1.getLong("chatOrderId"));
                                    if (previousOrderId == 0) {
                                        previousOrderId = userChatWindowModel.getChatOrderId();
                                    } else {
                                        if (previousOrderId != userChatWindowModel.getChatOrderId()) {
                                            if (childchatarray.size() != 0) {
                                                childchatarray.get(childchatarray.size() - 1).setShowDate(true);
                                            }
                                            previousOrderId = userChatWindowModel.getChatOrderId();
                                        }
                                    }
                                } else {

                                }
                                childchatarray.add(userChatWindowModel);

                            }

                            chatArrayList.addAll(childchatarray);
                            if (chatArrayList.size() == 0) {
                                isPersecondApiCall = true;
                            }
                            chatAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);


            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", Constants.AUTHORIZATION);
                headers.put("id", Constants.ID);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getLatestMessage() {
        isLatestApiResponsePending = true;
        Log.e("MESSAGEID IN LOOP", messageId + "");
        Log.e("ishaveastrologerMessage", ishaveastrologerMessage + "");
        if (ishaveastrologerMessage == false) {
            if (chatArrayList.size() == 0) {
                messageId = 0;
            } else {
                messageId = chatArrayList.get(0).getId();
            }
        }
        Log.e("GETLATESTMESSAGE", "chatOrderId => " + chatOrderId);

        String url;
        if (currnettime == 0) {
            if (messageId == 0) {
                url = Constants.LATEST_CHAT_MESSAGE +
                        "?chatId=" + chatOrderId +
                        "&isSentByAstrologer=" + true +
                        "&userId=" + Constants.ID;

            } else {
                url = Constants.LATEST_CHAT_MESSAGE +
                        "?chatId=" + chatOrderId +
                        "&isSentByAstrologer=" + true +
                        "&messageId=" + messageId +
                        "&userId=" + Constants.ID;
            }
        } else {

            if (messageId == 0) {
                url = Constants.LATEST_CHAT_MESSAGE +
                        "?chatId=" + chatOrderId +
                        "&isSentByAstrologer=" + true +
                        "&typeTime=" + currnettime +
                        "&userId=" + Constants.ID;

            } else {
                url = Constants.LATEST_CHAT_MESSAGE +
                        "?chatId=" + chatOrderId +
                        "&isSentByAstrologer=" + true +
                        "&messageId=" + messageId +
                        "&typeTime=" + currnettime +
                        "&userId=" + Constants.ID;
            }

        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("getLatestMessage", " url => " + url);
                            Log.e("getLatestMessage", response);
                            if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                                JSONArray dataArray = jsonObject.getJSONArray("data");
                                ArrayList<UserAstrogerChatWindowModel> childchatarray = new ArrayList<>();
                                for (int i = 0; i < dataArray.length(); i++) {
                                    boolean deletedForConsultant = false;
                                    boolean deletedForUser = false;
                                    UserAstrogerChatWindowModel userChatWindowModel = new UserAstrogerChatWindowModel();
                                    JSONObject jsonObject1 = dataArray.getJSONObject(i);
                                    userChatWindowModel.setId(jsonObject1.getLong("id"));

                                    messageId = jsonObject1.getLong("id");
                                    fromIdCount = messageId;
                                    Log.e("Testt<><>MessageId", String.valueOf(messageId));
                                    ishaveastrologerMessage = true;
                                    userChatWindowModel.setMessage(jsonObject1.getString("message"));
                                    userChatWindowModel.setFrom_user_id(jsonObject1.getLong("fromId"));
                                    userChatWindowModel.setTo_user_id(jsonObject1.getLong("toId"));
                                    userChatWindowModel.setCreation_time(jsonObject1.getLong("creationtime"));
                                    userChatWindowModel.setConsultant(jsonObject1.getBoolean("isConsultant"));
                                    userChatWindowModel.setType(jsonObject1.getString("type"));
                                    userChatWindowModel.setAstrologerName(astologerName);
                                    userChatWindowModel.setParentReply(false);

                                    if (jsonObject1.has("lowBalanceText") && !jsonObject1.isNull("lowBalanceText")) {
                                        userChatWindowModel.setLowBalanceText(jsonObject1.getBoolean("lowBalanceText"));
                                    } else {
                                        userChatWindowModel.setLowBalanceText(false);
                                    }


                                    if (jsonObject1.has("parentMessageType") && !jsonObject1.isNull("parentMessageType")) {
                                        userChatWindowModel.setParentMessageType(jsonObject1.getString("parentMessageType"));
                                    } else {
                                        userChatWindowModel.setParentMessageType("");
                                    }
                                    if (jsonObject1.has("parentMessageId") && !jsonObject1.isNull("parentMessageId")) {
                                        userChatWindowModel.setParentMessageId(jsonObject1.getLong("parentMessageId"));
                                    } else {

                                    }
                                    if (chatAdapter != null) {
                                        if (chatAdapter.latestOrderId != 0) {
                                            userChatWindowModel.setChatOrderId(chatOrderId);
                                        }
                                    }

                                    userChatWindowModel.setShowDate(false);

                                    if (jsonObject1.has("parentMessage") && !jsonObject1.isNull("parentMessage")) {
                                        userChatWindowModel.setParentMessage(jsonObject1.getString("parentMessage"));
                                    } else {
                                        userChatWindowModel.setParentMessage("");
                                    }


                                    if (jsonObject1.has("parentMessageSentByUser") && !jsonObject1.isNull("parentMessageSentByUser")) {
                                        userChatWindowModel.setParentMessageSentByUser(jsonObject1.getBoolean("parentMessageSentByUser"));
                                    } else {
                                        userChatWindowModel.setParentMessageSentByUser(null);
                                    }

                                    if (jsonObject1.has("deletedForConsultant") && !jsonObject1.isNull("deletedForConsultant")) {
                                        deletedForConsultant = jsonObject1.getBoolean("deletedForConsultant");
                                    } else {
                                        deletedForConsultant = false;
                                    }
                                    if (jsonObject1.has("deletedForUser") && !jsonObject1.isNull("deletedForUser")) {
                                        deletedForUser = jsonObject1.getBoolean("deletedForUser");
                                    } else {
                                        deletedForUser = false;
                                    }

                                    Log.e("1", jsonObject1.getString("message"));
                                    if (deletedForConsultant && deletedForUser) {
                                        userChatWindowModel.setMessageDelete(true);
                                        Log.e("1", "1");
                                    } else if (deletedForUser && jsonObject1.getBoolean("isConsultant")) {
                                        userChatWindowModel.setMessageDelete(true);
                                        Log.e("1", "2");
                                    } else {
                                        userChatWindowModel.setMessageDelete(false);
                                        Log.e("1", "3");
                                    }

                                    childchatarray.add(userChatWindowModel);

                                }

                                if (!childchatarray.isEmpty())
                                    if (chatAdapter != null) {
                                        if (chatArrayList.size() == 0) {
                                        } else {
                                            if (chatArrayList.get(0).getId() != messageId) {
                                                chatArrayList.addAll(0, childchatarray);
                                            }
                                        }

                                        if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
//                                            chatAdapter.notifyDataSetChanged();
                                            chatAdapter.changeData(chatArrayList);
                                            recyclerView.scrollToPosition(0);
                                            fromIdOldCount = 0;
                                            z = 0;
                                            smallMessageCount = 0;
                                            fromIdCount = 0;
                                            rl_count_view.setVisibility(View.GONE);
                                            tv_small_message_count.setVisibility(View.GONE);
                                        } else {
                                            chatAdapter.changeData(chatArrayList);
//                                            chatAdapter.notifyItemInserted(0);
                                            if (rl_down.getVisibility() == View.VISIBLE) {
                                                smallMessageCount = smallMessageCount + 1;
                                                rl_count_view.setVisibility(View.VISIBLE);
                                                tv_small_message_count.setVisibility(View.VISIBLE);
                                                tv_small_message_count.setText(String.valueOf(smallMessageCount));
                                            } else {
                                                fromIdOldCount = 0;
                                                z = 0;
                                                smallMessageCount = 0;
                                                fromIdCount = 0;
                                                rl_count_view.setVisibility(View.GONE);
                                                tv_small_message_count.setVisibility(View.GONE);
                                            }

                                        }

                                    }


                                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("typing"));
                                if (jsonObject1.has("orderStatus") && !jsonObject1.isNull("orderStatus")) {
                                    orderStatus = jsonObject1.getString("orderStatus");
                                } else {
                                    orderStatus = "";
                                }


                                Log.e("order_status", "=========================");
                                Log.e("order_status", orderStatus);
                                Log.e("order_status", "id => " + chatOrderId);
                                Log.e("isChatCompleted", isChatCompleted + "");

                                if (orderStatus.equalsIgnoreCase("COMPLETED") && !isChatCompleted) {
                                    isChatCompleted = true;
                                    below_ll.setVisibility(View.GONE);
                                    attachment_iv.setVisibility(View.GONE);
//                                    Intent orderHistory = new Intent(UserAstrologerChatWindowActivity.this, CompleteChatDetails.class);
//                                    orderHistory.putExtra("chatorder_id", chatOrderId);
//                                    orderHistory.putExtra("astrologer_id", astrologerId);
//                                    orderHistory.putExtra("astrologer_name", astologerName);
//                                    orderHistory.putExtra("iden", "chat_end");
//                                    orderHistory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(orderHistory);
//                                    finish();
                                }

                                if (jsonObject1.has("isChildPresent") && !jsonObject1.isNull("isChildPresent")) {
                                    isChildRequestCreated = jsonObject1.getBoolean("isChildPresent");
                                } else {
                                    isChildRequestCreated = false;
                                }

                                //Show dialog for offer and Fixed session when time is less than 2 mintues
                                if (jsonObject.has("remainingTimeInSec") && !jsonObject.isNull("remainingTimeInSec")) {
                                    Log.d("RemainingTime", "onResponse: " + remainingTimeInsecForOffer);
                                    remainingTimeInsecForOffer = jsonObject.getLong("remainingTimeInSec");
                                    if (remainingTimeInsecForOffer < 0) {
                                        if (isChildRequestCreated && (!isChatIntiateDialogShow)) {
                                            cvNewChatStatus.setVisibility(View.GONE);
                                            isChatIntiateDialogShow = true;
//                                            cvNewChatStatus.setVisibility(View.VISIBLE);
                                            tvNewChatStatus.setText("Please wait new chat is initiating...");
                                        }
                                    }
                                } else {
                                    remainingTimeInsecForOffer = 0;
                                }

                                if (isOfferV3 || isFixSession) {
                                    if (isChatContinueEnable && isAstrologerVersionCompatiable && (!isQuickRechargeVisible) && (!isPaymentTypesShow)) {
                                        if (remainingTimeInsecForOffer != 0 && remainingTimeInsecForOffer <= 150 && !isDialogShowInCaseOfPO && !isChildRequestCreated) {
                                            if (!continueChatText.trim().isEmpty()) {
                                                tvContinueChat.setText(continueChatText);
                                                cvContinueChat.setVisibility(View.VISIBLE);
                                                isDialogShowInCaseOfPO = true;
                                            }
                                        }
                                    }
                                }


                                if (jsonObject1.has("astrologerTyping") && !jsonObject1.isNull("astrologerTyping")) {

                                    if (jsonObject1.getBoolean("astrologerTyping")) {
                                        typing_tv.setVisibility(View.VISIBLE);
                                        headingTV.setVisibility(View.GONE);
                                    } else {
                                        typing_tv.setVisibility(View.GONE);
                                        headingTV.setVisibility(View.VISIBLE);
                                    }
                                }

                                isLatestApiResponsePending = false;

                            } else {

                                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("typing"));
                                if (jsonObject1.has("orderStatus") && !jsonObject1.isNull("orderStatus")) {
                                    orderStatus = jsonObject1.getString("orderStatus");
                                } else {
                                    orderStatus = "";
                                }

                                if (jsonObject1.has("isChildPresent") && !jsonObject1.isNull("isChildPresent")) {
                                    isChildRequestCreated = jsonObject1.getBoolean("isChildPresent");
                                } else {
                                    isChildRequestCreated = false;
                                }


                                if (jsonObject.has("remainingTimeInSec") && !jsonObject.isNull("remainingTimeInSec")) {
                                    Log.d("RemainingTime", "onResponse: " + remainingTimeInsecForOffer);
                                    remainingTimeInsecForOffer = jsonObject.getLong("remainingTimeInSec");
                                    if (remainingTimeInsecForOffer < 0) {
                                        if (isChildRequestCreated && !isChatIntiateDialogShow) {
//                                            cvNewChatStatus.setVisibility(View.VISIBLE);
                                            isChatIntiateDialogShow = true;
                                            cvNewChatStatus.setVisibility(View.GONE);
                                            tvNewChatStatus.setText("Please wait new chat is initiating...");
                                        }

                                    }
                                } else {
                                    remainingTimeInsecForOffer = 0;
                                }

                                Log.e("order_status", "=========================");
                                Log.e("order_status", orderStatus);
                                Log.e("order_status", "id => " + chatOrderId);


                                if (isOfferV3 || isFixSession) {
                                    if (isChatContinueEnable && isAstrologerVersionCompatiable && (!isQuickRechargeVisible) && (!isPaymentTypesShow)) {
                                        if (remainingTimeInsecForOffer != 0 && remainingTimeInsecForOffer <= 150 && !isDialogShowInCaseOfPO && !isChildRequestCreated) {
                                            if (!continueChatText.trim().isEmpty()) {
                                                tvContinueChat.setText(continueChatText);
                                                cvContinueChat.setVisibility(View.VISIBLE);
                                                isDialogShowInCaseOfPO = true;
                                            }
                                        }
                                    }
                                }


                                Log.e("isChatCompleted", isChatCompleted + "");
                                if (orderStatus.equalsIgnoreCase("COMPLETED") && isChatCompleted == false) {
                                    isChatCompleted = true;
                                    below_ll.setVisibility(View.GONE);
                                    attachment_iv.setVisibility(View.GONE);
//                                    Intent orderHistory = new Intent(UserAstrologerChatWindowActivity.this, CompleteChatDetails.class);
//                                    orderHistory.putExtra("chatorder_id", chatOrderId);
//                                    orderHistory.putExtra("astrologer_id", astrologerId);
//                                    orderHistory.putExtra("astrologer_name", astologerName);
//                                    orderHistory.putExtra("iden", "chat_end");
//                                    orderHistory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(orderHistory);
//                                    finish();
                                }

                                if (jsonObject1.has("astrologerTyping") && !jsonObject1.isNull("astrologerTyping")) {

                                    Boolean test = jsonObject1.getBoolean("astrologerTyping");
                                    Log.e("BooleanCheck", String.valueOf(test));

                                    if (jsonObject1.getBoolean("astrologerTyping")) {

                                        typing_tv.setVisibility(View.VISIBLE);
                                        headingTV.setVisibility(View.GONE);
                                    } else {
                                        typing_tv.setVisibility(View.GONE);
                                        headingTV.setVisibility(View.VISIBLE);
                                    }
                                }

                                if (jsonObject1.has("lastDeliveredMessageId") && !jsonObject1.isNull("lastDeliveredMessageId")) {
                                    Log.e("lastDeliveredMessageId", +jsonObject1.getLong("lastDeliveredMessageId") + "");

                                    if (isMessageSent) {
                                        for (int i = 0; i < chatArrayList.size(); i++) {
                                            if (chatArrayList.get(i).getId() <= jsonObject1.getLong("lastDeliveredMessageId") && !chatArrayList.get(i).isConsultant() && !chatArrayList.get(i).isDelivered()) {
                                                chatArrayList.get(i).setDelivered(true);
                                                chatAdapter.changeData(chatArrayList);
                                            }

                                        }
                                    }
                                }

                                isLatestApiResponsePending = false;

                            }

                        } catch (JSONException e) {
                            isLatestApiResponsePending = false;
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLatestApiResponsePending = false;
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", Constants.AUTHORIZATION);
                headers.put("id", Constants.ID);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private void getAstrologerActualPrice() {
        String url = null;
        try {
            url = Constants.GET_ASTROLOGER_ACTUAL_PRICE +
                    "?userId=" + URLEncoder.encode(Constants.ID + "", "UTF-8") +
                    "&appId=" + URLEncoder.encode(Constants.APP_ID + "", "UTF-8") +
                    "&consultantId=" + URLEncoder.encode(astrologerId + "", "UTF-8") +
                    "&timeZone=" + URLEncoder.encode(tz + "", "UTF-8");


        } catch (UnsupportedEncodingException e) {

        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                        if (jsonObject.has("data") && !jsonObject.isNull("data")) {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                            if (jsonObject1.has("price") && !jsonObject1.isNull("price")) {
                                if (tz.equalsIgnoreCase("Asia/Calcutta") || tz.equalsIgnoreCase("Asia/Kolkata")) {
                                    int price = jsonObject1.getInt("price");
                                    astrologerOrignalprice = String.valueOf(price);
                                } else {
                                    int price = jsonObject1.getInt("price");
                                    astrologerOrignalprice = Utilities.getConvertedValueFromINR(price, preferences);
                                }
                            } else {
                                astrologerOrignalprice = "0";
                            }

                            if (jsonObject1.has("priceMessage") && !jsonObject1.isNull("priceMessage")) {
                                continueChatText = jsonObject1.getString("priceMessage");
                            }

                        }

                    } else {
                        if (jsonObject.has("reason") && !jsonObject.isNull("reason")) {
                            Utilities.showToast(UserAstrologerChatWindowActivity.this, jsonObject.getString("reason"));
                        } else {
                            Utilities.showToast(UserAstrologerChatWindowActivity.this, getResources().getString(R.string.at_something_went_wrong));
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Utilities.showToast(UserAstrologerChatWindowActivity.this, getResources().getString(R.string.at_something_went_wrong));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utilities.showToast(UserAstrologerChatWindowActivity.this, getResources().getString(R.string.at_something_went_wrong));
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", Constants.AUTHORIZATION);
                headers.put("id", Constants.ID);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getAstrologerAppVersion() {
        String url = null;
        try {
            url = Constants.CHECK_ASTROLOGER_IS_VALID_FOR_CHAT_CONTINUE +
                    "?userId=" + URLEncoder.encode(Constants.ID + "", "UTF-8") +
                    "&appId=" + URLEncoder.encode(Constants.APP_ID + "", "UTF-8") +
                    "&businessId=" + URLEncoder.encode(Constants.BUSINESS_ID + "", "UTF-8") +
                    "&consultantId=" + URLEncoder.encode(astrologerId + "", "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e("dlskd", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                        if (jsonObject.has("data") && !jsonObject.isNull("data")) {
                            isAstrologerVersionCompatiable = jsonObject.getBoolean("data");
                            Log.d("getAstrologerAppVersion", "onResponse: => " + isAstrologerVersionCompatiable);
                        } else {
                            isAstrologerVersionCompatiable = false;
                        }
                    } else {
                        isAstrologerVersionCompatiable = false;
                        Utilities.showToast(UserAstrologerChatWindowActivity.this, "Some thing went wrong");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Utilities.showToast(UserAstrologerChatWindowActivity.this, "Some thing went wrong");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isAstrologerVersionCompatiable = false;
                Utilities.showToast(UserAstrologerChatWindowActivity.this, "Some thing went wrong");
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", Constants.AUTHORIZATION);
                headers.put("id", Constants.ID);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.VOLLEY_TIME_OUT, Constants.VOLLEY_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);

    }

    private void orderComplete() {

        String url = null;
        try {
            url = Constants.COMPLETE_CHAT_ORDER +
                    "?chatOrderId=" + URLEncoder.encode(chatOrderId + "", "UTF-8")
                    + "&userId=" + URLEncoder.encode(Constants.ID + "", "UTF-8")
                    + "&isUserEnded=true";

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equalsIgnoreCase("success")) {
                        isChatCompleted = true;
                        //  isPersecondApiCall=false;
                        Utilities.showToast(UserAstrologerChatWindowActivity.this, "Chat ended");
//                        Intent orderHistory = new Intent(UserAstrologerChatWindowActivity.this, CompleteChatDetails.class);
//                        orderHistory.putExtra("chatorder_id", chatOrderId);
//                        orderHistory.putExtra("astrologer_id", astrologerId);
//                        orderHistory.putExtra("astrologer_name", astologerName);
//                        orderHistory.putExtra("iden", "chat_end");
//                        orderHistory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(orderHistory);
//                        finish();

                    } else {

                        Utilities.showToast(UserAstrologerChatWindowActivity.this, object.getString("reason"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", Constants.AUTHORIZATION);
                headers.put("id", Constants.ID);
                return headers;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }
}