import { AbstractControl } from '@angular/forms';

export function urlValidator(control: AbstractControl) {
  if (control.value.substr(0,8) !== 'https://') {
    return { urlValid: true };
  }
  return null;
}