import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {FaEye, FaEyeSlash} from 'react-icons/fa';
import {SignInForm} from "../../auth/auth.model.ts";
import {FieldError} from "../../shared/components/errors/field-error.tsx";
import "./sign-in.page.scss";
import logo from '../../assets/logo.png'
import {IoMdLogIn} from "react-icons/io";
import {handleError} from "../../utils/handle-error.ts";
import {AxiosError} from "axios";
import {useMutation} from "@tanstack/react-query";
import {signIn} from "../../auth/auth-service.ts";
import useAuthStore from "../../auth/auth-store.ts";
import {useNavigate} from "react-router-dom";
import {toastError, toastSuccess} from "../../shared/toast/toast.ts";

export const SignInPage = () => {
    const {setAccessToken} = useAuthStore()
    const navigate = useNavigate()
    const {
        register,
        handleSubmit,
        formState: {errors},
    } = useForm<SignInForm>({mode: 'onTouched'})
    const [passwordShown, setPasswordShown] = useState(false);
    const mutation = useMutation({
        mutationFn: signIn,
        onError: (error: AxiosError) => {
            console.log(error)
            toastError(handleError(error))
        },
        onSuccess: (data: { token: string }) => {
            toastSuccess('You are successfully signed in!')
            setAccessToken(data.token)
            navigate('/')
        }
    });
    const onSubmit = (signInRequest: SignInForm) => {
        console.log(signInRequest);
        mutation.mutate({
            mail: signInRequest.username,
            password: signInRequest.password
        })
    };

    const togglePasswordVisibility = () => {
        setPasswordShown(passwordShown => !passwordShown);
    };

    return (
        <div>
            <div className={'sign-container'}>
                <form onSubmit={handleSubmit(onSubmit)} className="sign-form">
                    <div className={'logo-section'}>
                        <h2>Sign In</h2>
                        <img src={logo} alt={'logo'}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="username">Username</label>
                            <input
                                className={'form-group-input'}
                                name="username"
                                {...register('username', {
                                    required: {
                                        value: true,
                                        message: 'Email is required'
                                    }
                                })}
                                placeholder="Enter your username"
                            />
                        {errors.username?.message && (
                            <FieldError error={errors.username.message}/>
                        )}
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <div className={'input-group'}>
                            <input
                                className={'form-group-input'}
                                name="password"
                                type={passwordShown ? "text" : "password"}
                                {...register('password', {
                                    required: {
                                        value: true,
                                        message: 'Password is required'
                                    }
                                })}
                                placeholder="Enter your password"
                            />
                            <i onClick={togglePasswordVisibility}>
                                {passwordShown ? <FaEyeSlash size={24}/> : <FaEye size={24}/>}
                            </i>
                        </div>
                        {errors.password?.message && (
                            <FieldError error={errors.password.message}/>
                        )}
                    </div>

                    <button type="submit" className="sign-button">
                        Sign In
                        <span className={'button-icon'}>
                            <IoMdLogIn size={24}/>
                        </span>
                    </button>
                    <button onClick={() => navigate('/sign-up')}
                            className={'link-sign'}>
                        Dont have an account yet? Sign Up
                    </button>
                </form>
            </div>
        </div>
    );
};