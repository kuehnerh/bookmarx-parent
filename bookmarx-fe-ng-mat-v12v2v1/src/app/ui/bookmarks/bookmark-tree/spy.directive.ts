import {AfterViewInit, ChangeDetectorRef, Directive, ElementRef, OnDestroy, OnInit} from "@angular/core";

let nextId = 1;

// Spy on any element to which it is applied.
// Usage: <div appSpy>...</div>
@Directive({selector: '[appSpy]'})
export class SpyDirective implements OnInit, OnDestroy, AfterViewInit {
  private id = nextId++;


  constructor(
    private elem: ElementRef,
    private cd: ChangeDetectorRef) {
    console.log(`Spy #${this.id} constructor`);

  }

  ngOnInit() {
    console.log(`Spy #${this.id} onInit`);
  }

  ngOnDestroy() {
    console.log(`Spy #${this.id} onDestroy`);
  }

  ngAfterViewInit() {
    console.log(`Spy #${this.id} ngAfterViewInit`);
    this.elem.nativeElement.focus();
    this.cd.detectChanges();
  }
}
