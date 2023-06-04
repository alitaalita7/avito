import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./SettingsProfile.css"
import myImage from "../../../image/set.png"
import Card from "../../Cards/Card/Card";

const SettingsProfile = () => {

    const user = JSON.parse(localStorage.getItem("userInfo"));
    console.log(user)

    const [editName, setEditName] = useState(false);
    const [editSurname, setEditSurname] = useState(false);
    const [editNumber, setEditNumber] = useState(false);
    const [editPassword, setEditPassword] = useState(false);
    const handleEditName = () => {
        setEditName(!editName);
    };
    const handleEditSurname = () => {
        setEditSurname(!editSurname);
    };
    const handleEditNumber = () => {
        setEditNumber(!editNumber);
    };
    const handleEditPassword = () => {
        setEditPassword(!editPassword);
    };

    return (
        <>
            <UserInfo/>
            <div className={"container"}>
                <div className={"set-page-name"}>
                    <h1>Настройки профиля</h1>
                </div>
                <div className={"set-container"}>
                    <div className={"set"}>
                        <p>Имя:</p>
                        {editName ? (<input className={"set-input"} type="text"/>)
                                : (<p className={"value"}>{user.name}</p>)}
                        <img src={myImage} alt="My Image" onClick={handleEditName}/>
                    </div>
                    <div className={"set"}>
                        <p>Фамилия:</p>
                        {editSurname ? (<input className={"set-input"} type="text"/>)
                                    : (<p className={"value"}>{user.surname}</p>)}
                        <img src={myImage} alt="My Image" onClick={handleEditSurname}/>
                    </div>
                    <div className={"set"}>
                        <p>Телефон:</p>
                        {editNumber ? (<input className={"set-input"} type="text"/>)
                                    : (<p className={"value"}>{user.phone}</p>)}
                        <img src={myImage} alt="My Image" onClick={handleEditNumber}/>
                    </div>
                    <div className={"set"}>
                        <p>Пароль:</p>
                        {editPassword ? (<input className={"set-input"} type="password"/>)
                                    : (<p className={"value"}>{user.password.replace(/./g, "*")}</p>)}
                        <img src={myImage} alt="My Image" onClick={handleEditPassword}/>
                    </div>
                </div>
                <button className={"remove-profile"}>Удалить профиль</button>
            </div>
        </>
    )
}


export default SettingsProfile