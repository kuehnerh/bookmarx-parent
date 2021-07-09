import {Component} from '@angular/core';
import {MatTooltipDefaultOptions} from "@angular/material/tooltip";


/** Custom options the configure the tooltip's default show/hide delays. */
export const myCustomTooltipDefaults: MatTooltipDefaultOptions = {
  showDelay: 2000,
  hideDelay: 0,
  touchendHideDelay: 0,
};


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
}
