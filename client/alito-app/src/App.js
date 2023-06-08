import React, {useEffect, useState} from 'react';
import Header from './components/Header/Header';
import {Route, Routes} from 'react-router-dom';
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

function App() {

    const [isLogIn, setIsLogIn] = useState(false);
    useEffect(() => {
        const userInfo = JSON.parse(localStorage.getItem('userInfo'));
        if(userInfo) setIsLogIn(true);
    }, [])

    return (
        <div className="App">
            <Header isLoggedIn={isLogIn} setIsLogIn={setIsLogIn}/>
            <Routes>
                <Route path='/' element={[<Main key={1}/>]}/>
                <Route path='/new-addvertisements' element={<NewAd/>}/>
                <Route path='/ad/:id' element={<SelectedCard/>}/>
                <Route path='/my-ads' element={<UserAdvertisements/>}/>
                <Route path={'/profile/:id/reviews'} element={<Reviews/>}/>
                <Route path={'/profile/:id/favorites'} element={<Favorites/>}/>
                <Route path={'/profile/:id/settings'} element={<SettingsProfile/>}/>
                <Route path={'/login'} element={<LogIn setIsLogIn={setIsLogIn}/>}/>
                <Route path={'/signup'} element={<SignUp setIsLogIn={setIsLogIn}/>}/>
                <Route path={'/edit-advertisements/:id'} element={<EditCard/>}/>
                <Route path={'/profile/:id/ads'} element={<UserAdvertisements/>}/>
                <Route path={'/add-review'}></Route>
            </Routes>
        </div>
    );
}

export default App;

