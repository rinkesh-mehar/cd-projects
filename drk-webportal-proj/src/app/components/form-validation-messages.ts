export class ErrorMessages {

  public static get textboxError(): string { return "Please fill out this field."; }
  public static get dropdownError(): string { return "Please select an item in the list."; }
  public static get checkboxError(): string { return "Please select one of the options."; }
  public static get invalidvalueError(): string { return "Please enter a valid value."; }
  public static get multiselectError(): string { return "Please select atleast one of the option."; }
  public static get calnderError(): string { return "Please select a date."; }
  public static get searchMessages(): string { return "No details found for your search."; }
  public static get noDataMessages(): string {return "No records pending for action."; }
  public static get compareNumberError(): string {return "Primary and alternate mobile number can not be same."; }
  public static get httpErrorResponseMessages(): string {return "There is an error. Please contact your system administrator."}
  public static get cropdataAPIErrorResponseMessages(): string {return "There is an error loading tile map. Please contact your system administrator."}
  public static get cropdatavillagesAPIErrorResponseMessages(): string {return "There is an error loading tile map villages. Please contact your system administrator."}
  public static get dataTableEmptyMessage(): string {return "No matching records found"}
  public static get bandNotFound(): string {return "Band Not Found. Please Try Again"}
}
