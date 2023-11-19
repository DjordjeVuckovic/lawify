import {PaymentCard} from "../../shared/components/payment-card/payment-card.tsx";
import {paymentOptions} from "./mock/payment-options.ts";
import {Fragment, useState} from "react";
import './payment-options.scss'
import {Button} from "../../shared/components/button/button.tsx";
import {PaymentOption} from "../../shared/model/common/payment-option.ts";

export const PaymentOptions = () => {
    const [selectedPayment, setSelectedPayment] = useState<PaymentOption | null>(null);
    const onClick = () => {
        console.log("Clicked button")
    }
    const selectPaymentMethod = (method: PaymentOption) => {
        setSelectedPayment(method);
    };
    return (
        <Fragment>
            <div className="payment-options-wrapper">
                <h2>Choose payment methods:</h2>
                <div className="payments-list">
                    {paymentOptions.map((p, i) =>
                        <PaymentCard key={i}
                                     onClick={() => selectPaymentMethod(p)}
                                     isSelected={selectedPayment === p}
                                     payment={p}/>
                    )}
                </div>
                <Button onClick={onClick}>Proceed</Button>
            </div>
        </Fragment>
    );
};
