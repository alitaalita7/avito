import React from "react";
import {Link, useNavigate} from "react-router-dom";
import "./SignUp.css"

const SignUp = ({setIsLogIn}) => {

    const navigate = useNavigate();
    const addUser = (event) => {
        event.preventDefault();
        const name = event.target.elements.name.value
        const surname = event.target.elements.surname.value
        const phone = event.target.elements.phone.value
        const password = event.target.elements.password.value

        fetch("http://localhost:8080/addUser", {
            method: "POST",
            headers: {'content-type': "application/json"},
            body: JSON.stringify({name: name, surname: surname, phone: phone, password: password})
        })
            .then(res => {
                if (res.ok) {
                    return res.json();
                } else {
                    throw new Error("Пользователь с таким номером телефона уже существует");
                }
            })
            .then(res => {
                localStorage.setItem("userInfo", JSON.stringify(res));
                setIsLogIn(true);
                navigate("/")
            })
            .catch(error => {
                document.querySelector(".error").style.display = "flex";
                console.log(error);
            });
    }

    return (
        <div className={"auto-container"}>
            <h1 className={"auto-page-name"}>Регистрация</h1>
            <form onSubmit={addUser}>
                <div className={"input-block"}>
                    <label htmlFor="name">Имя:</label>
                    <input name={"name"} id={"name"} type="text" required/>
                </div>
                <div className={"input-block"}>
                    <label htmlFor={"surname"}>Фамилия:</label>
                    <input name={"surname"} id={"surname"} type="text" required/>
                </div>
                <div className={"input-block"}>
                    <label htmlFor="phone">Телефон:</label>
                    <input name={"phone"} id={"phone"} type="text" required/>
                </div>
                <div className={"input-block"}>
                    <label htmlFor="password">Пароль:</label>
                    <input name={"password"} id={"password"} type="password" required/>
                </div>
                <button type="submit" className={"auto-button"}>Зарегистрироваться</button>
            </form>
            <p className={"error"}>Пользователь с таким номером телефона уже существует</p>
            <Link to={'/login'} className={"auto-link"}>Уже есть аккаунт? Войдите</Link>
        </div>

    )
}

export default SignUp