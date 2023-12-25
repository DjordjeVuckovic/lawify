import "./navbar.scss"
import logo from "../../../assets/logo.png";
import {NavLink} from "./ui/nav-link.tsx";
import {useNavigate} from "react-router-dom";
import useAuthStore from "../../../auth/auth-store.ts";
import {useUserPayload} from "../../../auth/user-hook.ts";
import {toastSuccess} from "../../toast/toast.ts";
import {AiOutlineLogout} from "react-icons/ai";
import {ButtonAsLink} from "./ui/button-as-link.tsx";
import {IoMdLogIn} from "react-icons/io";
import {VscSignIn} from "react-icons/vsc";
import {FaHome} from "react-icons/fa";
import {TbZoomMoney} from "react-icons/tb";

export const Navbar = () => {
    const navigate = useNavigate()
    const user = useUserPayload()
    const {setAccessToken} = useAuthStore()

    const logOut = () => {
        setAccessToken(null)
        navigate('')
        toastSuccess("You have been successfully logged out")
    }

    return (
        <nav className='navbar-wrapper'>
            <section className='padding-base inner-width navbar-container'>
                <div className={'flex-center logo-container'}>
                    <img src={logo} alt={'logo'} className={'image-logo'} loading='eager' onClick={() => navigate('')}/>
                </div>
                <div className='flex-center navbar-menu-middle'>
                    <NavLink url={''}> Home <FaHome size={16}/> </NavLink>
                    {user && <NavLink url={'/payment-providers'}>Your Providers <TbZoomMoney  size={18}/> </NavLink>}
                </div>
                <div className='navbar-menu-end'>
                    {!user && <NavLink url={'/sign-in'}>Sign In <IoMdLogIn  size={16}/> </NavLink>}
                    {!user && <NavLink url={'/sign-up'}>Sign Up <VscSignIn   size={16}/> </NavLink>}
                    {user &&
                        <ButtonAsLink onClick={logOut}>
                            Log out
                            <span className={'button-as-link__icon'}>
                                <AiOutlineLogout size={16}/>
                            </span>
                        </ButtonAsLink>
                    }

                </div>
            </section>
        </nav>
    );
}