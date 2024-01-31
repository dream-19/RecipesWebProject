import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../recipe.service'; //import service

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})

export class RecipeListComponent implements OnInit {
  recipes: any = []; //create the recipes array

  constructor(private recipeService: RecipeService) { }

  //on init make the call
  ngOnInit() {
    //call to get all the recipes using the getRecipes() method of the service
    this.recipeService.getRecipes().subscribe(data => {
      this.recipes = data; //assign the data to the recipes array
    });
  }
}
