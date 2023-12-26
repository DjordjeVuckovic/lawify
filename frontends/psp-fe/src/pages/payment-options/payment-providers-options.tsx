import {Fragment, useState} from "react";
import {PaymentOption} from "../../shared/model/common/payment-option.ts";
import {PaymentCard} from "../../shared/components/payment-card/payment-card.tsx";
import './payment-options.scss'
import {BiEuro} from "react-icons/bi";

type PaymentOptionWithSelected = PaymentOption & { isSelected: boolean }

export interface PaymentOptionsProps {
    paymentOptions: PaymentOption[]
    onSelection: (payments: PaymentOption[]) => void
}

export const PaymentProvidersOptions = ({paymentOptions, onSelection}: PaymentOptionsProps) => {
    const paymentOptionWithSelected = paymentOptions.map(x => ({...x, isSelected: false}))
    const [selectedPayments, setSelectedPayments] = useState<PaymentOptionWithSelected[]>([]);
    const selectPaymentMethod = (method: PaymentOptionWithSelected) => {
        const exists = selectedPayments?.some(x => x.id === method.id);
        const payments = !exists ?
            [...selectedPayments, {...method, isSelected: true}]
            : selectedPayments.map(x => x.id === method.id ? {...x, isSelected: !x.isSelected} : x);
        onSelection(payments.filter(x => x.isSelected))
        setSelectedPayments(payments);
    };
    return (
        <Fragment>
            <div className="payment-options-wrapper">
                <div className="payments-list">
                    {paymentOptionWithSelected.map((p, i) => (
                        <div key={i} className={'flex-col-center g-5'}>
                            <span className={'main-color-text flex-center g-3'}>{p.price} <BiEuro size={24}/> </span>
                            <PaymentCard key={i}
                                         onClick={() => selectPaymentMethod(p)}
                                         isSelected={selectedPayments?.some(x => x.id === p.id && x.isSelected)}
                                         payment={p}
                            />
                        </div>

                        )
                    )}
                </div>
            </div>
        </Fragment>
    );
};
