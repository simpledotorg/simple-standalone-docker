/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.simple.keycloak;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.UserCredentialManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapter;

/**
 *
 * @author ademarcq
 */
public class SimpleUserStorage extends AbstractUserAdapter.Streams {

    private final String username;
    private final String email;
    private final String passwordHash;
    private final Set<RoleModel> roles = new HashSet<>();

    public SimpleUserStorage(KeycloakSession session, RealmModel realm, ComponentModel model,
            String username,  List<String> roleNames) {
        super(session, realm, model);
        this.username = username;
        this.email = username;
        this.passwordHash = username;
        this.storageId = new StorageId(model.getId(), username);

        for (String roleName : roleNames) {
            RoleModel role = realm.getRole(roleName);
            if (role != null) {
                this.roles.add(role);
            }
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    protected Set<RoleModel> getRoleMappingsInternal() {
        return roles;
    }

    @Override
    public SubjectCredentialManager credentialManager() {
         return new UserCredentialManager(session, realm, this);
    }

}
