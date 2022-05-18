package com.astrotalk.sdk.api.utils;


public class AstroConstants {

    public static boolean LIVE_MODE = false;

    public static String DOMAIN = domainMainChat();
    public static String DOMAIN_KUNDLI = domainKundli();
    public static String DOMAIN_USER_CHAT = domainPaidChat();

    public static String domainMainChat() {
        if(LIVE_MODE) {
            return "https://api.prod.astrotalk.in/AstroTalk/";
        }
        else {
            return "http://api.dev3.astrotalk.com:8080/AstroTalk/";
        }
    }

    public static String domainKundli() {
        if(LIVE_MODE) {
            return "http://api.kundali.astrotalk.com/v1/";
        }
        else {
            return "http://api.dev1.astrotalk.com/v1/";
        }
    }

    public static String domainPaidChat() {
        if(LIVE_MODE) {
            return "https://api.paidchat.astrotalk.com/AstrotalkChat/";
        }
        else {
            return "http://43.204.79.87:8080/AstrotalkChat/";
        }
    }

    public static final int APP_ID = 5;
    public static final int BUSINESS_ID = 1;
    public static final int CHAT_SERVICE_ID = 4;
    public static final int CONSULTANT_TYPE_ID = 1;
    public static final int LANGUAGE_ID = 1;
    public static final String SDK_VERSION = "0.0.1";

    public static final String IMAGE_DOMAIN = "https://astrotalk.s3.amazonaws.com/consultant_pic/";
    public static final int VOLLEY_TIME_OUT = 4 * 15 * 1000;

    // Shared Preferences
    public static final String USER_DETAIL = "astrotalk_userdetail";
    public static final String USER_ID = "user_id";
    public static final String JWT_TOKEN = "jwt_token";
    public static final String WINZO_DURATION = "winzo_duration";

    public static final String TIME_ZONE = "Asia/Kolkata";
    public static final String CONVERSION_FACTOR = "conversionFactor";
    public static final String ISO_CODE = "isoCode";
    public static final String WAITLIST_SHOW_HIDE = "waitlistshowHIde";

    /*
    * API Calls
    */

    // Chat List
    public static final String GET_LAST_CHAT_ID_STATUS = DOMAIN_USER_CHAT + "chat/order/get/user/chat-status";
    public static final String GET_NEXT_ONLINE_TIME = DOMAIN + "consultant/get/total/wait/time";
    public static final String CHECK_CAN_CHAT_CAN_CALL_V2 = DOMAIN + "get/status/for/call-chat/v3";
    public static final String WAIT_LIST_CHECK_USER_V2_NEW = DOMAIN + "queue-token/user/waitlist/get/v3";

    // Intake Form
    public static final String CHAT_INTAKE_FORM = DOMAIN_USER_CHAT + "chat/order/fill-intake-form";
    public static final String JOIN_WAIT_LIST_V2 = DOMAIN + "call-chat/queue/create-token/v2";
    public static final String GET_LAST_INTAKE_RECORD = DOMAIN + "get/lastRecord";

    // Search
    public static final String ASTROTALK_PLACE_SEARCH_API = DOMAIN_KUNDLI + "cities/allcountries/autocomplete";
    public static final String ALL_LOCATION_AUTOCOMPLETE = "https://api.locationiq.com/v1/autocomplete.php";

    // Call Kit
    public static final String ACCEPT_CHAT = DOMAIN_USER_CHAT + "chat/order/accept/byUser/v2";
    public static final String REJECT_CHAT = DOMAIN_USER_CHAT + "chat/order/canceled/byUser/v2";

    // Chat Screen
    public static final String CHAT_HISTORY = DOMAIN_USER_CHAT + "chat/message/get-chat-history/v3";
    public static final String LATEST_CHAT_MESSAGE = DOMAIN_USER_CHAT + "/chat/message/v2/get-latest-message/v2";
    public static final String SEND_CHAT_MESSAGE = DOMAIN_USER_CHAT + "chat/message/send-message/v3";
    public static final String COMPLETE_CHAT_ORDER = DOMAIN_USER_CHAT + "chat/order/completed/v2";
    public static final String UPLOAD_IMAGE = DOMAIN + "api/v1/horoscope/user/upload-files";
    public static final String DELETE_SINGLE_USER_ASTROLOGER_MESSAGE = DOMAIN_USER_CHAT + "chat/message/deleteChatMsg/v2";
    public static final String GET_FLAG_VALUE = DOMAIN_USER_CHAT + "chat/message/get-flag-value";
    public static final String GET_FLAG_VALUE_SECOND = DOMAIN_USER_CHAT + "chat/message/v2/get-flag-value";

    // Chat Order List
    public static final String COMPLETE_CHAT_ORDER_LIST = DOMAIN_USER_CHAT + "chat/order/get-list/v3";

    // Other
    public static final String CANCEL_WAITLIST = DOMAIN + "call-chat/queue/cancel/token";

    // New Apis
    public static final String VERIFY_TOKEN = DOMAIN + "winzo/verify/thirdparty/user/token";

}