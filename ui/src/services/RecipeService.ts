import http from '../http-common'
import IRecipeData from "../types/Recipe";

const getAll = (params: any) => {
    return http.get<any>("/recipes", {params: params})
}

const get = (id: any) => {
    return http.get<IRecipeData>(`/recipes/${id}`)
}

const create = (data: IRecipeData) => {
    return http.post<IRecipeData>("/recipes", data)
}

const update = (id: any, data: IRecipeData) => {
    return http.put<any>(`/recipes/${id}`, data)
}

const remove = (id: any) => {
    return http.delete<any>(`/recipes/${id}`)
}

const removeAll = () => {
    return http.delete<any>(`/recipes`)
}

const findByTitle = (title: string) => {
    return http.get<Array<IRecipeData>>(`/recipes?title=${title}`)
}

const RecipeService = {
    getAll,
    get,
    create,
    update,
    remove,
    removeAll,
    findByTitle
}

export default RecipeService;