import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {NavigationService} from "../shared/navigation.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  showBackButton$: Observable<boolean>;

  languages: { key: string; label: string, flag: string }[] = [
    {key: 'bg', label: 'Български', flag: 'BG'},
    {key: 'cs', label: 'Čeština', flag: 'CZ'},
    {key: 'de', label: 'Deutsch', flag: 'DE'},
    {key: 'en', label: 'English', flag: 'GB'},
    {key: 'hr', label: 'Hrvatski', flag: 'HR'},
    {key: 'pl', label: 'Polski', flag: 'PL'},
    {key: 'ro', label: 'Română', flag: 'RO'},
    {key: 'sk', label: 'Slovenčina', flag: 'SK'},
    {key: 'ro-MD', label: 'moldovenesc', flag: 'MD'}
  ];

  ngOnInit(): void {
  }

  constructor(
    private router: Router,
    private navigationService: NavigationService
  ) {
    this.showBackButton$ = this.navigationService.isNavigateBackPossible$();
  }

  public getEnvironmentName(): string {
    return "dev";
    // return this.configFacade.getEnvironmentConfiguration().name;
  }

  public goHistoryBack(): void {
    this.navigationService.navigateBack();
  }

  public changeLanguage(language: { key: string; label: string, flag: string }): void {
    //window.location.href = "/ui/" + language.key + this.navigationService.getCurrentUrl();
  }

  public changePage(path: string): void {
    console.log("changePage: " + path);
    this.navigationService.navigateToPath(path);
  }

  public getUsername(): string {
    return "Heiko";
    // return this.configFacade.getUserProfile().displayName;
  }

  public getCurrentUrl(): string {
    return this.navigationService.getCurrentUrl();
  }


}
