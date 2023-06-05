import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import './Card.css'

const Card = ({id, ad}) => {

    const [isFavourite, setIsFavourite] = useState(false);
    const navigate = useNavigate();
    const handleNavigateCard = (e, id) => {
        navigate(`/ad/${id}`)
    }
    const handleFavouriteClick = () => {
        if (isFavourite) {
            removeFromFavorite(ad.id);
        } else {
            addToFavorite(ad.id);
        }
    };
    useEffect(() => {
        const userId = JSON.parse(localStorage.getItem("userInfo")).id;
        fetch(`http://localhost:8080/is-favorite-exist`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({user_id: userId, ad_id: ad.id}),
        })
            .then(res=>res.text())
            .then(res=>{
                if(res==='yes') setIsFavourite(true)
            })
    }, [])

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

    return (
        <div className="ad-card" key={ad.id}>
            <img
                src={ad.photo}
                alt={ad.title}
                className="ad-card__image"
                onClick={(e) => {
                    handleNavigateCard(e, ad.id)
                }}
                style={{cursor: "pointer"}}
            />
            <div className="ad-card__content">
                <div className='ad-card__title-favourite'>
                    <h3
                        className="ad-card__title"
                        onClick={(e) => {
                            handleNavigateCard(e, ad.id)
                        }}
                        style={{cursor: "pointer"}}>{ad.title}
                    </h3>
                    <button onClick={handleFavouriteClick} className='ad-card__favourite'>
                        <span>{
                            isFavourite? '💙' : '🤍'
                        }</span>
                    </button>
                </div>
                <p className="ad-card__price">{ad.price} Р</p>
                <p className="ad-card__district">{ad.district}</p>
                <p className="ad-card__date">{ad.date_created}</p>
            </div>
        </div>
    )
}

export default Card