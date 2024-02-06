import { Component } from '@angular/core';
import { RecipeService } from '../recipe.service';
import { Recipe } from '../Recipes';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '../alert.service';
import { DefaultImageService } from '../default-image.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrl: './recipe-detail.component.css'
})

export class RecipeDetailComponent {
  recipe: Recipe | undefined;
  ingredientsCount?: number;
  stepsCount?: number;
  placeholderphoto: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private recipeService: RecipeService,
    private alertService: AlertService,
    private defaultImageService: DefaultImageService
  ) {}

  ngOnInit() {
    const recipeId = Number(this.route.snapshot.paramMap.get('id')); // Convert recipeId to a number
    if (recipeId) {
      //get the recipe
      this.recipeService.getRecipe(recipeId).subscribe((data: Recipe) => {
        this.recipe = data;
        if (!this.recipe) {
          console.error('Recipe not found');
          this.alertService.setMessageError('Recipe not found');
          this.router.navigate(['/']);
        }

        //order the steps by stepNumber
        if (this.recipe && this.recipe.stepsById) {
          this.recipe.stepsById.sort((a, b) => a.stepNumber - b.stepNumber);
        }
        console.log(this.recipe);

        if(this.recipe.photo == null || this.recipe.photo == "") {
          this.placeholderphoto = this.defaultImageService.getDefaultImage();
        } 
      
      });

      //get the number of ingredients of the recipe
      this.recipeService.countIngredientsOfRecipe(recipeId).subscribe((data: number) => {
        this.ingredientsCount = data; //get the number of ingredients of the recipe
        console.log(this.ingredientsCount);
      });
      //get the number of steps of the recipe
      this.recipeService.countStepsOfRecipe(recipeId).subscribe((data: number) => {
        this.stepsCount = data; //get the number of steps of the recipe
        console.log(this.stepsCount);
      });
    }

    
  }

   //edit a recipe
   editRecipe(id: number) {
    this.router.navigate(['/recipe', id, 'edit']);
  }

  //delete a recipe
  deleteRecipe(id: number) {
    this.recipeService.deleteRecipe(id).subscribe({
      next: () => {
        this.router.navigate(['/']);
        this.alertService.setMessage('Recipe deleted successfully');

      },
      error: (err) => {
        console.error('Error deleting recipe', err);
        this.alertService.setMessageError('Error deleting recipe');
      }
    });
  }

  //go back to the recipes list
  goBack() {
    this.router.navigate(['/']);
  }

}