import img from '../../assets/payment-success.png';
export const PaymentSuccess = () => {
    return (
        <div className='flex-col-center'>
            <h1 className={'large-text green'}>Payment successfully processed!</h1>
            <img src={img} alt={'img'}/>
        </div>
    );
};
