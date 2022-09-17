import { VirtualMachines } from './virtual-machines';
export class PageVirtualMachines {
    content: VirtualMachines[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;
}
