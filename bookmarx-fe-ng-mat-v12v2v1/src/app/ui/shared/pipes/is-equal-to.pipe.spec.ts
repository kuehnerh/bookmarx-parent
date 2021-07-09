import {IsEqualToPipe} from './is-equal-to.pipe';

describe('IsEqualToPipe', () => {

  it('should create an instance', () => {
    const pipe = new IsEqualToPipe();
    expect(pipe).toBeTruthy();
  });

  it('should return true if value and argument are the same', () => {
    const pipe = new IsEqualToPipe();

    const result = pipe.transform('test', 'test');

    expect(result).toBeTrue();
  });

  it('should return false if value and argument are the different', () => {
    const pipe = new IsEqualToPipe();

    const result = pipe.transform('test', 'hallo');

    expect(result).toBeFalse();
  });

});
