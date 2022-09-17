import { VirtualMachinesConfiguration } from './virtual-machines-configuration';
export class PageVirtualMachinesConfiguration {
    content: VirtualMachinesConfiguration[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number ;
    first: boolean ;
    sort: string ;
    numberOfElements: number ;
}
