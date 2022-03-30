package com.astrotalk.sdk.api.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class Constants {
    public static final String BOTTOM_INDEX = "bottom_index";
    public static final int VOLLEY_TIME_OUT = 4 * 15 * 1000;
    public static final int VOLLEY_RETRY = 1;
    public static final String GCM_ID = "gcm_id";
    public static final String SHOW_NUDGE_SCREEN = "show_nudge";
    public static final String SHOW_ASSISTANT_CHAT_DEMO = "assistant_chat_demo";
    public static final String USER_DETAIL = "userdetail";
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String TAG = "AstroYeti";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_PIC = "user_pic";
    public static final String WALLET_BALANCE = "wallet_balance";
    public static final String HAS_GST = "has_gst";
    public static final String NEW_FILTER_USE = "new_filter_use";
    public static final String SHOW_SUPPORT_CALL = "show_support_call";
    public static final String SHOW_TRUECALLER_LOGIN = "show_truecaller_login";
    public static final String SHOW_OFFER_HOMEPAGE = "show_offer_homepage";
    public static final String IS_HEAR_ABOUT_US_FILLED = "where_did_you_hear_about_us";
    public static final String USER_CREATION_TIME = "USER_CREATION_TIME";
    public static final String INTAKE_USER_EMAIL = "INTAKE_USER_EMAIL";
    public static final String USER_POB = "USER_POB";
    public static final String USER_PARTNER_POB = "USER_PARTNER_POB";
    public static final String USER_POB_LAT = "USER_POB_LAT";
    public static final String USER_POB_LON = "USER_POB_LON";
    public static final String USER_PARTNER_POB_LAT = "USER_PARTNER_POB_LAT";
    public static final String USER_PARTNER_POB_LON = "USER_PARTNER_POB_LON";
    public static final String YOU_TUBE_KEY = "you_tube_key";
    public static final String GOOGLE_PLACE_KEY = "google_place_key";
    public static final String BASE_PASSWORD = "bUM4UEZHQ3BFcjd6Z3V3Qg==";
    public static final String LAST_USED_PG = "LAST_USED_PG";
    public static final String HARDWAREID = "hard_wareid";
    public static final String IS_HIDE_FREE_POPUP = "is_show_free_popup";
    public static final String IS_HIDE_FREE_POPUP_SPICIAL = "is_show_free_popup_spicial";
    public static final String IS_HIDE_SECIAL_OFFER = "is_hide_spicial_offer";
    public static final String CHATLIST_TEXT = "chat_list_text";
    public static final String CALLLIST_TEXT = "call_list_text";
    public static final String SPECIAL_OFFER_SHOW = "is_show_special_offer";
    public static final String IS_OFFER_ON = "isofferOn";
    public static final String IS_USERHAS_ORDERS = "isUserHashOrders";
    public static final String IS_PO_ON = "is_po_on";
    public static final String IS_SHOW_LIVE_ANONYMOUS_CALL = "is_show_live_anonymous_call";
    public static final String IS_SHOW_LIVE_ANONYMOUS_CHAT = "is_show_live_anonymous_chat";
    public static final String IS_HIDE_REPORT = "is_hide_report";
    public static final String IS_SHOW_PO_APPLY_POPUP = "is_show_po_apply_popup";
    public static final String UNREAD_NUMBER_NOTIF_COUNT = "unread_notification";
    public static final String UNREAD_NOTIFICATIONtIME = "unread_notificationtime";
    public static final String FIVE_MIN_TIPS = "five_min_tips";
    public static final String IS_REMOVE_SINGLE_WAITLISTTIPS = "is_remove_single_waitlist_tips";
    public static final String WAITLIST_SHOW_HIDE = "waitlistshowHIde";
    public static final String PO_START_TIME = "po_start_time";
    public static final String COUNTRY_CODE_FROM_IP = "country_code_from_ip";
    public static String IS_WELCOME_MESSAGE_SHOW = "is_welcome_message_show";
    public static final String IS_OTHER_NOTIFICATION_ON = "is_other_notification_on";
    public static final String ASSISTANT_CROSS_BUTTON_CLICKED = "assistant_cross_button_clicked";
    public static final String DIRE = "imageDir";
    public static final String DIRE_BANNER = "imageDir_banner";
    public static final String DIRE_PAYMENTICON = "imageDir_ICON";
    public static final String CURRENCY_SYMBOL = "symbol";
    public static final String SUPPORT_LAYER_HIDE = "SUPPORT_LAYER_HIDE";
    public static long CHAT_LAYER_ISSUE_WITH_ORDER_ID = 404l;
    public static final String VERIFIED_MOBILE_SIDE_MENU = "VERIFIED_MOBILE_SIDE_MENU";
    public static final String VERIFIED_MOBILE_COUNTRYCODE_SIDE_MENU = "VERIFIED_MOBILE_COUNTRYCODE_SIDE_MENU";
    public static final String OFFER_TEXT_LIST = "offer_textList";
    public static final String USER_RECENT_ACTIVITY = "user_recent_activity";
    public static final String REFERRAL_TEMPLATE_ID = "Xcc6";
    public static final String ASTROLOGER_SHARE_TEMPLATE_ID = "6H62";
    public static final String IS_REFERRAL = "isReferral";
    public static final String REFERENCE_ID = "reference_id";
    public static final String STORED_REFERENCE_ID = "stored_reference_id";
    public static final String ASTROLOGER_ID = "stored_astrologer_id";
    public static final String ASTROLOGER_NAME = "stored_astrologer_name";
    public static final String IS_REFERRAL_BY_ANYONE = "is_referral_by_anyone";
    public static final String IS_REFERRAL_SHARE = "is_referral_share";
    public static final String SHOW_BOTTOM_NAV = "show_bottom_nav";
    public static final String SHOW_FLOATING_BUTTON = "show_floating_button_global";
    public static final String SHOW_PO = "show_po";
    public static final String SHOW_EXTENDED_FLOATING_BUTTON = "show_floating_button";
    public static final String SHOW_EXTENDED_FLOATING_BUTTON_FREE_ACTIVITY = "show_floating_button_free";

    public static final String SHOW_EXTENDED_FLOATING_BUTTON_HOROSCOPE_ACTIVITY = "show_floating_button_horoscope";
    public static final String SHOW_EXTENDED_FLOATING_BUTTON_MATCH_MAKING_ACTIVITY = "show_floating_button_horoscope";

    public static final String FEEDBACK_TYPE_RATING = "RATING";
    public static final String FEEDBACK_TYPE_CEO_FEEDBACK = "CEO_FEEDBACK";
    public static final String SHARE_KUNDLI_URL = "https://astrotalk.com/kundali-report/";

    public static final String MATCH_MAKING_DIALOG_CANCEL_COUNT = "match_making_cancel_count";
    public static final String MATCH_MAKING_CLICK_ON_SHARE_BUTTON = "match_making_share_with_friends";


    public static final String SHOW_NAME = "show_name";

    public static final String KUNDLI_LIST = "kundli_list";

    public static final String CONVERSION_FACTOR = "conversionFactor";
    public static final String ISO_CODE = "isoCode";
    public static final String MULTIPLE_CURRENCY_ENABLED = "multipleCurrencyEnabled";
    //  public static boolean IS_VOIP_VIDEO_CALL_INPROGRESS = false;

    //    live
    public static String PO_IMAGE_URL = "ecf54c50-43e8-47ab-bf9c-934849b41b76.jpg";
    public static final String GO_TO_NEXT = "next";
    public static final String GOING_TO = "going_to";
    public static final String GO_TO_PREVIOUS = "previous";


//    live

    public static String PAYTM_MID = "CODEYE84459150770958";
    public static String PAYTM_MID_DEV = "CODEYE70146993422767";
    public static String PAYTM_INDUSTRY_TYPE_ID = "Retail109";
    public static String PAYTM_INDUSTRY_TYPE_ID_DEV = "Retail";
    public static String PAYTM_CHANNEL_ID = "WAP";
    public static String PAYTM_WEBSITE = "WEBPROD";
    public static String PAYTM_WEBSITE_DEV = "APPSTAGING";
    public static String PAYTM_CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
    public static String PAYTM_CALLBACK_URL_DEV = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";

    /**
     * PAYTM Dev Wallet credentials -
     * Mobile number - 7777777777
     * Password - Paytm12345
     * OTP - 489871
     */

    //    live
  /*  public static String MID = "CODEYE84459150770958";
    public static String INDUSTRY_TYPE_ID = "Retail109";
    public static String CHANNLE_ID = "WAP";
    public static String WEBSITE = "WEBPROD";
    public static String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=";
*/

    //test
    public static String MID = "aGuuxv80528402551811";

    public static String INDUSTRY_TYPE_ID = "Retail";
    public static String CHANNLE_ID = "WAP";
    public static final String SUPORT_USER_CHATID = "supprt_user_chat_id";
    public static String WEBSITE = "WEBSTAGING";
    public static String CALLBACK_URL = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=";

    public static final String INTAKE_USER_NAME = "intake_user_name";
    public static final String INTAKE_USER_LAST_NAME = "intake_user_last_name";
    public static final String INTAKE_USER_CODE = "intake_user_code";
    public static final String DEFAULT_CODE = "default_code";
    public static final String INTAKE_USER_PHONE = "intake_user_phone";
    public static final String INTAKE_USER_GENDER = "intake_user_gender";
    public static final String INTAKE_USER_DOB = "intake_user_dob";
    public static final String INTAKE_USER_TOB = "intake_user_tob";
    public static final String INTAKE_USER_CITY = "intake_user_city";
    public static final String INTAKE_USER_STATE = "intake_user_state";
    public static final String INTAKE_USER_COUNTRY = "intake_user_country";
    public static final String INTAKE_USER_MARITALSTATUS = "intake_user_maritalstatus";
    public static final String INTAKE_USER_OCCUPATION = "intake_user_occupation";
    public static final String INTAKE_USER_PROBLEM_AREA = "intake_user_problem_area";
    public static final String INTAKE_PROBLEM__POSITION = "intake_user_problem_position";
    public static final String INTAKE_PARTNER_NAME = "intake_partner_name";
    public static final String INTAKE_PARTNER_DOB = "intake_partner_dob";
    public static final String INTAKE_PARTNER_TOB = "intake_partner_tob";
    public static final String INTAKE_PARTNER_CITY = "intake_partner_city";
    public static final String INTAKE_PARTNER_STATE = "intake_partner_state";
    public static final String INTAKE_PARTNER_COUNTRY = "intake_partner_country";
    public static final String INTAKE_QUESTION = "intake_question";
    public static final String CHAT_IMAGES_DIRECTORY = "/AstroTalk/chat_images/";
    public static final String USD_RATE = "use_rate";
    public static final String USER_TIME_ZONE = "user_time_zone";
    public static final String VERSION_NAME = "user_app_version";
    public static final String FOREIGN_APPOINTMENT_PRICE_HOMESCREEN = "foregin_appointment_price_homescreen";
    public static final String COMPLETE = "COMPLETED";
    public static String USER_AUTH_TOKEN = "user_auth_token";
    public static final String USER_NAME = "user_name";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String COUPON_NAME = "coupon_name";
    public static final String VERIFIED_NUMBER = "verified_number";
    public static final String VERIFIED_COUNTY_CODE = "verified_ncounty_code";
    public static final String USER_LAST_NAME = "user_last_name";
    public static final String REPORT_INDIA_PRICE = "report_india_price";
    public static final String REPORT_OUTSIDE_PRICE = "report_outside_price";
    public static final String CALL_INDIA_PRICE = "call_india_price";
    public static final String CALL_OUTSIDE_PRICE = "call_outside_price";
    public static final String CHAT_INDIA_PRICE = "chat_india_price";
    public static final String CHAT_OUTSIDE_PRICE = "chat_outside_price";
    public static final String IS_HOROSCOPE_AVAILABLE = "is_horoscope_avalble";
    public static final String SIGN = "sign";
    public static final String SIGN2 = "sign2";
    public static final String SIGN_TIME_INTERVAL = "sign_time_interval";
    public static final String LANGUAGE = "language";
    public static final String LANGUAGE_ID = "language_id";
    public static final String COMMENT_SHOW = "comment_show";
    public static final String IS_LANGUAGE_POPUP = "is_show_language";
    public static final String IS_SHOW_GREENTICK_POPUP = "is_show_greentck_popup";
    public static final String IS_CHAT_CONTINUE_ENABLE = "is_chat_continue_enable";
    public static final String IS_KUNDLI_RATING_DIALOG_SHOW = "is_kundli_rating_dialog_show";
    public static final String RATING_DIALOG_SHOW_BACKEND_STATUS = "rating_dialog_show_backend_status";
    public static final String IS_RATING_POPUP_ENABLE = "is_rating_popup_enable_from_utils";
    public static final String ASSISTANT_CHAT_DEFAULT_MESSAGE = "chat_assistant_default_message";
    public static final String LIKE_MESSGES = "likemessges";
    public static final String IS_NIGHT_MODE_ON = "night_mode";
    public static final String KUNDLI_DETAIL_EXIST = "kundli_detail_exist";
    public static final String IS_SCREEN_SIZE_BIGGER = "is_screen_bigger";
    public static String HOROSCOPE_DAILY_NOTIFICATION_TIME = "horoscope_daily_notification_time";

    /// business and app id

    public static int BUSINESS_ID = 1;
    public static int APP_ID = 1;
    public static int CONSULTANT_TYPE_ID = 1;

    /// service id
    public static int QUERY_SERVICEID = 1;
    public static int REPORT_SERVICEID = 2;
    public static int CALL_SERVICEID = 3;
    public static int CHAT_SERVICEID = 4;
    public static int PRODUCT_SERVICEID = 5;

    //CONSULTANT TYPE
    public static int ASTROLOGER = 1;
    public static int COUNSELLOR = 2;
    public static int QUERY_IDEN = 1;
    public static int SECOND_OPINION_QUERY_IDEN = 2;
    public static int REPORT_IDEN = 3;
    public static String IMAGE_DOMAIN = "https://astrotalk.s3.amazonaws.com/consultant_pic/";
    public static final String ASTROLOGER_PROFILE_PREFIX = "https://astrotalk.com/best-astrologer/";
    public static final String ALL_IMAGE_PREFIX = "https://astrotalk.s3.amazonaws.com/images/";
    public static String WEB_DOMAIN = "https://astrotalk.com/";

    //================ Agora=================//


    public static final String PREF_NAME = "io.agora.openlive";
    public static final int DEFAULT_PROFILE_IDX = 1;
    public static final String PREF_RESOLUTION_IDX = "pref_profile_index";
    public static final String PREF_ENABLE_STATS = "pref_enable_stats";
    public static final String PREF_MIRROR_LOCAL = "pref_mirror_local";
    public static final String PREF_MIRROR_REMOTE = "pref_mirror_remote";
    public static final String PREF_MIRROR_ENCODE = "pref_mirror_encode";


//    private static final int BEAUTY_EFFECT_DEFAULT_CONTRAST = BeautyOptions.LIGHTENING_CONTRAST_NORMAL;
    private static final float BEAUTY_EFFECT_DEFAULT_LIGHTNESS = 0.5f;
    private static final float BEAUTY_EFFECT_DEFAULT_SMOOTHNESS = 0.6f;
    private static final float BEAUTY_EFFECT_DEFAULT_REDNESS = 0.3f;

//    public static final BeautyOptions DEFAULT_BEAUTY_OPTIONS = new BeautyOptions(
//
//            BEAUTY_EFFECT_DEFAULT_CONTRAST,
//            BEAUTY_EFFECT_DEFAULT_LIGHTNESS,
//            BEAUTY_EFFECT_DEFAULT_SMOOTHNESS,
//            BEAUTY_EFFECT_DEFAULT_REDNESS);
//
////            BEAUTY_EFFECT_DEFAULT_CONTRAST,
////            BEAUTY_EFFECT_DEFAULT_LIGHTNESS,
////            BEAUTY_EFFECT_DEFAULT_SMOOTHNESS,
////            BEAUTY_EFFECT_DEFAULT_REDNESS);
//
//
//    public static VideoEncoderConfiguration.VideoDimensions[] VIDEO_DIMENSIONS = new
//            VideoEncoderConfiguration.VideoDimensions[]{
//            VideoEncoderConfiguration.VD_424x240,
//            VideoEncoderConfiguration.VD_640x360,
//            VideoEncoderConfiguration.VD_640x360,
//            VideoEncoderConfiguration.VD_640x360,
//            VideoEncoderConfiguration.VD_640x360
//    };
//
//
//    public static int[] VIDEO_MIRROR_MODES = new int[]{
//            io.agora.rtc.Constants.VIDEO_MIRROR_MODE_AUTO,
//            io.agora.rtc.Constants.VIDEO_MIRROR_MODE_ENABLED,
//            io.agora.rtc.Constants.VIDEO_MIRROR_MODE_DISABLED,
//    };

    /*For Bottom navigation*/

    private static Context contexts;
    private SharedPreferences mPref;
    static Constants sessionObj;
    private SharedPreferences.Editor mEditor;
    public static final String mPREF_NAME = "APP_PREF";

    public void setIndex(int position) {
        mEditor.putInt(BOTTOM_INDEX, position);
        mEditor.commit();
//        GetterSetter.setSelectedBottomNavPosition(position);
    }


    public Constants(@NonNull Context context) {
        int mPRIVATE_MODE = 0;
        mPref = context.getSharedPreferences(mPREF_NAME, mPRIVATE_MODE);
        mEditor = mPref.edit();
    }

    public static Constants newInstance(@NonNull Context context) {
        contexts = context;
        if (sessionObj == null)
            sessionObj = new Constants(context);
        return sessionObj;
    }

    public int getIndex() {
        return mPref.getInt(BOTTOM_INDEX, 0);
    }


    //========================================================//


    public static final boolean LIVE_MODE = true;


    public static String DOMAIN = "http://api.dev1.astrotalk.com:8080/AstroTalk/";
    public static String DOMAIN_LIVE_STREAMING = "http://api.dev1live.astrotalk.com:8080/AstrotalkLive/";
    public static String DOMAIN_CHAT = "http://api.dev1supportchat.astrotalk.com:8080/AstroChat/";
    public static String DOMAIN_KUNDLI = "http://api.dev1.astrotalk.com/v1/";
    public static String DOMAIN_USER_CHAT = "http://api.dev1paidchat.astrotalk.com:8080/AstrotalkChat/";
    public static String DOMAIN_ASTROMALL = "http://api.dev1astromall.astrotalk.com:8080/AstroMall/";

    public static String AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NTAxNDIzMzg2IiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJidXNpbmVzc0lkIjoxLCJhcHBJZCI6MSwicm9sZV90eXBlIjoiUk9MRV9VU0VSIiwiaWQiOjQ3NjkxNCwiaXNMaXZlIjpmYWxzZSwiaWF0IjoxNjQ4NTM1OTQwLCJleHAiOjE2ODAwNzE5NDB9.ib0SdlOlvn37S1HOmzcTVYrG3ClBo7G6hdbMaBnrxQ0";
    public static String  ID = "476914";
    public static long  CHAT_ORDER_ID = 605051;

    //  LIVE mode
    /*public static String DOMAIN = "https://api.prod.astrotalk.in/AstroTalk/";
    public static String DOMAIN_LIVE_STREAMING = "https://api.live.astrotalk.com/AstrotalkLive/";
    public static String DOMAIN_CHAT = "https://api.supportchat.astrotalk.com/AstroChat/";
    public static String DOMAIN_KUNDLI = "http://api.kundali.astrotalk.com/v1/";
    public static String DOMAIN_USER_CHAT = "https://api.paidchat.astrotalk.com/AstrotalkChat/";
    public static String DOMAIN_ASTROMALL = "https://api.astromall.astrotalk.com/AstroMall/";*/


    public static String REGISTER_USER = DOMAIN + "login/signup/user/google/v3";
    public static String GET_CONSULTANT_TYPE = DOMAIN + "/consultant/type/get?userId=%1$s&businessId=%2$s&&appId=%3$s";
    public static String GET_SLUG_CONSULTANT = DOMAIN + "/consultant/get/slug?id=";
    public static String REGISTER_USING_EMAIL = DOMAIN + "login/signup/user/email";
    public static String EMAIL_LOGIN = DOMAIN + "login/user";
    public static final String UPDATE_USER_GCM = DOMAIN + "user/api/v1/update_device_token";
    public static String GET_NOTIFICATION_NEW_API = DOMAIN + "notification/get/by/user";
    public static final String UPLOAD_IMAGE = DOMAIN + "api/v1/horoscope/user/upload-files";
    public static final String CONVERT_CURRENCY = DOMAIN + "get/conversion/rate";
    public static final String GET_ASTROLOGER_LIST_NEWAPI = DOMAIN + "consultant/get/consultant/for/users/v2";
    public static final String ASK_QUESTION = DOMAIN + "api/v1/ask/question/add";
    public static final String GET_QUESTIONS = DOMAIN + "api/v1/ask/question/get-user";
    public static final String GET_QUESTIONS_DETAILS = DOMAIN + "api/v1/ask/question/get";
    public static final String COMMENT = DOMAIN + "api/v1/ask/question/give_rating_ireview";

    /*Live Video recording list*/
    public static final String LIVE_VIDEO_RECORDINGS = DOMAIN_LIVE_STREAMING + "live/event/get/recordingclip/ongoing";

    /*GET SET USER CURRENCY*/
    public static final String GET_ALL_CURRENCY = DOMAIN + "currency/get/all";
    public static final String SET_USER_CURRENCY = DOMAIN + "currency/set/by/user";

    //Assistant Chat Api's
    public static final String BLOCK_ASTROLOGER_IN_ASSISTANT_CHAT = DOMAIN_CHAT + "chat-assistance-manager/block/assistance";
    public static final String MUTE_ASTROLOGER_IN_ASSISTANT_CHAT = DOMAIN_CHAT + "chat-assistance-manager/mute/chat";
    public static final String CHECK_ASSISTANT_CHAT_BLOCK_BY_ASTROLOGER = DOMAIN_CHAT + "chat-assistance-manager/get/block/assistance";
    public static final String CREATE_ASSISTANT_CHAT_TICKET = DOMAIN_CHAT + "chat-assistance-manager/create-chat";
    public static final String SEND_MESSAGE_IN_ASSISTANT_CHAT = DOMAIN_CHAT + "chat-assistance-manager/send/message";
    public static final String GET_ASSISTANT_CHAT_HISTORY = DOMAIN_CHAT + "chat-assistance-manager/get-chat-history";
    public static final String GET_ASSISTANT_CHAT_LATEST_MESSAGE = DOMAIN_CHAT + "chat-assistance-manager/get-latest-message";
    public static final String ASSISTANT_CHAT_MESSAGE_DELETE = DOMAIN_CHAT + "chat-assistance-manager/delete/message";
    public static final String CHECK_PAID_USER = DOMAIN_CHAT + "chat-assistance-manager/is/paid/user";
    public static final String ASSISTANT_CHAT_LIST = DOMAIN_CHAT + "chat-assistance-manager/admin/chat/list";
    public static final String DELETE_SINGLE_ASSISTANT_CHAT = DOMAIN_CHAT + "chat-assistance-manager/delete/chat";

    /*get category list in chat api*/
    public static final String GET_CATEGORY_QUESTIONS = DOMAIN + "filter/get/category/questions";
    public static final String GET_CATEGORY = DOMAIN + "filter/get/category/v1";


    //report api
    public static String GET_REPORT_TYPE = DOMAIN + "report_type/v2";
    public static String REPORT_INTAKE_FORM = DOMAIN + "report";
    public static String REPORT_HISTORY = DOMAIN + "report";
    public static String GET_LAST_INTAKE_RECORD = DOMAIN + "get/lastRecord";

    // EXOTEL API
    public static String GET_NEW_WALLET_DETAILS = DOMAIN + "wallet/getMoney";
    public static String GET_PAYMENT_LOG = DOMAIN + "payment-log/get/payment/log";
    public static String RECHARGE_AMMOUNT = DOMAIN + "recharge/amount/get/by/userId";

    public static String GET_CALL_DETAILS = DOMAIN + "calling/order/get";
    public static String CALLING_HISTORY_BYID = DOMAIN + "calling/order/getById";
    public static String CALLING_INTAKEFORM = DOMAIN + "calling/fill-intake-form";

    //// user astrologer chat model


    public static String CREATE_CHAT_ORDERID = DOMAIN + "chat/order/create";
    public static final String CHECK_ASTROLOGER_IS_VALID_FOR_CHAT_CONTINUE = DOMAIN + "show/chat/continue";
    public static String GET_CHECKSUM = DOMAIN + "payment/gateway/paytm/generate-checksum";
    public static String GET_CHECKSUM_V2 = DOMAIN + "payment/gateway/paytm/generate-checksum/v2";
    public static String INITIATE_PAYTM_TRANSCATION = DOMAIN + "payment/gateway/paytm/initiate-payments/V2";
    public static String CHECK_CANCHAT_CANCALL_V2 = DOMAIN + "get/status/for/call-chat/v3";
    public static String CHECK_CANCHAT_V4 = DOMAIN + "get/status/for/call-chat/v4";
    public static String LIVE_CALL_CONTINUE = DOMAIN + "queue-token/create/live-continue";
    public static String DELETE_ORDER = DOMAIN + "order/delete/byId";
    public static String ASTROLOGER_NOTIFY = DOMAIN + "notify-user/for/consultant-services";
    public static String HAS_GST_URL = DOMAIN + "has-gst";
    public static String UPDATE_APPID = DOMAIN + "user/update/timezone/appId";

    public static String GET_NEXT_ONLINE_TIME = DOMAIN + "consultant/get/total/wait/time";
    public static String CHAT_SHARE = WEB_DOMAIN + "share/";
    public static String REPORT_SHARE = WEB_DOMAIN + "sharereport/";
    //////cart api's


    public static String CUSTOMER_SUPORT = DOMAIN + "get/customer/support";
    /// coupon code api
    public static String APPLY_COUPON = DOMAIN + "coupon/isToGiveCoupon";
    public static String IS_TOEKEN_EXPIRE = DOMAIN + "isTokenExpired";
    public static String CHECK_VERSION = DOMAIN + "user/get/app/version";
    /// new feedback and review api
    public static String rating_review_api = DOMAIN + "rating-review/create";
    public static String feedback_api = DOMAIN + "feedback/create";


    /*get astrologer status*/
    public static String GET_ASTROLOGER_CALLING_LIST = DOMAIN + "freeAPI/consultant/getAllStatusById";

    // Waitlist

    //public static String LIVE_EVENT_WAITLIST = DOMAIN + "queue-token/get/liveEvent/user";

    public static String WAITLIST_CHECK_USER_V2 = DOMAIN + "call-chat/queue/check/user/waitlist/v2";
    public static String WAITLIST_CHECK_USER_V2_NEW = DOMAIN + "queue-token/user/waitlist/get/v3";
    public static String CANCEL_WAITLIST = DOMAIN + "call-chat/queue/cancel/token";
    public static String JOIN_WAITLIST_V2 = DOMAIN + "call-chat/queue/create-token/v2";
    public static String IS_GCM_AVAILABLE = DOMAIN + "user/has-gcmId";
    public static String PAYMENT_LOG = DOMAIN + "payment-log/submit";
    public static String PAYMENT_COMPLETE_LOG = DOMAIN + "payment-log/complete";
    public static String PAYMENT_UNIVERSAL = DOMAIN + "universal/orders/submit-order";
    public static String HOME_PAGE_MINIMUM_PRICE_NEW_API = DOMAIN + "home-screen";
    public static String HOME_PAGE_MINIMUM_PRICE_NEW_API_WITH_API_KEYS = DOMAIN + "/home-screen/with/api/keys";
    public static String UPDATE_GCM_WITOUTLOGIN = DOMAIN + "login/user/without/login";
    public static String GET_REFERAL_DETAILS = DOMAIN + "referral/v2/getReferralPage";
    public static String APPLY_REFERRAL = DOMAIN + "referral/v2/apply/referral";
    public static String IS_SHOW_REFERRAL = DOMAIN + "referral/v2/isToGiveReferral";
    public static String FOR_GOT_PASSWORD = DOMAIN + "user/forgot/password";
    public static String VERYFY_PHONE_NUMBER = DOMAIN + "user/primary/verify-mobile/save";
    public static String CHECK_IS_NUMBERVERIFY = DOMAIN + "user/verify-mobile/get";
    public static String CHECK_IS_FIREBASEON = DOMAIN + "isToVerifyMobile/firebase";
    public static String SECONDRY_NUMBER_VERIFY = DOMAIN + "user/verify-mobile/save";
    public static String HOROSCOPE_UPDATE = DOMAIN + "horoscope/update/sun/sign";
    public static String INVOICE_RECEIPT = DOMAIN + "invoice/generate/invoice/receipt/wallet";
    public static String NEW_BLOG = DOMAIN + "blog/article/get";
    public static final String MY_ORDERS_ON_HOME_SCREEN = DOMAIN_USER_CHAT + "order/get/my-order/v1";


    public static String APPLY_PERMOTIONALCODE_NEW = DOMAIN + "offer/promotional/applyCode/v2";
    public static String SET_USER_LANGUAGE = DOMAIN + "user/set/language";
    public static String INTAKE_FORM_COMMENT_HIDE = DOMAIN + "isToHideCommentFromIntakeForm";
    public static String PROFILE_CALL_CHAT_BTN_HIDE = DOMAIN + "get/flag/boolean/by/id";

    public static final String REQUEST_PRODUCT_CALLBACK = DOMAIN + "/product-callback/create-request";
    public static final String SEND_OTP = DOMAIN + "/referral/send-otp";
    public static final String SEND_OTP_NEW = DOMAIN + "login/send-otp/v2";
    public static final String SEND_OTP_VERYFY_OTP = DOMAIN + "login/verify-otp/v2";
    public static final String DELETE_ACCOUNT = DOMAIN + "/admin/delete/user/by/id/v2";

    public static String GET_ASTROLOGER_PROFILE = DOMAIN + "freeAPI/consultant/get/by/slug/v2";
    public static String GET_ASTROLOGER_FIXED_SESSION_OFFERS = DOMAIN + "consultant/fixed-sessions/consultant/active";
    public static String USER_PROFILE_UPDTAE = DOMAIN + "user/get/profile";
    public static String DELETE_ALL_TRANSACTIONS = DOMAIN + "/wallet/deleteForUser";
    public static String DELETE_SINGLE_TRANSACTIONS = DOMAIN + "/wallet/delete-unDelete/forUser";
    public static String DELETE_SINGLE_OR_ALL_PAYMENT_LOGS = DOMAIN + "payment-log/delete";
    public static String DELETE_SINGLE_OR_ALL_CASHBACKS = DOMAIN + "membership/delete";
    public static String DELETE_ALL_ORDER = DOMAIN + "/order/deleteAll/byUserId";

    public static String GET_PAYMNET_TYPE = DOMAIN + "payment/method/get/by/user";
    public static String GET_HOME_PAGE_BANNER = DOMAIN + "banner/get";
    public static String GET_HOME_PAGE_BANNER_NEW = DOMAIN + "banner/get/v3";
    public static String GET_HOME_PAGE_BANNER_NEW_V2 = DOMAIN + "banner/user/banner/get";

    public static String GET_HOROSCOPE_DETAILS = DOMAIN + "horoscope/get";
    public static String GET_ALL_LANGUAGE_LIST = DOMAIN + "language/get/by/userId";
    public static String GET_SELECTED_LANGUAGE_LIST = DOMAIN + "language-user/get";
    public static String UPDATE_SELECTED_LANGUAGE = DOMAIN + "language-user/add-update";
    public static String DELETE_SELECTED_LANGUAGE = DOMAIN + "language-user/delete";
    public static String REPORT_FEEDBACK = DOMAIN + "report/feedback/call-chat";
    public static String PO_WAITING = DOMAIN + "poLogs/get";
    public static String GET_NOTIFICATION_LIST = DOMAIN + "notification/get/type";
    public static String NOTIFICATION_BLOCK_UNBLOCK = DOMAIN + "/notification/block-unblock";
    public static String SHARE_CHAT_GET_URL = DOMAIN + "order/share/create";
    public static String GET_ASTROLOGER_LSIT = DOMAIN + "consultant/get-list/byUserId";
    public static String NAME_VISIBLE = DOMAIN + "user/set/name-visible";
    public static String NAME_VISIBLE_GET = DOMAIN + "user/get/name-visible";
    public static String CHECK_PLANETARY_EXIST = DOMAIN_KUNDLI + "kundli/checkexist";


    public static String GET_ALL_RATING = DOMAIN + "rating-review/get/forProfile";
    public static String CONSULTANT_BIO = DOMAIN + "freeAPI/consultant/get/longBio";
    public static String COMMON_LANGUAGE_LIST = DOMAIN + "language/get/common-language";
    public static final String SECOND_OPENINON_REPORT = DOMAIN + "report/secondOpinion/create";
    public static final String AUTO_DEBIT_INK = DOMAIN + "auto/debit/link";
    public static final String CHECK_AUTODEBIT = DOMAIN + "/user/auto/debit/v2";
    public static final String AUTODEBIT_WALLET_RECHARGE = DOMAIN + "/auto/debit/payment";

    public static final String EMAIL_VERIFICATION_SEND_OTP = DOMAIN + "login/send-otp/v2/email";
    public static final String EMAIL_VERIFICATION_OTP_VERIFICATION = DOMAIN + "login/verify-otp/v2/email";
    public static String UPDATE_AUTO_DEBIT = DOMAIN + "auto/debit/delete/";
    public static String COMMENT_BY_ASTROLOGER = DOMAIN + "/api/v1/flag/review/info";
    public static String AUTO_DEBIT_INFO = DOMAIN + "auto/debit/info";
    public static String APPLY_VOUCHER_CODE = DOMAIN + "voucher/apply";


    public static final String GET_ORDER_IMAGES = DOMAIN + "order/get/images";

    public static String NEWS_SECTION_HOMEPAGE = DOMAIN + "media/partner/get";

    public static String CEO_MESSAGE = DOMAIN + "/get/static/value";
    public static final String SHOW_LIVE_ANONYMOUS_CALL = DOMAIN + "/get/static/value";
    public static final String SHOW_LIVE_ANONYMOUS_CHAT = DOMAIN + "/get/static/value";
    public static String HD_MODE_FOR_LIVE_EVENT = DOMAIN + "/get/static/value";
    public static final String GET_ASTROMALL_GROUP_CHAT_HISTORY = DOMAIN + "group/chat/get-history";
    public static final String GET_ASTROMALL_GROUP_SEND_MESSAGE = DOMAIN + "group/chat/send-message";
    public static final String GET_ASTROMALL_GROUP_NEW_MESSAGE = DOMAIN + "group/chat/get-latest-messages";


    public static final String GET_PRESCRIPTIONLIST = DOMAIN + "prescription/getPrescriptionList";
    public static final String GIFT_VOUCHER_LIST = DOMAIN + "gift-voucher/get";
    public static final String CREATE_GIFT_VOUCHER = DOMAIN + "gift-voucher/create";
    public static final String CLAIM_VOUCHER = DOMAIN + "gift-voucher/apply";


    public static final String USER_REGISTRATION_SOURCE = DOMAIN + "user-registration-source/get";
    public static final String USER_REGISTRATION_SOURCE_INPUT = DOMAIN + "user-registration-source/input-details";
    public static final String USER_REGISTRATION_SOURCE_EXSISTS = DOMAIN + "user-registration-source/check-sourceId-exists";
    public static final String RECOMMEDNDATION_ANALYTICS_CREATE = DOMAIN + "recommendation/analytics/create";
    public static final String SHWO_IS_PO = DOMAIN + "offer/promotional/isToShowPromotionalOffer/v2";
    public static final String GET_COUNTRY_FROM_IP = DOMAIN + "/get/countryCodeByIp";
    public static final String GET_WINNINGS_LIST = DOMAIN + "/referral/referral/list";


    public static final String DELETE_GROUP_MESSAGE = DOMAIN + "group/chat/delete-msg";
    public static final String ASTROLOGER_GALLRY_IMAGE = DOMAIN + "consultant-gallery/get/allConsultantImages";
    public static final String RATING_REVIEW_SHOW = DOMAIN + "rating-review/isToShow/rating";
    public static final String CANCEL_RATING_REVIEW = DOMAIN + "rating-review/cancel/rating";
    public static final String GET_USER_MOBILE_DETAILS_PAYPAL = DOMAIN + "user/mobile/get";
    public static final String GET_ASTROLOGER_ACTUAL_PRICE = DOMAIN + "consultant/get/price";


    public static final String MANAGMENT_FEEDBACK_ADD = DOMAIN + "managementFeedback/add";
    public static final String CHECK_RATING_POPUP_VISIBILITY = DOMAIN + "managementFeedback/get/rating/popup";
    public static final String NPS_QUESTIONS = DOMAIN + "nps/score/get";
    public static final String NPS_ADD_SCORE = DOMAIN + "nps/score/addupdate";
    public static final String GET_TIMETABLE = DOMAIN + "timetable/get/weekly";
    public static final String DELETE_REVIEW_RATING = DOMAIN + "/rating-review/delete/rating";


    public static final String GET_TIMEZONE = DOMAIN_KUNDLI + "timezone";
    public static final String GET_ASHTAKOOT_POINTS = DOMAIN_KUNDLI + "match_ashtakoot_points";
    public static final String GET_DASHAKOOT_POINTS = DOMAIN_KUNDLI + "match_dashakoot_points";
    public static final String MATCH_MANGLIK_REPORT = DOMAIN_KUNDLI + "match_manglik_report";
    public static final String MATCH_MANGLIK_REPORT_SINGLE = DOMAIN_KUNDLI + "manglik";
    public static final String YOAG_COMBINATION = DOMAIN_KUNDLI + "find/combination";
    public static final String CITY_ADD = DOMAIN_KUNDLI + "cities/addall";

    public static final String SAVE_KUNDALI = DOMAIN + "birthdetail/save/planets";
    public static final String GET_KUNDALI = DOMAIN + "birthdetail/getSortedList";
    public static final String DELETE_KUNDALI_MATCHMAKING = DOMAIN + "birthdetail/delete";
    public static final String SEARCH_KUNDALI_MATCHMAKING = DOMAIN + "birthdetail/search";
    public static final String CHECK_FILTER_USE = DOMAIN + "freeAPI/consultant/is/Filter/use";
    public static final String USER_DEVICE_INFO = DOMAIN + "user-device/create";
    public static final String ADD_COUNTRIES_API = DOMAIN_KUNDLI + "cities/allcountries/add";
    public static final String ALL_LOCATION_AUTOCOMPLETE = "https://api.locationiq.com/v1/autocomplete.php";

    public static final String ASTROTALK_PLACE_SEARCH_API = DOMAIN_KUNDLI + "cities/allcountries/autocomplete";
    public static final String GET_ASTROTALK_BLOG = "https://astrotalk.com/blogapi/";
    public static final String SEARCH_ASTROTALK_BLOG = "https://astrotalk.com/blogapi/search/";
    public static String DELETE_ALL_RECENT_SEARCH = DOMAIN + "recent-search/remove/result/all";


    public static final String GROUDP_MESSAGE_ADD = DOMAIN + "group/chat/automated/message/add";
    public static final String REPORT_ALLOW = DOMAIN + "is/report/allowed";
    public static final String WAITLIST_HOLD_UNHOLD = DOMAIN + "queue-token/user/hold/unhold";
    public static final String PHONE_NUMBER_VERI = DOMAIN + "login/user/mobile-otp-login";
    public static final String OTP_NUMBER_VERI = DOMAIN + "login/user/mobile-otp-login/verify";
    public static final String OTP_NUMBER_VERIFICATION_FORGIN = DOMAIN + "login/user/mobile-firebase-login/verify";
    public static final String OTP_NUMBER_VERIFICATION_TRUECALLER = DOMAIN + "login/user/mobile-truecaller-login/verify";

    public static String ADD_REMOVE_FAV_ASTROLOGER = DOMAIN + "user/setFavouriteConsultant";
    public static String GET_GOLD_MEMBERSHIP = DOMAIN + "membership/get/membership/details";
    public static String GET_CASBACK_LIST = DOMAIN + "membership/get/cashback";
    public static String ACTIVATE_ASTROTALK_GOLD = DOMAIN + "membership/user/gold";
    public static String CLAIM_GOLD_CASBACK = DOMAIN + "membership/claim/cashback";
    public static String MEMEBERSHIP_STSTUS = DOMAIN + "membership/rating/nps/st    atus";
    public static String GET_HOMEPAGE_OFFER = DOMAIN + "offer/v3/status";

    //    public static String BLOCK_ASTROLOGER_BY_USER = DOMAIN + "block/user/astrologerBlock";
    public static String BLOCK_ASTROLOGER_BY_USER = DOMAIN + "user/block/consultant";


    //ASTROMALL
    public static String GET_PRODUCT_TYPE = DOMAIN_ASTROMALL + "product-type/get";
    public static String ASTROMALL_GET_PRODUCT_TYPE = DOMAIN_ASTROMALL + "astromall/get/product-type";
    public static String GET_PRODUCT_LIST_DETAIL = DOMAIN_ASTROMALL + "product-type/product/get";
    public static String GET_PRODUCT_LIST = DOMAIN_ASTROMALL + "astromall/get/product";
    public static String GET_PRODUCT_INFO = DOMAIN_ASTROMALL + "product-type/product/information/get";
    public static final String GET_ALL_PRODUCT_SEARCH = DOMAIN_ASTROMALL + "product-type/product/search/global";
    public static final String GET_PRODUCT_ASTROLOGER_LIST = DOMAIN_ASTROMALL + "product-type/product/consultant/get";
    public static String PRODUCT_LONG_BIO = DOMAIN_ASTROMALL + "product-type/product/description";
    public static String PRODUCT_COD_OPTION = DOMAIN_ASTROMALL + "product-type/order/set/to/cod";
    public static String CHECK_INTAKE_VISIBILITY = DOMAIN_ASTROMALL + "product-type/product/intake-form-fields/get";
    public static String CART_INTAKE_FORM = DOMAIN_ASTROMALL + "product-type/order/create/v3";
    public static String CART_INTAKE_SINGLE_ORDER_DETAIL = DOMAIN_ASTROMALL + "product-type/order/history/get/order";
    public static String CART_PRODUCT_DETAILS = DOMAIN_ASTROMALL + "product-type/order/history/get";
    public static String ADD_ONS_LIST = DOMAIN_ASTROMALL + "product-type/addon/get";
    public static String CREATE_ADDRESS = DOMAIN_ASTROMALL + "address/create/v2";
    public static String UPDATE_ADDRESS = DOMAIN_ASTROMALL + "address/update/v2";
    public static String GET_ADDRESS = DOMAIN_ASTROMALL + "address/get";
    public static final String GET_PRODUCT_ASTROLOGER_LIST_SEARCH = DOMAIN + "consultant/search-in-product";
    public static String RATING_REVIEW_PRODUCT = DOMAIN + "rating-review/get/forProduct";
    public static String DELETE_ADDRESS = DOMAIN_ASTROMALL + "address/delete";
    public static String GET_HOME_PAGE_TESTIMONIAL = DOMAIN + "freeAPI/consultant/get-list/Testimonial";
    public static String GET_LOGIN_DETAIL = DOMAIN + "login/login-page-new";

    public static String GET_FIX_SESSION = DOMAIN + "consultant/fixed-sessions/consultant/active";
    public static String ADD_RECENT_SEARCH = DOMAIN + "recent-search/add/result";
    public static String RECENT_GLOABL_SEARCH = DOMAIN + "recent-search/get/result";
    public static String REMOVE_RECENT_SEARCH = DOMAIN + "recent-search/remove/result";
    public static String VISIT_ASTROLOGER_PROFILE = DOMAIN + "profile/visitor/create";

    public static String ASTROMALL_BANNER = DOMAIN_ASTROMALL + "astromall-banner/get";
    public static String ASTOMALL_PRODUCT_LINE = DOMAIN_ASTROMALL + "astromall/get/products/line";
    public static String ASTOMALL_PRODUCT_QUESTION = DOMAIN_ASTROMALL + "product-question-answer/get";
    public static String ASTOMALL_PRODUCT_PROMISES = DOMAIN_ASTROMALL + "product-promise/get";
    public static String SELECT_ADD_ONS = DOMAIN_ASTROMALL + "astromall/get/product/add-on";
    public static String CALCULATE_RATTI_PRICE = DOMAIN_ASTROMALL + "astromall/calculate/product/price";
    public static String ASTROMALL_BUY_NOW = DOMAIN_ASTROMALL + "astromall/buy/now/v2";
    public static String SELECT_ADDRESS = DOMAIN_ASTROMALL + "astromall/select/address/v2";
    public static String SELECT_CUNSULTANT = DOMAIN_ASTROMALL + "astromall/select/consultant/v2";
    public static String GET_ORDER_DETAIL = DOMAIN_ASTROMALL + "astromall/get/order/details/v2";
    public static String CREATE_ASTROMALL_ORDER = DOMAIN_ASTROMALL + "astromall/create/order/v2";


    // ADMIN-USER-SUPPORT-CHAT-DOMAIN
    public static final String GET_CHAT_TICKET_LIST = DOMAIN_CHAT + "/chat/api/v2/get-chat-list";
    public static String SUPPORT_AUTOMATEDMESSAGE = DOMAIN_CHAT + "support/api/v1/automated-messages";
    public static String GET_CHAT_HISTORY = DOMAIN_CHAT + "chat/api/v1/get-chat-history";
    public static final String SEND_MESSAGE = DOMAIN_CHAT + "chat/api/v1/send-message";
    public static final String SUPPORT_LATEST_MESSAGE = DOMAIN_CHAT + "/chat/api/v2/get-latest-message";
    public static final String GET_TICKET_REVIEW = DOMAIN_CHAT + "chat/rating/review/v1/get";
    public static final String POST_TICKRT_REVIEW = DOMAIN_CHAT + "chat/rating/review/v1/add";
    public static final String CREATE_NEW_TICKET = DOMAIN_CHAT + "chat/api/v2/create-chat";
    public static final String REOPEN_TICKET = DOMAIN_CHAT + "chat/api/reopen/ticket";
    public static final String DELETE_ALL_TICKET_CHAT = DOMAIN_CHAT + "chat/api/delete-all-user-chats";
    public static final String DELETE_INDIVIDUAL_TICKET_CHAT = DOMAIN_CHAT + "chat/api/v2/delete-chat";
    public static final String DELETE_CHAT_TICKET_MESSAGE = DOMAIN_CHAT + "chat/api/v1/deleteChatMsgV2";
    public static final String IS_SUPPORT_TICKET_OPEN = DOMAIN_CHAT + "chat/api/v2/get-open-chat-status";


    //================ USERASTROLOGERCHAT=================//
    public static String CHAT_INTAKE_FORM = DOMAIN_USER_CHAT + "chat/order/fill-intake-form";
    public static String ACCEPT_CHAT = DOMAIN_USER_CHAT + "chat/order/accept/byUser/v2";
    public static String REJECT_CHAT = DOMAIN_USER_CHAT + "chat/order/canceled/byUser/v2";
    public static String COMPLETE_CAHT_ORDER = DOMAIN_USER_CHAT + "chat/order/completed/v2";
    public static String COMPLETE_CAHT_ORDER_LIST = DOMAIN_USER_CHAT + "chat/order/get-list/v3";
    public static String CHAT_STATUS_FOR_USER = DOMAIN_USER_CHAT + "chat/order/get/status/for/user";
    public static String GET_LAST_CHAT_ID_STATUS = DOMAIN_USER_CHAT + "chat/order/get/user/chat-status";
    public static String GET_LAST_CHAT_ID_STATUSv2 = DOMAIN_USER_CHAT + "chat/order/get/user/chat-status/v3";
    public static String USER_ASTROLOGER_SEND_TEXT_MESSAGE = DOMAIN_USER_CHAT + "chat/message/send-message/v3";
    public static String USER_ASTROLOGER_CHAT_START_HISTORY = DOMAIN_USER_CHAT + "chat/message/get-chat-history/v3";
    public static String DELETE_SINGLE_USER_ASROLOGER_MESSAGE = DOMAIN_USER_CHAT + "chat/message/deleteChatMsg/v2";
    public static String USER_ASTROLOGER_CHAT_START_HISTORY_WO_PAGE = DOMAIN_USER_CHAT + "chat/message//get-chat-history/without/page/v2";
    public static final String GET_FLAG_VALUE = DOMAIN_USER_CHAT + "chat/message/get-flag-value";
    public static final String GET_FLAG_VALUE_SECOUND = DOMAIN_USER_CHAT + "chat/message/v2/get-flag-value";
    public static final String ASTROLOGER_USER_LATESTCHATMESSAGE = DOMAIN_USER_CHAT + "/chat/message/v2/get-latest-message/v2";
    public static final String SEARCH_ALL_MESSAGES_NEW = DOMAIN_USER_CHAT + "chat/message/search/user/messages";
    public static final String GET_ALL_CHAT_SEARCH_ASTROLOGER_LIST = DOMAIN_USER_CHAT + "chat/message/search/user/astrologer";
    public static final String CONTINUE_CHAT_IN_CASE_OF_OFFER_AND_FIXED_SESSION = DOMAIN + "queue-token/create/chat-continue";
    //================ USERASTROLOGERCHAT=================//

    public static String PAPAL_CLIENTID = "AXcfQe-af8zaTrrfPYHwXDcVZ1CwkamtBa-VXBwbqP4cJBhni3_34Zy8t4mxyA3sjudNpXexeqe50XcP";
    public static String PAPAL_CLIENTID_SANBOX = "AV5wmzFoZ5-3qTUNt2J_sHwSrBcKexfpb7Iv0w2rGtRSjzVUv1uGwb6d5QgHgI-oZx6zVGHOLnVKPdJf";


    public static String CODEYETI_RAZOR_PAY_LIVE = "rzp_live_nbFIyWp9PWCqNl";
    public static String CODEYETI_RAZOR_PAY_TEST = "rzp_test_K2FmWkyeoXIGD3";


    public static String MASKYETI_RAZOR_PAY4_LIVE = "rzp_live_idcMSOkCdvdxCB";
    public static String MASKYETI_RAZOR_PAY_TEST = "rzp_test_mnLQsJF2nkRjsg";


    public static String ASTROTALK_RAZOR_PAY_LIVE = "rzp_live_Mw6ZYXea2k4yyj";
    public static String ASTROTALK_RAZOR_PAY_TEST = "rzp_test_8z4JnWLwdNHC3e";

}
