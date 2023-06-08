import React, {useEffect} from 'react';
import {useState} from 'react';
import SearchBar from './SearchBar/SearchBar';
import Advertisements from "./Cards/Advertisements/Advertisements";
function Main() {

    const [searchText, setSearchText] = useState('');
    const handleSearch = (text) => {setSearchText(text);};

    const [ads, setAds] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8080/get-all-advertisements", {})
            .then(res => res.json())
            .then(res => setAds(res))
    }, [])

    const [selectedCategory, setSelectedCategory] = useState('');

    const filteredAds = ads.filter((ad) => {
        console.log(ad.category)
        const adTitle = ad.title.toLowerCase();
        const categoryTitle = selectedCategory.toLowerCase();
        return (
            adTitle.includes(searchText.toLowerCase()) &&
            (selectedCategory === '' || ad.category.toLowerCase() === categoryTitle)
        );

    });

    return (
        <div className="Main">
            <SearchBar onSearch={handleSearch}
                       selectedCategory={selectedCategory}
                       setSelectedCategory={setSelectedCategory}
            />
            <Advertisements ads={filteredAds} />
        </div>
    );
}
export default Main
