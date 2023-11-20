import {PaymentOption} from "../../../shared/model/common/payment-option.ts";

export const paymentOptions: PaymentOption[] = [
    {
        imageUrl: "https://wedoblob.blob.core.windows.net/psp/paypal.png",
        id: "1",
        name: "PayPal",
    },
    {
        imageUrl: "https://wedoblob.blob.core.windows.net/psp/crypto-bitcoin.png",
        id: "2",
        name: "Crypto Bitcoin",
    },
    {
        imageUrl: "https://wedoblob.blob.core.windows.net/psp/card.png",
        id: "3",
        name: "Card",
    },
    {
        imageUrl: "https://wedoblob.blob.core.windows.net/psp/qr.png",
        id: "4",
        name: "QrCode"
    },
]