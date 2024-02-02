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
      ingredientsById: this.fb.array([]),
      stepsById: this.fb.array([]), 
     
    });
    
  }

  //Method to add the ingredients
  get ingredientsById(): FormArray {
    return this.recipeForm.get('ingredientsById') as FormArray;
  }
  
  newIngredient(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required],
      quantity: ['', Validators.required],
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
      };
    }
  }

  onSubmit() {
    if (this.recipeForm.valid ) {

      // Add the step number to each step
      const stepsWithNumbers = this.stepsById.value.map((step: any, index: number) => ({
        ...step,
        stepNumber: index + 1 // Assign step number based on position in the array
      }));

      this.recipeForm.value.stepsById = stepsWithNumbers;
    

      //add the dateOfCreation
      this.recipeForm.value.dateOfCreation = new Date().toISOString().slice(0, 10);
      console.log(this.recipeForm.value);
      this.recipeService.addRecipe(this.recipeForm.value).subscribe({
        next: (res) => {
          // Handle the response
          //console.log("added"+ res);
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
