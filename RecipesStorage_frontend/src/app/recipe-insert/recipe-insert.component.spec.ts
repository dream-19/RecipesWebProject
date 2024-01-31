import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeInsertComponent } from './recipe-insert.component';

describe('RecipeInsertComponent', () => {
  let component: RecipeInsertComponent;
  let fixture: ComponentFixture<RecipeInsertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RecipeInsertComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RecipeInsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
