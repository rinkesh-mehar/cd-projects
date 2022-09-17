import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterPipe implements PipeTransform {

  transform(items: any[], keyword: any, properties: string[]): any[] {
    if (!items) return [];
    if (!keyword) return items;
    //debugger removed
    return items.filter(item => {
      var itemFound: Boolean;
      for (let i = 0; i < properties.length; i++) {

          let value = item[properties[i]] + "";
        if (value.toLowerCase().indexOf(keyword.toLowerCase()) !== -1) {
          itemFound = true;
          break;
        }
      }
      return itemFound;
    });

  }
  // transform(arr: any[], prop: string, value: string): any {
  //   if (arr) {
  //     if (!value) {
  //       return arr
  //     } else {
  //       return arr.filter(obj => obj[prop].toUpperCase().includes(value.toUpperCase()))
  //     }
  //   } else {
  //     return []
  //   }
  // }

}
