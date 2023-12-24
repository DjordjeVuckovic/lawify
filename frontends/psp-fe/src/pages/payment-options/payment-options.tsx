import {PaymentCard} from "../../shared/components/payment-card/payment-card.tsx";
import {Fragment, useState} from "react";
import './payment-options.scss'
import {Button} from "../../shared/components/button/button.tsx";
import {PaymentOption} from "../../shared/model/common/payment-option.ts";
import {PaymentAmount} from "../payment-transaction/ui/payment-amount/payment-amount.tsx";
export interface PaymentOptionsProps {
    paymentOptions: PaymentOption[]
    amount: number
    onPayment: (paymentId: string) => void
}
export const PaymentOptions = ({onPayment,paymentOptions, amount} : PaymentOptionsProps) => {
    const [selectedPayment, setSelectedPayment] = useState<PaymentOption | null>(null);
    const payments = paymentOptions ?? paymentOptions
    const onClick = () => {
        if (!selectedPayment) {
           return
        }
        onPayment(selectedPayment.id);
    }
    const selectPaymentMethod = (method: PaymentOption) => {
        setSelectedPayment(method);
    };
    return (
        <Fragment>
            <div className="payment-options-wrapper">
                <h2>Choose payment methods:</h2>
                <div className="payments-list">
                    {payments.map((p, i) =>
                        <PaymentCard key={i}
                                     onClick={() => selectPaymentMethod(p)}
                                     isSelected={selectedPayment === p}
                                     payment={p}/>
                    )}
                </div>
                <PaymentAmount amount={amount}/>
                <Button onClick={onClick} disabled={selectedPayment === null}>Proceed</Button>
            </div>
        </Fragment>
    );
};
