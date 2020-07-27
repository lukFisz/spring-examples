import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  private URL = 'http://localhost:8080';
  private HEADER_PARAM_NAME = 'Authorization';
  static SESSION_TOKEN_NAME = '__jwtToken';

  constructor(private httpclient: HttpClient) {  }

  public generateToken(request): JwtClientService {
    this.httpclient.post(
      this.URL + '/login',
      request,
      {observe: 'response'}
    ).subscribe(value => {
      localStorage.setItem(
        JwtClientService.SESSION_TOKEN_NAME,
        value.headers.get(this.HEADER_PARAM_NAME)
      );
    })
    return this;
  }

  public get(route): Observable<any> {
    return this.httpclient.get(this.URL + '/api' + route, {headers: this.getPreparedHeaders()});
  }

  public post(route, body = {}): Observable<any> {
    return this.httpclient.post(this.URL + '/api' + route, body, {headers: this.getPreparedHeaders()});
  }

  private getPreparedHeaders(): HttpHeaders {
    return new HttpHeaders().set(this.HEADER_PARAM_NAME, this.token());
  }

  token(): string {
    return localStorage.getItem(JwtClientService.SESSION_TOKEN_NAME)
  }

  destroy(): boolean {
    localStorage.removeItem(JwtClientService.SESSION_TOKEN_NAME);
    return localStorage.getItem(JwtClientService.SESSION_TOKEN_NAME) === null;
  }

}
