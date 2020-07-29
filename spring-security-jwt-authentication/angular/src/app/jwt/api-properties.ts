
export class ApiProperties {
  static URL: string = 'http://localhost:8080';
  static LOGIN_PREFIX: string = '/login'
  static API_PREFIX: string = '/api'
  static HEADER_PARAM_NAME: string = 'Authorization';
  static SESSION_TOKEN_NAME: string = '__jwtToken';

  static loginURL(): string {
    return this.URL + this.LOGIN_PREFIX;
  }

  static apiURL(): string {
    return this.URL + this.API_PREFIX;
  }
}
