import { createContext } from "react";
import { AuthResponse } from "../model";

interface AuthContextType {
    accessToken: string;
    isLogin: (callback: VoidFunction) => void;
    signIn: (authResponse: AuthResponse, callback: VoidFunction) => void;
    signOut: (callback: VoidFunction) => void;
}

//auth context
export const AuthContext = createContext<AuthContextType>({} as AuthContextType);
