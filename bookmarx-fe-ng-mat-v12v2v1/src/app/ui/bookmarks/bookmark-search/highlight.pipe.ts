import { Pipe, PipeTransform } from '@angular/core';
import {Bookmark} from "../../../core/domain/bookmark.model";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";

@Pipe({
  name: 'highlight'
})
export class HighlightPipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer) {
  }

  transform(value: Bookmark, textToHighlight: string): SafeHtml {

    let result = value.title + ' - ' + value.url;

    const pattern = textToHighlight
      .replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&")
      .split(' ')
      .filter(t => t.length > 0)
      .join('|');
    console.log('Pattern:' + pattern);
    const regex = new RegExp(pattern, 'gi');



    result = textToHighlight ? result.replace(regex, match => `<span style="color: red; font-weight: bold">${match}</span>`) : result;
    return this.sanitizer.bypassSecurityTrustHtml(result);
  }

}
