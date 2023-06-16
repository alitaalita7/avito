import React, {useEffect, useState} from "react";
import UserInfo from "../UserInfo/UserInfo";
import "./SettingsProfile.css"
import myImage from "../../../image/set.png"
import Card from "../../Cards/Card/Card";
import {useNavigate, useParams} from "react-router-dom";

const SettingsProfile = ({setIsLogIn}) => {

    const {id} = useParams();
    const navigate = useNavigate()


    const [nameValue, setNameValue] = useState('');
    const [surnameValue, setSurnameValue] = useState('');
    const [passwordValue, setPasswordValue] = useState('');

    const [userInfo, setUserInfo] = useState({})

    const getUserInfo = () => {
        fetch("http://localhost:8080/get-user-info-by-id", {
            method: "POST",
            headers: {'content-type': "application/json"},
            body: JSON.stringify({user_id: id})
        })
            .then(res => res.json())
            .then(res => {
                setUserInfo(res)
                setNameValue(res.name)
                setSurnameValue(res.surname)
                setPasswordValue(res.password)
            })
    }

    const updateUserInfo = async (info) => {

        const response = await fetch(`http://localhost:8080/edit-user/${id}`, {
            method: 'PUT',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(info)
        })
        const res_json = await response.json()
        localStorage.setItem("userInfo", JSON.stringify(res_json))
        setUserInfo(res_json)
    }

    const handleUpdateUserInfo = async (e) => {
        e.preventDefault()
        const inputs = document.querySelectorAll('.set-input')
        const data = {}

        inputs.forEach(item => {
            const field_name = item.name
            if (item.value) {
                data[field_name] = item.value
            } else {
                data[field_name] = item.placeholder
            }
        })

        await updateUserInfo(data)

        alert("Данные успешно обновлены")
    }

    useEffect(() => {
        getUserInfo()
    }, [])

    return (
        <>
            <UserInfo setIsLogIn={setIsLogIn}/>
            <form onSubmit={handleUpdateUserInfo} className={"set-profile-container"}>
                <div className={"set-page-name"}>
                    <h1>Настройки профиля</h1>
                </div>
                <div className={"set-container"}>
                    <div className={"set"}>
                        <p>Имя:</p>
                        <input className="set-input"
                               type="text"
                               name={"name"}
                               value={nameValue}
                               onChange={(event) => setNameValue(event.target.value)}
                        />
                    </div>
                    <div className={"set"}>
                        <p>Фамилия:</p>
                        <input className="set-input"
                               type="text"
                               name={"surname"}
                               value={surnameValue}
                               onChange={(event) => setSurnameValue(event.target.value)}
                        />
                    </div>
                    <div className={"set"}>
                        <p>Пароль:</p>
                        <input className="set-input"
                               type="password"
                               name={"password"}
                               value={passwordValue}
                               onChange={(event) => setPasswordValue(event.target.value)}
                        />
                    </div>
                </div>
                <button type={"submit"} className={"button-confirm"}>Сохранить изменения</button>
            </form>
        </>
    )
}


export default SettingsProfile