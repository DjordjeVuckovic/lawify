import "./navbar.scss"
import logo from "../../../assets/logo.png";
import {NavLink} from "./ui/nav-link.tsx";
import {useNavigate} from "react-router-dom";
export const Navbar = () => {
    const navigate = useNavigate()
    return (
     <nav className='navbar-wrapper'>
         <section className='padding-base inner-width navbar-container'>
             <div className={'flex-center logo-container'}>
                 <img src={logo} alt={'logo'} className={'image-logo'} loading='eager' onClick={() => navigate('')}/>
             </div>
             <div className='flex-center navbar-menu-middle'>
                 <NavLink title={'Payments'} url={'/payments'}/>
             </div>
             <div className='navbar-menu-end'>
                 <NavLink title={'Sign In'} url={'/sign-in'}/>
                 <NavLink title={'Sign Up'} url={'/sign-up'}/>
             </div>
         </section>
     </nav>
    );
}