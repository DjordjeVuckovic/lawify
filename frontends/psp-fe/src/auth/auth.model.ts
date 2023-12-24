export interface SignInForm {
    username: string;
    password: string;
}

export interface SignInRequest {
    mail: string;
    password: string;
}
export interface SignUpForm {
    username: string;
    password: string;
    confirmPassword: string;
    name: string;
    bankAccount: string;
}
export interface SignUpRequest {
    username: string;
    password: string;
    name: string;
    bankAccount: string;
}

export type AuthResponse = {
    token: string;
    tokenType: string;
    notBeforePolicy: number;
    scope: string;
}