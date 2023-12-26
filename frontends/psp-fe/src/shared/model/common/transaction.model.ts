import {PaymentOption} from "./payment-option.ts";

export interface TransactionModel {
    id: string;
    orderId: string;
    timeStamp: Date;
    merchantId: string;
    amount: number;
    status: TransactionStatus;
}

export type TransactionStatus = 'PENDING' | 'SUCCESS' | 'FAILED' | "STARTED";

export type GetTransactionResponse = {
    transaction: TransactionModel;
    availableServices: PaymentOption[];
    backAccount: string;
};

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