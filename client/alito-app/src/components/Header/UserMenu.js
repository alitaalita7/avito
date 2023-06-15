import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import './UserMenu.css'

function UserMenu({setIsLogIn, onClose}) {

  const [reviewInfo, setReviewInfo] = useState({});

  useEffect(()=>{
    fetch("http://localhost:8080/get-review-info-by-user",{
      method:"POST",
      headers:{'content-type': "application/json"},
      body: JSON.stringify({id: JSON.parse(localStorage.getItem("userInfo")).id})
    })
        .then(res=> res.json())
        .then(res=> setReviewInfo(res))
  }, [])

  const logOut=()=>{
    localStorage.removeItem("userInfo")
    setIsLogIn(false)
  }

  return (
    <div className="user-menu">
      <ul>
        <li>
            {reviewInfo.avg?
                <p>{reviewInfo.avg.toFixed(1) + " (из " + reviewInfo.count + " отзывов)"}</p>
                : <p>Нет отзывов</p>}
        </li>
        <li>
          <Link to={"/profile/" + JSON.parse(localStorage.getItem("userInfo")).id + "/ads"} onClick={onClose}>Мои объявления</Link>
        </li>
        <li>
          <Link to={"/profile/" + JSON.parse(localStorage.getItem("userInfo")).id + "/favorites"} onClick={onClose}>Избранное</Link>
        </li>
        <li>
          <Link to={"/profile/" + JSON.parse(localStorage.getItem("userInfo")).id + "/reviews"} onClick={onClose}>Отзывы</Link>
        </li>
        <li>
          <Link to={"/profile/" + JSON.parse(localStorage.getItem("userInfo")).id + "/settings"} onClick={onClose}>Настройки профиля</Link>
        </li>
        <li>
          <Link to="/login" onClick={()=>{onClose(); logOut()} } >Выход</Link>
        </li>
        {JSON.parse(localStorage.getItem("userInfo")).is_admin === true &&
            <li>
              <Link to="/admin" onClick={onClose} >Админ-панель</Link>
            </li>
        }
      </ul>
    </div>
  );
}

export default UserMenu;
