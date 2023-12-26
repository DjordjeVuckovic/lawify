package org.lawify.psp.mediator.payments;


import org.lawify.psp.mediator.payments.models.PspLineItem;
import org.lawify.psp.mediator.payments.models.UserInfo;

import java.util.List;

public interface IPaymentClient {
    PspPaymentIntend createPaymentIntent(UserInfo userInfo, List<PspLineItem> lineItems);
}
