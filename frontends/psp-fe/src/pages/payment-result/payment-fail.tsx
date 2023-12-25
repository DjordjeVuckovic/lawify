import img from "../../assets/payment-fail.png";


export const PaymentFail = () => {
    return (
        <div className='flex-col-center padding-base'>
            <h1 className={'large-text green'}>You payment failed!</h1>
            <img src={img} alt={'img'}/>
        </div>
    );
};
