import paymentImg from '../../assets/payment-home.png';
import './home.page.scss';
export const HomePage = () => {
    return (
        <div className="landing-container">
            <header className="landing-header">
                <h1>Welcome to Our Payment Service</h1>
                <p>Secure, reliable, and efficient payment solutions for your business.</p>
            </header>
            <div>
                <img src={paymentImg} alt='img' />
            </div>
        </div>
    );
};
