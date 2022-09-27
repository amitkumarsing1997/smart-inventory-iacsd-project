import { useContext } from "react";
import { AuthContext } from "./AuthContext";

// auth hook
export const useAuth = () => {
    return useContext(AuthContext);
};