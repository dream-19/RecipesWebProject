import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { Recipe } from './Recipes';

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

  //add a new recipe
  addRecipe(recipe: Recipe){
    return this.http.post(this.apiUrl+'recipes', [recipe], { responseType: 'text' });
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
