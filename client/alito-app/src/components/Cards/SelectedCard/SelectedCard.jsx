import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import ".//SelectedCard.css"
import editImage from "../../../image/edit.svg"
import deleteImage from "../../../image/delete.svg"
import userInfo from "../../user/UserInfo/UserInfo";
import card from "../Card/Card";
const SelectedCard = () => {
    const { id } = useParams();
    const [cardData, setCardData] = useState({});
    const [isFavourite, setIsFavourite] = useState(false);
    const [reviewInfo, setReviewInfo] = useState({});
    const [userInfo, setUserInfo] = useState({});
    const [showFullPhone, setShowFullPhone] = useState(false);

    const handleFavouriteClick = () => {
        setIsFavourite(!isFavourite);
        console.log("Добавлено в избранное");
    };

    const handleShowPhoneClick = () => {
        setShowFullPhone(!showFullPhone);
    };

    useEffect(() => {
        fetch("http://localhost:8080/get-advertisement-by-id", {
            method: "POST",
            headers: { 'content-type': "application/json" },
            body: JSON.stringify({ id: id })
        })
            .then(res => res.json())
            .then(res => setCardData(res));

        fetch("http://localhost:8080/get-review-info-by-ad", {
            method: "POST",
            headers: { 'content-type': "application/json" },
            body: JSON.stringify({ id: id })
        })
            .then(res => res.json())
            .then(res => setReviewInfo(res));

        fetch("http://localhost:8080/get-user-info-by-ad", {
            method: "POST",
            headers: { 'content-type': "application/json" },
            body: JSON.stringify({ id: id })
        })
            .then(res => res.json())
            .then(res => setUserInfo(res));

        const userId = JSON.parse(localStorage.getItem("userInfo")).id;

        fetch(`http://localhost:8080/is-favorite-exist`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({user_id: userId, ad_id: id}),
        })
            .then(res=>res.text())
            .then(res=>{
                if(res==='yes') setIsFavourite(true)
            })
    }, [id]);

    const handleFavourite = () => {
        if (isFavourite) {
            removeFromFavorite(cardData.id);
        } else {
            addToFavorite(cardData.id);
        }
    };
    const addToFavorite = (id) => {
        const userId = JSON.parse(localStorage.getItem("userInfo")).id;
        fetch("http://localhost:8080/add-to-favorite", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({user_id: userId, ad_id: id}),
        })
            .then((res) => {
                setIsFavourite(true);
            })
            .catch((error) => {
                console.error("Ошибка при добавлении в избранное:", error);
            });
    };
    const removeFromFavorite = (id) => {
        const userId = JSON.parse(localStorage.getItem("userInfo")).id;
        fetch(`http://localhost:8080/delete-from-favorites`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({user_id: userId, ad_id: id}),
        })
            .then(res => res.json())
            .then((res) => {
                setIsFavourite(false);
            })
    };

    const navigate = useNavigate();
    const handleNavigateCategory = () => {
        navigate("/", {
            state:{category: cardData.category}
        });
    };
    const isMyAd =()=>{
        const user = JSON.parse(localStorage.getItem("userInfo")).id
        if(cardData.user_id==user){
            return true
        }
        else return false
    }
    const handleNavigateToEdit = () =>{
        navigate("/edit-advertisements/" + cardData?.id)
    }

    const handleNavigateProfile = () => {
        navigate("/profile/" + userInfo.id + '/ads')
    }

    return (
        <div className="page-container">
            <div className="ad-block">
                <div onClick={handleNavigateCategory} className="category">{cardData.category}</div>
                <h1 className="title">{cardData.title}</h1>
                <div className={"button-group"}>
                    <button onClick={handleFavourite} className="favorite-button">
                        <span>{
                            isFavourite? '💙' : '🤍'
                        }</span>
                        Добавить в избранное
                    </button>
                    {isMyAd()&&
                        <button className={"edit-button-ad"}  onClick={handleNavigateToEdit} >
                            <img src={editImage}/>
                        </button>}
                    {isMyAd()&&
                        <button className={"delete-button-ad"}>
                            <img src={deleteImage}/>
                        </button>}
                </div>
                <div className="image-container">
                    <img src={cardData.photo} alt={cardData.title} className="image" />
                </div>
                <div className="address">
                    <h2 className="address-label">Адрес:</h2>
                    <span className="address-text">
            г. {cardData.city}, р-н {cardData.district}, ул. {cardData.street}, д. {cardData.house}
          </span>
                </div>
                <div className="description">
                    <h2 className="description-label">Описание:</h2>
                    <span className="description-text">{cardData.description}</span>
                </div>
                <div className="details">
                    <span className="date">{cardData.date_created} •  </span>
                    <span className="views">Просмотров: 15  •  </span>
                    <span className="favorites">В избранном: 3</span>
                </div>
            </div>
            <div className="fast-block">
                <h1 className="price">{cardData.price} Р</h1>
                <button className="show-phone-button" onClick={handleShowPhoneClick}>
                    <span>Показать телефон</span>
                    <span>{showFullPhone ? userInfo.phone : '+7 ХХХ XXX XX XX'}</span>
                </button>
                <div className="seller-info" onClick={handleNavigateProfile}>
                    <div>
                        <div className="seller-name">{userInfo.name}</div>
                        <div className="seller-description">
                            {reviewInfo.avg ?
                                <div>{reviewInfo.avg + " (из " + reviewInfo.count + " отзывов)"}</div>
                                : <div>Нет отзывов</div>}
                        </div>
                    </div>
                    <div className={"seller-photo"}>
                        <h1>{userInfo.name?.slice(0, 1)}</h1>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SelectedCard