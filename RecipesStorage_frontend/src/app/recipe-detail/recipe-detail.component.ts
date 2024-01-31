import { Component } from '@angular/core';
import { RecipeService } from '../recipe.service';
import { Recipe } from '../Recipes';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrl: './recipe-detail.component.css'
})

export class RecipeDetailComponent {
  recipe: Recipe | undefined;

  constructor(
    private route: ActivatedRoute,
    private recipeService: RecipeService
  ) {}

  ngOnInit() {
    const recipeId = Number(this.route.snapshot.paramMap.get('id')); // Convert recipeId to a number
    if (recipeId) {
      this.recipeService.getRecipe(recipeId).subscribe((data: Recipe) => {
        this.recipe = data;

        //order the steps by stepNumber
        if (this.recipe && this.recipe.stepsById) {
          this.recipe.stepsById.sort((a, b) => a.stepNumber - b.stepNumber);
        }
        console.log(this.recipe);
      
      });
    }
  }
}