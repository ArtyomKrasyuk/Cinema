package com.example.APIGateway.service;

import com.example.APIGateway.dto.RegistrationDTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    private final String clientId;
    private final String clientSecret;
    private final String serverUrl;
    private final String realmName;
    private final String userRole;

    public AuthService(
            @Value("${client-id}") String clientId,
            @Value("${client-secret}") String clientSecret,
            @Value("${server-url}") String serverUrl,
            @Value("${realm-name}") String realmName,
            @Value("${user-role}") String userRole
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.serverUrl = serverUrl;
        this.realmName = realmName;
        this.userRole = userRole;
    }

    public AccessTokenResponse authenticateUser(String login, String password){
        try (Keycloak userClient = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmName)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.PASSWORD)
                .username(login)
                .password(password)
                .build()
        ) {
            return userClient.tokenManager().getAccessToken();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String registerUser(RegistrationDTO dto){
        try(Keycloak adminClient = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmName)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build()
        ){
            RealmResource realmResource = adminClient.realm(realmName);
            UsersResource usersResource = realmResource.users();

            UserRepresentation user = new UserRepresentation();
            user.setUsername(dto.login());
            user.setEmail(dto.login());
            user.setFirstName(dto.firstname());
            user.setLastName(dto.lastname());
            user.setEnabled(true);
            user.setEmailVerified(true);

            String userId = null;
            try (Response response = usersResource.create(user)) {
                if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                    String error = response.readEntity(String.class);
                    throw new Exception("Не удалось создать пользователя. Код ответа: " + response.getStatus() + ". Ошибка: " + error);
                }
                String path = response.getHeaderString("Location");
                userId = path.substring(path.lastIndexOf('/') + 1);

                setInitialPassword(usersResource, userId, dto.password());

                assignRealmRoleToUser(realmResource, userId, userRole);

                return userId;

            } catch (Exception e) {
                throw new Exception("Ошибка при регистрации: " + e.getMessage(), e);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void setInitialPassword(UsersResource usersResource, String userId, String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        passwordCred.setTemporary(false);

        usersResource.get(userId).resetPassword(passwordCred);
    }

    private void assignRealmRoleToUser(RealmResource realmResource, String userId, String roleName) {

        RoleRepresentation realmRole;

        try {
            realmRole = realmResource.roles().get(roleName).toRepresentation();
        } catch (Exception e) {
            throw new RuntimeException("Realm-роль '" + roleName + "' не найдена в Realm '" + realmName + "'.", e);
        }

        realmResource.users().get(userId).roles()
                .realmLevel()
                .add(Collections.singletonList(realmRole));
    }
}
