package com.mohaymen.store.service;

import com.mohaymen.store.model.UserInfo;

public interface IKeycloakService {
    UserInfo getUser(String id);
}
