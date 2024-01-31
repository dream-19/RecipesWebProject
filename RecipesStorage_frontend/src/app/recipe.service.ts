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
}
