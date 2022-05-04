import React, {useEffect, useState} from "react";
import {SearchBar} from "./SearchBar";
import Dropdown from "./Dropdown";
import Pagination from "./Pagination";
import {Recipe} from "./Recipe";
import IDropDownItem from "../types/DropDownItem";
import RecipeService from "../services/RecipeService";
import IRequestParams from "../types/Request";

const RecipesList = () => {
    const [pageSize, setPageSize] = useState(1)
    const [page, setPage] = useState(1)
    const [search, setSearch] = useState('')
    const [count, setCount] = useState(1)
    const [currentIndex, setCurrentIndex] = useState(-1)
    const [recipes, setRecipes] = useState([])

    const pageSizes: IDropDownItem[] = [{title: '3'}, {title: '6'}, {title: '9'}]

    const getRequestParams = (title: string, page: number, pageSize: number) => {
        let params: IRequestParams = {};
        if (title) {
            params.title = title
        }

        if (page) {
            params.page = page - 1
        }

        if (pageSize) {
            params.size = pageSize
        }
        return params;
    }

    useEffect(() => {
        const fetchRecipes = async () => {
            await retrieveRecipes();
        }
        fetchRecipes().catch(console.error);
    }, [])


    const retrieveRecipes = async () => {
        const params = getRequestParams(search, page, pageSize);
        try {
            const axiosResponse = await RecipeService.getAll(params)
            const {recipes, totalPages} = axiosResponse.data
            setRecipes(recipes)
            setCount(totalPages)
        } catch (e) {
            console.log(e)
        }
    }

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearch(event.target.value);
    };

    const handlePageSizeSelect = (title: string, id: any, key: any) => {
        setPageSize(Number.parseInt(title))
    }

    const handlePageChange = async (event: any, newValue: number) => {
        setPage(newValue)
        await retrieveRecipes()
    }

    const handleEnter = async (event: any) => {
        if (event.key == 'Enter') {
            await retrieveRecipes()
        }
    }

    return (
        <div>
            <SearchBar
                search={search}
                handleChange={handleInputChange}
                handleSubmit={handleEnter}
            />
            <h1>
                Recipes List
            </h1>
            <Dropdown
                title={"Items per list"}
                items={pageSizes}
                onSelect={handlePageSizeSelect}
            />
            <Pagination count={count} page={page} onChange={handlePageChange}/>
            <div style={{width: 300, height: 300}}>
                {recipes && recipes.map((recipe: any, index: number) =>
                    <Recipe recipe={recipe}/>
                )}
            </div>
        </div>
    )
}
export {RecipesList}