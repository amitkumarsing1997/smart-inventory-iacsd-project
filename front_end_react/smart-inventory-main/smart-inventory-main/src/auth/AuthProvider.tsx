import React, { ReactNode, useMemo, useState } from "react";
import { Constant } from "../constants/Constants";
import { AuthResponse } from "../model";
import {AuthContext} from "./AuthContext";

//auth provider
export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [accessToken, setAccessToken] = useState<string>('');

    const isLogin = (callback: VoidFunction) => {
        const accessToken = localStorage.getItem(Constant.ACCESS_TOKEN);
        if (accessToken) {
            setAccessToken(accessToken);
            callback();
        }
    }

    const signIn = (authResponse: AuthResponse, callback: VoidFunction) => {
        setAccessToken(authResponse.accessToken);
        localStorage.setItem(Constant.ACCESS_TOKEN, authResponse.accessToken);
        localStorage.setItem(Constant.REFRESH_TOKEN, authResponse.refreshToken);
        localStorage.setItem(Constant.UID, authResponse.moreInfo[Constant.UID]);
        localStorage.setItem(Constant.USERNAME, authResponse.moreInfo[Constant.USERNAME]);
        callback();
    };

    const signOut = (callback: VoidFunction) => {
        setAccessToken('');
        localStorage.removeItem(Constant.ACCESS_TOKEN);
        localStorage.removeItem(Constant.REFRESH_TOKEN);
        localStorage.removeItem(Constant.UID);
        localStorage.removeItem(Constant.USERNAME);
        callback();
    };

    const value = useMemo(
        () => ({ accessToken, isLogin, signIn, signOut}),
        [accessToken]
    );

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};