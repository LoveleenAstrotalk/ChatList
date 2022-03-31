package com.astrotalk.sdk.api.activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.googleApi.GoogleAddressModel;
import com.astrotalk.sdk.api.model.UniversalAstrologerListModel;
import com.astrotalk.sdk.api.utils.Constants;
import com.astrotalk.sdk.api.utils.Utilities;
import com.google.android.material.textfield.TextInputLayout;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatIntakeFormActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private String dob = "";
    private String pdob = "";
    private TextView registrationBtn;
    private SharedPreferences sharedPreferences;
    private String tz;
    private long userId = -1;
    private RadioButton radioMale, radioFemale;
    private String selectedTime = "";
    private String timeOfBirthPartner = "";
    private LinearLayout partner_details_ll;
    private LinearLayout partner_details_option;
    private CheckBox partner_details_checkbox;
    private String dobIdent = "";
    private TextView comment_count;
    private ImageView message_iv;
    private EditText dobET, TimeBirthET;
    private Calendar cal;
    private Toolbar toolbar;
    private TextView toolbarTV;
    private int hour, minute;
    private RadioGroup radioSexGroup;
    private EditText nameET, lastnameET, phoneET, questionET, city_name, state_name, country_name;
    private RadioButton radioSexButton;
    private String gender;
//    private Tracker mTracker;
    private EditText partneNnameET, pdobET, pTimeBirthET, pcity_name, pstate_name, pcountry_name;
    private ImageView notification_iv;
    private Calendar DefaultStartCalender, DefaultStartCalenderFemale;
    private String startTimeIn24Hours;
    private String countryCode = "";
    private TextView enter_partner_details;
    private UniversalAstrologerListModel universalAstrologerListModel;
    private List<String> maritalStatusCategories = new ArrayList<String>();
    private Spinner marital_spinner;
    private String maritalStatus = "";
    private EditText occupationET;
    boolean isGcmAvailble = true;
    boolean isWaitListJoined = false;
    private String userVersion;
    private List<String> categories = new ArrayList<String>();
    private Spinner spinner;
    private String selectedItem = "";
    private int selectedPosition = 0;
    private EditText otherET;
    private String status = "ask";
    private String place_api_key = "";
    private TextView comment_heading;
    private String consultantType = "";
    private Boolean isToShowPlaces = false;
    ArrayList<GoogleAddressModel> userAddressArrayList = new ArrayList<>();
    private TextInputLayout pob_city, pob_state, pob_country, p_pob_city, p_pob_state, p_pob_country;
    private AppCompatAutoCompleteTextView auto_complete_edit_text_astrotalkapi;
    private AppCompatAutoCompleteTextView auto_complete_edit_text_astrotalkapi_partner;
    private AppCompatAutoCompleteTextView auto_complete_edit_text;
    private AppCompatAutoCompleteTextView auto_complete_edit_text_partner;
    private static final String PLACES_DETAILS_API_BASE = "https://maps.googleapis.com/maps/api/place/details";
    private static final String OUT_JSON = "/json";
    private long fixedSessionId = -1;
    public GoogleAddressModel googleAddressModel, googleAddressModelPartner, astrotalkPlaceModel, astrotalkPlacePartner;
    long language_id = 1;

    private boolean atLocationApi = false;
    private String chatType = "NORMAL";

    private boolean isOfferV3 = false;
    int from = 1;
    private RequestQueue requestQueue;
    private TextView accceptChat;
    private Long chatOrderId;

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour = hourOfDay;
            minute = minutes;
            startTimeIn24Hours = new StringBuilder().append(hour).append(':')
                    .append(minute).toString();
            updateStartTime(hour, minute);

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_intake_form);

        requestQueue = Volley.newRequestQueue(this);

        if (getIntent().hasExtra("from")) {
            from = getIntent().getIntExtra("from", 1);
        }

        if (getIntent().hasExtra("isOfferV3")) {

            isOfferV3 = getIntent().getBooleanExtra("isOfferV3", false);

            Log.e("dkskjldj", isOfferV3 + "");

        }

        if (getIntent().hasExtra("fixedSessionId")) {
            fixedSessionId = getIntent().getLongExtra("fixedSessionId", -1);
        }


        if (getIntent().hasExtra("consultant")) {
            consultantType = getIntent().getStringExtra("consultant");

        }

        if (getIntent().hasExtra("chatType")) {
            chatType = getIntent().getStringExtra("chatType");
        }


        HashMap<String, Object> eventProperties = new HashMap<String, Object>();//added by CleverTap Assistant
        if (isOfferV3){
            eventProperties.put("type", chatType);
        }else {
            eventProperties.put("type", "Offer");
        }
        if (from == 2) {
            eventProperties.put("Source","ChatList");
        }else {
            eventProperties.put("Source","profile");
        }

        sharedPreferences = getSharedPreferences(Constants.USER_DETAIL, MODE_PRIVATE);
        status = getIntent().getStringExtra("status");

        language_id = sharedPreferences.getLong(Constants.LANGUAGE_ID, 1);

        try {
            isToShowPlaces = getIntent().getExtras().getBoolean("isToShowPlaces");
            atLocationApi = getIntent().getExtras().getBoolean("atLocationApi");

            Log.e("dlkjsd", isToShowPlaces + "");
        } catch (Exception e) {
            e.printStackTrace();
            isToShowPlaces = false;
            atLocationApi = false;
        }

        pob_city = findViewById(R.id.pob_city);
        pob_state = findViewById(R.id.pob_state);
        pob_country = findViewById(R.id.pob_country);
        p_pob_city = findViewById(R.id.p_pob_city);
        p_pob_state = findViewById(R.id.p_pob_state);
        p_pob_country = findViewById(R.id.p_pob_country);

        dobET = (EditText) findViewById(R.id.dobET);
        dobET.setOnClickListener(this);

        accceptChat = findViewById(R.id.accceptChat);
        accceptChat.setOnClickListener(this);

        DefaultStartCalender = Calendar.getInstance();
        DefaultStartCalender.set(Calendar.YEAR, 1990);
        DefaultStartCalender.set(Calendar.DATE, 1);
        DefaultStartCalender.set(Calendar.MONTH, 0);


        DefaultStartCalenderFemale = Calendar.getInstance();
        DefaultStartCalenderFemale.set(Calendar.YEAR, 1990);
        DefaultStartCalenderFemale.set(Calendar.DATE, 1);
        DefaultStartCalenderFemale.set(Calendar.MONTH, 0);

        enter_partner_details = (TextView) findViewById(R.id.enter_partner_details);
        enter_partner_details.setOnClickListener(this);


