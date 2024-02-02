import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Recipe } from './Recipes';
import { Ingredient } from './Ingredients';
import { Step } from './Steps';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private apiUrl = 'http://localhost:8080/recipeStorage/'; // API url

  constructor(private http: HttpClient) { }

  //get all the recipes
  getRecipes(): Observable<Recipe[]> {
    return this.http.get(this.apiUrl+'recipes') as Observable<Recipe[]>;
  }

  //count the recipes
  countRecipes(): Observable<number> {
    return this.http.get(this.apiUrl+'recipes/count') as Observable<number>;
  }

  //get a recipe by id
  getRecipe(id: number): Observable<Recipe> {
    return this.http.get(this.apiUrl+'recipes/'+id) as Observable<Recipe>;
  }

  //count ingredients of a recipe
  countIngredientsOfRecipe(id: number): Observable<number> {
    return this.http.get(this.apiUrl+'recipes/'+id+'/ingredients/count') as Observable<number>;
  }

  //couunt steps of a recipe
  countStepsOfRecipe(id: number): Observable<number> {
    return this.http.get(this.apiUrl+'recipes/'+id+'/steps/count') as Observable<number>;
  }

  //search a recipe
  searchRecipe(name: string): Observable<Recipe[]> {
    return this.http.get(this.apiUrl+'recipes/title/start/'+name) as Observable<Recipe[]>;
  }

  //add a new recipe
  addRecipe(recipe: Recipe){
    return this.http.post(this.apiUrl+'recipes', [recipe], { responseType: 'text' });
  }

  //add array of ingredients to a recipe
  addIngredientsInRecipe(id: number, ingredients: Ingredient[]){
    return this.http.post(this.apiUrl+'recipes/'+id+'/ingredients', ingredients, { responseType: 'text' });
  }

  //add array of steps to a recipe
  addStepsInRecipe(id: number, steps: Step[]){
    return this.http.post(this.apiUrl+'recipes/'+id+'/steps', steps, { responseType: 'text' });
  }

  //update a recipe (put requests)
  updateRecipe(id: number, recipe: Recipe){
    return this.http.put(this.apiUrl+'recipes/'+id, recipe, { responseType: 'text' });
  }

  //delete all steps of a recipe
  deleteAllSteps(id: number){
    return this.http.delete(this.apiUrl+'recipes/'+id+'/steps', { responseType: 'text' });
  }

  //delete all ingredients of a recipe
  deleteAllIngredients(id: number){
    return this.http.delete(this.apiUrl+'recipes/'+id+'/ingredients', { responseType: 'text' });
  }

  //delete a recipe
  deleteRecipe(id: number){
    return this.http.delete(this.apiUrl+'recipes/'+id, { responseType: 'text' });
  }

  //delete all the recipes
  deleteAllRecipes(){
    return this.http.delete(this.apiUrl+'recipes', { responseType: 'text' });
  }
}
