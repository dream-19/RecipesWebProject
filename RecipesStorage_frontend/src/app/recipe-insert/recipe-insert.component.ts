import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../recipe.service';
import { Router } from '@angular/router';
import { Recipe } from '../Recipes';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { AlertService } from '../alert.service';

@Component({
  selector: 'app-recipe-insert',
  templateUrl: './recipe-insert.component.html',
  styleUrl: './recipe-insert.component.css'
})

export class RecipeInsertComponent implements OnInit {
  recipeForm!: FormGroup; //! to tell ts that this variable will be initialized later
 
  constructor(
    private fb: FormBuilder,
    private recipeService: RecipeService,
    private alertService: AlertService,
    private router: Router) {}

  ngOnInit() {
      //If I don't put the right field it will not be possible to submit the form
    this.recipeForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      serving: ['', Validators.required],
      time: ['', Validators.required],
      difficulty: ['', Validators.required],
      photo: [''], // Photo will be a base64 string
      ingredients: this.fb.array([]) 
     
    });
  }

  // Add a new ingredient group to the ingredients FormArray
  addIngredient() {
    const ingredientGroup = this.fb.group({
      name: [''],
      quantity: [''],
      unitOfMeasurement: ['']
    });
    this.ingredients.push(ingredientGroup);
  }

  // Remove an ingredient group from the ingredients FormArray
  removeIngredient(index: number) {
    this.ingredients.removeAt(index);
  }

  // Getter for the ingredients FormArray
  get ingredients() {
    return this.recipeForm.get('ingredients') as FormArray;
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
      };
    }
  }

  onSubmit() {
    if (this.recipeForm.valid) {
      //add the dateOfCreation
      this.recipeForm.value.dateOfCreation = new Date().toISOString().slice(0, 10);
      console.log(this.recipeForm.value);
      this.recipeService.addRecipe(this.recipeForm.value).subscribe({
        next: (res) => {
          // Handle the response
          console.log("added"+ res);
          this.router.navigate(['/']);
          this.alertService.setMessage('Recipes Added Successfully');

        },
        error: (err) => {
          // Handle the error
          console.log(err);
          console.log(err.error.message);
          this.alertService.setMessageError("Error trying to add the recipe");
        }
      });
    }
  }
}
