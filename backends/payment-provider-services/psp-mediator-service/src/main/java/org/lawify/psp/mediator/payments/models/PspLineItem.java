package org.lawify.psp.mediator.payments.models;

import lombok.Builder;

import java.util.UUID;
@Builder
public class PspLineItem {
    public UUID id;
    public double price;
    public String name;
}
