import useAuthStore from "./auth-store.ts";
import {useEffect, useState} from "react";
import {parseJwt} from "../utils/parse-jwt.ts";
import {User} from "../shared/model/users/user.ts";

export const useUserPayload = (): User | null => {
    const accessToken = useAuthStore((state) => state.accessToken);
    const [userPayload, setUserPayload] = useState<User>(null);

    useEffect(() => {
        if(!accessToken){
            setUserPayload(null);
            return;
        }
        const payload = parseJwt(accessToken);
        const user: User = {
            email: payload.email,
            role: payload.role,
            id: payload.sub
        }
        console.log(user)
        setUserPayload(user);
    }, [accessToken]);

    return userPayload;
};