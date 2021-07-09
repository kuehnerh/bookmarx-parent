import {Pipe, PipeTransform} from '@angular/core';

/**
 * Pipe that checks if the values are equal or not
 */
@Pipe({
  name: 'isEqualTo'
})
export class IsEqualToPipe implements PipeTransform {

  /**
   * Checks if the both parameters are the same or not
   *
   * @param value First value
   * @param arg Second value
   */
  transform(value: any, arg: any): boolean {
    return value === arg;
  }

}
