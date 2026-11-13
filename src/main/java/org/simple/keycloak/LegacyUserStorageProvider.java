package org.simple.keycloak;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyUserStorageProvider
        implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(LegacyUserStorageProvider.class);
    private static final String SIMPLE_USER_QUERY = "select \n"
            + "    email_authentications.id, \n"
            + "    email,\n"
            + "    full_name,\n"
            + "    encrypted_password,\n"
            + "    access_level\n"
            + "from email_authentications\n"
            + "join user_authentications on email_authentications.id = user_authentications.authenticatable_id\n"
            + "join users on user_authentications.user_id = users.id \n"
            + "where email=?";

    private KeycloakSession ks;
    private ComponentModel cm;

    public LegacyUserStorageProvider(KeycloakSession ks, ComponentModel cm, DataSource dataSource) {
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ C");
        this.ks = ks;
        this.cm = cm;
        this.dataSource = dataSource;
    }

    @Override
    public void close() {
        // TOOD: should we be doing something here ?
    }

    @Override
    public UserModel getUserById(RealmModel rm, String id) {
        return getUserByUsername(rm, StorageId.externalId(id));
    }

    @Override
    public UserModel getUserByUsername(RealmModel rm, String userName) {
        LOGGER.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ D0\n" + SIMPLE_USER_QUERY);

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SIMPLE_USER_QUERY)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    LOGGER.info("No user found for name <{}>", userName);
                    return null;
                }
                LOGGER.info("User found for name <{}>", userName);
                SimpleUserStorage currentUser = new SimpleUserStorage(ks, rm, cm);
                currentUser.setUsername(userName);
                currentUser.setEmail(rs.getString("email"));
                currentUser.setPasswordHash(rs.getString("encrypted_password"));
                LOGGER.info("User found for name <{}>, hashed Password is <{}>", currentUser.getUsername(), currentUser.getPasswordHash());
                LOGGER.info(">>>>" + rs.toString());
                return currentUser;
            }
        } catch (SQLException ex) {
            // todo: proper log
            System.getLogger(LegacyUserStorageProvider.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
        // TODO: move it there
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

        return true;
    }

}
