import {redirect, useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import ".//SelectedCard.css"
import editImage from "../../../image/edit.svg"
import archiveImage from "../../../image/archive.png"
import recoveryImage from "../../../image/recovery.png"
import deleteImage from "../../../image/delete.svg"
import userInfo from "../../user/UserInfo/UserInfo";
import card from "../Card/Card";
import {toast, ToastContainer} from "react-toastify";

const SelectedCard = () => {
    const {id} = useParams();
    const [cardData, setCardData] = useState({});
    const [isFavourite, setIsFavourite] = useState(false);
    const [reviewInfo, setReviewInfo] = useState({});
    const [userInfo, setUserInfo] = useState({});
    const [showFullPhone, setShowFullPhone] = useState(false);
    const [countFavorites, setCountFavorites] = useState('');

    const handleShowPhoneClick = () => {
        setShowFullPhone(!showFullPhone);
        navigator.clipboard.writeText(userInfo.phone)
        toast.info("Скопировано в буфер обмена")
    };

    useEffect(() => {
        fetch("http://localhost:8080/get-advertisement-by-id", {
            method: "POST",
            headers: {'content-type': "application/json"},
            body: JSON.stringify({id: id})
        })
            .then(res => res.json())
            .then(res => setCardData(res));

        fetch("http://localhost:8080/get-review-info-by-ad", {
            method: "POST",
            headers: {'content-type': "application/json"},
            body: JSON.stringify({id: id})
        })
            .then(res => res.json())
            .then(res => setReviewInfo(res));

        fetch("http://localhost:8080/get-user-info-by-ad", {
            method: "POST",
            headers: {'content-type': "application/json"},
            body: JSON.stringify({id: id})
        })
            .then(res => res.json())
            .then(res => setUserInfo(res));

        const userId = JSON.parse(localStorage.getItem("userInfo")).id;

        fetch(`http://localhost:8080/is-favorite-exist`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({user_id: userId, ad_id: id}),
        })
            .then(res => res.text())
            .then(res => {
                if (res === 'yes') setIsFavourite(true)
            })

        fetch(`http://localhost:8080/get-count-favorites-by-ad`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({ad_id: id}),
        })
            .then(res => res.json())
            .then(res => setCountFavorites(res))

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
            state: {category: cardData.category}
        });
    };
    const isMyAd = () => {
        const user = JSON.parse(localStorage.getItem("userInfo")).id
        if (cardData.user_id == user) {
            return true
        } else return false
    }
    const isAdmin = () => {
        const admin = JSON.parse(localStorage.getItem("userInfo")).is_admin
        if (admin) {
            return true
        } else return false
    }
    const handleNavigateToEdit = () => {
        navigate("/edit-advertisements/" + cardData?.id)
    }

    const handleNavigateProfile = () => {
        navigate("/profile/" + userInfo.id + '/ads')
    }

    const showConfirmToArchive = () => {
        const con = window.confirm("Вы действительно хотите архивировать это объявление? Другие пользователи его не увидят")
        if (con) {
            fetch(`http://localhost:8080/delete-advertisement-archive`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({ad_id: id}),
            })
                .then((res) => {
                    if(res.status==204)
                        navigate(`/profile/${JSON.parse(localStorage.getItem("userInfo")).id}/ads`)
                })
        }
    }
    const showConfirmToRecovery = () => {
        const con = window.confirm("Вы действительно хотите восстановить это объявление? Его снова увидят другие пользователи.")
        if (con) {
            fetch(`http://localhost:8080/recovery-advertisement`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({ad_id: cardData.id}),
            })
                .then((res) => {
                    if (res.status == 204)
                        navigate(`/profile/${JSON.parse(localStorage.getItem("userInfo")).id}/ads`)
                })
        }
    }

    const showConfirmToDelete = () => {
        const con = window.confirm("Удалить объявление с id = " + (id) + "?")
        if (con) {
            fetch(`http://localhost:8080/admin/delete-advertisement`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({ad_id: cardData.id, admin_id: JSON.parse(localStorage.getItem("userInfo")).id}),
            })
                .then((res) => {
                    if (res.status == 204)
                        navigate(`/admin`)
                })
        }
    }

    return (
        <div className="page-container">
            <div className="ad-block">
                <div onClick={handleNavigateCategory} className="category">{cardData.category}</div>
                <h1 className="title">{cardData.title}</h1>
                <div className={"button-group"}>
                    <button onClick={handleFavourite} className="favorite-button">
                        <span>{
                            isFavourite ? '💙' : '🤍'
                        }</span>
                        Добавить в избранное
                    </button>
                    {isMyAd() &&
                        <button className={"edit-button-ad"} onClick={handleNavigateToEdit}>
                            <img src={editImage}/>
                        </button>}
                    {isMyAd() && cardData.status==="active" &&
                        <button className={"archive-button-ad"} onClick={showConfirmToArchive}>
                            <img className={"archiveImage"} src={archiveImage}/>
                        </button>}
                    {isMyAd() && cardData.status==="archive" &&
                        <button className={"recovery-button-ad"} onClick={showConfirmToRecovery}>
                            <img className={"recoveryImage"} src={recoveryImage}/>
                        </button>}
                    {isAdmin() &&
                        <button className={"recovery-button-ad"} onClick={showConfirmToDelete}>
                            <img className={"recoveryImage"} src={deleteImage}/>
                        </button>}
                </div>
                <div className="image-container">
                    <img src={cardData.photo} alt={cardData.title} className="image"/>
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
                    <span className="favorites">В избранном: {countFavorites}</span>
                </div>
            </div>
            <div className="fast-block">
                <h1 className="price">{cardData.price} Р</h1>
                <button className="show-phone-button" onClick={handleShowPhoneClick}>
                    <span>Показать телефон</span>
                    <span id={"phone"}>{showFullPhone ? userInfo.phone : '+7 ХХХ XXX XX XX'}</span>
                </button>
                <div className="seller-info" onClick={handleNavigateProfile}>
                    <div>
                        <div className="seller-name">{userInfo.name}</div>
                        <div className="seller-description">
                            {reviewInfo.avg ?
                                <div>{reviewInfo.avg.toFixed(1) + " (из " + reviewInfo.count + " отзывов)"}</div>
                                : <div>Нет отзывов</div>}
                        </div>
                    </div>
                    <div className={"seller-photo"}>
                        <h1>{userInfo.name?.slice(0, 1)}</h1>
                    </div>
                </div>
            </div>
            <ToastContainer />
        </div>
    );
};

export default SelectedCard