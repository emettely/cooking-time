import React from 'react';
import './App.css';
import NavBar from "./components/NavBar";
import {RecipesList} from "./components/RecipesList";
import {AddRecipe} from "./components/AddRecipe";
import {Recipe} from "./components/Recipe";
import {Route, Routes} from "react-router-dom";
import {CookRecipes} from "./components/CookRecipes";

function App() {
    return (
        <div>
            <NavBar/>
            <Routes>
                <Route path={'/'} element={<RecipesList/>}/>
                <Route path={'/recipes'} element={<RecipesList/>}/>
                <Route path={'/add'} element={<AddRecipe/>}/>
                <Route path={'/recipes/:id'} element={<Recipe/>}/>
                <Route path={'/cook/:id'} element={<CookRecipes/>}/>
            </Routes>
        </div>
    );
}

export default App;
