export interface PaymentOption{
    id: string
    name: string
    imageUrl: string
    price?: number
}
export interface PaymentIntent{
    url: string
}

export interface SubscriptionPaymentRequest {
    userEmail: string;
    subscriptionIds: string[];
}