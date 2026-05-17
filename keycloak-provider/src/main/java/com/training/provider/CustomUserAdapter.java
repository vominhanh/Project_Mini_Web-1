package com.training.provider;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import com.training.entity
        .AppUserEntity;

public class CustomUserAdapter

extends
AbstractUserAdapterFederatedStorage {

    private final AppUserEntity user;

    private final ComponentModel model;

    public CustomUserAdapter(

            KeycloakSession session,

            RealmModel realm,

            ComponentModel model,

            AppUserEntity user
    ) {

        super(session, realm, model);

        this.user = user;

        this.model = model;
    }

    @Override
    public String getUsername() {

        return user.getUsername();
    }

    @Override
    public void setUsername(
            String username
    ) {

    }

    @Override
    public String getId() {

        return StorageId.keycloakId(

                model,

                user.getUsername()
        );
    }
}