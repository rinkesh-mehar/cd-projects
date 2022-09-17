import { FormControl } from '@angular/forms';

export function fileSizeValidator(files: FileList) {
  return function (control: FormControl) {
    const file = control.value;
    if (file) {
      const fileSize = files.item(0).size;
      if (fileSize >= 2097152)
        return { fileSizeValidator: true };
      else
        return null;
    }
    return null;
  };
}

export function fileSizeValidatorForDoc(files: FileList) {
  return function (control: FormControl) {
    const file = control.value;
    if (file) {
      const fileSize = files.item(0).size;
      if (fileSize >= 10485760)
        return { fileSizeValidatorForDoc: true };
      else
        return null;
    }
    return null;
  };
}