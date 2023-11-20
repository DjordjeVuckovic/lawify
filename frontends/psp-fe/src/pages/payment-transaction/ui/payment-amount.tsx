import {MdPayment} from "react-icons/md";

export const PaymentAmount = ({amount}: { amount: number }) => {
    return (
        <div className={'flex-center g-5'}>
            <MdPayment color={'rgba(42, 67, 248, 1)'} size={30}/>
            <div>You will need to pay <span className={'main-color-text'}>{amount} €</span>.&nbsp;&nbsp;Do you want to continue?</div>
        </div>
    );
};
