import React, {useEffect} from 'react';
import {useState} from 'react';
import SearchBar from './SearchBar/SearchBar';
import Advertisements from "./Cards/Advertisements/Advertisements";
import "./Main.css"
function Main() {

    const [searchText, setSearchText] = useState('');
    const handleSearch = (text) => {setSearchText(text);};
    const [currentPage, setCurrentPage] = useState(0);
    const [countPage, setCountPage] = useState(0);
    const [ads, setAds] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const loadMoreAdvertisements = () => {

        const nextPage = currentPage + 1;

        const getCountPage =()=>{
            return fetch("http://localhost:8080/get-pages-count")
                    .then(res=>res.json())
            // .then(res=>setCountPage(res))
        }

        const getAllAdvertisements=()=>{
            return fetch("http://localhost:8080/get-all-advertisements", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({user_id: JSON.parse(localStorage.getItem("userInfo")).id, page:nextPage, pageSize:20})
            })
                .then(res => res.json())
                // .then(res => {
                //     setAds([...ads,...res])
                //     setCurrentPage(nextPage);
                // })
                // .catch((error) => {
                //     console.error("Ошибка при загрузке объявлений:", error);
                // });
        }
        Promise.all([getCountPage(),getAllAdvertisements()])
            .then(([responseCountPage, responseAdvertisements])=>{
                setAds([...ads,...responseAdvertisements])
                setCurrentPage(nextPage);
                setCountPage(responseCountPage)
                if(nextPage===countPage){
                    setCurrentPage(nextPage)
                }
            })


    }
    useEffect(() => {
        loadMoreAdvertisements();
    }, [])

    const [selectedCategory, setSelectedCategory] = useState('');

    const filteredAds = ads.filter((ad) => {
        const adTitle = ad.title.toLowerCase();
        const categoryTitle = selectedCategory.toLowerCase();
        return (
            adTitle.includes(searchText.toLowerCase()) &&
            (selectedCategory === '' || ad.category.toLowerCase() === categoryTitle)
        );

    });

    return (
        <div className="Main" >
            <SearchBar onSearch={handleSearch}
                       selectedCategory={selectedCategory}
                       setSelectedCategory={setSelectedCategory}
            />
            <Advertisements ads={filteredAds} />
            <div className={"load-container"}>
                {currentPage!==countPage?
                    <button className={"load-button"} onClick={loadMoreAdvertisements}>Загрузить еще</button>
                    :<p className={"load-text"}>Объявлений больше нет</p>
                }
            </div>

        </div>
    );
}
export default Main
