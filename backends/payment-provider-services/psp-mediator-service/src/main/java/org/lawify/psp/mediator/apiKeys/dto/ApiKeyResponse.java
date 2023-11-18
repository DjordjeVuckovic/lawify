package org.lawify.psp.mediator.apiKeys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiKeyResponse {
    public String key;
    public UUID id;
}
