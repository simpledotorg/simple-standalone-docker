package org.simple.keycloak;

import java.util.ArrayList;
import java.util.List;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyUserStorageProvider
        implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyUserStorageProvider.class);
    

    private KeycloakSession ks;
    private ComponentModel cm;

    public LegacyUserStorageProvider(KeycloakSession ks, ComponentModel cm) {
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ C");
        this.ks = ks;
        this.cm = cm;
    }

    @Override
    public void close() {
        // TOOD: should we be doing something here ?
    }

    @Override
    public UserModel getUserById(RealmModel rm, String string) {
        return getUserByUsername(rm, string);
    }

    @Override
    public UserModel getUserByUsername(RealmModel rm, String userName) {
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ D");
        return new SimpleUserStorage(ks, rm, cm, userName, userName, userName, new ArrayList<String>());

        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public UserModel getUserByEmail(RealmModel rm, String string) {
        return getUserByUsername(rm, string);
    }

    @Override
    public boolean supportsCredentialType(String string) {
        return true; // Maybe more complex one day
    }

    @Override
    public boolean isConfiguredFor(RealmModel rm, UserModel um, String string) {
        return true; // Maybe more complex one day
    }

    @Override
    public boolean isValid(RealmModel rm, UserModel um, CredentialInput ci) {
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ E <" + um.getUsername() + ">");
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ E <" + ci.getChallengeResponse() + ">");
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ E <" + ci.toString() + ">");
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
