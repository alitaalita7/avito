import React, {useEffect, useLayoutEffect, useState} from 'react';
import Header from './components/Header/Header';
import {json, Route, Routes, useNavigate} from 'react-router-dom';
import Main from './components/Main'
import NewAd from './components/AddAdvertisement/NewAd'
import SelectedCard from "./components/Cards/SelectedCard/SelectedCard";
import UserAdvertisements from "./components/user/UserAdvertisements/UserAdvertisements";
import Reviews from "./components/user/Reviews/Reviews";
import Favorites from "./components/user/Favorites/Favorites";
import SettingsProfile from "./components/user/SettingsProfile/SettingsProfile";
import LogIn from "./components/ Authorization/LogIn/LogIn";
import SignUp from "./components/ Authorization/SignUp/SignUp";
import EditCard from "./components/Cards/EditCard/EditCard";
import AdReview from "./components/user/AddReview/AdReview";
import ProtectRouter from "./hoc/ProtectRouter";
import 'react-toastify/dist/ReactToastify.css';
import Admin from "./components/Admin/Admin";
import Footer from "./components/Footer/Footer";
import "./App.css"
import About from "./components/About/About";

function App() {

    const [isLogIn, setIsLogIn] = useState(!!localStorage.getItem("userInfo"));
    const userInfo = JSON.parse(localStorage.getItem("userInfo")) || {is_admin:false}

    return (
        <div className="App">
            <Header isLoggedIn={isLogIn} setIsLogIn={setIsLogIn}/>
            <Routes>
                <Route path='/'
                       element={<ProtectRouter isLogin={isLogIn}>
                                    <Main/>
                                </ProtectRouter>}/>
                <Route path='/new-advertisements'
                       element={<ProtectRouter isLogin={isLogIn}>
                                     <NewAd/>
                                </ProtectRouter>}/>
                <Route path='/ad/:id'
                       element={<ProtectRouter isLogin={isLogIn}>
                                    <SelectedCard/>
                                </ProtectRouter>}/>
                <Route path={'/profile/:id/reviews'}
                       element={<ProtectRouter isLogin={isLogIn}>
                                    <Reviews setIsLogIn={setIsLogIn}/>
                                </ProtectRouter>}/>
                <Route path={'/profile/:id/favorites'}
                       element={<ProtectRouter isLogin={isLogIn}>
                                    <Favorites setIsLogIn={setIsLogIn}/>
                                </ProtectRouter>}/>
                <Route path={'/profile/:id/settings'}
                       element={<ProtectRouter isLogin={isLogIn}>
                                    <SettingsProfile setIsLogIn={setIsLogIn}/>
                                </ProtectRouter>}/>
                <Route path={'/login'}
                       element={<LogIn setIsLogIn={setIsLogIn}/>}/>
                <Route path={'/signup'}
                       element={<SignUp setIsLogIn={setIsLogIn}/>}/>
                <Route path={'/edit-advertisements/:id'}
                       element={<ProtectRouter isLogin={isLogIn}><EditCard/></ProtectRouter>}/>
                <Route path={'/profile/:id/ads'}
                       element={<ProtectRouter isLogin={isLogIn}><UserAdvertisements/></ProtectRouter>}/>
                <Route path={'/profile/:id/add-review'}
                       element={<ProtectRouter isLogin={isLogIn}>
                                    <AdReview setIsLogIn={setIsLogIn}/>
                                </ProtectRouter>}/>
                <Route path={'/admin'}
                       element={<ProtectRouter isLogin={userInfo.is_admin} path={"/"}>
                                    <Admin/>
                                </ProtectRouter>}/>
                <Route path={'/about'}
                       element={<ProtectRouter isLogin={isLogIn}>
                                    <About/>
                                </ProtectRouter>}/>
            </Routes>
            {!isLogIn &&
                <Footer/>
            }
        </div>
    );
}

export default App;

