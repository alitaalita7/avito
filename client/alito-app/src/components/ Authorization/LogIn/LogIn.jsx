import React from "react";
import {json, Link, useNavigate} from "react-router-dom";
import "./LogIn.css"

const LogIn =({setIsLogIn})=>{

    const navigate = useNavigate();
    const isSign=(event)=>{
        event.preventDefault();
        const phone = event.target.elements.phone.value
        const password = event.target.elements.password.value

        fetch("http://localhost:8080/auth", {
            method: "POST",
            headers: { 'content-type': "application/json" },
            body: JSON.stringify({ phone: phone, password: password })
        })
            .then(res => {
                if (res.ok) {
                    return res.json();
                } else {
                    throw new Error("Неправильный телефон или пароль");
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


    return(
        <div className={"auto-container"}>
            <h1 className={"login-page-name"}>Вход</h1>
            <form onSubmit={isSign}>
                <div className={"input-block"}>
                    <label htmlFor="phone">Телефон:</label>
                    <input name={"phone"} id={"phone"} type="text" required />
                </div>
                <div className={"input-block"}>
                    <label htmlFor="password">Пароль:</label>
                    <input name={"password"} id={"password"} type="password" required />
                </div>
                <button type="submit" className={"auto-button"}>Войти</button>
            </form>
            <p className={"error"}>Неправильный телефон или пароль</p>
            <Link to={'/signup'} className={"auto-link"}>Зарегистрироваться</Link>
        </div>

    )
}

export default LogIn