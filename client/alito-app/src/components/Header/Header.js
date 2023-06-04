import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import UserMenu from './UserMenu';
import './Header.css'
import Logo from '../../image/Logo.png'

function Header({isLoggedIn, setIsLogIn}) {

    const [isUserMenuOpen, setIsUserMenuOpen] = useState(false);
    const navigate = useNavigate();
    const handleNavigateNewItem = () => {
        navigate("/new-addvertisements")
    }
    const handleNavigateLogIn = () => {
        navigate("/login")
    }
    const handleUserMenuToggle = () => {
        setIsUserMenuOpen(!isUserMenuOpen);
    };

    return (
        <header className="header">
            <Link to="/" className="logo-link">
                <img src={Logo} alt="Logo" className="logo"/>
            </Link>
            {isLoggedIn? (
                <>
                    <button className="button" onClick={handleNavigateNewItem}>
                        Разместить объявление
                    </button>
                    <button className="user-button" onClick={handleUserMenuToggle}>
                        {JSON.parse(localStorage.getItem("userInfo")).name
                            + " "
                            + JSON.parse(localStorage.getItem("userInfo")).surname
                            + ` ᐯ`}
                    </button>
                    {isUserMenuOpen && <UserMenu setIsLogIn={setIsLogIn} onClose={handleUserMenuToggle}/>}
                </>
            ): <button className="user-button" onClick={handleNavigateLogIn}>
                    Войти
                </button>
            }
        </header>
    );
}

export default Header;