//        AppController application = (AppController) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.enableAdvertisingIdCollection(true);
//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory("Action")
//                .setAction("Share")
//                .build());

        userId = sharedPreferences.getLong(Constants.USER_ID, -1);
        tz = sharedPreferences.getString(Constants.USER_TIME_ZONE, "");


        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            userVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        auto_complete_edit_text = findViewById(R.id.auto_complete_edit_text);
        auto_complete_edit_text_partner = findViewById(R.id.auto_complete_edit_text_partner);

        auto_complete_edit_text.setThreshold(2);
        auto_complete_edit_text_partner.setThreshold(2);

        auto_complete_edit_text_astrotalkapi = findViewById(R.id.auto_complete_edit_text_astrotalkapi);
        auto_complete_edit_text_astrotalkapi_partner = findViewById(R.id.auto_complete_edit_text_astrotalkapi_partner);

        auto_complete_edit_text_astrotalkapi.setOnClickListener(this);
        auto_complete_edit_text_astrotalkapi_partner.setOnClickListener(this);


        auto_complete_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                googleAddressModel = null;
                city_name.setText("");
                state_name.setText("");
                country_name.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        auto_complete_edit_text_partner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                googleAddressModelPartner = null;
                pcity_name.setText("");
                pstate_name.setText("");
                pcountry_name.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        auto_complete_edit_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                googleAddressModel = null;
