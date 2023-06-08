import React, {useEffect, useState} from "react";
import EditImg from "../../../image/edit.svg"
import {useLocation, useParams} from "react-router-dom";
import './EditCard.css'
import logIn from "../../ Authorization/LogIn/LogIn";

const EditCard = () => {
    const {id} = useParams();

    const [editTitle, setEditTitle] = useState(false);
    const [editCategory, setEditCategory] = useState(false);
    const [editPrice, setEditPrice] = useState(false);
    const [editCity, setEditCity] = useState(false);
    const [editDistrict, setEditDistrict] = useState(false);
    const [editStreet, setEditStreet] = useState(false);
    const [editHouse, setEditHouse] = useState(false);
    const [editPhoto, setEditPhoto] = useState(false);
    const [editDescription, setEditDescription] = useState(false);

    const [titleValue, setTitleValue] = useState('');
    const [categoryValue, setCategoryValue] = useState('');
    const [priceValue, setPriceValue] = useState('');
    const [cityValue, setCityValue] = useState('');
    const [districtValue, setDistrictValue] = useState('');
    const [streetValue, setStreetValue] = useState('');
    const [houseValue, setHouseValue] = useState('');
    const [photoValue, setPhotoValue] = useState('');
    const [descriptionValue, setDescriptionValue] = useState('');

    const [cardData, setCardData] = useState({});

    const handleEditTitle = () => {
        setEditTitle(!editTitle);
    };
    const handleEditCategory = () => {
        setEditCategory(!editCategory);
    };
    const handleEditPrice = () => {
        setEditPrice(!editPrice);
    };
    const handleEditCity = () => {
        setEditCity(!editCity);
    };
    const handleEditDistrict = () => {
        setEditDistrict(!editDistrict);
    };
    const handleEditStreet = () => {
        setEditStreet(!editStreet);
    };
    const handleEditHouse = () => {
        setEditHouse(!editHouse);
    };
    const handleEditPhoto = () => {
        setEditPhoto(!editPhoto);
    };
    const handleEditDescription = () => {
        setEditDescription(!editDescription);
    };

    const handleTitleChange = (event) => {
        setTitleValue(event.target.value);
    };
    const handleCategoryChange = (event) => {
        setCategoryValue(event.target.value);
    };
    const handlePriceChange = (event) => {
        setPriceValue(event.target.value);
    };
    const handleCityChange = (event) => {
        setCityValue(event.target.value);
    };
    const handleDistrictChange = (event) => {
        setDistrictValue(event.target.value);
    };
    const handleStreetChange = (event) => {
        setStreetValue(event.target.value);
    };
    const handleHouseChange = (event) => {
        setHouseValue(event.target.value);
    };
    const handlePhotoChange = (event) => {
        setPhotoValue(event.target.value);
    };
    const handleDescriptionChange = (event) => {
        setDescriptionValue(event.target.value);
    };

    const updateCardData = () => {
        fetch("http://localhost:8080/get-advertisement-by-id", {
            method: "POST",
            headers: { 'content-type': "application/json" },
            body: JSON.stringify({ id: id })
        })
            .then(res => res.json())
            .then(res => setCardData(res));
    }

    useEffect(() => {
        setTimeout(() => {
            updateCardData();
        }, 1000)
    }, [id, cardData])


    const handleTitleKeyDown = (event) => {
        if (event.key === 'Enter') {
            const column = event.target.name
            const value = event.target.value
            fetch('http://localhost:8080/edit-user', {
                method: 'POST',
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({ad_id: id, column: column, value: value})
            })
                .then(res => res.text())
                .then(res => console.log(res))
            setEditTitle(false);
            setEditCategory(false);
            setEditPrice(false);
            setEditCity(false);
            setEditDistrict(false);
            setEditStreet(false);
            setEditHouse(false);
            setEditPhoto(false);
            setEditDescription(false);
        }
    };

    return (
        <>
            <h1 className="title-edit-card">Редактирование объявления</h1>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditTitle} />
                <p>Название:</p>
                {editTitle ? (
                    <input className="set-input"
                           type="text"
                           name={"title"}
                           value={titleValue}
                           onChange={handleTitleChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.title}</p>
                )}
            </div>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditCategory} />
                <p>Категория:</p>
                {editCategory ? (
                    <input className="set-input"
                           type="text"
                           name={"category"}
                           value={categoryValue}
                           onChange={handleCategoryChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.category}</p>
                )}
            </div>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditPrice} />
                <p>Цена:</p>
                {editPrice ? (
                    <input className="set-input"
                           type="text"
                           name={"price"}
                           value={priceValue}
                           onChange={handlePriceChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.price}</p>
                )}
            </div>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditCity} />
                <p>Город:</p>
                {editCity ? (
                    <input className="set-input"
                           type="text"
                           name={"city"}
                           value={cityValue}
                           onChange={handleCityChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.city}</p>
                )}
            </div>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditDistrict} />
                <p>Район:</p>
                {editDistrict ? (
                    <input className="set-input"
                           type="text"
                           name={"district"}
                           value={districtValue}
                           onChange={handleDistrictChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.district}</p>
                )}
            </div>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditStreet} />
                <p>Улица:</p>
                {editStreet ? (
                    <input className="set-input"
                           type="text"
                           name={"street"}
                           value={streetValue}
                           onChange={handleStreetChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.street}</p>
                )}
            </div>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditHouse} />
                <p>Дом:</p>
                {editHouse ? (
                    <input className="set-input"
                           type="text"
                           name={"house"}
                           value={houseValue}
                           onChange={handleHouseChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.house}</p>
                )}
            </div>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditPhoto} />
                <p>Фото:</p>
                {editPhoto ? (
                    <input className="set-input"
                           type="text"
                           name={"photo"}
                           value={photoValue}
                           onChange={handlePhotoChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.photo}</p>
                )}
            </div>
            <div className="edit">
                <img className="edit-button-ad" src={EditImg} onClick={handleEditDescription} />
                <p>Описание:</p>
                {editDescription ? (
                    <input className="set-input"
                           type="text"
                           name={"description"}
                           value={descriptionValue}
                           onChange={handleDescriptionChange}
                           onKeyDown={handleTitleKeyDown}
                    />
                ) : (
                    <p className="value">{cardData?.description}</p>
                )}
            </div>
        </>
    );
};

export default EditCard