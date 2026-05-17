package com.training.provider;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models
        .RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user
        .UserLookupProvider;
import org.springframework.security.crypto.bcrypt
        .BCryptPasswordEncoder;

import com.training.entity.AppUserEntity;
import com.training.service.UserService;

public class CustomUserStorageProvider

implements

UserStorageProvider,

UserLookupProvider,

CredentialInputValidator {

    private final KeycloakSession
            session;

    private final ComponentModel
            model;

    private final UserService
            userService =
            new UserService();

    private final BCryptPasswordEncoder
            encoder =
            new BCryptPasswordEncoder();

    public CustomUserStorageProvider(

            KeycloakSession session,

            ComponentModel model
    ) {

        this.session = session;

        this.model = model;
    }

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserByUsername(

            RealmModel realm,

            String username
    ) {

        AppUserEntity user =

                userService.findByUsername(
                        username
                );

        if(user == null) {
            return null;
        }

        return new CustomUserAdapter(

                session,

                realm,

                model,

                user
        );
    }

    @Override
public UserModel getUserById(

        RealmModel realm,

        String id
) {

    String username =
            StorageId.externalId(id);

    AppUserEntity user =

            userService.findByUsername(
                    username
            );

    if(user == null) {
        return null;
    }

    return new CustomUserAdapter(

            session,

            realm,

            model,

            user
    );
}

    @Override
    public UserModel getUserByEmail(

            RealmModel realm,

            String email
    ) {

        return null;
    }

    @Override
    public boolean supportsCredentialType(
            String credentialType
    ) {

        return credentialType.equals(
                "password"
        );
    }

    @Override
    public boolean isConfiguredFor(

            RealmModel realm,

            UserModel user,

            String credentialType
    ) {

        return true;
    }

    @Override
public boolean isValid(

        RealmModel realm,

        UserModel user,

        CredentialInput input
) {

    System.out.println(
            "LOGIN USER = " +
            user.getUsername()
    );

    AppUserEntity dbUser =

            userService.findByUsername(
                    user.getUsername()
            );

    if(dbUser == null) {

        System.out.println(
                "USER NOT FOUND"
        );

        return false;
    }

    System.out.println(
            "DB PASSWORD = " +
            dbUser.getPassword()
    );

    System.out.println(
            "INPUT PASSWORD = " +
            input.getChallengeResponse()
    );

    boolean matched =

            encoder.matches(

                    input.getChallengeResponse(),

                    dbUser.getPassword()
            );

    System.out.println(
            "PASSWORD MATCH = " +
            matched
    );

    return matched;
}
}