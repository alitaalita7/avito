import React, {useEffect, useState} from 'react';
import Header from './components/Header/Header';
import {Route, Routes} from 'react-router-dom';
import Main from './components/Main'
import NewAd from './components/AddAdvertisement/NewAd'
import SelectedCard from "./components/Cards/SelectedCard/SelectedCard";
import MyAdvertisements from "./components/user/MyAdvertisements/MyAdvertisements";
import Reviews from "./components/user/Reviews/Reviews";
import Favorites from "./components/user/Favorites/Favorites";
import SettingsProfile from "./components/user/SettingsProfile/SettingsProfile";
import LogIn from "./components/ Authorization/LogIn/LogIn";
import SignUp from "./components/ Authorization/SignUp/SignUp";

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
                <Route path='/my-ads' element={<MyAdvertisements/>}/>
                <Route path={'/reviews'} element={<Reviews/>}/>
                <Route path={'/favorites'} element={<Favorites/>}/>
                <Route path={'/settings'} element={<SettingsProfile/>}/>
                <Route path={'/login'} element={<LogIn setIsLogIn={setIsLogIn}/>}/>
                <Route path={'/signup'} element={<SignUp setIsLogIn={setIsLogIn}/>}/>
            </Routes>
        </div>
    );
}

export default App;

