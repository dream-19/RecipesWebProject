import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../recipe.service';
import { Recipe } from '../Recipes';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  recipes: Recipe[] = []; // Declare the type as Recipe[]
  count!: number;
  error: string = '';

  constructor(private recipeService: RecipeService) { }

  ngOnInit() {
    // Retrieve all the recipes from the API
    this.recipeService.getRecipes().pipe(
      catchError(err => {
        this.error = 'Failed to load recipes';
        console.error('Error loading recipes', err);
        return of([]); // Return an empty array if there's an error
      })
    ).subscribe(data => {
      this.recipes = data;
    });

    //Count the recipes present
    this.recipeService.countRecipes().pipe(
      catchError(err => {
        this.error = 'Failed to count recipes';
        console.error('Error counting recipes', err);
        return of(0); // Return 0 if there's an error
      })
    ).subscribe(data => {
      this.count = data;
    });

  }
}
