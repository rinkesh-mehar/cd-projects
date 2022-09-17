import {MatCheckboxChange} from '@angular/material';
import {Component, Input, ViewEncapsulation} from '@angular/core';
import {FormControl} from '@angular/forms';

@Component({
    selector: 'app-select-check-all',
    templateUrl: './select-check-all.component.html',
    styleUrls: ['./select-check-all.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class SelectCheckAllComponent {
    @Input() model: FormControl;
    @Input() values = [];
    @Input() text = 'Select All';

    isChecked(): boolean {
        return this.model.value && this.values.length
            && this.model.value.length === this.values.length;
    }

    isIndeterminate(): boolean {
        return this.model.value && this.values.length && this.model.value.length
            && this.model.value.length < this.values.length;
    }

    toggleSelection(change: MatCheckboxChange): void {
        if (change.checked) {
            this.model.setValue(this.values);
        } else {
            this.model.setValue([]);
        }
    }
}