import {httpClient} from "../../http-client/http-client.ts";
import {PaymentOption} from "../model/common/payment-option.ts";

export const fetchSubscriptions= async (): Promise<PaymentOption[]> => {
    const response =
        await httpClient.get(`/psp-mediator-service/api/v1/subscriptions`);
    return response.data;
}