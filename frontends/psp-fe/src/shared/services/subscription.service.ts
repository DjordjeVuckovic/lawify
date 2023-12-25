import {httpClient} from "../../http-client/http-client.ts";
import {PaymentIntent, PaymentOption, SubscriptionPaymentRequest} from "../model/common/payment-option.ts";

export const fetchSubscriptions= async (): Promise<PaymentOption[]> => {
    const response =
        await httpClient.get(`/psp-mediator-service/api/v1/subscriptions`);
    return response.data;
}

export const fetchSubscriptionsByUser = async (userId: string): Promise<PaymentOption[]> => {
    const response =
        await httpClient.get(`/psp-mediator-service/api/v1/subscriptions/users/${userId}`);
    return response.data;
}
export const fetchAvailableSubscriptionsByUser = async (userId: string): Promise<PaymentOption[]> => {
    const response =
        await httpClient.get(`/psp-mediator-service/api/v1/subscriptions/available/users/${userId}`);
    return response.data;
}

export const processNewSubscriptionPayment = async (payment: SubscriptionPaymentRequest): Promise<PaymentIntent> => {
    const response =
        await httpClient.post(`/psp-mediator-service/api/v1/subscriptions/payment-intent`,payment
        );
    return response.data;
}