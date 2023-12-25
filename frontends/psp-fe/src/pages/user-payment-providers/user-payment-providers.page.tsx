import {IoAddCircleOutline} from "react-icons/io5";
import './user-payment-providers.page.scss'
export const UserPaymentProvidersPage = () => {
    return (
        <div className={'padding-base inner-width payment-providers-container'}>
            <div className={'flex-space'}>
                <h1 className={'main-color-text-h1'}>Your payment providers</h1>
                <button type="button" className="payment-providers-btn">
                    Subscribe to new
                    <span className={'button-icon'}>
                            <IoAddCircleOutline  size={24}/>
                        </span>
                </button>
            </div>
        </div>
    );
};
