import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../recipe.service';
import { Recipe } from '../Recipes';
import { catchError, of } from 'rxjs';
import { Router } from '@angular/router';
import { AlertService } from '../alert.service';
import { DefaultImageService } from '../default-image.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit {
  recipes: Recipe[] = []; // Declare the type as Recipe[]
  count!: number;
  error: string = '';

  constructor(private recipeService: RecipeService, private router: Router, private alertService: AlertService, private defaultImageService: DefaultImageService) { }


  ngOnInit() {
    // Retrieve all the recipes from the API
    this.loadRecipes();
    this.countAllRecipes();
    

  }

  loadRecipes() {
    this.recipeService.getRecipes().pipe(
      catchError(err => {
        this.error = 'Failed to load recipes';
        console.error('Error loading recipes', err);
        return of([]); // Return an empty array if there's an error
      })
    ).subscribe((data: Recipe[]) => {
      console.log(data);
      this.recipes = data;
      //check if the recipe has a photo, otherwise use a default one (in base64)
      this.recipes.forEach((recipe: Recipe) => {
        if (!recipe.photo) {
          recipe.photo = this.defaultImageService.getDefaultImage();
        }
    });
  });
  }

  countAllRecipes() {
    //Count the recipes present
    this.recipeService.countRecipes().pipe(
      catchError(err => {
        this.error = 'Failed to count recipes';
        console.error('Error counting recipes', err);
        return of(0); // Return 0 if there's an error
      })
    ).subscribe((data: number) => {
      this.count = data;
    });
  }

  //Navigate to the recipe details page when button is clicked
  showRecipeDetails(id: number) {
    this.router.navigate(['/recipe', id]);
  }

  //add a new recipe with form
  showRecipeForm() {
    this.router.navigate(['/recipe']);
  }

  //delete a recipe
  deleteRecipe(id: number) {
    this.recipeService.deleteRecipe(id).subscribe((data: any) => {
      console.log(data);
      this.loadRecipes();
      this.countAllRecipes();
      this.alertService.setMessage('Recipe deleted');
    });
  }

  //delete all the recipes
  deleteAllRecipes() {
    this.recipeService.deleteAllRecipes().subscribe((data: any) => {
      console.log(data);
      this.loadRecipes();
      this.countAllRecipes();
      this.alertService.setMessage('Recipes deleted');
    });
  }
}