//                auto_complete_edit_text.setText("");
//                final GooglePlacesSearchAdapter.PlaceAutocomplete item = googlePlacesSearchAdapter.getItem(position);
//                auto_complete_edit_text.setText(item.toString());
//                final String placeId = String.valueOf(item.placeId);
//                getLocationDetails(placeId, false);
//                if (!Constants.LIVE_MODE)
//                    Log.i("TAG", "Autocomplete item selected: " + item.description);
//            }
//        });
//
//        auto_complete_edit_text_partner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                googleAddressModelPartner = null;
//                auto_complete_edit_text_partner.setText("");
//                final GooglePlacesSearchAdapter.PlaceAutocomplete item = googlePlacesSearchAdapterPartner.getItem(position);
//                auto_complete_edit_text_partner.setText(item.toString());
//                final String placeId = String.valueOf(item.placeId);
//                getLocationDetails(placeId, true);
//            }
//        });


        otherET = (EditText) findViewById(R.id.otherET);
        categories.add(getResources().getString(R.string.at_select_topic_of_concern_drop_down));
        categories.add(getResources().getString(R.string.at_career_and_business_drop_down));
        categories.add(getResources().getString(R.string.at_marriage_drop_down));
        categories.add(getResources().getString(R.string.at_love_and_relationship_drop_down));
        categories.add(getResources().getString(R.string.at_wealth_and_property_drop_down));
        categories.add(getResources().getString(R.string.at_education_drop_down));
        categories.add(getResources().getString(R.string.at_legal_matters_drop_down));
        categories.add(getResources().getString(R.string.at_child_name_consultation_drop_down));
        categories.add(getResources().getString(R.string.at_business_name_consultation_drop_down));
        categories.add(getResources().getString(R.string.at_gem_stone_consultation_drop_down));
        categories.add(getResources().getString(R.string.at_commodity_trading_consultation_drop_down));
        categories.add(getResources().getString(R.string.at_match_making_drop_down));
        categories.add(getResources().getString(R.string.at_birth_time_rectification_drop_down));
        categories.add(getResources().getString(R.string.at_name_correction_consultation_drop_down));
        categories.add(getResources().getString(R.string.at_travel_abroad_consultation_drop_down));
        categories.add(getResources().getString(R.string.at_remedy_consultation_drop_down));
        categories.add(getResources().getString(R.string.at_health_consultation_drop_down));
        categories.add(getResources().getString(R.string.at_others_drop_down));
        spinner = (Spinner) findViewById(R.id.proble_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        intViews();

    }


    private void intViews() {
        universalAstrologerListModel = (UniversalAstrologerListModel) getIntent().getSerializableExtra("astrologer_details");
//        isWaitListJoined = getIntent().getExtras().getBoolean("isWaitListJoined");


        questionET = (EditText) findViewById(R.id.questionET);
        comment_heading = (TextView) findViewById(R.id.comment_heading);
        comment_count = (TextView) findViewById(R.id.comment_count);
        if (isOfferV3) {
            questionET.setVisibility(View.GONE);
            comment_heading.setVisibility(View.GONE);
            comment_count.setVisibility(View.GONE);
        } else {
            questionET.setVisibility(View.GONE);
            comment_heading.setVisibility(View.GONE);
            comment_count.setVisibility(View.GONE);
        }
        TimeBirthET = (EditText) findViewById(R.id.TimeBirthET);
        city_name = (EditText) findViewById(R.id.city_name);
        state_name = (EditText) findViewById(R.id.state_name);
        country_name = (EditText) findViewById(R.id.country_name);
        nameET = (EditText) findViewById(R.id.nameET);
        lastnameET = (EditText) findViewById(R.id.lastnameET);
        phoneET = (EditText) findViewById(R.id.phoneET);


        TimeBirthET.setOnClickListener(this);
        partneNnameET = (EditText) findViewById(R.id.partneNnameET);
        pdobET = (EditText) findViewById(R.id.pdobET);
        pTimeBirthET = (EditText) findViewById(R.id.pTimeBirthET);
        pcity_name = (EditText) findViewById(R.id.pcity_name);
        pstate_name = (EditText) findViewById(R.id.pstate_name);
        pcountry_name = (EditText) findViewById(R.id.pcountry_name);
        pdobET.setOnClickListener(this);
        pTimeBirthET.setOnClickListener(this);
        partner_details_option = (LinearLayout) findViewById(R.id.partner_details_option);

        if (isOfferV3) {
            partner_details_option.setVisibility(View.GONE);
        } else {
            partner_details_option.setVisibility(View.VISIBLE);
        }
        partner_details_checkbox = (CheckBox) findViewById(R.id.partner_details_checkbox);
        partner_details_checkbox.setOnClickListener(this);
        questionET.addTextChangedListener(this);
//        message_iv = (ImageView) findViewById(R.id.message_iv);
//        message_iv.setVisibility(View.VISIBLE);
//        message_iv.setOnClickListener(this);
//        notification_iv = (ImageView) findViewById(R.id.notification_iv);
//        notification_iv.setVisibility(View.GONE);
//        notification_iv.setOnClickListener(this);
        comment_count.setText(0 + "/140");

        cal = Calendar.getInstance();
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbarTV = (TextView) findViewById(R.id.toolbarTV);
//        toolbarTV.setText(getResources().getString(R.string.chat_intake_form_heading));
        registrationBtn = (TextView) findViewById(R.id.registrationBtn);
        registrationBtn.setOnClickListener(this);

        registrationBtn.setText(getResources().getString(R.string.at_start_chat_with) + " " + universalAstrologerListModel.getFirstname());

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        partner_details_ll = (LinearLayout) findViewById(R.id.partner_details_ll);
        radioMale = (RadioButton) findViewById(R.id.radioMale);
        radioMale.setOnClickListener(this);
        radioFemale = (RadioButton) findViewById(R.id.radioFemale);
        radioFemale.setOnClickListener(this);


        maritalStatusCategories.add(getResources().getString(R.string.at_select_martial_status_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_single_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_married_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_divorced_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_separated_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_widowed_drop_down));
        marital_spinner = (Spinner) findViewById(R.id.marital_spinner);
        occupationET = (EditText) findViewById(R.id.occupationET);
        ArrayAdapter<String> maritalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, maritalStatusCategories);
        maritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marital_spinner.setAdapter(maritalAdapter);

        if (isToShowPlaces) {
            auto_complete_edit_text.setVisibility(View.VISIBLE);
            auto_complete_edit_text_astrotalkapi.setVisibility(View.GONE);
            pob_city.setVisibility(View.GONE);
            pob_state.setVisibility(View.GONE);
            pob_country.setVisibility(View.GONE);
        } else if (atLocationApi) {
            auto_complete_edit_text.setVisibility(View.GONE);
            pob_city.setVisibility(View.GONE);
            pob_state.setVisibility(View.GONE);
            pob_country.setVisibility(View.GONE);
            auto_complete_edit_text_astrotalkapi.setVisibility(View.VISIBLE);
        } else {
            auto_complete_edit_text_astrotalkapi.setVisibility(View.GONE);
            auto_complete_edit_text.setVisibility(View.GONE);
            pob_city.setVisibility(View.VISIBLE);
            pob_state.setVisibility(View.VISIBLE);
            pob_country.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.registrationBtn) {

            chatIntakeform();

        }

        if (id == R.id.accceptChat) {

            chatOrderInput();

        }
    }

    public void chatOrderInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.text_input_order_id, null, false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Constants.CHAT_ORDER_ID = Long.parseLong(input.getText().toString().trim());
                chatAccept();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStartTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        selectedTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        if (dobIdent.equalsIgnoreCase("own")) {
            TimeBirthET.setText(selectedTime);
            Log.d("dateOfBirth", "getParams: "+selectedTime);

        } else if (dobIdent.equalsIgnoreCase("partner")) {
            timeOfBirthPartner = selectedTime;
            Log.d("dateOfBirth", "getParams: "+timeOfBirthPartner);
            pTimeBirthET.setText(timeOfBirthPartner);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() == 0) {
            comment_count.setText(0 + "/140");
        } else {
            comment_count.setText(charSequence.length() + "/140");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    private void chatIntakeform() {
        final String Url = Constants.CHAT_INTAKE_FORM;
        Log.e("dskd", Url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("dskd", response);

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                        createChatOrderId(jsonObject1.getLong("id"));
                    } else {

                        Utilities.showToast(ChatIntakeFormActivity.this, jsonObject.getString("reason"));
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
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("userId","476914");
                params.put("name", "Loveleen");
                params.put("lastName", "Kaur");
                params.put("mobile", "9501423386");
                params.put("countrycode", "+91");
                params.put("email", "loveleen.kaur@astrotalk.com");
                params.put("dob", "15-February-1995,05:45 PM");
                params.put("gender", "Female");
                params.put("placeOfBirth", "Jalandhar");
                params.put("lat", "75.5033789");
                params.put("lon", "31.3223787");
                params.put("address", "New Santokhpura, Jalandhar");
                params.put("timezoneid", "Asia/Calcutta");
                params.put("comment","");
                params.put("userType", "ASTROTALK");
                params.put("deviceType", "ANDROID");
                params.put("partnername", "");
                params.put("partnerdob", "");
                params.put("partnertimeofbirth", "");
                params.put("partnerplaceofbirth", "");
                params.put("partnerAddress", "");
                params.put("partnerLat", "");
                params.put("partnerLon", "");
                params.put("problemarea", "Career and Business");
                params.put("maritialStatus", "Un Married");
                params.put("occupation", "Business");
                params.put("chatVersion", "v2");
                params.put("appVersionUser", "1.1.197");
                params.put("appId", Constants.APP_ID + "");
                params.put("businessId", Constants.BUSINESS_ID + "");
                return params;
            }

            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", Constants.AUTHORIZATION);
                headers.put("id", Constants.ID);
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private void createChatOrderId(long intakeformId) {
        String derIdValue = "";
        if (fixedSessionId == -1) {
            if (chatType.equalsIgnoreCase("Emergency")) {
                chatType = "EMERGENCY";
            } else {
                chatType = "NORMAL";
            }
            derIdValue = "&fixedSessionId=" + "";
        } else {
            chatType = "FIXED";
            derIdValue = "&fixedSessionId=" + fixedSessionId;
        }

        String url = null;
        try {
            url = Constants.JOIN_WAIT_LIST_V2 +
                    "?chatIntakeFormId=" + URLEncoder.encode(intakeformId + "", "UTF-8") +
                    "&userId=" + "476914" +
                    "&consultantId=" + URLEncoder.encode(universalAstrologerListModel.getId() + "", "UTF-8") +
                    "&tokenType=" + URLEncoder.encode("CHAT", "UTF-8") +
                    "&cancelLast=" + isWaitListJoined +
                    "&appVersionUser=" + "1.1.197" +
                    "&isOfferV3=" + isOfferV3 +
                    "&isPo=" + false +
                    "&appId=" + URLEncoder.encode(Constants.APP_ID + "", "UTF-8") +
                    "&businessId=" + URLEncoder.encode(Constants.BUSINESS_ID + "", "UTF-8") +
                    "&timezone=" + "Asia/Calcutta" +
                    "&chatType=" + URLEncoder.encode(chatType, "UTF-8") +
                    derIdValue;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!Constants.LIVE_MODE)
            Log.e("send join chat wait", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("dskdsdsdd", response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equalsIgnoreCase("success")) {
                        //Utilities.firebaseEventsstartServices(mFirebaseAnalytics, ChatIntakeFormActivity.this, "Chat", "Chat_Start");

                        sharedPreferences.edit().putBoolean(Constants.FIVE_MIN_TIPS, false).apply();

                        JSONObject jsonObject1 = new JSONObject(object.getString("data"));

//                        chatOrderId = jsonObject1.getLong("id");

//                        Intent intentCHat = new Intent(ChatIntakeFormActivity.this, UserAstrologerChatWindowActivity.class);
//                        intentCHat.putExtra("chatorder_id", chatOrderId);
//                        intentCHat.putExtra("astrologer_id", universalAstrologerListModel.getId());
//                        intentCHat.putExtra("astrologer_name", universalAstrologerListModel.getFirstname());
//                        startActivity(intentCHat);


//                        if (from == 2) {
//                            Intent orderHistory = new Intent(ChatIntakeFormActivity.this, ChatAstrologerlistActivity.class);
//                            orderHistory.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            orderHistory.putExtra("from", from);
//                            startActivity(orderHistory);
//                            finish();
//                        } else {
//                            finish();
//                        }
//
//
//                        HashMap<String, Object> eventProperties = new HashMap<String, Object>();//added by CleverTap Assistant
//                        eventProperties.put("Type", "Chat");//added by CleverTap Assistant
//                        if (from == 2) {
//                            eventProperties.put("Source", "Astrologer_Profile_chat");//added by CleverTap Assistant
//                        } else {
//                            eventProperties.put("Source", "Chat_list");//added by CleverTap Assistant
//                        }

                    } else {
                        Utilities.showToast(ChatIntakeFormActivity.this, object.getString("reason"));
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private void chatAccept() {

        chatOrderId = Constants.CHAT_ORDER_ID;

        String url = null;
        try {
            url = Constants.ACCEPT_CHAT +
                    "?chatOrderId=" + URLEncoder.encode(chatOrderId + "", "UTF-8")+
                    "&userId=" +Constants.ID;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equalsIgnoreCase("success")) {

                        Intent intentCHat = new Intent(ChatIntakeFormActivity.this, UserAstrologerChatWindowActivity.class);
                        intentCHat.putExtra("chatorder_id", chatOrderId);
                        intentCHat.putExtra("astrologer_id", universalAstrologerListModel.getId());
                        intentCHat.putExtra("astrologer_name", universalAstrologerListModel.getFirstname());
                        startActivity(intentCHat);

                    } else {

                        Utilities.showToast(ChatIntakeFormActivity.this, object.getString("reason"));
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
