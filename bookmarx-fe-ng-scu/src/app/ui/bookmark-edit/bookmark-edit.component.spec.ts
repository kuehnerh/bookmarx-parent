import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookmarkEditComponent } from './bookmark-edit.component';

describe('BookmarkEditComponent', () => {
  let component: BookmarkEditComponent;
  let fixture: ComponentFixture<BookmarkEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookmarkEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookmarkEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
