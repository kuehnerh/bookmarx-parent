import {Pipe, PipeTransform} from '@angular/core';

/**
 * Pipe that checks if the values are equal or not
 */
@Pipe({
  name: 'startsWith'
})
export class StartsWithPipe implements PipeTransform {

  /**
   * Checks if the both parameters are the same or not
   *
   * @param value First value
   * @param arg Second value
   */
  transform(str: any, prefix: any): boolean {
    return String(str).startsWith(prefix);
  }

}
