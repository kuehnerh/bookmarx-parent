import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookmarkTreelistComponent } from './bookmark-treelist.component';

describe('BookmarkTreelistComponent', () => {
  let component: BookmarkTreelistComponent;
  let fixture: ComponentFixture<BookmarkTreelistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookmarkTreelistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookmarkTreelistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
