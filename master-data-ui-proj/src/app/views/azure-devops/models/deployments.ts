export class Deployments {
    id: number;
    vmID: number;
    vmName: string;
    applicationName: string;
    applicationInternalPort: number;
    applicationExternalPort: number;
    dockerIP: string;
    commandScript: string; 
}
