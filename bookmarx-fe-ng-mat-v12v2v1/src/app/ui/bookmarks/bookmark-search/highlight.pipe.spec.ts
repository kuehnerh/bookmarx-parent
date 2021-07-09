import { HighlightPipe } from './highlight.pipe';
import {DomSanitizer} from "@angular/platform-browser";

describe('HighlightPipe', () => {
  it('create an instance', () => {
    const pipe = new HighlightPipe(null as any);
    expect(pipe).toBeTruthy();
  });
});
