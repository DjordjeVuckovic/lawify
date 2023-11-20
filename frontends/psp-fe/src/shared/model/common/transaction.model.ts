export interface TransactionModel {
    id: string;
    orderId: string;
    timeStamp: Date;
    merchantId: string;
    amount: number;
    status: string;
}

export interface TransactionRequest {
    transactionId: string;
    subscriptionId: string;
}

export interface TransactionResponse {
    timeStamp: Date;
    redirectUrl: string;
    correlationId: string;
    appName: string;
    bankService: boolean
}