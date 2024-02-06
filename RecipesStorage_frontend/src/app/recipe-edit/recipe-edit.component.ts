import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, Validators, FormArray, FormGroup } from '@angular/forms';
import { RecipeService } from '../recipe.service';
import { AlertService } from '../alert.service';
import { Recipe } from '../Recipes';
import { Ingredient } from '../Ingredients';
import { Step } from '../Steps';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrls: ['./recipe-edit.component.css']
})
export class RecipeEditComponent implements OnInit {
  recipeForm!: FormGroup;
  recipeId!: number; // Assuming the recipe ID is a number
  recipe: Recipe | undefined;
  array_of_ingredients: Ingredient[] = [];
  array_of_steps: Step[] = [];
  photoPreview: string | ArrayBuffer | null = null; 

  constructor(
    private fb: FormBuilder,
    private recipeService: RecipeService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit() {
    this.recipeForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      serving: ['', [Validators.required, Validators.min(1)]],
      time: ['', [Validators.required, Validators.min(1)]],
      difficulty: ['', Validators.required],
      photo: [''], // Assuming photo is handled as a base64 string
      ingredientsById: this.fb.array([]),
      stepsById: this.fb.array([])
    });

    // Load the recipe data
    this.loadRecipe();
  }

   // Load the recipe data from the service
   loadRecipe() {
    // Get the recipe ID from the route
    const recipeId = this.route.snapshot.params['id']; 
    this.recipeId = recipeId;
  

    // Load the recipe data from the service and populate the form
    this.recipeService.getRecipe(recipeId).subscribe({
      next: (recipe) => {
        if (!recipe) {
          // Handle error
          console.error('Recipe not found');
          this.alertService.setMessageError("Recipe not found");
          this.router.navigate(['/']);
          return;
        }
        this.recipe = recipe;
        // Populate the form with the recipe data
        this.recipeForm.patchValue({
          title: recipe.title,
          description: recipe.description,
          serving: recipe.serving,
          time: recipe.time,
          difficulty: recipe.difficulty,
          photo: recipe.photo // Assuming the photo is stored as a base64 string
      
        });
        recipe.ingredientsById.forEach((ingredient: any) => {
          this.ingredientsById.push(this.fb.group(ingredient));
        });

        recipe.stepsById.forEach((step: any) => {
          this.stepsById.push(this.fb.group(step));
        });

        // Order the steps by stepNumber
        recipe.stepsById.sort((a, b) => a.stepNumber - b.stepNumber);

        console.log('Recipe loaded', recipe);
      },
      error: (err) => {
        // Handle error
        console.error('Error loading recipe', err);
        this.alertService.setMessageError("Error trying to load the recipe");
        this.router.navigate(['/']);
      }
    });
  }


  
  //Method to add the ingredients
  get ingredientsById(): FormArray {
    return this.recipeForm.get('ingredientsById') as FormArray;
  }
  
  newIngredient(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required],
      quantity: ['', [Validators.required, Validators.min(0)]],
      unitOfMeasurement: ['', Validators.required],
    });
  }
  
  addIngredient() {
    this.ingredientsById.push(this.newIngredient());
  }
  
  removeIngredient(index: number) {
    this.ingredientsById.removeAt(index);
  }

  //Method to add the steps
  get stepsById(): FormArray {
    return this.recipeForm.get('stepsById') as FormArray;
  }

  newStep(): FormGroup {
    return this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  addStepFollowingThis(index: number) {
    this.stepsById.insert(index + 1, this.newStep());
    console.log("adding:"+ index + 1);

  }

  addStep() {
    this.stepsById.push(this.newStep());
  }

  removeStep(index: number) {
    this.stepsById.removeAt(index);
  }
  // This method is called whenever the user selects an image file (convert to base64 string to store the photo in the db)
  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length) {
      const file = input.files[0];
      const reader = new FileReader();

      reader.readAsDataURL(file);
      reader.onload = () => {
        this.recipeForm.patchValue({
          photo: reader.result as string
        });
        this.photoPreview = reader.result; // Update the preview URL
      }
    }
  }

 
  //EDIT the recipe
  onSubmit() {
    if (this.recipeForm.valid) {
      const stepsWithNumbers = this.stepsById.value.map((step: any, index: number) => ({
        ...step,
        stepNumber: index + 1
      }));
  
      this.recipeForm.value.stepsById = stepsWithNumbers;
  
      // First, update the recipe data
      this.recipeService.updateRecipe(this.recipeId, this.recipeForm.value).subscribe({
        next: (res) => {
          // Use forkJoin to execute deletion and then addition of ingredients and steps in parallel
          forkJoin([
            this.recipeService.deleteAllIngredients(this.recipeId),
            this.recipeService.deleteAllSteps(this.recipeId)
          ]).subscribe({
            next: ([ingredientsDeletionResult, stepsDeletionResult]) => {
              // After deletions, add the new ingredients and steps
              forkJoin([
                this.recipeService.addIngredientsInRecipe(this.recipeId, this.recipeForm.value.ingredientsById),
                this.recipeService.addStepsInRecipe(this.recipeId, this.recipeForm.value.stepsById)
              ]).subscribe({
                next: ([ingredientsAdditionResult, stepsAdditionResult]) => {
                  // Only navigate away after all updates are complete
                  this.router.navigate(['/recipe', this.recipeId]);
                  this.alertService.setMessage('Recipe Updated Successfully');
                },
                error: (err) => {
                  console.error('Error updating ingredients or steps', err);
                  this.alertService.setMessageError("Error trying to update the recipe components");
                }
              });
            },
            error: (err) => {
              console.error('Error deleting ingredients or steps', err);
              this.alertService.setMessageError("Error preparing the recipe components for update");
            }
          });
        },
        error: (err) => {
          console.error('Error updating recipe', err);
          this.alertService.setMessageError("Error trying to update the recipe");
        }
      });
    }
  }
}
