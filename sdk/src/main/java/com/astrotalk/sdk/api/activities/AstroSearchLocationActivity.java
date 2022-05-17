package com.astrotalk.sdk.api.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.adapter.AstroNewSearchPlaceAdapter;
import com.astrotalk.sdk.api.googleApi.GoogleAddressModel;
import com.astrotalk.sdk.api.utils.AstroConstants;
import com.astrotalk.sdk.api.utils.AstroDividerItemDecoration;
import com.astrotalk.sdk.api.utils.AstroWrapContentLinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;

public class AstroSearchLocationActivity extends AppCompatActivity implements AstroNewSearchPlaceAdapter.LocationItemClick {

    private final Context context = this;
    ArrayList<GoogleAddressModel> userAddressArrayList = new ArrayList<>();
    private TextView toolbarTV, tv_message;
    private LinearLayout no_result;
    private Toolbar toolbar;
    private EditText search;
    private RecyclerView recycler_view;
    private AstroWrapContentLinearLayoutManager linearLayoutManager;
    private AstroNewSearchPlaceAdapter locationAdapter;
    private ImageView imv_cancel;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SharedPreferences sharedPreferences;
    private TextView searchsecound;
    private RequestQueue requestQueue;
    private long user_id = -1;
    private String jwt_token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.at_activity_search_location);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTV = (TextView) findViewById(R.id.toolbarTV);
        toolbarTV.setText(getResources().getString(R.string.at_kundli_place_of_birth));

        requestQueue = Volley.newRequestQueue(context);
        no_result = findViewById(R.id.no_result);
        searchsecound = findViewById(R.id.searchsecound);
        sharedPreferences = getSharedPreferences(AstroConstants.USER_DETAIL, MODE_PRIVATE);
        user_id = sharedPreferences.getLong(AstroConstants.USER_ID, -1);
        jwt_token = sharedPreferences.getString(AstroConstants.JWT_TOKEN, "");
        search = findViewById(R.id.search);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
        recycler_view = findViewById(R.id.recycler_view);

        imv_cancel = findViewById(R.id.imv_cancel);
        imv_cancel.setOnClickListener(v -> search.getText().clear());

        linearLayoutManager = new AstroWrapContentLinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(linearLayoutManager);
        locationAdapter = new AstroNewSearchPlaceAdapter(context, userAddressArrayList, this);
        recycler_view.setAdapter(locationAdapter);


        if (!this.isDestroyed()) {
            ((SimpleItemAnimator) Objects.requireNonNull(recycler_view.getItemAnimator())).setSupportsChangeAnimations(false);
        }
        recycler_view.setItemAnimator(null);

        Drawable drawable = getResources().getDrawable(R.drawable.at_recyler_devider1);
        AstroDividerItemDecoration dividerItemDecoration = new AstroDividerItemDecoration(context, drawable, 50);
        recycler_view.addItemDecoration(dividerItemDecoration);
        search.requestFocus();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    imv_cancel.setVisibility(View.VISIBLE);
                    if (s.toString().length() > 2) {
                        requestLocation(s);
                    }
                } else {
                    imv_cancel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchsecound.setOnClickListener(v -> opensearchDialog());
    }


    private void opensearchDialog() {
        userAddressArrayList.clear();
        final Dialog location_dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        location_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        location_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        location_dialog.setContentView(R.layout.at_new_location_search_pop_up);
        location_dialog.setCancelable(true);
        location_dialog.setCanceledOnTouchOutside(true);


        ImageView imv_close = (ImageView) location_dialog.findViewById(R.id.imv_close);
        ImageView close2 = (ImageView) location_dialog.findViewById(R.id.close2);
        ImageView imv_search = (ImageView) location_dialog.findViewById(R.id.imv_search);
        EditText searchDialog = (EditText) location_dialog.findViewById(R.id.searchDialog);
        TextView no_data = (TextView) location_dialog.findViewById(R.id.no_result);
        RelativeLayout no_data_found_Rl = location_dialog.findViewById(R.id.no_data_found_Rl);
        ProgressBar progressBar = (ProgressBar) location_dialog.findViewById(R.id.progressBar);
        no_data_found_Rl.setVisibility(View.GONE);
        searchDialog.setText(search.getText().toString());

        try {
            RecyclerView recyclerLocation_new = (RecyclerView) location_dialog.findViewById(R.id.recyclerLocation_new);
            AstroWrapContentLinearLayoutManager linearLayoutManager = new AstroWrapContentLinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerLocation_new.setLayoutManager(linearLayoutManager);
            locationAdapter = new AstroNewSearchPlaceAdapter(context, userAddressArrayList, this);
            recyclerLocation_new.setAdapter(locationAdapter);
            Drawable drawable = getResources().getDrawable(R.drawable.at_recyler_devider1);
            AstroDividerItemDecoration dividerItemDecoration = new AstroDividerItemDecoration(context, drawable, 50);
            recyclerLocation_new.addItemDecoration(dividerItemDecoration);
        } catch (Exception ex) {
            Log.d("TAG", ex.toString());
        }

        close2.setOnClickListener(v -> location_dialog.dismiss());

        imv_close.setOnClickListener(v -> location_dialog.dismiss());

        imv_search.setOnClickListener(v -> {
            userAddressArrayList.clear();
            locationAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.VISIBLE);
            no_data_found_Rl.setVisibility(View.GONE);
            String url;
            url = AstroConstants.ALL_LOCATION_AUTOCOMPLETE +
                    "?q=" + searchDialog.getText().toString() +
                    "&key=" + "pk.e8a0a377dd1df522d510716ba61eaeab";
            Log.e("url", url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("response", response);
                    try {
                        progressBar.setVisibility(View.GONE);
                        JSONArray dataArray = new JSONArray(response);
                        ArrayList<GoogleAddressModel> childchatarray = new ArrayList<>();
                        GoogleAddressModel addressModel1 = new GoogleAddressModel();

                        for (int i = 0; i < dataArray.length(); i++) {
                            GoogleAddressModel addressModel = new GoogleAddressModel();
                            JSONObject jsonObject1 = dataArray.getJSONObject(i);

                            if (jsonObject1.has("type") && !jsonObject1.isNull("type")) {
                                if (jsonObject1.getString("type").equalsIgnoreCase("village")
                                        || jsonObject1.getString("type").equalsIgnoreCase("city") || jsonObject1.getString("type").equalsIgnoreCase("town")) {

                                    if (jsonObject1.has("address") && !jsonObject1.isNull("address")) {
                                        JSONObject jsonObject2 = new JSONObject(jsonObject1.getString("address"));

                                        if (jsonObject2.has("name") && !jsonObject2.isNull("name")) {
                                            addressModel.setCity(jsonObject2.getString("name"));

                                        } else {
                                            addressModel.setCity("");
                                        }

                                        if (jsonObject2.has("state") && !jsonObject2.isNull("state")) {
                                            addressModel.setState(jsonObject2.getString("state"));
                                        } else {
                                            addressModel.setState("");
                                        }

                                        if (jsonObject2.has("country") && !jsonObject2.isNull("country")) {
                                            addressModel.setCountry(jsonObject2.getString("country"));
                                        } else {
                                            addressModel.setCountry("");
                                        }

                                        if (jsonObject2.has("district") && !jsonObject2.isNull("district")) {
                                            addressModel.setDistrict(jsonObject2.getString("district"));
                                        } else {
                                            addressModel.setDistrict("");
                                        }
                                    }

                                    addressModel.setNew(true);

                                    if (jsonObject1.has("lat") && !jsonObject1.isNull("lat")) {
                                        addressModel.setLat(jsonObject1.getDouble("lat"));
                                    }

                                    if (jsonObject1.has("lon") && !jsonObject1.isNull("lon")) {
                                        addressModel.setLon(jsonObject1.getDouble("lon"));
                                    }

                                    childchatarray.add(addressModel);
                                }
                            }
                        }

                        userAddressArrayList.addAll(childchatarray);
                        locationAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        no_data.setText("Couldn't find results for " + "\"" + searchDialog.getText().toString() + "\"");
                        no_data_found_Rl.setVisibility(View.VISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    no_data.setText("Couldn't find results for " + "\"" + searchDialog.getText().toString() + "\"");
                    no_data_found_Rl.setVisibility(View.VISIBLE);

                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);

        });

        if (!isFinishing()) {
            try {
                location_dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void requestLocation(CharSequence s) {
        userAddressArrayList.clear();
        recycler_view.getRecycledViewPool().clear();
        locationAdapter.notifyDataSetChanged();
        String url;
        url = AstroConstants.ASTROTALK_PLACE_SEARCH_API +
                "?limit=" + 15 +
                "&key=" + s;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    ArrayList<GoogleAddressModel> childchatarray = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        GoogleAddressModel addressModel = new GoogleAddressModel();
                        JSONObject jsonObject1 = dataArray.getJSONObject(i);

                        if (jsonObject1.has("name") && !jsonObject1.isNull("name")) {
                            addressModel.setCity(jsonObject1.getString("name"));
                        } else {
                            addressModel.setCity("");
                        }
                        if (jsonObject1.has("state") && !jsonObject1.isNull("state")) {
                            addressModel.setState(jsonObject1.getString("state"));
                        } else {
                            addressModel.setState("");
                        }

                        addressModel.setNew(false);

                        if (jsonObject1.has("timezone") && !jsonObject1.isNull("timezone")) {
                            addressModel.setTimezone(jsonObject1.getString("timezone"));
                        } else {
                            addressModel.setTimezone("");
                        }

                        if (jsonObject1.has("countryName") && !jsonObject1.isNull("countryName")) {
                            addressModel.setCountry(jsonObject1.getString("countryName"));
                        } else {
                            addressModel.setCountry("");
                        }

                        if (jsonObject1.has("district") && !jsonObject1.isNull("district")) {
                            addressModel.setDistrict(jsonObject1.getString("district"));
                        } else {
                            addressModel.setDistrict("");
                        }

                        if (jsonObject1.has("latitude") && !jsonObject1.isNull("latitude")) {
                            addressModel.setLat(jsonObject1.getDouble("latitude"));
                        }

                        if (jsonObject1.has("longitude") && !jsonObject1.isNull("longitude")) {
                            addressModel.setLon(jsonObject1.getDouble("longitude"));
                        }
                        childchatarray.add(addressModel);
                        Log.e("array size", userAddressArrayList.size() + "");
                    }

                    userAddressArrayList.addAll(childchatarray);
                    locationAdapter.notifyDataSetChanged();

                    if (userAddressArrayList.size() == 0) {
                        no_result.setVisibility(View.VISIBLE);
                        recycler_view.setVisibility(View.GONE);
                    } else {
                        no_result.setVisibility(View.GONE);
                        recycler_view.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> { });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationItemClick(GoogleAddressModel locationResultList, int position, String address) {
        Intent intent = new Intent();
        intent.putExtra("location_detail", locationResultList);
        intent.putExtra("address", address);
        intent.putExtra("cityName", locationResultList.getName());
        intent.putExtra("stateName", locationResultList.getState());
        intent.putExtra("countryName", locationResultList.getCountry());
        intent.putExtra("timezone", locationResultList.getTimezone());
        intent.putExtra("latitude", locationResultList.getLat());
        intent.putExtra("longitude", locationResultList.getLon());
        setResult(RESULT_OK, intent);
        finish();
    }
}