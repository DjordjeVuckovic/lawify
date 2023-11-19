package org.lawify.psp.mediator.apiKeys.dto;

import java.util.Date;
import java.util.UUID;

public class ApiKeyRequest {
    public UUID merchantId;
    public Date expiresAt;
}
