package com.astrotalk.sdk.api.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.googleApi.GoogleAddressModel;
import com.astrotalk.sdk.api.model.UniversalAstrologerListModel;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroUtilities;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class AstroChatIntakeFormActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener {

    private final Context context = this;
    private final List<String> maritalStatusCategories = new ArrayList<>();
    private final List<String> categories = new ArrayList<>();
    public GoogleAddressModel googleAddressModel, googleAddressModelPartner, astrotalkPlaceModel, astrotalkPlacePartner;
    boolean isGcmAvailble = true;
    long language_id = 1;
    int from = 1;
    private RequestQueue requestQueue;
    private String dob = "";
    private String pdob = "";
    private TextView registrationBtn;
    private SharedPreferences sharedPreferences;
    private RadioButton radioMale, radioFemale;
    private String selectedTime = "";
    private String timeOfBirthPartner = "";
    private CheckBox partner_details_checkbox;
    private String dobIdent = "";
    private TextView comment_count;
    private EditText dobET, TimeBirthET;
    private Calendar cal;
    private int hour, minute;
    private RadioGroup radioSexGroup;
    private EditText nameET, lastnameET, phoneET, questionET, city_name, state_name, country_name;
    private RadioButton radioSexButton;
    private String gender;
    private EditText partneNnameET, pdobET, pTimeBirthET, pcity_name, pstate_name, pcountry_name;
    private Calendar DefaultStartCalender, DefaultStartCalenderFemale;
    private long user_id = -1;
    private String jwt_token = "";
    private Toolbar toolbar;
    private TextView toolbarTV;

    private final TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour = hourOfDay;
            minute = minutes;
            updateStartTime(hour, minute);
        }
    };

    private String countryCode = "";
    private TextView enter_partner_details;
    private UniversalAstrologerListModel universalAstrologerListModel;
    private Spinner marital_spinner;
    private String maritalStatus = "";
    private EditText occupationET;
    private Spinner spinner;
    private String selectedItem = "";
    private int selectedPosition = 0;
    private EditText otherET;
    private String status = "ask";
    private TextView comment_heading;
    private Boolean isToShowPlaces = false;
    private TextInputLayout pob_city, pob_state, pob_country, p_pob_city, p_pob_state, p_pob_country;
    private AppCompatAutoCompleteTextView auto_complete_edit_text_astrotalkapi;
    private AppCompatAutoCompleteTextView auto_complete_edit_text_astrotalkapi_partner;
    private long fixedSessionId = -1;
    private boolean atLocationApi = false;
    private String chatType = "NORMAL";
    private LinearLayout langaugeLL;
    private String languageID = "1";
    private boolean fromPO = false;
    private CheckBox english_checkbox, hindi_checkbox;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_activity_chat_intake_form);

        requestQueue = Volley.newRequestQueue(context);
        sharedPreferences = getSharedPreferences(AstroConstants.USER_DETAIL, MODE_PRIVATE);
        user_id = sharedPreferences.getLong(AstroConstants.USER_ID, -1);
        jwt_token = sharedPreferences.getString(AstroConstants.JWT_TOKEN, "");

        if (getIntent().hasExtra("from")) {
            from = getIntent().getIntExtra("from", 1);
        }

        if (getIntent().hasExtra("fixedSessionId")) {
            fixedSessionId = getIntent().getLongExtra("fixedSessionId", -1);
        }

        if (getIntent().hasExtra("chatType")) {
            chatType = getIntent().getStringExtra("chatType");
        }

        if (getIntent().hasExtra("status")) {
            status = getIntent().getStringExtra("status");
        }

        language_id = AstroConstants.LANGUAGE_ID;

        try {
            if (getIntent().hasExtra("isToShowPlaces")) {
                isToShowPlaces = getIntent().getExtras().getBoolean("isToShowPlaces");
            }
            if (getIntent().hasExtra("atLocationApi")) {
                atLocationApi = getIntent().getExtras().getBoolean("atLocationApi");
            }
        } catch (Exception e) {
            e.printStackTrace();
            isToShowPlaces = false;
            atLocationApi = false;
        }

        if (getIntent().hasExtra("fromPo")) {
            fromPO = getIntent().getBooleanExtra("fromPo", false);
            atLocationApi = true;
        }

        langaugeLL = findViewById(R.id.langaugeLL);

        if (fromPO) {
            langaugeLL.setVisibility(View.VISIBLE);
        } else {
            langaugeLL.setVisibility(View.GONE);
        }

        english_checkbox = findViewById(R.id.english_checkbox);
        english_checkbox.setChecked(true);
        hindi_checkbox = findViewById(R.id.hindi_checkbox);
        hindi_checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            hindi_checkbox.setSelected(!isChecked);
        });

        english_checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            english_checkbox.setSelected(!isChecked);
        });

        pob_city = findViewById(R.id.pob_city);
        pob_state = findViewById(R.id.pob_state);
        pob_country = findViewById(R.id.pob_country);
        p_pob_city = findViewById(R.id.p_pob_city);
        p_pob_state = findViewById(R.id.p_pob_state);
        p_pob_country = findViewById(R.id.p_pob_country);
        dobET = findViewById(R.id.dobET);
        dobET.setOnClickListener(this);

        DefaultStartCalender = Calendar.getInstance();
        DefaultStartCalender.set(Calendar.YEAR, 1990);
        DefaultStartCalender.set(Calendar.DATE, 1);
        DefaultStartCalender.set(Calendar.MONTH, 0);

        DefaultStartCalenderFemale = Calendar.getInstance();
        DefaultStartCalenderFemale.set(Calendar.YEAR, 1990);
        DefaultStartCalenderFemale.set(Calendar.DATE, 1);
        DefaultStartCalenderFemale.set(Calendar.MONTH, 0);

        auto_complete_edit_text_astrotalkapi = findViewById(R.id.auto_complete_edit_text_astrotalkapi);
        auto_complete_edit_text_astrotalkapi_partner = findViewById(R.id.auto_complete_edit_text_astrotalkapi_partner);

        auto_complete_edit_text_astrotalkapi.setOnClickListener(this);
        auto_complete_edit_text_astrotalkapi_partner.setOnClickListener(this);

        otherET = findViewById(R.id.otherET);
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
        spinner = findViewById(R.id.proble_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        intViews();

        getChatIntakeDetails();
    }

    private void intViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTV = (TextView) findViewById(R.id.toolbarTV);
        toolbarTV.setText(getResources().getString(R.string.at_chat_intake_form_heading));

        if (getIntent().hasExtra("astrologer_details")) {
            universalAstrologerListModel = (UniversalAstrologerListModel) getIntent().getSerializableExtra("astrologer_details");
        }

        questionET = findViewById(R.id.questionET);
        comment_heading = findViewById(R.id.comment_heading);
        comment_count = findViewById(R.id.comment_count);
        questionET.setVisibility(View.GONE);
        comment_heading.setVisibility(View.GONE);
        comment_count.setVisibility(View.GONE);
        TimeBirthET = findViewById(R.id.TimeBirthET);
        city_name = findViewById(R.id.city_name);
        state_name = findViewById(R.id.state_name);
        country_name = findViewById(R.id.country_name);
        nameET = findViewById(R.id.nameET);
        lastnameET = findViewById(R.id.lastnameET);
        phoneET = findViewById(R.id.phoneET);
        TimeBirthET.setOnClickListener(this);
        partneNnameET = findViewById(R.id.partneNnameET);
        pdobET = findViewById(R.id.pdobET);
        pTimeBirthET = findViewById(R.id.pTimeBirthET);
        pcity_name = findViewById(R.id.pcity_name);
        pstate_name = findViewById(R.id.pstate_name);
        pcountry_name = findViewById(R.id.pcountry_name);
        pdobET.setOnClickListener(this);
        pTimeBirthET.setOnClickListener(this);
        partner_details_checkbox = findViewById(R.id.partner_details_checkbox);
        partner_details_checkbox.setOnClickListener(this);
        questionET.addTextChangedListener(this);
        comment_count.setText(0 + "/140");

        cal = Calendar.getInstance();
        registrationBtn = findViewById(R.id.registrationBtn);
        registrationBtn.setOnClickListener(this);
        registrationBtn.setText(getResources().getString(R.string.at_start_chat_with) + " " + universalAstrologerListModel.getFirstname());

        radioSexGroup = findViewById(R.id.radioSex);
        radioMale = findViewById(R.id.radioMale);
        radioMale.setOnClickListener(this);
        radioFemale = findViewById(R.id.radioFemale);
        radioFemale.setOnClickListener(this);

        countryCode = "+91";

        maritalStatusCategories.add(getResources().getString(R.string.at_select_martial_status_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_single_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_married_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_divorced_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_separated_drop_down));
        maritalStatusCategories.add(getResources().getString(R.string.at_widowed_drop_down));
        marital_spinner = findViewById(R.id.marital_spinner);
        occupationET = findViewById(R.id.occupationET);
        marital_spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> maritalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maritalStatusCategories);
        maritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marital_spinner.setAdapter(maritalAdapter);

        // todo
        pob_city.setVisibility(View.GONE);
        pob_state.setVisibility(View.GONE);
        pob_country.setVisibility(View.GONE);
        auto_complete_edit_text_astrotalkapi.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dobET) {
            dobIdent = "own";
            selectDate();
        } else if (id == R.id.pdobET) {
            dobIdent = "partner";
            selectDateFemale();
        } else if (id == R.id.TimeBirthET) {
            if (dobET.getText().toString().equalsIgnoreCase("")) {
                AstroUtilities.showToast(context, getResources().getString(R.string.at_please_select_dob_first));
            } else {
                dobIdent = "own";
                startselectTime();
            }
        } else if (id == R.id.pTimeBirthET) {
            if (pdobET.getText().toString().equalsIgnoreCase("")) {
                AstroUtilities.showToast(context, getResources().getString(R.string.at_please_select_partners_dob_first));
            } else {
                dobIdent = "partner";
                startselectTime();
            }
        } else if (id == R.id.auto_complete_edit_text_astrotalkapi) {
            Intent intent = new Intent(context, AstroSearchLocationActivity.class);
            startActivityForResult(intent, 100);
        } else if (id == R.id.auto_complete_edit_text_astrotalkapi_partner) {
            Intent intent2 = new Intent(context, AstroSearchLocationActivity.class);
            startActivityForResult(intent2, 200);
        } else if (id == R.id.registrationBtn) {
            int selectedId = radioSexGroup.getCheckedRadioButtonId();
            radioSexButton = findViewById(selectedId);

            if (nameET.getText().toString().trim().equalsIgnoreCase("")) {
                AstroUtilities.showToast(this, getResources().getString(R.string.at_please_enter_name));
            } else if (countryCode.equalsIgnoreCase("")) {
                AstroUtilities.showToast(this, getResources().getString(R.string.at_please_enter_country_code));
            } else if (radioSexButton == null) {
                AstroUtilities.showToast(this, getResources().getString(R.string.at_please_select_gender));
            } else if (dobET.getText().toString().trim().equalsIgnoreCase("")) {
                AstroUtilities.showToast(this, getResources().getString(R.string.at_please_enter_date_of_birth));
            } else if (TimeBirthET.getText().toString().trim().equalsIgnoreCase("")) {
                AstroUtilities.showToast(this, getResources().getString(R.string.at_please_enter_time_of_birth));
            } else if (auto_complete_edit_text_astrotalkapi.getText().toString().trim().equalsIgnoreCase("")) {
                AstroUtilities.showToast(this, getResources().getString(R.string.at_please_enter_your_place_of_birth));
            } else if (astrotalkPlaceModel == null) {
                AstroUtilities.showToast(this, getResources().getString(R.string.at_please_enter_a_valid_place_of_birth));
            } else {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                continueFormSubmission();
            }

        } else if (id == R.id.enter_partner_details) {
            partner_details_checkbox.performClick();
        }
    }

    private void selectDateFemale() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth);
                DefaultStartCalenderFemale.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                DefaultStartCalenderFemale.set(Calendar.DATE, cal.get(Calendar.DATE));
                DefaultStartCalenderFemale.set(Calendar.MONTH, cal.get(Calendar.MONTH));
                pdob = new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());
                pdobET.setText(pdob);
            }

        }, DefaultStartCalenderFemale.get(Calendar.YEAR), DefaultStartCalenderFemale.get(Calendar.MONTH), DefaultStartCalenderFemale.get(Calendar.DAY_OF_MONTH));

        pickerDialog.getDatePicker().setSpinnersShown(true);
        pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        pickerDialog.getDatePicker().setCalendarViewShown(false);
        Window window = pickerDialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    private void continueFormSubmission() {

        chatIntakeForm();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void selectDate() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                cal.set(year, monthOfYear, dayOfMonth);
                DefaultStartCalender.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                DefaultStartCalender.set(Calendar.DATE, cal.get(Calendar.DATE));
                DefaultStartCalender.set(Calendar.MONTH, cal.get(Calendar.MONTH));
                dob = new SimpleDateFormat("dd-MMMM-yyyy").format(cal.getTime());
                dobET.setText(dob);
            }
        }, DefaultStartCalender.get(Calendar.YEAR), DefaultStartCalender.get(Calendar.MONTH), DefaultStartCalender.get(Calendar.DAY_OF_MONTH));

        pickerDialog.getDatePicker().setSpinnersShown(true);
        pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        pickerDialog.getDatePicker().setCalendarViewShown(false);
        Window window = pickerDialog.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }

    private void updateStartTime(int hours, int mins) {
        String timeSet;
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
        String minutes;
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        selectedTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        if (dobIdent.equalsIgnoreCase("own")) {
            TimeBirthET.setText(selectedTime);
            Log.d("dateOfBirth", "getParams: " + selectedTime);

        } else if (dobIdent.equalsIgnoreCase("partner")) {
            timeOfBirthPartner = selectedTime;
            Log.d("dateOfBirth", "getParams: " + timeOfBirthPartner);
            pTimeBirthET.setText(timeOfBirthPartner);
        }
    }

    public void startselectTime() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                timePickerListener, hour, minute, false);
        timePickerDialog.show();
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

    @SuppressLint("SetTextI18n")
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        if (parent.getId() == R.id.marital_spinner) {
            if (position == 0) {
                maritalStatus = "";
            } else {
                if (language_id == 1 || language_id == 2) {
                    maritalStatus = parent.getItemAtPosition(position).toString();
                } else {
                    if (position == 1) {
                        maritalStatus = "Single";
                    } else if (position == 2) {
                        maritalStatus = "Married";
                    } else if (position == 3) {
                        maritalStatus = "Divorced";
                    } else if (position == 4) {
                        maritalStatus = "Separated";
                    } else if (position == 5) {
                        maritalStatus = "Widowed";
                    }
                }
            }
        }

        if (parent.getId() == R.id.proble_spinner) {
            if (language_id == 1 || language_id == 2) {
                selectedItem = parent.getItemAtPosition(position).toString();
            } else {
                if (position == 1) {
                    selectedItem = "Career and Business";
                } else if (position == 2) {
                    selectedItem = "Marriage";
                } else if (position == 3) {
                    selectedItem = "Love and Relationship";
                } else if (position == 4) {
                    selectedItem = "Wealth and Property";
                } else if (position == 5) {
                    selectedItem = "Education";
                } else if (position == 6) {
                    selectedItem = "Legal Matters";
                } else if (position == 7) {
                    selectedItem = "Child Name Consultation";
                } else if (position == 8) {
                    selectedItem = "Business Name Consultation";
                } else if (position == 9) {
                    selectedItem = "Gem Stone Consultation";
                } else if (position == 10) {
                    selectedItem = "Commodity trading consultation";
                } else if (position == 11) {
                    selectedItem = "Match making";
                } else if (position == 12) {
                    selectedItem = "Birth Time Rectification";
                } else if (position == 13) {
                    selectedItem = "Name Correction Consultation";
                } else if (position == 14) {
                    selectedItem = "Travel Abroad Consultation";
                } else if (position == 15) {
                    selectedItem = "Remedy Consultation";
                } else if (position == 16) {
                    selectedItem = "Health Consultation";
                }
            }

            selectedPosition = position;
            if (selectedPosition == 17) {
                otherET.setVisibility(View.VISIBLE);
            } else {
                otherET.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onBackPressed() {
        chatIntakeformOnBackPressed();
        super.onBackPressed();
    }

    public void popup(String reason, String deepLink) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.at_custom_popop);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView cancel_button = dialog.findViewById(R.id.cancel_btn);
        TextView text = dialog.findViewById(R.id.text);
        text.setText(reason);
        text.setTextColor(getResources().getColor(R.color.at_black));
        TextView submit_btn = dialog.findViewById(R.id.submit_btn);

        cancel_button.setOnClickListener(v -> {
            dialog.dismiss();
        });

        submit_btn.setOnClickListener(v -> {
            dialog.dismiss();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deepLink));
            startActivity(browserIntent);
        });

        if (!isFinishing()) {
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void chatIntakeForm() {
        AstroUtilities.showLoader(context);
        final String Url = AstroConstants.CHAT_INTAKE_FORM;
        Log.e("dskd", Url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AstroUtilities.closeLoader();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                        createChatOrderId(jsonObject1.getLong("id"));

                    } else {
                        AstroUtilities.showToast(context, jsonObject.getString("reason"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, error -> AstroUtilities.closeLoader()) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();

                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                if (selectedId <= 0) {
                    gender = "";
                } else {
                    radioSexButton = findViewById(selectedId);
                    gender = radioSexButton.getText().toString();

                    if (language_id == 1 || language_id == 2) {
                        Log.e("Response", "Language Selected");
                    } else {
                        if (gender.equalsIgnoreCase(getResources().getString(R.string.at_male_intake))) {
                            gender = "Male";
                        } else {
                            gender = "Female";
                        }
                    }
                }

                params.put("userId", user_id + "");
                params.put("name", nameET.getText().toString());
                params.put("lastName", lastnameET.getText().toString());
                params.put("mobile", "");
                params.put("countrycode", countryCode);
                params.put("email", "");
                params.put("dob", dobET.getText().toString().trim() + "," + TimeBirthET.getText().toString().trim());
                Log.d("dateOfBirth", "getParams: " + dobET.getText().toString().trim() + "," + TimeBirthET.getText().toString().trim());
                params.put("gender", gender);
                String makeplaceofbitrh = city_name.getText().toString();

                if (!state_name.getText().toString().equalsIgnoreCase("")) {
                    makeplaceofbitrh = makeplaceofbitrh + ", " + state_name.getText().toString();
                }
                makeplaceofbitrh = makeplaceofbitrh + ", " + country_name.getText().toString();
                params.put("placeOfBirth", makeplaceofbitrh);
                if (isToShowPlaces) {
                    if (googleAddressModel != null && googleAddressModel.getLat() != null)
                        params.put("lat", googleAddressModel.getLat() + "");
                    else
                        params.put("lat", "");
                    if (googleAddressModel != null && googleAddressModel.getLon() != null)
                        params.put("lon", googleAddressModel.getLon() + "");
                    else
                        params.put("lon", "");
                } else if (atLocationApi) {
                    if (astrotalkPlaceModel != null && astrotalkPlaceModel.getLat() != null)
                        params.put("lat", astrotalkPlaceModel.getLat() + "");
                    else
                        params.put("lat", "");
                    if (astrotalkPlaceModel != null && astrotalkPlaceModel.getLon() != null)
                        params.put("lon", astrotalkPlaceModel.getLon() + "");
                    else
                        params.put("lon", "");

                }
                if (atLocationApi) {
                    params.put("address", auto_complete_edit_text_astrotalkapi.getText().toString().trim());
                    params.put("placeOfBirth", auto_complete_edit_text_astrotalkapi.getText().toString().trim());

                } else {
                    params.put("address", makeplaceofbitrh);
                }
                params.put("timezoneid", AstroConstants.TIME_ZONE);
                params.put("comment", "");
                params.put("userType", "ASTROTALK");
                params.put("deviceType", "ANDROID");

                if (partner_details_checkbox.isChecked()) {
                    params.put("partnername", "");
                    params.put("partnerdob", "");
                    params.put("partnertimeofbirth", "");
                    String pplaceofbitrh = "";
                    if (!pcity_name.getText().toString().equalsIgnoreCase("")) {
                        pplaceofbitrh = pcity_name.getText().toString();
                    }
                    if (!pstate_name.getText().toString().equalsIgnoreCase("")) {
                        pplaceofbitrh = pplaceofbitrh + ", " + pstate_name.getText().toString();
                    }
                    if (!pcountry_name.getText().toString().equalsIgnoreCase("")) {
                        pplaceofbitrh = pplaceofbitrh + ", " + pcountry_name.getText().toString();
                    }
                    params.put("partnerplaceofbirth", "");
                    if (atLocationApi) {


                        params.put("partnerplaceofbirth", "");
                        params.put("partnerAddress", "");
                        if (astrotalkPlacePartner != null && astrotalkPlacePartner.getLon() != null)
                            params.put("partnerLon", "");

                        else
                            params.put("partnerLon", "");
                        if (astrotalkPlacePartner != null && astrotalkPlacePartner.getLat() != null)
                            params.put("partnerLat", "");
                        else
                            params.put("partnerLat", "");

                    } else {
                        params.put("partnerAddress", "");
                    }
                } else {
                    params.put("partnername", "");
                    params.put("partnerdob", "");
                    params.put("partnertimeofbirth", "");
                    params.put("partnerplaceofbirth", "");
                    params.put("partnerAddress", "");
                    params.put("partnerLat", "");
                    params.put("partnerLon", "");
                }
                if (selectedPosition == 0) {
                    selectedItem = "";
                } else if (selectedPosition == 17) {
                    selectedItem = otherET.getText().toString();
                }
                params.put("problemarea", selectedItem);
                params.put("maritialStatus", maritalStatus);
                params.put("occupation", occupationET.getText().toString().trim());
                params.put("chatVersion", "v2");
                params.put("appVersionUser", AstroConstants.SDK_VERSION);
                params.put("appId", AstroConstants.APP_ID + "");
                params.put("businessId", AstroConstants.BUSINESS_ID + "");
                return params;
            }

            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", jwt_token);
                headers.put("id", user_id + "");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(AstroConstants.VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private void createChatOrderId(long intakeformId) {
        String derIdValue;
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
        AstroUtilities.showLoader(context);
        try {
            url = AstroConstants.JOIN_WAIT_LIST_V2 +
                    "?chatIntakeFormId=" + URLEncoder.encode(intakeformId + "", "UTF-8") +
                    "&userId=" + URLEncoder.encode(user_id + "", "UTF-8") +
                    "&consultantId=" + URLEncoder.encode(universalAstrologerListModel.getId() + "", "UTF-8") +
                    "&tokenType=" + URLEncoder.encode("CHAT", "UTF-8") +
                    "&appVersionUser=" + AstroConstants.SDK_VERSION +
                    "&appId=" + URLEncoder.encode(AstroConstants.APP_ID + "", "UTF-8") +
                    "&businessId=" + URLEncoder.encode(AstroConstants.BUSINESS_ID + "", "UTF-8") +
                    "&timezone=" + URLEncoder.encode(AstroConstants.TIME_ZONE, "UTF-8") +
                    "&chatType=" + URLEncoder.encode(chatType, "UTF-8") +
                    "&cancelLast=" + false +
                    derIdValue;

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AstroUtilities.closeLoader();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equalsIgnoreCase("success")) {
                        JSONObject dataobject = new JSONObject(object.getString("data"));

                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();

//                        Intent orderHistory = new Intent(context, AstroChatAstrologerListActivity.class);
//                        if (from == 2) {
//                            orderHistory.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            orderHistory.putExtra("from", from);
//                            orderHistory.putExtra("id", dataobject.getLong("id"));
//                            startActivity(orderHistory);
//                            finish();
//                        } else {
//                            orderHistory.putExtra("id", dataobject.getLong("id"));
//                            orderHistory.putExtra("from", 2);
//                            startActivity(orderHistory);
//                            finishAffinity();
//                        }
                    } else {
                        if(object.getString("reason").equals("Insufficient Balance")) {
                            // todo loveleen
//                            AstroUtilities.showToast(context, object.getString("reason"));
                            popup(object.getString("message"), object.getString("deepLink"));
                        }
                        else {
                            AstroUtilities.showToast(context, object.getString("reason"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    AstroUtilities.closeLoader();
                }
            }
        }, error -> AstroUtilities.closeLoader()) {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Authorization", jwt_token);
                headers.put("id", user_id + "");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(AstroConstants.VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void getChatIntakeDetails() {
        AstroUtilities.showLoader(context);
        String url;
        url = AstroConstants.GET_LAST_INTAKE_RECORD +
                "?userId=" + user_id +
                "&type=" + "CHAT";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AstroUtilities.closeLoader();
                try {
                    Log.e("report details", response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("data"));
                        if (jsonObject1.has("name") && !jsonObject1.isNull("name")) {
                            if (jsonObject1.getString("name").equalsIgnoreCase("")) {
                                Log.e("Response", "No name is present");
                            } else {
                                nameET.setText(jsonObject1.getString("name"));
                            }
                        }

                        if (jsonObject1.has("lastName") && !jsonObject1.isNull("lastName")) {
                            if (jsonObject1.getString("lastName").equalsIgnoreCase("")) {
                                Log.e("Response", "No last name is present");
                            } else {
                                lastnameET.setText(jsonObject1.getString("lastName"));
                            }
                        } else {
                            lastnameET.setText("");
                        }

                        if (jsonObject1.has("mobile") && !jsonObject1.isNull("mobile")) {
                            if (jsonObject1.getString("mobile").equalsIgnoreCase("")) {
                                Log.e("Response", "No number is present");
                            } else {
                                phoneET.setText(jsonObject1.getString("mobile"));
                            }
                        }

                        if (jsonObject1.has("gender") && !jsonObject1.isNull("gender")) {
                            gender = jsonObject1.getString("gender");
                        } else {
                            gender = "";
                        }

                        if (gender.equalsIgnoreCase("male")) {
                            radioMale.performClick();
                        } else if (gender.equalsIgnoreCase("female")) {
                            radioFemale.performClick();
                        }

                        List<String> dobarray = Arrays.asList(jsonObject1.getString("dob").split(","));
                        if (dobarray.size() > 0) {
                            dobET.setText(dobarray.get(0));

                            DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
                            Date date;// converting String to date
                            try {
                                date = df.parse(dobET.getText().toString());
                                DefaultStartCalender = Calendar.getInstance();
                                DefaultStartCalender.setTime(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (dobarray.size() == 2) {
                            TimeBirthET.setText(dobarray.get(1));
                            Log.d("date_time", "getParams: " + dobarray.get(1));
                        }

                        List<String> placebirtharray = Arrays.asList(jsonObject1.getString("placeOfBirth").split(","));
                        if (placebirtharray.size() > 2) {
                            city_name.setText(placebirtharray.get(0).trim());
                            state_name.setText(placebirtharray.get(1).trim());
                            country_name.setText(placebirtharray.get(2).trim());
                        } else if (placebirtharray.size() == 2) {
                            city_name.setText(placebirtharray.get(0).trim());
                            country_name.setText(placebirtharray.get(1).trim());
                        } else if (placebirtharray.size() == 1) {
                            city_name.setText(placebirtharray.get(0).trim());

                        }
                        if (jsonObject1.has("partnername") && !jsonObject1.isNull("partnername")) {
                            if (jsonObject1.getString("partnername").equalsIgnoreCase("")) {
                                partneNnameET.setText("");
                            } else {
                                partneNnameET.setText(jsonObject1.getString("partnername"));
                            }
                        } else {
                            partneNnameET.setText("");
                        }

                        if (jsonObject1.has("partnerdob") && !jsonObject1.isNull("partnerdob")) {
                            if (jsonObject1.getString("partnerdob").equalsIgnoreCase("")) {
                                Log.e("Response", "Partner Dob");
                            } else {
                                pdobET.setText(jsonObject1.getString("partnerdob"));
                                DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
                                Date date;
                                try {
                                    date = df.parse(jsonObject1.getString("partnerdob"));
                                    DefaultStartCalenderFemale = Calendar.getInstance();
                                    DefaultStartCalenderFemale.setTime(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        } else {
                            pdobET.setText("");
                        }

                        if (jsonObject1.has("partnertimeofbirth") && !jsonObject1.isNull("partnertimeofbirth")) {
                            if (jsonObject1.getString("partnertimeofbirth").equalsIgnoreCase("")) {
                                Log.e("Response", "Empty Block");
                            } else {
                                pTimeBirthET.setText(jsonObject1.getString("partnertimeofbirth"));
                            }
                        }

                        List<String> pplacebirtharray = Arrays.asList(jsonObject1.getString("partnerplaceofbirth").split(","));
                        if (pplacebirtharray.size() > 2) {
                            pcity_name.setText(pplacebirtharray.get(0));
                            pstate_name.setText(pplacebirtharray.get(1));
                            pcountry_name.setText(pplacebirtharray.get(2));
                        } else if (pplacebirtharray.size() == 2) {
                            pcity_name.setText(pplacebirtharray.get(0));
                            pcountry_name.setText(pplacebirtharray.get(1));
                        } else if (pplacebirtharray.size() == 1) {
                            pcity_name.setText(pplacebirtharray.get(0));
                        }

                        if (jsonObject1.has("comment") && !jsonObject1.isNull("comment")) {
                            questionET.setText(jsonObject1.getString("comment"));
                        }
                        if (jsonObject1.has("occupation") && !jsonObject1.isNull("occupation")) {
                            occupationET.setText(jsonObject1.getString("occupation"));
                        }

                        if (jsonObject1.has("maritialStatus") && !jsonObject1.isNull("maritialStatus")) {
                            maritalStatus = jsonObject1.getString("maritialStatus");
                        }

                        if (maritalStatus.equalsIgnoreCase("")) {
                            marital_spinner.setSelection(0);
                        } else if (maritalStatus.equalsIgnoreCase(getResources().getString(R.string.at_single_drop_down))) {
                            marital_spinner.setSelection(1);
                        } else if (maritalStatus.equalsIgnoreCase(getResources().getString(R.string.at_married_drop_down))) {
                            marital_spinner.setSelection(2);
                        } else if (maritalStatus.equalsIgnoreCase(getResources().getString(R.string.at_divorced_drop_down))) {
                            marital_spinner.setSelection(3);
                        } else if (maritalStatus.equalsIgnoreCase(getResources().getString(R.string.at_separated_drop_down))) {
                            marital_spinner.setSelection(4);
                        } else if (maritalStatus.equalsIgnoreCase(getResources().getString(R.string.at_widowed_drop_down))) {
                            marital_spinner.setSelection(5);
                        }

                        if (jsonObject1.has("problemarea") && !jsonObject1.isNull("problemarea")) {
                            selectedItem = jsonObject1.getString("problemarea");
                        }

                        if (selectedItem.equalsIgnoreCase("")) {
                            spinner.setSelection(0);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_career_and_business_drop_down))) {
                            spinner.setSelection(1);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_marriage_drop_down))) {
                            spinner.setSelection(2);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_love_and_relationship_drop_down))) {
                            spinner.setSelection(3);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_wealth_and_property_drop_down))) {
                            spinner.setSelection(4);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_education_drop_down))) {
                            spinner.setSelection(5);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_legal_matters_drop_down))) {
                            spinner.setSelection(6);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_child_name_consultation_drop_down))) {
                            spinner.setSelection(7);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_business_name_consultation_drop_down))) {
                            spinner.setSelection(8);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_gem_stone_consultation_drop_down))) {
                            spinner.setSelection(9);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_commodity_trading_consultation_drop_down))) {
                            spinner.setSelection(10);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_match_making_drop_down))) {
                            spinner.setSelection(11);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_birth_time_rectification_drop_down))) {
                            spinner.setSelection(12);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_name_correction_consultation_drop_down))) {
                            spinner.setSelection(13);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_travel_abroad_consultation_drop_down))) {
                            spinner.setSelection(14);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_remedy_consultation_drop_down))) {
                            spinner.setSelection(15);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_health_consultation_drop_down))) {
                            spinner.setSelection(16);
                        } else if (selectedItem.equalsIgnoreCase(getResources().getString(R.string.at_others_drop_down))) {
                            spinner.setSelection(17);
                        } else {
                            otherET.setText(selectedItem);
                            spinner.setSelection(17);
                        }

                        double lat = 0;
                        double log = 0;
                        double latpartner = 0;
                        double logpartner = 0;
                        if (jsonObject1.has("lat") && !jsonObject1.isNull("lat") && jsonObject1.has("lon") && !jsonObject1.isNull("lon")) {
                            if (jsonObject1.getDouble("lat") != 0 && jsonObject1.getDouble("lon") != 0) {
                                lat = jsonObject1.getDouble("lat");
                                log = jsonObject1.getDouble("lon");
                            }
                        }

                        if (lat != 0 && log != 0) {
                            if (jsonObject1.has("address") && !jsonObject1.isNull("address")) {
                                auto_complete_edit_text_astrotalkapi.setText(jsonObject1.getString("address"));
                                astrotalkPlaceModel = new GoogleAddressModel();
                                astrotalkPlaceModel.setLat(jsonObject1.getDouble("lat"));
                                astrotalkPlaceModel.setLon(jsonObject1.getDouble("lon"));
                            }
                        }

                        if (jsonObject1.has("partnerLat") && !jsonObject1.isNull("partnerLat")) {
                            latpartner = jsonObject1.getDouble("partnerLat");
                        }
                        if (jsonObject1.has("partnerLon") && !jsonObject1.isNull("partnerLon")) {
                            logpartner = jsonObject1.getDouble("partnerLon");
                        }

                        if (latpartner != 0 && logpartner != 0) {
                            if (jsonObject1.has("partnerAddress") && !jsonObject1.isNull("partnerAddress")) {
                                auto_complete_edit_text_astrotalkapi_partner.setText(jsonObject1.getString("partnerAddress"));
                                astrotalkPlacePartner = new GoogleAddressModel();
                                astrotalkPlacePartner.setLat(jsonObject1.getDouble("partnerLat"));
                                astrotalkPlacePartner.setLon(jsonObject1.getDouble("partnerLon"));
                            }
                        }

                    } else {
                        AstroUtilities.closeLoader();
                        AstroUtilities.showToast(context, "Something went wrong");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    AstroUtilities.closeLoader();
                }
            }
        }, error -> AstroUtilities.closeLoader()) {
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

    private void chatIntakeformOnBackPressed() {
        final String Url = AstroConstants.CHAT_INTAKE_FORM;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, response -> Log.e("Response", "Success"),
                error -> Log.e("Response", "Error")) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                if (selectedId <= 0) {
                    gender = "";
                } else {
                    radioSexButton = findViewById(selectedId);

                    radioSexButton = findViewById(selectedId);
                    gender = radioSexButton.getText().toString();

                    if (language_id == 1 || language_id == 2) {
                        Log.e("Response", "Language Selected");
                    } else {
                        if (gender.equalsIgnoreCase(getResources().getString(R.string.at_male_intake))) {
                            gender = "Male";
                        } else {
                            gender = "Female";
                        }
                    }
                }

                params.put("userId", user_id + "");
                params.put("name", nameET.getText().toString());
                params.put("lastName", lastnameET.getText().toString());
                params.put("mobile", phoneET.getText().toString());
                params.put("countrycode", countryCode);
                params.put("email", "");
                Log.d("date_time", "getParams: " + dobET.getText().toString().trim() + "," + TimeBirthET.getText().toString().trim());
                params.put("dob", dobET.getText().toString().trim() + "," + TimeBirthET.getText().toString().trim());
                params.put("gender", gender);
                String makeplaceofbitrh = city_name.getText().toString();

                if (!state_name.getText().toString().equalsIgnoreCase("")) {
                    makeplaceofbitrh = makeplaceofbitrh + "," + state_name.getText().toString();
                }
                makeplaceofbitrh = makeplaceofbitrh + "," + country_name.getText().toString();
                params.put("placeOfBirth", makeplaceofbitrh);
                if (isToShowPlaces) {
                    if (googleAddressModel != null && googleAddressModel.getLat() != null)
                        params.put("lat", googleAddressModel.getLat() + "");
                    else
                        params.put("lat", "");
                    if (googleAddressModel != null && googleAddressModel.getLon() != null)
                        params.put("lon", googleAddressModel.getLon() + "");
                    else
                        params.put("lon", "");
                } else if (atLocationApi) {

                    if (astrotalkPlaceModel != null && astrotalkPlaceModel.getLat() != null)
                        params.put("lat", astrotalkPlaceModel.getLat() + "");
                    else
                        params.put("lat", "");
                    if (astrotalkPlaceModel != null && astrotalkPlaceModel.getLon() != null)
                        params.put("lon", astrotalkPlaceModel.getLon() + "");
                    else
                        params.put("lon", "");
                }

                if (atLocationApi) {
                    params.put("placeOfBirth", auto_complete_edit_text_astrotalkapi.getText().toString().trim());
                    params.put("address", auto_complete_edit_text_astrotalkapi.getText().toString().trim());
                } else {
                    params.put("address", makeplaceofbitrh);
                }
                params.put("timezoneid", AstroConstants.TIME_ZONE);
                params.put("comment", "");
                params.put("userType", "ASTROTALK");
                params.put("deviceType", "ANDROID");

                if (partner_details_checkbox.isChecked()) {
                    params.put("partnername", "");
                    params.put("partnerdob", "");
                    params.put("partnertimeofbirth", "");
                    String pplaceofbitrh = "";
                    if (!pcity_name.getText().toString().equalsIgnoreCase("")) {
                        pplaceofbitrh = pcity_name.getText().toString();
                    }
                    if (!pstate_name.getText().toString().equalsIgnoreCase("")) {
                        pplaceofbitrh = pplaceofbitrh + "," + pstate_name.getText().toString();
                    }
                    if (!pcountry_name.getText().toString().equalsIgnoreCase("")) {
                        pplaceofbitrh = pplaceofbitrh + "," + pcountry_name.getText().toString();
                    }
                    params.put("partnerplaceofbirth", "");
                    if (atLocationApi) {

                        params.put("partnerplaceofbirth", "");
                        params.put("partnerAddress", "");
                        if (astrotalkPlacePartner != null && astrotalkPlacePartner.getLon() != null)
                            params.put("partnerLon", "");
                        else
                            params.put("partnerLon", "");
                        if (astrotalkPlacePartner != null && astrotalkPlacePartner.getLat() != null)
                            params.put("partnerLat", "");
                        else
                            params.put("partnerLat", "");

                    } else {
                        params.put("partnerAddress", "");
                    }
                } else {
                    params.put("partnername", "");
                    params.put("partnerdob", "");
                    params.put("partnertimeofbirth", "");
                    params.put("partnerplaceofbirth", "");
                    params.put("partnerAddress", "");
                    params.put("partnerLat", "");
                    params.put("partnerLon", "");
                }
                if (selectedPosition == 0) {
                    selectedItem = "";
                } else if (selectedPosition == 17) {
                    selectedItem = otherET.getText().toString();
                }
                params.put("problemarea", selectedItem);
                params.put("maritialStatus", maritalStatus);
                params.put("occupation", occupationET.getText().toString().trim());
                params.put("chatVersion", "v2");
                params.put("appVersionUser", AstroConstants.SDK_VERSION);
                params.put("appId", AstroConstants.APP_ID + "");
                params.put("businessId", AstroConstants.BUSINESS_ID + "");
                return params;
            }

            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Authorization", jwt_token);
                headers.put("id", user_id + "");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(AstroConstants.VOLLEY_TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null) {
                astrotalkPlaceModel = (GoogleAddressModel) data.getSerializableExtra("location_detail");
                if (astrotalkPlaceModel.getState().isEmpty()) {
                    auto_complete_edit_text_astrotalkapi.setText(astrotalkPlaceModel.getCity() + ", " + astrotalkPlaceModel.getCountry());
                } else {
                    auto_complete_edit_text_astrotalkapi.setText(astrotalkPlaceModel.getCity() + ", " + astrotalkPlaceModel.getState() + ", " + astrotalkPlaceModel.getCountry());
                }
            }
        } else if (requestCode == 200) {
            if (data != null) {
                astrotalkPlacePartner = (GoogleAddressModel) data.getSerializableExtra("location_detail");
                if (astrotalkPlacePartner.getState().isEmpty()) {
                    auto_complete_edit_text_astrotalkapi_partner.setText(astrotalkPlacePartner.getCity() + ", " + astrotalkPlacePartner.getCountry());
                } else {
                    auto_complete_edit_text_astrotalkapi_partner.setText(astrotalkPlacePartner.getCity() + ", " + astrotalkPlacePartner.getState() + ", " + astrotalkPlacePartner.getCountry());
                }
            }
        }
    }
}