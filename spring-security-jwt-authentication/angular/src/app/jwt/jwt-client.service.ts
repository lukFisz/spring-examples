import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ApiProperties} from "./api-properties";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  constructor(private httpclient: HttpClient) {
  }

  public generateToken(credentials, success = () => {}, error = (e) => {}) {
    return this.httpclient.post(
      ApiProperties.loginURL(),
      credentials,
      {observe: 'response'}
    ).subscribe(value => {
      localStorage.setItem(
        ApiProperties.SESSION_TOKEN_NAME,
        value.headers.get(ApiProperties.HEADER_PARAM_NAME)
      );
      success()
    }, e => error(e));
  }

  get(route): Observable<any> {
    return this.httpclient.get(ApiProperties.apiURL() + route, {headers: this.getPreparedHeaders()});
  }

  post(route, body = {}): Observable<any> {
    return this.httpclient.post(ApiProperties.apiURL() + route, body, {headers: this.getPreparedHeaders()});
  }

  getPreparedHeaders(): HttpHeaders {
    if (this.isTokenStored())
      return new HttpHeaders().set(ApiProperties.HEADER_PARAM_NAME, this.token());
    else
      return new HttpHeaders();
  }

  token(): string {
    return localStorage.getItem(ApiProperties.SESSION_TOKEN_NAME)
  }

  isTokenStored(): boolean {
    return localStorage.getItem(ApiProperties.SESSION_TOKEN_NAME) !== null
  }

  destroy(): boolean {
    localStorage.removeItem(ApiProperties.SESSION_TOKEN_NAME);
    return !this.isTokenStored();
  }

}
