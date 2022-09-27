import { ChakraProvider } from '@chakra-ui/react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import { AuthCheck } from './auth/AuthCheck';
import { AuthProvider } from './auth/AuthProvider';
import { theme } from './config/ThemeExtension';
import { GenerateBill } from './pages/postlogin/bill/GenerateBill';
import { ListBill } from './pages/postlogin/bill/ListBill';
import { ViewBill } from './pages/postlogin/bill/ViewBill';
import { CheckInScreen } from './pages/postlogin/check-in/CheckInScreen';
import { CheckIOHistory } from './pages/postlogin/checkIOHistory/CheckIOHistory';
import { Dashboard } from './pages/postlogin/dashboard/Dashboard';
import { PostloginLayout } from './pages/postlogin/layout/PostloginLayout';
import { ListProduct } from './pages/postlogin/product/ListProduct';
import { SaveProduct } from './pages/postlogin/product/SaveProduct';
import { ListProductCategory } from './pages/postlogin/productCategory/ListProductCategory';
import { UserProfile } from './pages/postlogin/profile/UserProfile';
import { Test } from './pages/postlogin/test';
import { LandingPage } from './pages/prelogin/LandginPage';

import { Login } from './pages/prelogin/Login';
import { Register } from './pages/prelogin/Register';


function App() {

  return (
    <ChakraProvider theme={theme}>
      <BrowserRouter>
        <AuthProvider>
          <Routes>
            <Route index element={<LandingPage/>} />
            <Route path='login' element={<Login />} />
            <Route path='register' element={<Register />} />
            <Route path='postlogin' element={
              <AuthCheck>
                <PostloginLayout />
              </AuthCheck>
            }>
              <Route index element={<Dashboard/>}/>
              <Route path='productcategory' element={<ListProductCategory/>} />
              <Route path='product/list' element={<ListProduct/>} />
              <Route path='product/save' element={<SaveProduct/>} />
              <Route path='check-in' element={<CheckInScreen/>}/>
              <Route path='bill/generate' element={<GenerateBill/>}/>
              <Route path='bill/list' element={<ListBill/>}/>
              <Route path='bill/view/:uid' element={<ViewBill/>}/>
              <Route path='checkiohistory' element={<CheckIOHistory/>}/>
              <Route path='user/profile' element={<UserProfile/>}/>
            </Route>
            <Route path='test' element={<Test/>}/>
          </Routes>
        </AuthProvider>
      </BrowserRouter>
    </ChakraProvider>
  );
}

export default App;
