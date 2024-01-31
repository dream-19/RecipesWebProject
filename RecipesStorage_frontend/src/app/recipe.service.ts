import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private apiUrl = 'http://localhost:8080/recipeStorage/'; // API url

  constructor(private http: HttpClient) { }

  //get all the recipes
  getRecipes() {
    return this.http.get(this.apiUrl+'recipes');
  }
}
