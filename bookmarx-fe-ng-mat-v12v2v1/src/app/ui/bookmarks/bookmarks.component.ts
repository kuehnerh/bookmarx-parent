import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-bookmarks',
  templateUrl: './bookmarks.component.html',
  styleUrls: ['./bookmarks.component.css']
})
export class BookmarksComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
  }


}
