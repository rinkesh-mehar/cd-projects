// Angular
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SearchFilterPipe } from './search-filter.pipe';

@NgModule({
  imports: [CommonModule],
  declarations: [SearchFilterPipe],
  exports: [SearchFilterPipe]
})
export class PipeModule { }
