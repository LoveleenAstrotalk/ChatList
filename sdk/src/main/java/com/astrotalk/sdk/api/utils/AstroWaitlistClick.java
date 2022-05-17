package com.astrotalk.sdk.api.utils;

import com.astrotalk.sdk.api.model.WaitlistModel;

public interface AstroWaitlistClick {
    void onClick(WaitlistModel waitlistModel, boolean seleted, boolean unhold_click, String holdUnhold);
}
