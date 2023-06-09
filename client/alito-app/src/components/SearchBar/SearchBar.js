import React, {useEffect, useState} from 'react';
import './SearchBar.css';
import {useLocation} from "react-router-dom";
function SearchBar(props) {
    //поиск
    const {onSearch} = props;
    const handleInputChange = (event) => {
        const searchText = event.target.value;
        onSearch(searchText);
    };
    //открытие и закрытие расскрывающегося меню с категориями
    const [isCategoryListExpanded, setIsCategoryListExpanded] = useState(false);
    const toggleCategoryList = () => setIsCategoryListExpanded(!isCategoryListExpanded);

    //массив категорий
    const categories = [
        {
            id: 1,
            title: "Транспорт",
        },
        {
            id: 2,
            title: "Работа",
        },
        {
            id: 3,
            title: "Недвижимость",
        },
        {
            id: 4,
            title: "Услуги"
        },
        {
            id: 5,
            title: "Для дома и дачи",
        },
        {
            id: 6,
            title: "Личные вещи",
        },
        {
            id: 7,
            title: "Электроника",
        },
        {
            id: 8,
            title: "Хобби и отдых"
        },
        {
            id: 9,
            title: "Животные"
        }];

    //работа с выбранной категорией
    const handleCategorySelection = (category) => {
        props.setSelectedCategory(category);
        toggleCategoryList()
    };
    const handleCategoryReset = () => { props.setSelectedCategory(''); };

    const {state} = useLocation()
    useEffect(()=>{
        if (state?.category){
            props.setSelectedCategory(state.category)
        }
    }, [state])

    return (
        <>
            <div className='search-block'>
                <button className='category-button' onClick={toggleCategoryList}>Все категории</button>
                <input className="search-bar" type="text" placeholder="Поиск..." onChange={handleInputChange}/>
            </div>
            {isCategoryListExpanded &&
                <div className="category-list">
                    <ul>
                        {categories.map((category, index) => (
                            <li key={index}>
                                <a onClick={() => handleCategorySelection(category.title)}>{category.title}</a>
                            </li>
                        ))}
                    </ul>
                </div>
            }
            <div className={"search-category"} style={props.selectedCategory ? {display: "block"} : {display: "none"}}>
                Категория:
                <span className={"search-category-text"}>   {props.selectedCategory}</span>
                <a onClick={handleCategoryReset} className={"search-category-back"}>✖</a>
            </div>
        </>
    );
}
export default SearchBar;

