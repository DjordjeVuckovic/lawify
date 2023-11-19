import "./payment-card.scss"
import {PaymentOption} from "../../model/common/payment-option.ts";
export const PaymentCard = ({payment,isSelected, onClick} :
                                {payment:PaymentOption,isSelected: boolean, onClick: () => void}) => {
    const cardClass = isSelected ?
        "payment-card active"
        : "payment-card";
    return (
        <>
        <div className={cardClass} onClick={onClick}>
           <img className="img"
               alt="Payment picture"
                src={payment.image}/>
        </div>
        </>
    );
};
