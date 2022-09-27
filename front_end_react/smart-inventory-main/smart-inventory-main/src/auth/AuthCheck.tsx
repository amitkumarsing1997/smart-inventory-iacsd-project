import React from 'react';
import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "./AuthHook";

export const AuthCheck = ({children}: {children: JSX.Element}) => {
    const auth = useAuth();
    let location = useLocation();

    // console.log('Access token :: ', auth.accessToken)

    if (!auth.accessToken) {
        return <Navigate to={"/"} state={location} replace/>
    }

    return children;
}