import {Injectable} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {Location} from "@angular/common";
import {map} from "rxjs/operators";
import {BehaviorSubject, Observable} from "rxjs";

/**
 * Navigation service that encapsulate all interaction with the angular router.
 */
@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  private urlHistory$: BehaviorSubject<string[]> = new BehaviorSubject<string[]>([]);

  constructor(private router: Router, private location: Location) {

    this.router.events.subscribe({
      next: (event: any) => {
        if (event instanceof NavigationEnd) {

          const actualHistory = this.urlHistory$.getValue();
          if (actualHistory[actualHistory.length - 1] !== event.urlAfterRedirects) {
            actualHistory.push(event.urlAfterRedirects);
          }
          this.urlHistory$.next([...actualHistory]);
        }
      }
    });
  }

  /**
   * Returns the current url
   */
  getCurrentUrl(): string {
    return this.router.url;
  }

  /**
   * Navigates to the given path
   *
   * @param path where we want to go to
   */
  navigateToPath(path: string): Promise<boolean> {
    return this.router.navigateByUrl(path);
  }

  /**
   * Routes one page back. If we haven't got a history we don't want to go back (that would result in the
   * last visited website (e.g. google, ...)), so we simply route to our root url.
   */
  navigateBack(): void {
    const actualHistory = this.urlHistory$.getValue();
    actualHistory.pop();
    this.urlHistory$.next([...actualHistory]);

    if (actualHistory.length > 0) {
      this.location.back();
    } else {
      this.router.navigateByUrl('/')
    }
  }

  /**
   * Defines if it is possible to navigate back. That is only possible when our history contains more than one entry.
   */
  isNavigateBackPossible$(): Observable<boolean> {
    return this.urlHistory$
      .pipe(map((values: string[]) => values.length > 1));
  }
}
