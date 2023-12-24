import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {FaEye, FaEyeSlash} from 'react-icons/fa';
import {SignUpForm} from "../../auth/auth.model.ts";
import {FieldError} from "../../shared/components/errors/field-error.tsx";
import "./sign-in.page.scss";
import logo from '../../assets/logo.png'
import {IoMdLogIn} from "react-icons/io";
import {handleError} from "../../utils/handle-error.ts";
import {AxiosError} from "axios";
import {useMutation} from "@tanstack/react-query";
import {signUp} from "../../auth/auth-service.ts";
import useAuthStore from "../../auth/auth-store.ts";
import {useNavigate} from "react-router-dom";
import {toastError, toastSuccess} from "../../shared/toast/toast.ts";
import {BsBank} from "react-icons/bs";

export const SignUpPage = () => {
    const {setAccessToken} = useAuthStore()
    const navigate = useNavigate()
    const {
        register,
        handleSubmit,
        formState: {errors},
    } = useForm<SignUpForm>({mode: 'onTouched'})
    const [passwordShown, setPasswordShown] = useState(false);
    const [passwordConfirmShown, setConfirmPasswordShown] = useState(false);
    const mutation = useMutation({
        mutationFn: signUp,
        onError: (error: AxiosError) => {
            console.log(error)
            toastError(handleError(error))
        },
        onSuccess: (data: { token: string }) => {
            toastSuccess('You are successfully signed up!')
            setAccessToken(data.token)
            navigate('/')
        }
    });
    const onSubmit = (signUpForm: SignUpForm) => {
        console.log(signUpForm);
        if (signUpForm.password !== signUpForm.confirmPassword) {
            toastError('Passwords do not match')
            return;
        }
        mutation.mutate({
            name: signUpForm.name,
            password: signUpForm.password,
            bankAccount: signUpForm.bankAccount,
            username: signUpForm.username
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
                        <h2>Sign Up</h2>
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
                        <label htmlFor="name">Application name</label>
                        <input
                            className={'form-group-input'}
                            name="name"
                            {...register('name', {
                                required: {
                                    value: true,
                                    message: 'Application name is required'
                                }
                            })}
                            placeholder="Enter your application name"
                        />
                        {errors.name?.message && (
                            <FieldError error={errors.name.message}/>
                        )}
                    </div>

                    <div className="form-group">
                        <label htmlFor="acc">Bank account</label>
                        <div className={'input-group'}>
                            <input
                                className={'form-group-input'}
                                name="acc"
                                {...register('bankAccount', {
                                    required: {
                                        value: true,
                                        message: 'Bank account is required'
                                    }
                                })}
                                placeholder="Enter your bank account"
                            />
                            <i>
                                {<BsBank />}
                            </i>
                        </div>
                        {errors.bankAccount?.message && (
                            <FieldError error={errors.bankAccount.message}/>
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

                    <div className="form-group">
                        <label htmlFor="confirmPassword">Confirm Password</label>
                        <div className={'input-group'}>
                            <input
                                className={'form-group-input'}
                                name="confirmPassword"
                                type={passwordConfirmShown ? "text" : "password"}
                                {...register('confirmPassword', {
                                    required: {
                                        value: true,
                                        message: 'Password is required'
                                    }
                                })}
                                placeholder="Enter your confiremd password"
                            />
                            <i onClick={() => setConfirmPasswordShown(true)}>
                                {passwordConfirmShown ? <FaEyeSlash size={24}/> : <FaEye size={24}/>}
                            </i>
                        </div>
                        {errors.confirmPassword?.message && (
                            <FieldError error={errors.confirmPassword.message}/>
                        )}
                    </div>


                    <button type="submit" className="sign-button">
                        Sign Up
                        <span className={'button-icon'}>
                            <IoMdLogIn size={24}/>
                        </span>
                    </button>
                    <button onClick={() => navigate('/sign-in')}
                            className={'link-sign'}>
                        Already have an account yet? Sign Up
                    </button>
                </form>
            </div>
        </div>
    );
};