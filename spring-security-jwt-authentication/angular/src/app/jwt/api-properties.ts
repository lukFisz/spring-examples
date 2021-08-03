
export class ApiProperties {
  static URL = 'http://localhost:8080';
  static LOGIN_PREFIX = '/login';
  static API_PREFIX = '/api';
  static HEADER_PARAM_NAME = 'Authorization';
  static SESSION_TOKEN_NAME = '__jwtToken';

  static loginURL(): string {
    return this.URL + this.LOGIN_PREFIX;
  }

  static apiURL(): string {
    return this.URL + this.API_PREFIX;
  }
}
