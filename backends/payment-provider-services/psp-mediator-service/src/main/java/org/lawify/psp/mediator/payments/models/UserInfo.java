package org.lawify.psp.mediator.payments.models;

import lombok.Builder;

import java.util.UUID;

@Builder
public class UserInfo {
    public UUID id;
    public String email;
}
