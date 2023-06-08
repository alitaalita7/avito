import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./SettingsProfile.css"
import myImage from "../../../image/set.png"
import Card from "../../Cards/Card/Card";

const SettingsProfile = () => {

    const user = JSON.parse(localStorage.getItem("userInfo"));

    const [editName, setEditName] = useState(false);
    const [editSurname, setEditSurname] = useState(false);
    const [editNumber, setEditNumber] = useState(false);
    const [editPassword, setEditPassword] = useState(false);

    const [nameValue, setNameValue] = useState('');
    const [surnameValue, setSurnameValue] = useState('');
    const [numberValue, setNumberValue] = useState('');
    const [passwordValue, setPasswordValue] = useState('');
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

    const handleNameChange = (event) => {
        setNameValue(event.target.value);
    };
    const handleSurnameChange = (event) => {
        setSurnameValue(event.target.value);
    };
    const handleNumberChange = (event) => {
        setNumberValue(event.target.value);
    };
    const handlePasswordChange = (event) => {
        setPasswordValue(event.target.value);
    };

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            const column = event.target.name
            const value = event.target.value
            fetch('http://localhost:8080/edit-user', {
                method: 'POST',
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({user_id: user.id, column: column, value: value})
            })
                .then(res => res.text())
                .then(res => console.log(res))
            setEditName(false);
            setEditSurname(false);
            setEditNumber(false);
            setEditPassword(false);
        }
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
                        {editName ? (<input className={"set-input"}
                                            type="text"
                                            name={"name"}
                                            onClick={handleNameChange}
                                            onKeyDown={handleKeyDown}
                                    />)
                                : (<p className={"value"}>{user.name}</p>)}
                        <img src={myImage} alt="My Image" onClick={handleEditName}/>
                    </div>
                    <div className={"set"}>
                        <p>Фамилия:</p>
                        {editSurname ? (<input className={"set-input"}
                                               type="text"
                                               name={"surname"}
                                               onClick={handleSurnameChange}
                                               onKeyDown={handleKeyDown}
                                        />)
                                    : (<p className={"value"}>{user.surname}</p>)}
                        <img src={myImage} alt="My Image" onClick={handleEditSurname}/>
                    </div>
                    <div className={"set"}>
                        <p>Телефон:</p>
                        {editNumber ? (<input className={"set-input"}
                                              type="text"
                                              name={"phone"}
                                              onClick={handleNumberChange}
                                              onKeyDown={handleKeyDown}
                                    />)
                                    : (<p className={"value"}>{user.phone}</p>)}
                        <img src={myImage} alt="My Image" onClick={handleEditNumber}/>
                    </div>
                    <div className={"set"}>
                        <p>Пароль:</p>
                        {editPassword ? (<input className={"set-input"}
                                                type="password"
                                                name={"password"}
                                                onClick={handlePasswordChange}
                                                onKeyDown={handleKeyDown}
                                        />)
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