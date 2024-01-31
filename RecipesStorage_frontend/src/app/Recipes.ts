import { Ingredient } from './Ingredients'; // Import the 'Ingredient' interface
import { Step } from './Steps'; // Import the 'Step' interface
/**
 * Interface for the Recipe object: it defines the structure of the object recipe
 * 
 */
export interface Recipe {
  id: number;
  title: string;
  dateOfCreation: Date;
  serving: number;
  description: string;
  time: number;
  difficulty: string;
  photo: string;
  ingredientsById: Ingredient[];
  stepsById: Step[];
}

  
  