package com.mohaymen.store.service;

import com.mohaymen.store.model.UserInfo;

public interface ISecurityService {
    String getLoginUserName();

    String getLoginUserId();

    UserInfo getCurrentUser(String userId);
}
