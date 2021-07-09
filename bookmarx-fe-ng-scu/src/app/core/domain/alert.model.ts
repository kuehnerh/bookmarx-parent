export interface Alert {
  type: AlertType;
  message: string;
}

export enum AlertType {
  SUCCESS,
  INFO,
  WARNING,
  ERROR
}
