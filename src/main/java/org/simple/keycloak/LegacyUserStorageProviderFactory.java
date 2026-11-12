package org.simple.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyUserStorageProviderFactory implements UserStorageProviderFactory<LegacyUserStorageProvider> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyUserStorageProviderFactory.class);

    public static final String ID = "simple-legacy-db";

    @Override
    public LegacyUserStorageProvider create(KeycloakSession ks, ComponentModel cm) {
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ B");
        return new LegacyUserStorageProvider(ks, cm);
    }

    @Override
    public String getId() {
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ A " + ID);
        return ID;
    }

}
