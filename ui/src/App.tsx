import React, {Component, useEffect, useState} from 'react';
import './App.css';
import {TopMenu} from "./components/TopMenu";
import {RecipesList} from "./components/RecipesList";
import {AddRecipe} from "./components/AddRecipe";
import {Recipe} from "./components/Recipe";
import {Route, Routes} from "react-router-dom";
import {SearchBar} from "./components/SearchBar";
import {Button, Menu, MenuItem, Pagination, Typography} from "@mui/material";
import Box from "@mui/material/Box";
import RecipeService from "./services/RecipeService";

function App() {
    const [recipes, setRecipes] = useState([])
    const [values, setValues] = useState<any>({
        search: '',
        currentRecipe: null,
        page: 1,
        currentIndex: -1,
        pageSize: 1,
        count: 1,
        anchorEl: null
    });

    const pageSizes = [3, 6, 9]

    useEffect(() => {
        const fetchRecipes = async () => {
            await retrieveRecipes();
        }
        fetchRecipes().catch(console.error);
    }, [])

    interface IRequestParams {
        title?: string;
        page?: number;
        size?: number;
    }

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

    const open = Boolean(values.anchorEl);

    const handlePageSizeClick = (event: React.MouseEvent<HTMLElement>) => {
        setValues({...values, anchorEl: event.currentTarget});
    };

    const handlePageSizeClose = () => {
        setValues({...values, anchorEl: null});
    };

    const retrieveRecipes = async () => {
        const {search, page, pageSize} = values
        const params = getRequestParams(search, page, pageSize);
        try {
            const axiosResponse = await RecipeService.getAll(params)
            const { recipes, totalPages } = axiosResponse.data
            setRecipes(recipes)
            setValues({...values, count: totalPages})
        } catch (e) {
            console.log(e)
        }
    }

    const handleInputChange = (prop: string) => (event: React.ChangeEvent<HTMLInputElement>) => {
        setValues({...values, [prop]: event.target.value});
    };

    const handlePageChange = async (event: any, newValue: number) => {
        setValues({...values, page: newValue})
        await retrieveRecipes()
    }

    return (
        <div>
            <TopMenu/>
            <SearchBar search={values.search} handleChange={handleInputChange('search')}/>
            <Typography>
                Recipes List
            </Typography>
            <Button onClick={handlePageSizeClick}>
                Items per list
            </Button>
            <Menu anchorEl={values.anchorEl}
                  open={open}
                  onClose={handlePageSizeClose}
            >
                {pageSizes.map ((size: number) =>
                    <MenuItem onClick={handlePageSizeClose} disableRipple>{size}</MenuItem>
                )}
            </Menu>
            <Pagination count={values.count} page={values.page} onChange={handlePageChange}/>
            <Box sx={{width: 300, height:300}}>
                {recipes && recipes.map((recipe: any, index: number)=>
                    <div>
                        <h4>{recipe.title}</h4>
                        <p>{recipe.description}</p>
                    </div>
                )}
            </Box>
            <Routes>
                <Route path={'/'} element={<RecipesList/>}/>
                <Route path={'/recipes'} element={<RecipesList/>}/>
                <Route path={'/add'} element={<AddRecipe/>}/>
                <Route path={'/recipes/:id'} element={<Recipe/>}/>
            </Routes>
        </div>
    );
}

export default App;
