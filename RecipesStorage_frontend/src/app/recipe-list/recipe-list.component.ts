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

  //edit a recipe
  editRecipe(id: number) {
    this.router.navigate(['/recipe', id, 'edit']);
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
    // Confirmation dialog
    const isConfirmed = confirm('Are you sure you want to delete all recipes? This action cannot be undone.');
    if (isConfirmed) {
      this.recipeService.deleteAllRecipes().subscribe((data: any) => {
        console.log(data);
        this.loadRecipes();
        this.countAllRecipes();
        this.alertService.setMessage('Recipes deleted');
      });
    }
    
  }


 //Search Recipes
  searchRecipes(event:Event) {
    const searchTerm = (event.target as HTMLInputElement).value;
    if (searchTerm === '') {
      this.loadRecipes();
      return;
    }
    if (searchTerm.trim()) {
      // Perform the search
      this.recipeService.searchRecipe(searchTerm).subscribe({
        next: (data) => {
          if (data != null) {
            console.log(data);
            if (Array.isArray(data)) {
              this.recipes = data;
            } else  { // single object
              this.recipes = [data];
            }
            //check if the recipe has a photo, otherwise use a default one (in base64)
            this.recipes.forEach((recipe: Recipe) => {
              if (!recipe.photo) {
                recipe.photo = this.defaultImageService.getDefaultImage();
              }
            });
        } else{
          this.recipes = [];
        }
        },
        error: (error) => {
          // Handle error
          console.error('Error searching for recipe', error);
          return of([]); // Return an empty array if there's an error
        }
      });
    }
  }

  //order recipes
  orderBy(event: any): void {
    const value = event.target.value;
    // Custom sort for difficulty
    const difficultyOrder: { [key: string]: number } = { 'Easy': 1, 'Medium': 2, 'Hard': 3 };


    switch (value) {
      case 'dateOfCreation_old':
        this.recipes.sort((a, b) => new Date(a.dateOfCreation).getTime() - new Date(b.dateOfCreation).getTime());
        break;
      case 'dateOfCreation_new':
        this.recipes.sort((a, b) => new Date(b.dateOfCreation).getTime() - new Date(a.dateOfCreation).getTime());
        break;
      case 'title_asc':
        this.recipes.sort((a, b) => a.title.localeCompare(b.title));
        break;
      case 'title_des':
        this.recipes.sort((a, b) => b.title.localeCompare(a.title));
        break;
      case 'serving':
        this.recipes.sort((a, b) => a.serving - b.serving);
        break;
      case 'time':
        this.recipes.sort((a, b) => a.time - b.time);
        break;
      case 'difficulty':
        //I have easy -> medium -> hard
        this.recipes.sort((a, b) => difficultyOrder[a.difficulty] - difficultyOrder[b.difficulty]);

        break;
      default:
        // Handle default case or do nothing
        break;
    }
  }
  
}
